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

import com.github.wtekiela.opensub4j.response.ListResponse;
import com.github.wtekiela.opensub4j.response.OpenSubtitlesApiSpec;
import com.github.wtekiela.opensub4j.response.ResponseStatus;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ResponseParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseParser.class);
    private static final String ILLEGAL_ACCESS_MSG = "Illegal access while binding field in response object";

    private static Set<Field> getAllClassFields(Class<?> type) {
        Set<Field> fields = new HashSet<>();
        Class<?> currentType = type;

        do {
            fields.addAll(Arrays.asList(currentType.getDeclaredFields()));
            currentType = currentType.getSuperclass();
        } while (currentType != null);

        return fields;
    }

    private static boolean isPrimitiveType(Class<?> target) {
        return Boolean.TYPE == target ||
            Boolean.class.equals(target) ||
            Byte.TYPE == target ||
            Byte.class.equals(target) ||
            Character.TYPE == target ||
            Character.class.equals(target) ||
            Short.TYPE == target ||
            Short.class.equals(target) ||
            Integer.TYPE == target ||
            Integer.class.equals(target) ||
            Long.TYPE == target ||
            Long.class.equals(target) ||
            Float.TYPE == target ||
            Float.class.equals(target) ||
            Double.TYPE == target ||
            Double.class.equals(target) ||
            String.class.equals(target);
    }

    <T> ListResponse<T> bind(ListResponse<T> instance, AbstractListOperation.ElementFactory<T> elementFactory,
                             Map<?, ?> response) {
        if (instance == null) {
            return null;
        }
        Set<Field> declaredFields = getAllClassFields(instance.getClass());
        for (Field field : declaredFields) {
            new FieldBindingTask<ListResponse<T>, T>(instance, elementFactory, response, field).run();
        }
        return instance;
    }

    <T> T bind(T instance, Map<?, ?> response) {
        if (instance == null) {
            return null;
        }
        Set<Field> declaredFields = getAllClassFields(instance.getClass());
        for (Field field : declaredFields) {
            new FieldBindingTask<>(instance, response, field).run();
        }
        return instance;
    }

    private class FieldBindingTask<T, V> implements Runnable {

        private final T instance;
        private final Field field;

        private final Map<?, ?> response;

        private AbstractListOperation.ElementFactory<V> elementFactory;

        private Object value;

        public FieldBindingTask(T instance, Map<?, ?> response, Field field) {
            this.instance = instance;
            this.response = response;
            this.field = field;
        }

        public FieldBindingTask(T instance, AbstractListOperation.ElementFactory<V> elementFactory, Map<?, ?> response,
                                Field field) {
            this.instance = instance;
            this.elementFactory = elementFactory;
            this.response = response;
            this.field = field;
        }

        public void run() {
            ensureFieldIsAccessible(field);
            OpenSubtitlesApiSpec annotation = field.getAnnotation(OpenSubtitlesApiSpec.class);
            if (annotation == null) {
                return;
            }

            value = response.get(annotation.fieldName());
            if (value == null) {
                return;
            }

            Class<?> source = value.getClass();
            Class<?> target = field.getType();

            if (isPrimitiveType(target)) {
                executePrimitiveFieldBinding(source, target);
            } else if (Optional.class.equals(target) && elementFactory != null) {
                executeListFieldBinding(source, target);
            } else if (ResponseStatus.class.equals(target)) {
                executeResponseStatusBinding(target, (String) value);
            } else {
                executeNestedFieldBinding(target);
            }
        }

        private void executePrimitiveFieldBinding(Class<?> source, Class<?> target) {
            if (needsStringConversion(target, source)) {
                value = parse(target, (String) value);
            }
            try {
                set(target, value);
            } catch (IllegalAccessException e) {
                LOGGER.warn(ILLEGAL_ACCESS_MSG, e);
            }
        }

        private void executeListFieldBinding(Class<?> source, Class<?> target) {
            if (source == Object[].class) {
                Object[] rawData = (Object[]) value;
                List<V> list = new ArrayList<>(rawData.length);
                try {
                    for (Object obj : rawData) {
                        list.add(ResponseParser.this.bind(elementFactory.newInstance(), (Map<?, ?>) obj));
                    }
                    set(target, Optional.of(list));
                } catch (ReflectiveOperationException e) {
                    LOGGER.warn("Error while binding field in response object", e);
                }
            } else {
                // needed for responses other than 200 OK that have empty string in "data"
                try {
                    set(target, Optional.empty());
                } catch (IllegalAccessException e) {
                    LOGGER.warn(ILLEGAL_ACCESS_MSG, e);
                }
            }
        }

        private void executeResponseStatusBinding(Class<?> target, String value) {
            try {
                Object responseStatus = target.getMethod("fromString", String.class).invoke(null, value);
                set(target, responseStatus);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.warn("Exception while binding ResponseStatus from String", e);
            }
        }

        private void executeNestedFieldBinding(Class<?> target) {
            try {
                Object nestedInstance = target.getDeclaredConstructor().newInstance();
                ResponseParser.this.bind(nestedInstance, (Map) value);
                set(target, nestedInstance);
            } catch (InstantiationException e) {
                LOGGER.warn("Could not instantiate nested class while binding field in response object", e);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.warn("Exception while binding nested object", e);
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
        private Object parse(Class<?> target, String value) {
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
                return null;
            }
        }

        @SuppressWarnings({"squid:S3776", "squid:S3011"})
        private void set(Class<?> target, Object value) throws IllegalAccessException {
            if (Boolean.TYPE == target) {
                Integer castedVal = (Integer) value;
                field.setBoolean(instance, castedVal == 1);
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

}
