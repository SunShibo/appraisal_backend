package com.wisewin.api.util;

import java.lang.reflect.Field;

/**
 * 对象参数空值判断工具类
 */
public class ParamNullUtil {
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
            //对象属性有一个不为空或null,返回false
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);

                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
