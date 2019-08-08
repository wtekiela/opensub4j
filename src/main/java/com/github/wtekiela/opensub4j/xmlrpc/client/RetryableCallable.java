package com.github.wtekiela.opensub4j.xmlrpc.client;

import org.apache.xmlrpc.XmlRpcException;

import java.util.concurrent.Callable;

class RetryableCallable implements Callable<Object> {

    private final Callable<Object> task;

    private final int maxAttempts;
    private final long intervalMillis;

    RetryableCallable(int maxAttempts, long intervalMillis, Callable<Object> task) {
        this.maxAttempts = maxAttempts;
        this.intervalMillis = intervalMillis;
        this.task = task;
    }

    @Override
    public Object call() throws Exception {
        int attemptsLeft = maxAttempts;
        while (true) {
            try {
                attemptsLeft--;
                return task.call();
            } catch (Exception e) {
                if (attemptsLeft > 0) {
                    Thread.sleep(intervalMillis);
                } else {
                    throw e;
                }
            }
        }
    }
}
