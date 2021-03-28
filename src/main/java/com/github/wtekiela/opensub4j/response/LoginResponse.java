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
package com.github.wtekiela.opensub4j.response;

import java.util.Objects;
import java.util.StringJoiner;

public class LoginResponse extends Response {

    @OpenSubtitlesApiSpec(fieldName = "token")
    private String token;

    public String getToken() {
        return token;
    }

    @OpenSubtitlesApiSpec(fieldName = "data")
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginResponse that = (LoginResponse) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LoginResponse.class.getSimpleName() + "[", "]")
            .add("token='" + token + "'")
            .add("userInfo=" + userInfo)
            .toString();
    }
}
