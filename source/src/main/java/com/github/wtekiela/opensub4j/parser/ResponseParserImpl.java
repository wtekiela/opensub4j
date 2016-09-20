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
package com.github.wtekiela.opensub4j.parser;

import java.util.Map;

import com.github.wtekiela.opensub4j.response.Reply;

public class ResponseParserImpl implements ResponseParser {

    public <T, S extends ResponseObjectBuilder<T>> T parse(S builder, Object response) {
        return response instanceof Map ? parseMap(builder, (Map<String, Reply>) response) : builder.build();
    }

    private <T, S extends ResponseObjectBuilder<T>> T parseMap(S builder, Map<String, Reply> response) {
        for (Map.Entry<String, Reply> entry : response.entrySet()) {
            String method = buildSetterName(entry.getKey());
            Class valueType = getObjectClass(entry.getValue());
            builder.set(method, entry.getValue(), valueType);
        }
        return builder.build();
    }

    private String buildSetterName(String key) {
        return "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    private Class getObjectClass(Object o) {
        if (o instanceof String) {
            return String.class;
        } else if (o instanceof Byte) {
            return Byte.class;
        } else if (o instanceof Short) {
            return Short.class;
        } else if (o instanceof Integer) {
            return Integer.class;
        } else if (o instanceof Long) {
            return Long.class;
        } else if (o instanceof Boolean) {
            return Boolean.class;
        } else if (o instanceof Float) {
            return Float.class;
        } else if (o instanceof Double) {
            return Double.class;
        } else if (o instanceof Map) {
            return Map.class;
        } else if (o instanceof Map[]) {
            return Map[].class;
        } else if (o instanceof Object[]) {
            return Object[].class;
        } else {
            return Object.class;
        }
    }

}
