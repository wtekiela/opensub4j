/**
 *    Copyright (c) 2015 Wojciech Tekiela
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.wtekiela.opensub4j.parser;

import com.github.wtekiela.opensub4j.response.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ResponseParserImpl implements ResponseParser {

    private static final Logger log = LoggerFactory.getLogger(ResponseParserImpl.class);

    @SuppressWarnings("unchecked") 
    public <T, S extends ResponseObjectBuilder<T>> T parse(S builder, Object response) {
        if (!(response instanceof Map)) {
            return builder.build();
        }

        Map<String, Reply> responseMap = (Map) response;
        for (Map.Entry<String, Reply> entry : responseMap.entrySet()) {
            String key = entry.getKey();
            String setterName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
            Class clazz = null;
            try {
                clazz = getObjectClass(entry.getValue());
                Method setter = builder.getClass().getMethod(setterName, clazz);
                setter.invoke(builder, entry.getValue());
            } catch (InvocationTargetException | IllegalAccessException e) {
                log.debug("Unable to set value:" + entry.getValue() + " for method:" + setterName);
            } catch (NoSuchMethodException ignore) {
                log.trace("No such method for builder: " + builder.getClass().getName() + ", setter:" + setterName + ", class:" + clazz);
            }
        }

        return builder.build();
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
