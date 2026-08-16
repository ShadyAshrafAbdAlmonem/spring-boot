package com.inventory.inventory_management_system.common.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * Base mapper class providing common mapping utilities.
 * Extend this class to create specific mappers for entities and DTOs.
 */
public class BaseMapper {

    /**
     * Convert source object to target class type
     *
     * @param source      the source object
     * @param targetClass the target class type
     * @param <S>         source type
     * @param <T>         target type
     * @return mapped target object
     */
    public static <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map object from " + source.getClass().getName() 
                    + " to " + targetClass.getName(), e);
        }
    }

    /**
     * Convert source object to target class type with custom property mapping
     *
     * @param source      the source object
     * @param targetClass the target class type
     * @param ignoreProperties properties to ignore during mapping
     * @param <S>         source type
     * @param <T>         target type
     * @return mapped target object
     */
    public static <S, T> T map(S source, Class<T> targetClass, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map object from " + source.getClass().getName() 
                    + " to " + targetClass.getName(), e);
        }
    }

    /**
     * Update target object with non-null properties from source object
     *
     * @param source the source object with updated values
     * @param target the target object to be updated
     */
    public static void update(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * Get names of null properties in the given object
     *
     * @param source the source object
     * @return array of null property names
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * Map source list to target type list
     *
     * @param sourceList  the source list
     * @param targetClass the target class type
     * @param <S>         source type
     * @param <T>         target type
     * @return list of mapped target objects
     */
    /*
    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }
        return sourceList.stream()
                .map(source -> map(source, targetClass))
                .collect(Collectors.toList());
    }
    */

    /**
     * Check if two objects are equal by comparing their properties
     *
     * @param obj1 the first object
     * @param obj2 the second object
     * @return true if objects are equal, false otherwise
     */
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }

    /**
     * Get property names of a class
     *
     * @param clazz the class
     * @return array of property names
     */
    public static String[] getPropertyNames(Class<?> clazz) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(clazz);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> propertyNames = new HashSet<>();
        for (PropertyDescriptor property : propertyDescriptors) {
            propertyNames.add(property.getName());
        }
        return propertyNames.toArray(new String[0]);
    }

    /**
     * Copy non-null properties from source to target
     *
     * @param source the source object
     * @param target the target object
     */
    public static void copyNonNullProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
}
