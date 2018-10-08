package com.example.demo22222222;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;

import java.util.List;

/**
 * author: ljian
 * describe: 通讯录相关demo
 * createDate:
 **/
public class MailListController {


    public  static void main(String[] args)throws Exception{
        getMailListPremesion("ebda0bd29c123af48ba44c66ffa5dcf0");
        getDeptUserList("71c39f1144913864a7c869c1749ab36b","82731762");

       getUserDetail("ebda0bd29c123af48ba44c66ffa5dcf0","14035045511222783");

        getChildDeptId("ebda0bd29c123af48ba44c66ffa5dcf0","1");
        //71c39f1144913864a7c869c1749ab36b
        getDeptList("71c39f1144913864a7c869c1749ab36b",null);

        getDeptDetail("71c39f1144913864a7c869c1749ab36b","1");
    }

    /**
     * 获取通讯录权限范围
     * @param accessToken
     */
    public static void getMailListPremesion(String accessToken)throws Exception{
       DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/auth/scopes");
        OapiAuthScopesRequest request = new OapiAuthScopesRequest();
       //request.setHttpMethod("GET");
        OapiAuthScopesResponse response = client.execute(request, accessToken);
        System.out.println("code:"+response.getErrcode());
        System.out.println("msg:"+response.getErrmsg());
        List<Long> authedDept = response.getAuthOrgScopes().getAuthedDept();
        for (Long aLong : authedDept) {
            System.out.println("dept:"+aLong);
        }
        List<String> userList =  response.getAuthOrgScopes().getAuthedUser();
        System.out.println("userSize:"+userList.size());
       for(String str:userList){
           System.out.println(str);
       }
        System.out.println("---------------------------------");
        List<String> list =  response.getAuthUserField();
        for (String str :list){
            System.out.println(str);
        }
    }

    /**
     * 获取部门用户userid列表
     * @param accessToken
     * @param deptId
     * @throws Exception
     */
    public static void getDeptUserList(String accessToken,String deptId)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
        OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
        req.setDeptId(deptId);
        req.setTopHttpMethod("GET");
        OapiUserGetDeptMemberResponse rsp = client.execute(req, accessToken);
        System.out.println("获取部门所有用户列表开始>>>>>>");
        System.out.println(rsp.getBody());
        System.out.println("获取部门所有用户列表结束<<<<<<");
    }

    /**
     * 获取用户详细信息
     * @param accessToken
     * @param userId
     * @return
     * @throws Exception
     */
    public static String getUserDetail(String accessToken,String userId)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setTopHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, accessToken);
        System.out.println("获取用户详细信息开始>>>>>>");
        System.out.println(response.getBody());
        System.out.println("获取用户详细信息结束<<<<<<");
        return null;
    }

    /**
     * 获取子部门id
     * @param accessToken
     * @param parentDeptId
     * @throws Exception
     */
    public static void getChildDeptId(String accessToken,String parentDeptId)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
        OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
        request.setId(parentDeptId);
        request.setTopHttpMethod("GET");
        OapiDepartmentListIdsResponse response = client.execute(request, accessToken);
        List<Long> subDeptIdList = response.getSubDeptIdList();
        for (Long aLong : subDeptIdList) {
            System.out.println("childId:"+aLong);
        }
    }

    /**
     * 获取部门列表
     * @param accessToken
     * @param parentDeptId
     * @throws Exception
     */
    public static void getDeptList(String accessToken,String parentDeptId)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(parentDeptId);
        request.setTopHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, accessToken);
        List<OapiDepartmentListResponse.Department> department = response.getDepartment();
        for (OapiDepartmentListResponse.Department department1 : department) {
            String string = JSONObject.toJSONString(department1);
            System.out.println(string);
        }
    }

    /**
     * 获取部门详情
     * @param accessToken
     * @param deptId
     * @throws Exception
     */
    public static void getDeptDetail(String accessToken,String deptId)throws Exception{
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(deptId);
        request.setTopHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, accessToken);
        System.out.println("部门详情开始>>>>>>");
        String string = JSONObject.toJSONString(response);
        System.out.println(string);
        System.out.println("部门详情结束<<<<<<");
    }
}
