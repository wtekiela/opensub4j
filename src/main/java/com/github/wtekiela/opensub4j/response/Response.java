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

public class Response {

    @OpenSubtitlesApiSpec(fieldName = "status")
    private ResponseStatus status;

    @OpenSubtitlesApiSpec(fieldName = "seconds")
    private double seconds;

    public ResponseStatus getStatus() {
        return status;
    }

    public double getSeconds() {
        return seconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, seconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Response response = (Response) o;
        return Double.compare(response.seconds, seconds) == 0 &&
            Objects.equals(status, response.status);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Response.class.getSimpleName() + "[", "]")
            .add("status=" + status)
            .add("seconds=" + seconds)
            .toString();
    }
}
