package com.cdq.o2o.util;

public class PathUtil {

    private static String seperator = System.getProperty("file.separator");

    public static String getImageBasePath() {
        String basePath = "";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/projectdev/image/";
        } else {
            basePath = "/home/xiangze/image/";
        }
        basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(Long shopId) {
        String imagePath = "/upload/item/shop/" + shopId + "/";
//        System.out.println(seperator);
        return imagePath.replace("/", seperator);
    }

}
