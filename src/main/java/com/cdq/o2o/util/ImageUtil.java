package com.cdq.o2o.util;

import com.cdq.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    private static Random random = new Random();

    /**
     * 处理缩略图
     * 步骤：
     * 1.生成随机文件名
     * 2.获取文件后缀名
     * 3.判断路径文件是否存在，如果不存在创建文件
     * 4.Thumbnails处理图片并保存
     *
     * @param targetAddr 文件存放地址
     */
    public static String generateThumbnails(ImageHolder imageHolder, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(imageHolder.getImgName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr:" + relativeAddr);
        File file = new File(PathUtil.getImageBasePath() + relativeAddr);
        logger.debug("current completeAddr:" + PathUtil.getImageBasePath() + relativeAddr);
        try {
            basePath = URLDecoder.decode(basePath, "utf-8");
            Thumbnails.of(imageHolder.getInputStream()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
                    .outputQuality(0.8f)
                    .toFile(file);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建路径文件
     */
    private static void makeDirPath(String targetAddr) {
        String realImagePath = PathUtil.getImageBasePath() + targetAddr;
        File file = new File(realImagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取文件后缀名
     *
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名称,名称格式：当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        String time = format.format(new Date());
        int number = random.nextInt(89999) + 10000;
        return time + String.valueOf(number);
    }

    /**
     * 如果storePath是目录及删除该目录下所有文件
     * 如果storePath是文件就删除该文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImageBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    public static void main(String[] args) throws IOException {
        basePath = URLDecoder.decode(basePath, "utf-8");
        System.out.println(basePath);

//        System.out.println("-------basePath------->"+basePath);
        /**
         * 错误：javax.imageio.IIOException: Can't read input file!
         * 原因：·文件路径中的某个文件名带有空格
         *        ·路径中带有  “.”
         * 解决方法： · basePath=URLDecoder.decode(basePath,"utf-8");
         *             ·替换带有.的路径名
         */
//        Thumbnails.of(new File("D:\\iamge\\cat.jpg"))
//                .size(200, 200)
//                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("E:\\Java web Project\\o2o\\src\\main\\resources\\cat.jpg")), 0.75f)
//                .outputQuality(0.8f).toFile("D:\\iamge\\cat.jpg");

    }

    public static String generateNormalImg(ImageHolder productImgHolder, String dest) {

        //1.获取随机文件名
        String randomFileNmae=ImageUtil.getRandomFileName();
        //2.获取后缀名
        String fileExtension=ImageUtil.getFileExtension(productImgHolder.getImgName());
        //获取相对地址
        String relativeAddr=dest+randomFileNmae+fileExtension;
        //创建路径文件
        makeDirPath(dest);
        //创建文件
        File file=new File(PathUtil.getImageBasePath()+relativeAddr);
        if (productImgHolder.getInputStream()!=null){
            try{
                //解决空格被转换成%20的问题
                basePath=URLDecoder.decode(basePath,"utf-8");
                Thumbnails.of(productImgHolder.getInputStream()).size(337,640)
                        .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"watermark.jpg")),0.25f)
                        .outputQuality(0.8f)
                        .toFile(file);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return relativeAddr;
    }
}
