package org.jboss.pnc.scheduler.core.tasks;

import org.jboss.pnc.scheduler.core.RemoteEntityClient;
import org.jboss.pnc.scheduler.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.spi.CDI;
import javax.transaction.TransactionManager;

public class AsyncInvokeStartJob extends SynchronizedAsyncControllerJob {

    private final RemoteEntityClient client;

    private final Task task;

    private static final Logger logger = LoggerFactory.getLogger(AsyncInvokeStartJob.class);

    public AsyncInvokeStartJob(TransactionManager tm, Task task) {
        super(tm);
        this.task = task;
        this.client = CDI.current().select(RemoteEntityClient.class).get();
    }

    @Override
    boolean execute() {
        logger.info("Invoking StartJob for " + task.getName());
        client.startJob(task);
        return true;
    }
}