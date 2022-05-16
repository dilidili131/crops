package com.lldd.crops.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/27 20:51
 */

public class AliOssService {
//    private static String endpoint = "https://oss-cn-zhangjiakou-internal.aliyuncs.com";
    private static String endpoint = "oss-cn-zhangjiakou.aliyuncs.com";
    private static String accessKeyId = "LTAI5tBzkzceH25p2TN47bz7";
    private static String accessKeySecret = "tm6R7wXBAGq6vOIJSRoRExx2cHf6sh";
    private static String bucketName = "lch-crops";
    private static String objectKey = "crops";

    public static void upload(MultipartFile multipartFile, String fileName) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        InputStream multipartFileInputStream = multipartFile.getInputStream();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectKey+fileName, multipartFileInputStream);
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();

    }

    public static void delete(String fileName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, objectKey+fileName);
        ossClient.shutdown();
    }

    public static void getImg(String name){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        ossClient.getObject(new GetObjectRequest(bucketName, objectKey+name+".jpg"), new File("/home/worm_img/"+name+".jpg"));
        ossClient.getObject(new GetObjectRequest(bucketName, objectKey+name+".jpg"), new File("D:\\"+name+".jpg"));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void uploadBufferedImage(BufferedImage image,String name) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imgOut = ImageIO.createImageOutputStream(bs);
        ImageIO.write(image,"jpg",imgOut);
        InputStream inputStream = new ByteArrayInputStream(bs.toByteArray());
        OSS ossclient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossclient.putObject(bucketName,objectKey+name+"2.jpg",inputStream);
        ossclient.shutdown();
    }
}
