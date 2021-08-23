/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2021-2021 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.rex.core.endpoints;

import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.jboss.pnc.rex.common.enums.Method;
import org.jboss.pnc.rex.core.GenericVertxHttpClient;
import org.jboss.pnc.rex.core.counter.Counter;
import org.jboss.pnc.rex.core.counter.Running;
import org.jboss.pnc.rex.dto.requests.FinishRequest;
import org.jboss.pnc.rex.model.Header;
import org.jboss.pnc.rex.model.requests.StartRequest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Path("/test")
public class HttpEndpoint {

    @Inject
    GenericVertxHttpClient client;

    @Inject
    ManagedExecutor executor;

    @Inject
    @Running
    Counter running;

    private final Queue<Long> record = new ConcurrentLinkedQueue<>();

    private boolean shouldRecord = false;

    @POST
    @Path("/accept")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response accept(String request) {
        record();
        return Response.ok().build();
    }

    @POST
    @Path("/stop")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response stop(String request) {
        record();
        return Response.ok().build();
    }

    @POST
    @Path("/acceptAndStart")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response acceptAndStart(StartRequest request) {
        record();
        executor.submit(() -> finishTask(request));
        return Response.ok("{\"task\": \"" + request.getPayload() + "\"}").build();
    }

    private void finishTask(StartRequest request) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            //ignore
        }
        FinishRequest body = new FinishRequest(true, "ALL IS OK");
        client.makeRequest(URI.create(request.getCallback()),
                Method.POST,
                List.of(Header.builder().name("Content-Type").value("application/json").build()),
                body,
                this::onResponse,
                throwable -> log.error("Couldn't reach local scheduler.", throwable));
    }

    private void onResponse(HttpResponse<Buffer> response) {
        if (response.statusCode() < 400) {
            log.info("Callback to scheduler positive!");
        } else {
            log.warn("Callback to scheduler negative! " + response.body().toString());
        }
    }

    private void record() {
        if (shouldRecord) {
            record.offer(running.getValue());
        }
    }

    public void startRecordingQueue() {
        record.clear();
        shouldRecord = true;
    }

    public Collection<Long> stopRecording() {
        shouldRecord = false;
        return record;
    }
}