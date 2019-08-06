/**
 * Copyright (c) 2019 Wojciech Tekiela
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.github.wtekiela.opensub4j.xmlrpc.client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class RetriableXmlRpcClient extends XmlRpcClient {

    public static final int DEFAULT_MAX_ATTEMPTS = 5;
    public static final int DEFAULT_INTERVAL = 1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(RetriableXmlRpcClient.class);

    private final int maxAttempts;
    private final long interval;

    public RetriableXmlRpcClient(XmlRpcClientConfig xmlRpcClientConfig) {
        this(xmlRpcClientConfig, DEFAULT_MAX_ATTEMPTS, DEFAULT_INTERVAL);
    }

    public RetriableXmlRpcClient(XmlRpcClientConfig xmlRpcClientConfig, int maxAttempts, int interval) {
        super();

        this.maxAttempts = maxAttempts;
        this.interval = interval;

        setConfig(xmlRpcClientConfig);
    }

    @Override
    public Object execute(final String method, final Object[] params) throws XmlRpcException {
        RetryTask execTask = new RetryTask(maxAttempts, interval, method, params);
        try {
            return execTask.call();
        } catch (Exception e) {
            throw new XmlRpcException("Exception occurred during XML-RPC call", e);
        }
    }

    private class RetryTask implements Callable<Object> {

        private final Callable<Object> task;

        private final int maxAttempts;
        private final long interval;

        private RetryTask(int maxAttempts, long interval, final String method, final Object[] params) {
            this.maxAttempts = maxAttempts;
            this.interval = interval;
            this.task = () -> {
                LOGGER.debug("Calling method: {}, with params: {}", method, Arrays.deepToString(params));
                Object response = RetriableXmlRpcClient.super.execute(method, params);
                LOGGER.debug("Response: {}", response);
                return response;
            };
        }

        @Override
        public Object call() throws Exception {
            int attemptsLeft = maxAttempts;
            while (true) {
                try {
                    attemptsLeft--;
                    return task.call();
                } catch (XmlRpcException e) {
                    if (attemptsLeft > 0) {
                        Thread.sleep(interval);
                    } else {
                        throw e;
                    }
                }
            }
        }
    }
}
