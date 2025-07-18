/*
 * Copyright (c) 2023 OceanBase.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.databasechange.model;

import java.io.Serializable;

import com.oceanbase.odc.core.shared.constant.ConnectType;
import com.oceanbase.odc.service.connection.model.ConnectionConfig;
import com.oceanbase.odc.service.connection.model.OBInstanceType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DatabaseChangeConnection implements Serializable {

    private static final long serialVersionUID = -5013749083390365604L;
    private Long id;
    private String name;
    private String tenantName;
    private String clusterName;
    private ConnectType type;
    private OBInstanceType instanceType;

    public DatabaseChangeConnection(ConnectionConfig connectionConfig) {
        if (connectionConfig != null) {
            this.id = connectionConfig.getId();
            this.name = connectionConfig.getName();
            this.type = connectionConfig.getType();
            this.instanceType = connectionConfig.getInstanceType();
            this.tenantName = connectionConfig.getTenantName();
            this.clusterName = connectionConfig.getClusterName();
        }
    }

}
