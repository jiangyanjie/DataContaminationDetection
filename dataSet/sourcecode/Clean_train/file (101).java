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

package com.antgroup.geaflow.shuffle.api.pipeline.buffer;

import com.antgroup.geaflow.shuffle.memory.ShuffleMemoryTracker;

public abstract class AbstractBuffer implements OutBuffer {

    protected int refCount;
    protected boolean memoryTrack;

    public AbstractBuffer() {
        this.memoryTrack = false;
    }

    public AbstractBuffer(boolean memoryTrack) {
        this.memoryTrack = memoryTrack;
    }

    @Override
    public void setRefCount(int refCount) {
        this.refCount = refCount;
    }

    @Override
    public boolean isDisposable() {
        return refCount <= 0;
    }

    @Override
    public boolean isMemoryTracking() {
        return memoryTrack;
    }

    protected void requireMemory(long dataSize) {
        if (memoryTrack) {
            ShuffleMemoryTracker.getInstance().requireMemory(dataSize);
        }
    }

    protected void releaseMemory(long dataSize) {
        if (memoryTrack) {
            ShuffleMemoryTracker.getInstance().releaseMemory(dataSize);
        }
    }

    protected abstract static class AbstractBufferBuilder implements BufferBuilder {

        protected int batchCount;
        protected boolean memoryTrack;

        public void enableMemoryTrack() {
            this.memoryTrack = true;
        }
    }

}
