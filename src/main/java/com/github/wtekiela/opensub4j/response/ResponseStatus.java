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

/**
 * Class representing HTTP response status
 *
 * IMPORTANT: only http code is taken into account for equals!
 */
public class ResponseStatus {

    // 2xx
    public static final ResponseStatus OK = new ResponseStatus(200, "OK");
    public static final ResponseStatus PARTIAL_CONTENT = new ResponseStatus(206, "Partial content");
    // 3xx
    public static final ResponseStatus MOVED = new ResponseStatus(301, "Moved (host)");
    // 4xx
    public static final ResponseStatus UNAUTHORIZED = new ResponseStatus(401, "Unauthorized");
    public static final ResponseStatus SUBTITLE_INVALID_FORMAT =
        new ResponseStatus(402, "Subtitles has invalid format");
    public static final ResponseStatus SUBTITLE_HASH_NOT_SAME =
        new ResponseStatus(403, "SubHashes (content and sent subhash) are not same!");
    public static final ResponseStatus SUBTITLE_INVALID_LANGUAGE =
        new ResponseStatus(404, "Subtitles has invalid language!");
    public static final ResponseStatus NOT_ALL_MANDATORY_PARAMS_SPECIFIED =
        new ResponseStatus(405, "Not all mandatory parameters was specified");
    public static final ResponseStatus NO_SESSION = new ResponseStatus(406, "No session");
    public static final ResponseStatus DOWNLOAD_LIMIT_REACHED = new ResponseStatus(407, "Download limit reached");
    public static final ResponseStatus INVALID_PARAMETERS = new ResponseStatus(408, "Invalid parameters");
    public static final ResponseStatus METHOD_NOT_FOUND = new ResponseStatus(409, "Method not found");
    public static final ResponseStatus OTHER_UNKNOWN_ERROR = new ResponseStatus(410, "Other or unknown error");
    public static final ResponseStatus INVALID_USER_AGENT = new ResponseStatus(411, "Empty or invalid useragent");
    public static final ResponseStatus S_INVALID_FORMAT = new ResponseStatus(412, "%s has invalid format (reason)");
    public static final ResponseStatus INVALID_IMDB_ID = new ResponseStatus(413, "Invalid ImdbID");
    public static final ResponseStatus UNKNOWN_USER_AGENT = new ResponseStatus(414, "Unknown User Agent");
    public static final ResponseStatus DISABLED_USER_AGENT = new ResponseStatus(415, "Disabled user agent");
    public static final ResponseStatus INTERNAL_SUBTITLE_VALIDATION_FAILED =
        new ResponseStatus(416, "Internal subtitle validation failed");
    // 5XX
    public static final ResponseStatus SERVICE_UNAVAILABLE = new ResponseStatus(503, "Service Unavailable");
    public static final ResponseStatus MAINTENANCE = new ResponseStatus(506, "Server under maintenance");
    // others
    public static final ResponseStatus PARSE_ERROR = new ResponseStatus(0, "parse error. not well formed");

    private final int code;
    private final String message;

    public ResponseStatus(int code) {
        this.code = code;
        this.message = "";
    }

    public ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Needed for ResponseParser
     *
     * @param value concatenated value of http code and message separated by space
     *
     * @return ResponseStatus created instance
     */
    public static ResponseStatus fromString(String value) {
        if (value.equals("parse error. not well formed")) {
            return PARSE_ERROR;
        }
        String[] parts = value.split(" ", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Value must contain code and message separated by space!");
        }
        int code = Integer.parseInt(parts[0]);
        String message = parts[1];
        return new ResponseStatus(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseStatus that = (ResponseStatus) o;
        return code == that.code;
    }

    @Override
    public String toString() {
        return code + ": \"" + message + "\"";
    }
}
