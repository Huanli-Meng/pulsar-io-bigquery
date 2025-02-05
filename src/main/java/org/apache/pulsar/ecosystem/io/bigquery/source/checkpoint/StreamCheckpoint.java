/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pulsar.ecosystem.io.bigquery.source.checkpoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The stream checkpoint position.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreamCheckpoint {
    protected static final String CheckpointStreamKeyFormat = "checkpoint-stream:%s";
    private String stream;
    private long offset;
    private StateType stateType;

    public StreamCheckpoint(String stream) {
        this.stream = stream;
        this.offset = 0;
        this.stateType = StateType.READING;
    }

    public static String getCheckpointStreamKeyFormat(String stream) {
        return String.format(CheckpointStreamKeyFormat, stream);
    }

    public void updateOffset(long offset) {
        if (offset > this.offset) {
            this.offset = offset;
        }
    }
}
