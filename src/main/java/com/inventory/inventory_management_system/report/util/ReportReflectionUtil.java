package com.inventory.inventory_management_system.report.util;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Utility class for extracting data from report DTOs using reflection.
 */
public class ReportReflectionUtil {

    /**
     * Extracts all scalar (non-collection, non-map) fields from an object as a Map.
     */
    public static Map<String, Object> extractScalarFields(Object obj) {
        Map<String, Object> result = new LinkedHashMap<>();
        if (obj == null) {
            return result;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (isCollection(field) || isMap(field)) {
                continue;
            }

            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                result.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                result.put(field.getName(), null);
            }
        }

        return result;
    }

    /**
     * Extracts scalar fields from an object as a List (ordered).
     */
    public static List<Object> extractScalarFieldsAsList(Object obj) {
        List<Object> result = new ArrayList<>();
        if (obj == null) {
            return result;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (isCollection(field) || isMap(field)) {
                continue;
            }

            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                result.add(value);
            } catch (IllegalAccessException e) {
                result.add(null);
            }
        }

        return result;
    }

    /**
     * Finds the first Collection field in the object (excluding maps).
     */
    public static Collection<?> findFirstCollectionField(Object obj) {
        if (obj == null) {
            return null;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (isCollection(field) && !isMap(field)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value instanceof Collection) {
                        return (Collection<?>) value;
                    }
                } catch (IllegalAccessException e) {
                    // ignore
                }
            }
        }

        return null;
    }

    private static boolean isCollection(Field field) {
        return Collection.class.isAssignableFrom(field.getType());
    }

    private static boolean isMap(Field field) {
        return Map.class.isAssignableFrom(field.getType());
    }
}