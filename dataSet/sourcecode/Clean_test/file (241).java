/*
 * Copyright 2023 AntGroup CO., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.runtime.core.scheduler.io;

import com.antgroup.geaflow.cluster.response.IResult;
import com.antgroup.geaflow.cluster.response.ShardResult;
import com.antgroup.geaflow.core.graph.ExecutionEdge;
import com.antgroup.geaflow.core.graph.ExecutionVertex;
import com.antgroup.geaflow.core.graph.ExecutionVertexGroup;
import com.antgroup.geaflow.shuffle.message.Shard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataExchanger {

    private static ThreadLocal<Map<Integer, Map<Integer, List<Shard>>>> taskInputEdgeShards = ThreadLocal.withInitial(HashMap::new);

    /**
     * Build task input for execution vertex.
     * @return key: taskIndex
     *         value: list of input shards
     */
    public static Map<Integer, List<Shard>> buildInput(ExecutionVertex vertex,
                                                       ExecutionEdge inputEdge,
                                                       CycleResultManager resultManager) {

        if (taskInputEdgeShards.get().containsKey(inputEdge.getEdgeId())) {
            return taskInputEdgeShards.get().get(inputEdge.getEdgeId());
        }
        Map<Integer, List<Shard>> result = new HashMap<>();

        int edgeId = inputEdge.getEdgeId();
        List<IResult> eventResults = resultManager.get(edgeId);
        Map<Integer, Shard> taskIdToInputShard = new HashMap<>();
        for (IResult eventResult : eventResults) {
            ShardResult shard = (ShardResult) eventResult;
            for (int i = 0; i < shard.getResponse().size(); i++) {
                int index = i % vertex.getParallelism();
                if (!taskIdToInputShard.containsKey(index)) {
                    taskIdToInputShard.put(index, new Shard(shard.getId(), new ArrayList<>()));
                }
                taskIdToInputShard.get(index).getSlices().add(shard.getResponse().get(i));
            }
        }
        for (Map.Entry<Integer, Shard> entry : taskIdToInputShard.entrySet()) {
            if (!result.containsKey(entry.getKey())) {
                result.put(entry.getKey(), new ArrayList<>());
            }
            result.get(entry.getKey()).add(entry.getValue());
        }
        taskInputEdgeShards.get().put(inputEdge.getEdgeId(), result);
        return result;
    }

    private static boolean needRepartition(ExecutionVertex vertex,
                                           ExecutionVertexGroup group) {

        List<Integer> parentIds = vertex.getParentVertexIds();
        if (parentIds != null && !parentIds.isEmpty()) {
            if (parentIds.size() > 1) {
                return true;
            }

            Integer parentId = parentIds.get(0);
            ExecutionVertex parentVertex = group.getVertexMap().get(parentId);
            if (parentVertex.isRepartition()) {
                return true;
            }

            // TODO Only parent vertex partition number greater than current vertex parallelism.
            if (parentVertex.getNumPartitions() != vertex.getParallelism()) {
                return true;
            }
        }
        return false;
    }

    public static void clear() {
        taskInputEdgeShards.get().clear();
    }
}
