package com.cy.kcat.content.controller;

import com.cy.kcat.content.biz.DramaPublishService;
import com.cy.kcat.content.domain.vo.DramaPublishVo;
import com.cy.kcat.feign.CamundaFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.CamundaConstants;
import org.dromara.common.core.domain.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 19788
 * @version 1.0
 * @date 2023/10/15 16:48
 * @description 发布视频
 */
@RestController
@Slf4j
public class PublishController {


    DramaPublishService dramaPublishService;

    CamundaFeignClient camundaFeignClient;



    /**
     * spirng 推荐写法 ： 脱离spring也安全
     * @param dramaPublishService
     */
    public PublishController(DramaPublishService dramaPublishService, CamundaFeignClient camundaFeignClient) {
        this.dramaPublishService = dramaPublishService;
        this.camundaFeignClient = camundaFeignClient;
    }


    @PostMapping("/publish")
    public R publish(@RequestBody DramaPublishVo publishVo) {
        //1.发布短剧
        Long dramaId = dramaPublishService.publishDrama(publishVo);
        log.info("发布视频信息：{}", publishVo);
        //2. 启动审核流程 每个短剧和审核流程应该有关联
        R start = camundaFeignClient.start(CamundaConstants.DRAMA_PROCESS_DEFINITION_KEY);
        //3.保存短剧和审核流

        return R.ok();
    }
}
