package com.example.demo22222222;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;

/**
 * author: ljian
 * describe:
 * createDate:
 **/
public class GetAccessToken {


    public static void main(String[] args){
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("ding50d4c5273b01d42c35c2f4657eb6378f");
        request.setAppsecret("l9_p2buI_2FmN9sMbr8Ba1KoBhHA41aafbP3Oe8JYc90KfNcqCD6KWjVehAoYU-a");
        //request.setHttpMethod("GET");
        try{
            OapiGettokenResponse response = client.execute(request);
            String accessToken = response.getAccessToken();
            System.out.println(accessToken);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
