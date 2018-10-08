package com.example.demo22222222;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * author: ljian
 * describe:  审批相关demo
 * createDate:
 **/
public class ApprovalDemo {

    public  static void main(String[] args)throws Exception{
        //test();

        /*LocalDate date = LocalDate.of(2018,8,17);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.atStartOfDay().atZone(zone).toInstant();
        String result = getBatchList("1a9769f60b4c3f00a72b6d6008db22f0","PROC-JFYJVERV-GBMYEN9DSZYEWH0L5C8N1-7BGUK0MJ-7",java.util.Date.from(instant));
        System.out.println(result);*/

        getApprovalDetail("1a9769f60b4c3f00a72b6d6008db22f0","6689a453-7921-4936-9710-f985463aa683");
        String string = getProcessList("1a9769f60b4c3f00a72b6d6008db22f0",null,0,100);
        Long count = getPendingNumber("1a9769f60b4c3f00a72b6d6008db22f0","manager7390");
        System.out.println("该员工的待审批数量为："+count);
    }

    /**
     * 发起审批实例
     * @throws Exception
     */
    public static void test()throws Exception{
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        //request.setAgentId(41605932L); 可选，isv调用必填
        request.setProcessCode("PROC-JFYJVERV-GBMYEN9DSZYEWH0L5C8N1-7BGUK0MJ-7");
        /*List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<>();
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> list2 = new ArrayList<>();

        OapiProcessinstanceCreateRequest.FormComponentValueVo obj1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        obj1.setName("换班");
        obj1.setValue("年假");
        list2.add(obj1);*/


        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo.setName("换班原因");
        vo.setValue("相亲");
        formComponentValues.add(vo);
        request.setFormComponentValues(formComponentValues);
      /*  OapiProcessinstanceCreateRequest.FormComponentValueVo obj2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        obj2.setName("开始时间");
        obj2.setValue("2018-09-14");

        OapiProcessinstanceCreateRequest.FormComponentValueVo obj3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        obj3.setName("结束时间");
        obj3.setValue("2018-09-15");

        OapiProcessinstanceCreateRequest.FormComponentValueVo obj4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        obj4.setName("时长");
        obj4.setValue("1.5");

        OapiProcessinstanceCreateRequest.FormComponentValueVo obj5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        obj5.setName("图片");
        obj5.setValue("[\"http://img1.imgtn.bdimg.com/it/u=1431659147,3510442122&fm=26&gp=0.jpg\"]");

        OapiProcessinstanceCreateRequest.FormComponentValueVo obj6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        obj6.setName("请假事由");
        obj6.setValue("相亲");

        OapiProcessinstanceCreateRequest.FormComponentValueVo obj7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        obj7.setName("明细");
        obj7.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(obj1, obj2,obj3,obj4))));

        list2.add(obj5);
        list2.add(obj6);
        list2.add(obj7);*/

        request.setFormComponentValues(formComponentValues);
        request.setApprovers("manager7390");
        request.setOriginatorUserId("14035045511222783");
        request.setDeptId(-1L);
        request.setCcList("manager7390");
        request.setCcPosition("START_FINISH");
        OapiProcessinstanceCreateResponse response = client.execute(request,"0742ddadf7da35efaeb4308ee9f27f49");

        System.out.println("返回码:"+response.getErrorCode()+",返回信息:"+response.getErrmsg());
        /*
        List<FormComponentValueVo> list2 = new ArrayList<FormComponentValueVo>();
        FormComponentValueVo obj3 = new FormComponentValueVo();
        obj3.setName("交通工具");
        obj3.setValue("飞机");

        FormComponentValueVo obj4 = new FormComponentValueVo();
        obj4.setName("出差事由");
        obj4.setValue("开会");

        FormComponentValueVo pic = new FormComponentValueVo();
        pic.setName("图片");
        pic.setValue("[\"http://aaa/media\"]");

        FormComponentValueVo mingxi2 = new FormComponentValueVo();
        mingxi2.setName("开始时间");
        mingxi2.setValue("2018-09-01");

        FormComponentValueVo mingxi3 = new FormComponentValueVo();
        mingxi3.setName("图片");
        mingxi3.setValue("[\"http://aaa/media\"]");

        FormComponentValueVo obj7 = new FormComponentValueVo();
        obj7.setName("明细");
        obj7.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(mingxi2, mingxi3))));

        list2.add(obj3);
        list2.add(obj4);
        list2.add(pic);
        list2.add(obj7);
        */
    }


    /**
     * 批量获取审批实例id
     * @param accssToken 验证token
     * @param processCode 模板id
     * @param startTime 开始时间
     * @return
     * @throws Exception
     */
    public static String getBatchList(String accssToken,String processCode,Date startTime)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/listids");
        OapiProcessinstanceListidsRequest req = new OapiProcessinstanceListidsRequest();
        req.setProcessCode(processCode);
        req.setStartTime(startTime.getTime());
        //req.setEndTime(1496678400000L); //非必填
        //req.setSize(10L); //非必填
        //req.setCursor(0L); //非必填
        //req.setUseridList("manager1,manager2"); //非必填

        OapiProcessinstanceListidsResponse response = client.execute(req, accssToken);
        if(response.getErrcode() == 0){
            System.out.println(response.toString());
            System.out.println(response.getResult().getList().toString());
            return "ok";
        }
        return "fail";
    }

    /**
     * 获取单个审批实例
     * @param accessToken 验证token
     * @param instanceId 审批实例
     * @return
     */
    public static String getApprovalDetail(String accessToken,String instanceId)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
        OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
        request.setProcessInstanceId(instanceId);
        OapiProcessinstanceGetResponse response = client.execute(request,accessToken);
        String result = JSONObject.toJSONString(response.getProcessInstance());
        System.out.println(result);
        return result;
    }

    /**
     * 获取用户可见的审批模板
     * @param accessToken 验证token
     * @param userId 钉钉用户id，不传默认获取该企业所有模板
     * @param currentPage 取值>=0
     * @param pageSize  取值<=100
     * @return
     */
    public static String getProcessList(String accessToken,String userId,Integer currentPage,Integer pageSize)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/listbyuserid");
        OapiProcessListbyuseridRequest request = new OapiProcessListbyuseridRequest();
        request.setUserid("manager7078");
        request.setOffset(currentPage.longValue());
        request.setSize(pageSize.longValue());
        OapiProcessListbyuseridResponse response = client.execute(request, accessToken);
        System.out.println("共有审批模板："+response.getResult().getProcessList().size());
        String result = JSONObject.toJSONString(response.getResult());
        System.out.println(result);
        return result;
    }

    /**
     * 获取用户待审批数量
     * @param accessToken
     * @param userId
     * @return
     */
    public static Long getPendingNumber(String accessToken,String userId)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/gettodonum");
        OapiProcessGettodonumRequest req = new OapiProcessGettodonumRequest();
        req.setUserid(userId);
        OapiProcessGettodonumResponse response = client.execute(req, accessToken);
        return response.getCount();
    }
}
