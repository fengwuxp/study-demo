package com.java8.intf;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lsr on 2017/5/16.
 */
public class EnumsStore {

    private static Map<Class/*EnumClass*/, Map<String/*name*/, String/*desc*/>> enumDescMap = new ConcurrentHashMap<>();

    public static String getDesc(Class<? extends Enum> enumClass, String enumName) {
        Map<String, String> enumMap = getEnumMap(enumClass);
        return enumMap.get(enumName);
    }

    public static Map<String, String> getEnumMap(Class enumClass) {
        if (!enumDescMap.containsKey(enumClass)) {
            HashMap<String, String> descs = new LinkedHashMap<>();
            enumDescMap.put(enumClass, descs);

            Field[] declaredFields = enumClass.getDeclaredFields();
            Field.setAccessible(declaredFields, true);

            for (Field field : declaredFields) {

                if ("$VALUES".equals(field.getName()))
                    continue;

                if (field.isAnnotationPresent(Desc.class)) {
                    Desc desc = field.getAnnotation(Desc.class);
                    descs.put(field.getName(), !desc.value().isEmpty() ? desc.value() : desc.name());
                } else {
                    descs.put(field.getName(), field.getName());
                }
            }

            return descs;
        } else {
            return enumDescMap.get(enumClass);
        }
    }

}
