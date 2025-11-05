package com.cy.kcat.content.minio.template;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.cy.kcat.content.minio.properties.MyMinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.dromara.common.core.enums.FormatsType;
import org.dromara.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static cn.dev33.satoken.SaManager.log;

@Component
public class MinioTemplate {

    @Autowired
    MinioClient minioClient;
    @Autowired
    MyMinioProperties myMinioProperties;

    private final String bucketName = "kcat";

    /**
     * 上传web请求的文件
     *
     * @param file
     */
    public String updateWebFile(MultipartFile file) {
        String url = "";
        try {
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            //获取文件类型
            String contentType = file.getContentType();
            //获取文件大小
            long size = file.getSize();
            //上传到桶
            bucketExistAndCreate(bucketName);
            //上传文件
            String path = DateUtils.parseDateToStr(FormatsType.YYYY_MM_DD, new Date());
            //对象名 年/月/日/uuid_扩展名
            String fileName = path + "/" + UUID.randomUUID().toString() + "_" + originalFilename;
           minioClient.putObject(PutObjectArgs.builder()
               .bucket(bucketName)
               .stream(file.getInputStream(), size, -1)
               .contentType(contentType)
               .object(fileName)
               .build());
            //返回文件的返回地址
            String endpoint = myMinioProperties.getEndpoint();
            url = endpoint + "/" + bucketName + "/" + fileName;
            log.info("文件上传成功，访问地址：{}", url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // test for excel
        return url ;

    }

    public void bucketExistAndCreate(String bucketName) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                //创建桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("桶创建成功");
            }
        } catch (
            Exception e) {
            throw new RuntimeException(e);
        }
    }
}
