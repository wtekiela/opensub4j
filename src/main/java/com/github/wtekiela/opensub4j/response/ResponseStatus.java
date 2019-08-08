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
package com.github.wtekiela.opensub4j.response;

/**
 * Class representing HTTP response status
 *
 * IMPORTANT: only http code is taken into account for equals!
 */
public class ResponseStatus {

    public static final ResponseStatus OK = new ResponseStatus(200, "OK");
    public static final ResponseStatus UNKNOWN_USER_AGENT = new ResponseStatus(414, "Unknown User Agent");

    private final int code;
    private final String message;

    /**
     * Needed for ResponseParser
     *
     * @param value concatenated value of http code and message separated by space
     * @return ResponseStatus created instance
     */
    public static ResponseStatus fromString(String value) {
        String[] parts = value.split(" ", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Value must contain code and message separated by space!");
        }
        int code = Integer.valueOf(parts[0]);
        String message = parts[1];
        return new ResponseStatus(code, message);
    }

    public ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseStatus that = (ResponseStatus) o;

        return code == that.code;
    }

    @Override
    public String toString() {
        return code + ": \"" + message + "\"";
    }
}
