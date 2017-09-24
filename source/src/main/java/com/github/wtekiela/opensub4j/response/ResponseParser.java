package com.github.wtekiela.opensub4j.response;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseParser {

    public static final String LIST_DATA_KEY = "data";

    public <T> List<T> bindList(Class<T> clazz, Map<String, Object[]> response) throws IllegalAccessException, InstantiationException {
        Object[] rawData = response.get(LIST_DATA_KEY);
        List<T> list = new ArrayList<>(rawData.length);
        for (Object obj : rawData) {
            list.add(bind(clazz.newInstance(), (Map) obj));
        }
        return list;
    }

    public <T> T bind(T instance, Map response) throws IllegalAccessException {
        Field[] declaredFields = instance.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            handleField(instance, response, field);
        }
        return instance;
    }

    private <T> void handleField(T instance, Map response, Field field) throws IllegalAccessException {
        ensureFieldIsAccessible(field);
        OpenSubtitlesApi annotation = field.getAnnotation(OpenSubtitlesApi.class);
        if (annotation != null) {
            handleAnnotatedField(instance, response, field, annotation);
        }
    }

    private <T> void handleAnnotatedField(T instance, Map response, Field field, OpenSubtitlesApi annotation) throws IllegalAccessException {
        String name = annotation.fieldName();
        Class<?> target = field.getType();
        Object value = response.get(name);
        Class<?> source = value.getClass();
        if (needsStringConversion(target, source)) {
            value = parse(target, (String) value);
        }
        set(target, value, instance, field);
    }

    private boolean needsStringConversion(Class<?> target, Class<?> source) {
        return target != String.class && source == String.class;
    }

    private void ensureFieldIsAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

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
            return target.getConstructor(String.class).newInstance(value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            return value;
        }
    }

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
