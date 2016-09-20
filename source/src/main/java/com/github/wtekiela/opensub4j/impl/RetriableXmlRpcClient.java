/**
 * Copyright (c) 2016 Wojciech Tekiela
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
package com.github.wtekiela.opensub4j.impl;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Callable;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RetriableXmlRpcClient extends XmlRpcClient {

    private final Logger logger = LoggerFactory.getLogger(RetriableXmlRpcClient.class);

    private final int maxAttempts;
    private final long interval;

    public RetriableXmlRpcClient(URL serverUrl) {
        this(serverUrl, 5, 1000);
    }

    public RetriableXmlRpcClient(URL serverUrl, int maxAttempts, long interval) {
        super();

        this.maxAttempts = maxAttempts;
        this.interval = interval;

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(serverUrl);
        setConfig(config);
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
                logger.debug("Calling method: {}, with params: {}", method, Arrays.deepToString(params));
                Object response = RetriableXmlRpcClient.super.execute(method, params);
                logger.debug("Response: {}", response);
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
