package com.cy.kcat.workflow.controller;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.dromara.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;


@RestController
@RequestMapping("/workflow")
public class ProcessDeployController {

    @Autowired
    RepositoryService repositoryService;

    @PostMapping("/deploy")
    public R deployProcess(@RequestParam("file") MultipartFile  file ) throws IOException {
        //获取文件bpmn文件名
        String filename = file.getOriginalFilename();
        //获取流
        InputStream fileInputStream = file.getInputStream();
        //部署
        Deployment deploy = repositoryService.createDeployment()
            .addInputStream(filename, fileInputStream).deploy();
        return R.ok("success", deploy.getId());

    }
}
