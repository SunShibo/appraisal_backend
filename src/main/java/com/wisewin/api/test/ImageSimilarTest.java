package com.wisewin.api.test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class ImageSimilarTest {
    /**
     * 搜索图片，求图片的相似度
     *
     * @param n      图片的数量
     * @param str1   原图片的地址
     * @param str2   要比较图片的地址
     * @param ident1 Identification接口的实例化对像，可以传入多个对像
     */
    public static void searchImage(int n, String str1, String str2, HistogramIdentification ident1) {
        int characNum = 1;
        String charact1 = ident1.getCharacteristic(str1);
        String charact2 = new String();
        Element[] element = new Element[n];
        //获得str1中图片的特征值
        for (int j = 0; j < n; j++) {
            float f = 0f;
            String url = str2 + (j + 1) + ".jpg";
            charact2 = ident1.getCharacteristic(url);
            f += ident1.identification(charact1, charact2);
            //f = f/characNum;
            f = (int) ((f / characNum) * 1000) / 1000f;
            //System.out.println(f);
            element[j] = new Element((j + 1), 1000 * f);
        }
        Element.sort2(element);
        BufferedImage img = null;
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            img = ImageDigital.readImg(str2 + element[i].index + ".jpg");
            String url = str2 + "T" + element[i].similar + "_" + element[i].index + ".jpg";
            ImageDigital.writeImg(img, "jpg", url);
            list.add(url);
        }
        System.out.println(list.get(0));

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
//		URL url = ImageSimilarTest.class.getResource("/image");
        String str1 = "/Users/Shibo/Desktop/image/maotai/maotai.jpeg";
        String str2 = "/Users/Shibo/Desktop/image/maotai/maotai";
        int n = 6;
        HistogramIdentification ident1 = new HistogramIdentification();
        //Identification ident2 = new PHash();
        searchImage(n, str1, str2, ident1);//ident1,    , ident2

		
		/*String str1 = "F:\\image processing\\测试图片素材\\测试图片3.2\\Baboo.jpg";
        String str2 = "F:\\image processing\\测试图片素材\\测试图片3.2\\imagesTest.jpg";
		processingTest(str1, str2);
		*/
    }

}

class Element implements Comparable {
    int index;
    float similar;

    Element(int index, float similar) {
        this.index = index;
        this.similar = similar;
    }

    @Override
    public int compareTo(Object o) {
        float s = ((Element) o).similar;
        if (this.similar < s) {
            return -1;
        } else if (this.similar == s) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 将数组中的i和j位置互换
     *
     * @param obj
     * @param i
     * @param j
     */
    public static void swap(Object[] obj, int i, int j) {
        if (i >= 0 && i < obj.length && j >= 0 && j < obj.length) {
            Object temp = obj[i];
            obj[i] = obj[j];
            obj[j] = temp;
        }
    }

    /**
     * 从小到大排序
     *
     * @param data
     */
    public static void sort1(Comparable data[]) {
        int n = data.length;
//		Comparable temp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                }
            }
        }
    }

    /**
     * 从大到小排序
     *
     * @param data
     */
    public static void sort2(Comparable data[]) {
        int n = data.length;
        Comparable temp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) < 0) {
                    swap(data, j, j + 1);
                }
            }
        }
    }
}