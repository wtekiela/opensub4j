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
