/**
 * Copyright (c) 2021 Wojciech Tekiela
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

import java.util.Arrays;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryableXmlRpcClient extends XmlRpcClient {

    /**
     * default maximum attempts
     */
    public static final int DEFAULT_MAX_ATTEMPTS = 5;

    /**
     * default interval between attempts in ms
     */
    public static final int DEFAULT_INTERVAL_MILLIS = 1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryableXmlRpcClient.class);

    private final int maxAttempts;
    private final long intervalMillis;

    /**
     * Decorator for {@link XmlRpcClient} that introduces a retry mechanism.
     * Uses default of 5 max attempts and 1s interval in between.
     */
    public RetryableXmlRpcClient() {
        this(DEFAULT_MAX_ATTEMPTS, DEFAULT_INTERVAL_MILLIS);
    }

    /**
     * Decorator for {@link XmlRpcClient} that introduces a retry mechanism.
     *
     * @param maxAttempts    maximum attempts
     * @param intervalMillis interval between attempts (in ms)
     */
    public RetryableXmlRpcClient(int maxAttempts, int intervalMillis) {
        super();
        this.maxAttempts = maxAttempts;
        this.intervalMillis = intervalMillis;
    }

    /**
     * Decorator for {@link XmlRpcClient} that introduces a retry mechanism.
     * Uses default of 5 max attempts and 1s interval in between.
     *
     * @param xmlRpcClientConfig xml-rpc client configuration
     */
    public RetryableXmlRpcClient(XmlRpcClientConfig xmlRpcClientConfig) {
        this(xmlRpcClientConfig, DEFAULT_MAX_ATTEMPTS, DEFAULT_INTERVAL_MILLIS);
    }

    /**
     * Decorator for {@link XmlRpcClient} that introduces a retry mechanism.
     *
     * @param xmlRpcClientConfig xml-rpc client configuration
     * @param maxAttempts        maximum attempts
     * @param intervalMillis     interval between attempts (in ms)
     */
    public RetryableXmlRpcClient(XmlRpcClientConfig xmlRpcClientConfig, int maxAttempts, int intervalMillis) {
        this(maxAttempts, intervalMillis);
        setConfig(xmlRpcClientConfig);
    }

    @Override
    public Object execute(final String method, final Object[] params) throws XmlRpcException {
        try {
            RetryableCallable retryableCallable = new RetryableCallable(
                maxAttempts,
                intervalMillis,
                () -> {
                    String paramsAsString = Arrays.deepToString(params);
                    LOGGER.debug("Calling method: {}, with params: {}", method, paramsAsString);
                    Object response = RetryableXmlRpcClient.super.execute(method, params);
                    LOGGER.debug("Response: {}", response);
                    return response;
                }
            );
            return retryableCallable.call();
        } catch (Exception e) {
            throw new XmlRpcException("Exception occurred during XML-RPC call", e);
        }
    }
}
