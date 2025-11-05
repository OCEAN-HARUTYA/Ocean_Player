package com.cy.kcat.content;

import cn.hutool.core.lang.UUID;
import io.minio.*;
import io.minio.http.Method;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

public class MinioTest {
    @Test
    public void test() throws Exception {
        String bucketName = "kcat";
        String path = "F:\\快猫\\day12 - 快猫短剧：对象存储&短剧发布\\19、内容服务：短剧发布业务：第一步：保存短剧信息.mp4";
        String fileName = UUID.randomUUID().toString() +  "19、内容服务：短剧发布业务：第一步：保存短剧信息.mp4";
        //1.创建一共MinoClient
        MinioClient minioClient = MinioClient.builder().endpoint("http://localhost:9000").
            credentials("ruoyi", "ruoyi123")
            .build();

        System.out.println(minioClient);
        //准备 Bucket
        BucketExistsArgs existsArgs = BucketExistsArgs.builder().bucket(bucketName).build();

        boolean exists = minioClient.bucketExists(existsArgs);
        if (!exists) {
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
            minioClient.makeBucket(makeBucketArgs);
            System.out.println("创建成功" + bucketName );
        }else {
            System.out.println("已经存在" + bucketName);
        }
        FileInputStream fileInputStream = new FileInputStream(path);

        //3.上传文件
        minioClient.putObject(
          PutObjectArgs.builder()
              .bucket(bucketName)
              .stream(fileInputStream, fileInputStream.available(), -1)
              .object(fileName)
              .contentType("video/mp4")
              .build()
        );
        System.out.println("上传成功");
        //私有桶
        //获取文件地址
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder()
            .method(Method.GET)
            .bucket(bucketName)
            .object(fileName)
            .expiry(2, TimeUnit.HOURS)
            .build();
        //获取文件访问地址
        String url = minioClient.getPresignedObjectUrl(build);
        System.out.println("文件地址：" + url);

        //公有桶
        // MinIo服务器/桶名/文件名


        //自定义权限桶
    }
}
