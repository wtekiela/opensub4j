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
package com.github.wtekiela.opensub4j.impl;

import com.github.wtekiela.opensub4j.response.OpenSubtitlesApiSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

class ResponseParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseParser.class);

    private static final String LIST_DATA_KEY = "data";

    <T> List<T> bindList(Class<T> clazz, Map<String, Object[]> response) throws ReflectiveOperationException {
        Object[] rawData = response.get(LIST_DATA_KEY);
        List<T> list = new ArrayList<>(rawData.length);
        for (Object obj : rawData) {
            list.add(bind(clazz.getDeclaredConstructor().newInstance(), (Map) obj));
        }
        return list;
    }

    <T> T bind(T instance, Map response) {
        if (instance == null) {
            return null;
        }
        Set<Field> declaredFields = getAllClassFields(instance.getClass());
        for (Field field : declaredFields) {
            bindField(instance, response, field);
        }
        return instance;
    }

    private static Set<Field> getAllClassFields(Class type) {
        Set<Field> fields = new HashSet<>();
        Class currentType = type;

        do {
            fields.addAll(Arrays.asList(currentType.getDeclaredFields()));
            currentType = currentType.getSuperclass();
        } while (currentType != null);

        return fields;
    }

    private <T> void bindField(T instance, Map response, Field field) {
        ensureFieldIsAccessible(field);
        OpenSubtitlesApiSpec annotation = field.getAnnotation(OpenSubtitlesApiSpec.class);
        if (annotation != null) {
            executeFieldBinding(instance, response, field, annotation);
        }
    }

    private <T> void executeFieldBinding(T instance, Map response, Field field, OpenSubtitlesApiSpec annotation) {
        Object value = response.get(annotation.fieldName());
        if (value == null) {
            return;
        }

        Class<?> source = value.getClass();
        Class<?> target = field.getType();

        if (needsStringConversion(target, source)) {
            value = parse(target, (String) value);
        }
        try {
            set(target, value, instance, field);
        } catch (IllegalAccessException e) {
            LOGGER.warn("Illegal access while binding field in response object", e) ;
        }
    }

    private boolean needsStringConversion(Class<?> target, Class<?> source) {
        return target != String.class && source == String.class;
    }

    @SuppressWarnings("squid:S3011")
    private void ensureFieldIsAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    @SuppressWarnings("squid:S3776")
    private Object parse(Class target, String value) {
        if (Boolean.class == target || Boolean.TYPE == target) {
            return Boolean.parseBoolean(value);
        } else if (Byte.class == target || Byte.TYPE == target) {
            return Byte.parseByte(value);
        } else if (Character.class == target || Character.TYPE == target) {
            return value.charAt(0);
        } else if (Short.class == target || Short.TYPE == target) {
            return Short.parseShort(value);
        } else if (Integer.class == target || Integer.TYPE == target) {
            return Integer.parseInt(value);
        } else if (Long.class == target || Long.TYPE == target) {
            return Long.parseLong(value);
        } else if (Float.class == target || Float.TYPE == target) {
            return Float.parseFloat(value);
        } else if (Double.class == target || Double.TYPE == target) {
            return Double.parseDouble(value);
        } else {
            return customObjectFromString(target, value);
        }
    }

    private Object customObjectFromString(Class target, String value) {
        try {
            return target.getMethod("fromString", String.class).invoke(null, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.warn("Exception while getting method fromString", e);
            return value;
        }
    }

    @SuppressWarnings({"squid:S3776", "squid:S3011"})
    private void set(Class target, Object value, Object instance, Field field) throws IllegalAccessException {
        if (Boolean.TYPE == target) {
            field.setBoolean(instance, (Boolean) value);
        } else if (Byte.TYPE == target) {
            field.setByte(instance, (Byte) value);
        } else if (Character.TYPE == target) {
            field.setChar(instance, (Character) value);
        } else if (Short.TYPE == target) {
            field.setShort(instance, (Short) value);
        } else if (Integer.TYPE == target) {
            field.setInt(instance, (Integer) value);
        } else if (Long.TYPE == target) {
            field.setLong(instance, (Long) value);
        } else if (Float.TYPE == target) {
            field.setFloat(instance, (Float) value);
        } else if (Double.TYPE == target) {
            field.setDouble(instance, (Double) value);
        } else {
            field.set(instance, value);
        }
    }

}
