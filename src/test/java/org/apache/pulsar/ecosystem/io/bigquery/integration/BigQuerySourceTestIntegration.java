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
package org.apache.pulsar.ecosystem.io.bigquery.integration;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.TimeUnit;
import lombok.Cleanup;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.schema.GenericRecord;
import org.junit.Test;

/**
 * BigQuery source integration test.
 */
public class BigQuerySourceTestIntegration {
    private static final String topic = "source-test-topic";
    private static final String subscription = "source-test-topic-sub";

    @Test
    public void testDeliverResult() throws Exception {

        @Cleanup
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build();

        @Cleanup
        Consumer<GenericRecord> consumer =
                pulsarClient.newConsumer(Schema.AUTO_CONSUME()).topic(topic).subscriptionName(subscription)
                        .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest).subscribe();
        int messageCount = 0;
        while (true) {
            Message<GenericRecord> message = consumer.receive(10, TimeUnit.SECONDS);
            if (message == null) {
                break;
            }
            messageCount++;
            consumer.acknowledge(message);
        }
        assertEquals(10, messageCount);
    }
}
