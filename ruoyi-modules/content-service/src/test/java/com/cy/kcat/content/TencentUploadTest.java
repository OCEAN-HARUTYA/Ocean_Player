package com.cy.kcat.content;

import com.cy.kcat.content.vod.properties.VodProperties;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SecretId:  AKID66ye8PTLzQddQvJUTHsHTTHuRmupX1JN
 * SecretKey: tYPCv0veCXfaHrEOK3Ys1xgCVbT9cryt
 */
@SpringBootTest
@Slf4j
public class TencentUploadTest {


    @Autowired
    VodUploadClient vodUploadClient;
    @Autowired
    VodProperties vodProperties;

    @Test
    public void upload() {
        //初始化上传客户端
        //构造上传视频的请求
        VodUploadRequest request = new VodUploadRequest();
        request.setMediaFilePath("F:\\pj\\4cbfb2e36c2259c36c5c0fe7b0aaf60d.mp4");
        request.setMediaName(""); //指定媒体名，文件名
        request.setSubAppId(vodProperties.getSubAppId());
        //调用上传
        try {
            VodUploadResponse response = vodUploadClient.upload("ap-japan", request);
            log.info("Upload FileId = {}", response.getFileId());
        } catch (Exception e) {
            // 业务方进行异常处理
            log.error("Upload Err", e);
        }

    }

    /**
     * 上传完后转码
     *
     */
    @Test
    public void upload2() {
        VodUploadRequest request = new VodUploadRequest();
        request.setMediaFilePath("F:\\pj\\4cbfb2e36c2259c36c5c0fe7b0aaf60d.mp4");
//        request.setMediaName(""); //指定媒体名，文件名
        request.setSubAppId(vodProperties.getSubAppId());
        request.setProcedure("transFlowAndQulity");
        //调用上传
        try {
            VodUploadResponse response = vodUploadClient.upload("ap-chengdu", request);
            log.info("Upload FileId = {}", response.getFileId());
        } catch (Exception e) {
            // 业务方进行异常处理
            log.error("Upload Err", e);
        }
    }

    @Autowired
    VodClient vodClient;
    @Test
    public void searchMedia() {
        SearchMediaRequest request = new SearchMediaRequest();
        request.setSubAppId(vodProperties.getSubAppId());
        request.setLimit(1000L);

        try {
            SearchMediaResponse response = vodClient.SearchMedia(request);
            System.out.println(response);

            //
            MediaInfo[] mediaInfoSet = response.getMediaInfoSet();
            for (MediaInfo mediaInfo : mediaInfoSet) {
                //基本信息
                MediaBasicInfo basicInfo = mediaInfo.getBasicInfo();
                //元数据
                MediaMetaData metaData = mediaInfo.getMetaData();
                //转码后的数据
                MediaTranscodeInfo transcodeInfo = mediaInfo.getTranscodeInfo();

                String name = basicInfo.getName();
                String vid = basicInfo.getVid();
                String mediaUrl = basicInfo.getMediaUrl();
                Float duration = metaData.getDuration();

                System.out.println("<名字>" + name + "<时长>" + duration + "<url>" + mediaUrl+ "<vid>" + vid);
                MediaTranscodeItem[] transcodeSet = transcodeInfo.getTranscodeSet();
                for (MediaTranscodeItem transcodeItem : transcodeSet) {
                    String url = transcodeItem.getUrl();
                    Long width = transcodeItem.getWidth();
                    Long bitrate = transcodeItem.getBitrate();
                    System.out.println("<转码后视频地址>" + url + "<清晰度>" + width + "<<码率>>" + bitrate + "<<视频名>>" +name);
                }

                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void pullEvent() {
        PullEventsRequest request = new PullEventsRequest();
        request.setSubAppId(vodProperties.getSubAppId());
        try {
            //拉取一个时间
            PullEventsResponse response = vodClient.PullEvents(request);
            //遍历所有event
            EventContent[] eventSet = response.getEventSet();
            for (EventContent eventContent : eventSet) {
                String eventHandle = eventContent.getEventHandle();
                String eventType = eventContent.getEventType();
                System.out.println("<<时间句柄>>" + eventHandle + "<<时间类型>>" + eventType);
                //模拟数据库和业务的处理
                Thread.sleep(2000);
                //回复确认
                ConfirmEventsRequest cfRequest = new ConfirmEventsRequest();
                cfRequest.setSubAppId(vodProperties.getSubAppId());
                cfRequest.setEventHandles(new String[]{eventHandle});
                vodClient.ConfirmEvents(cfRequest);
                System.out.println("<<<确认成功>>>" + eventHandle  );
            }
            //主动拉取信息落库
            System.out.println(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
