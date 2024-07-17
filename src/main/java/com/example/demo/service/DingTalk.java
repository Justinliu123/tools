package com.example.demo.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.taobao.api.ApiException;

public class DingTalk {
    public static OapiGettokenResponse getToken(String appKey, String appSecret) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
//        1276530803
        OapiGettokenRequest request = new OapiGettokenRequest();
//        dingin1tc7kd24brbafi
        request.setAppkey(appKey);
//        eN0Ig3WFAb5v343u7_vcSh2d1_DvLvQpZbv2G4IKWosdFeVAp3oKqLn2uE6RvsVI
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        try {
            OapiGettokenResponse response = client.execute(request);
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OapiV2UserGetbymobileResponse getByMobile(String appKey, String appSecret, String phone) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile(phone);
        OapiGettokenResponse oapiGettokenResponse = getToken(appKey,appSecret);
        String accessToken = oapiGettokenResponse.getAccessToken();
        try {
            OapiV2UserGetbymobileResponse rsp = client.execute(req, accessToken);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void main(String[] args) {
        OapiGettokenResponse response = getToken("dingin1tc7kd24brbafi", "eN0Ig3WFAb5v343u7_vcSh2d1_DvLvQpZbv2G4IKWosdFeVAp3oKqLn2uE6RvsVI");
        OapiV2UserGetbymobileResponse responseUser = getByMobile("dingin1tc7kd24brbafi", "eN0Ig3WFAb5v343u7_vcSh2d1_DvLvQpZbv2G4IKWosdFeVAp3oKqLn2uE6RvsVI", "18514700422");

        System.out.println(response.getAccessToken());
        System.out.println(responseUser.getResult().getUserid());
    }
}
