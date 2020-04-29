package com.wisewin.api.util;

import java.util.Random;

/**
 * Created by Administrator on 2018/3/23.
 */
public class RandomUtils {

    public static String getRandomNumber(int digital) {
        int[] array = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();

        int result;
        int i;
        for(result = 10; result > 1; --result) {
            i = rand.nextInt(result);
            int tmp = array[i];
            array[i] = array[result - 1];
            array[result - 1] = tmp;
        }

        result = 0;

        for(i = 0; i < digital; ++i) {
            result = result * 10 + array[i];
        }

        return getSuffixCode(digital, result);
    }

    public static String getSuffixCode(int length, int initNum) {
        String code = "";

        for(int i = length; i > (initNum + "").length(); --i) {
            code = code + "0";
        }

        return code + initNum;
    }
}
