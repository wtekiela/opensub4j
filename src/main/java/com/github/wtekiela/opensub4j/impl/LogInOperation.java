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
package com.github.wtekiela.opensub4j.impl;

import com.github.wtekiela.opensub4j.response.LoginResponse;

class LogInOperation extends AbstractOperation<LoginResponse> {

    private final String user;
    private final String pass;
    private final String lang;
    private final String useragent;

    LogInOperation(String user, String pass, String lang, String useragent) {
        this.user = user;
        this.pass = pass;
        this.lang = lang;
        this.useragent = useragent;
    }

    @Override
    String getMethodName() {
        return "LogIn";
    }

    @Override
    Object[] getParams() {
        return new Object[] {user, pass, lang, useragent};
    }

    @Override
    LoginResponse getResponseObject() {
        return new LoginResponse();
    }

}
