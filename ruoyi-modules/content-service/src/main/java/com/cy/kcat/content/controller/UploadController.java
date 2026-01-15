package com.cy.kcat.content.controller;

import com.cy.kcat.content.minio.template.MinioTemplate;
import org.dromara.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 * @author
 */
@RestController
public class UploadController {

    @Autowired
     MinioTemplate minioTemplate;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        String url = minioTemplate.updateWebFile(file);
        return R.ok(url, "上传成功");
    }

}
