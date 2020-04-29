package com.wisewin.api.util;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/31.
 */
public class ArrayUtils {
    public static final String DEFAULT_SPLIT = ",";

    public ArrayUtils() {
    }

    public static boolean isEmpty(Object[] obj) {
        return null == obj || obj.length == 0;
    }

    public static String join(Object[] obj) {
        return join(obj, ",");
    }

    public static String join(Object[] obj, String split) {
        StringBuffer sb = new StringBuffer();
        if(null != obj && obj.length > 0) {
            for(int i = 0; i < obj.length; ++i) {
                sb.append(obj[i]);
                if(i != obj.length - 1) {
                    sb.append(split);
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println(join(list.toArray(), ","));
    }
}
