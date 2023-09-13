package com.example.demo.terminal;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.*;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.example.demo.service.DingTalk;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;


public class Test01 {
    public static void main(String[] args) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
            OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
            req.setAgentId(1276530803L);
            req.setUseridList("282645396321181493");
            req.setToAllUser(false);
            Msg message = new Msg();
            message.setMsgtype("link");
            Link link = new Link();
            link.setPicUrl("@lALPDfmVcbOw2CfNA0zNBQA");
            link.setMessageUrl("http://101.200.56.62/#/excel?industry=electricity&itemNumber=HNZB2023-07-3-030");
            link.setText("辽宁公司沈西厂厂内1.303MW分布式光伏项目EPC工程公开招标项目招标公告");
            link.setTitle("您关注的\"label\"新的招标文件");
            message.setLink(link);
            req.setMsg(message);
            OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(req, "11af6f1664273ff3bf52ba45014f2aed");
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
    public static String uploadMedia() {
        OapiMediaUploadResponse rsp = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
            OapiMediaUploadRequest req = new OapiMediaUploadRequest();
            req.setType("image");
            FileItem fileItem = new FileItem("/download/images/government.png");
            req.setMedia(fileItem);
            rsp = client.execute(req, DingTalk.getToken("dingin1tc7kd24brbafi", "eN0Ig3WFAb5v343u7_vcSh2d1_DvLvQpZbv2G4IKWosdFeVAp3oKqLn2uE6RvsVI").getAccessToken());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getMediaId());
        return rsp.getMediaId();
    }
}
