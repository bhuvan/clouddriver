/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.clouddriver.elasticsearch.events;

import com.netflix.spinnaker.clouddriver.elasticsearch.ElasticSearchServerGroupTagger;
import com.netflix.spinnaker.clouddriver.orchestration.events.DeleteServerGroupEvent;
import com.netflix.spinnaker.clouddriver.orchestration.events.OperationEvent;
import com.netflix.spinnaker.clouddriver.orchestration.events.OperationEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteServerGroupEventHandler implements OperationEventHandler {
  private final ElasticSearchServerGroupTagger serverGroupTagger;

  @Autowired
  public DeleteServerGroupEventHandler(ElasticSearchServerGroupTagger serverGroupTagger) {
    this.serverGroupTagger = serverGroupTagger;
  }

  @Override
  public void handle(OperationEvent operationEvent) {
    if (!(operationEvent instanceof DeleteServerGroupEvent)) {
      return;
    }

    DeleteServerGroupEvent deleteServerGroupEvent = (DeleteServerGroupEvent) operationEvent;
    serverGroupTagger.deleteAll(
      deleteServerGroupEvent.getCloudProvider(),
      deleteServerGroupEvent.getAccountId(),
      deleteServerGroupEvent.getRegion(),
      deleteServerGroupEvent.getName()
    );
  }
}