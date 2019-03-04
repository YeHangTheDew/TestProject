package com.xuanwu.apaas.master.rest;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xuanwu.apaas.core.utils.http.response.RestResult;
import com.xuanwu.apaas.master.util.AnalysisFuntionUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/test")
@Component
@Api(value="测试",produces="application/json")
public class TestRest {
    @Autowired
    AnalysisFuntionUtil analysisFuntionUtil;

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public  Response test(Map map){
/*        String jsonString=MapUtils.getString(map,"steps");
        JSONArray jarr = JSON.parseArray(jsonString);
        for(int i=0,len=jarr.size();i<len;i++){
            JSONObject temp=  jarr.getJSONObject(i);
            System.out.printf(temp.getString("setpid")+i);
        }


        Response response=RestResult.success(123456L, "测试成功！");*/
        String sourcejsonStr="{\"conf\":[{\"key\":\"ui_work_appedit\",\"name\": \"生动化采集表单\",\"value\": \"我是获取的数据\"}]}";
        String targetjsonStr="{\n" +
                "    \"appcode\": \"sales\",//默认发布的为sfa应用\n" +
                "    \"objectmap\": [//实体映射数据  //此mvp可以不用\n" +
                "        {\n" +
                "            \"key\":\"kx_visit_commoncollect\",//实体对象标识（英文名）\n" +
                "            \"name\":\"通用采集表\",\n" +
                "            \"code\":\"@getconfvaluebykey(kx_visit_commoncollect)\",\n" +
                "            \"publishconf\": {//发布定义\n" +
                "                \"publishtype\": \"2\",//发布类型 1. 发布 2. 不需要发布\n" +
                "                \"offlineconf\": {//离线发布定义  //待定\n" +
                "                    \"publishtype\": \"2\",//是否发布\n" +
                "                    \"offlinetype\": \"1\",//类型  //1,2\n" +
                "                        \"logiccode\": \"@getconfvaluebykey(业务逻辑编码key)+defalt:123\",//业务逻辑编码 离线下载，可以为空\n" +
                "                    \"objectcode\": \"@getconfvaluebykey(kx_visit_commoncollect)\",\n" +
                "                    \"grantfunctions\":[//离线发布授权\n" +
                "                        {\n" +
                "                            //操作两张表 pl_grantfunction pl_assignfunction\n" +
                "                            \"parentgrantfunctioncode\": \"\",//授权功能上级code 为空，代表的是要写入的授权功能在第一层\n" +
                "                            \"grantfunctioncode\": \"\",//新增时，需要生成id\n" +
                "                            \"markcode\": \"\",//授权功能标记\n" +
                "                            \"grantfunctionname\": \"授权功能名称\",\n" +
                "                            \"functioncategory\": \"4\",//离线类型  \n" +
                "                            \"functioncode\": \"@getconfvaluebykey(kx_visit_commoncollect)\"//发布到pl_assignfunction的功能点 //对象编码\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                \"dataruleconf\": {//数据规则定义\n" +
                "                    \"publishtype\": \"2\",\n" +
                "                    \"datarules\": [\n" +
                "                        {//待定 \n" +
                "                            \"datarulecode\": \"\",\n" +
                "                            \"objectcode\": \"\",\n" +
                "                            \"datarulename\": \"\",\n" +
                "                            \"rules\": [\n" +
                "\t\t\t                    {\n" +
                "                                    \"objectcode\": \"1098054025380761697\",\n" +
                "                                    \"propertycode\": \"1098054025380761695\",\n" +
                "                                    \"op\": \"refobject\",\n" +
                "                                    \"value\": \"820000000000000000\"\n" +
                "\t\t\t                    }\n" +
                "\t\t                    ]\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"logicmap\": [//行为逻辑映射数据 //此mvp可以不用管理此部分数据\n" +
                "        {\n" +
                "            \"key\":\"\",//行为逻辑key\n" +
                "            \"name\": \"\",//行为逻辑名称\n" +
                "            \"code\":\"\"//行为逻辑的编码\n" +
                "        }\n" +
                "    ],\n" +
                "    \"uimap\": [\n" +
                "        {\n" +
                "            \"key\": \"ui_work_appedit\",\n" +
                "            \"name\": \"采集表单\",\n" +
                "            \"code\": \"@getconfvaluebykey:ui_work_appedit\",//是从contextjson中的conf中查找相应的key的value填充\n" +
                "            \"publishconf\": {//发布定义\n" +
                "                \"publishtype\": \"1\",//\n" +
                "                \"functions\": [//除了页面本身功能注册外，有按钮功能控制时，需要在此定义 //元数据库 biz_function\n" +
                "                    {\n" +
                "                        \"functionname\": \"导出\",//功能名称\n" +
                "                        \"functioncategory\": \"3\",\n" +
                "                        \"clientcategory\": \"2\",//ui用户端\n" +
                "                        \"functioncode\": \"@getconfvaluebykey:ui_work_appedit+default:123\"//取 conf中配置的值，回写\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"grantfunctions\": [//发布授权定义\n" +
                "                    {\n" +
                "                        //操作两张表 pl_grantfunction pl_assignfunction\n" +
                "                        \"parentgrantfunctioncode\": \"\",//授权功能上级code 为空，代表的是要写入的授权功能在第一层\n" +
                "                        \"grantfunctioncode\": \"\",//新增时，需要生成id\n" +
                "                        \"markcode\": \"\",//授权功能标记\n" +
                "                        \"grantfunctionname\": \"授权功能名称\",\n" +
                "                        \"functioncategory\": \"2\",//表单类型\n" +
                "                        \"clientcategory\": \"2\",//ui用户端\n" +
                "                        \"functioncode\": \"\",//发布到pl_assignfunction的功能点 \n" +
                "                        \"publishroles\": [\"\",\"\"]//发布到那些权限组\n" +
                "                    }, \n" +
                "                    {\n" +
                "                        \"parentgrantfunctioncode\": \"\",//授权功能上级code 为空，代表的是要写入的授权功能在第一层\n" +
                "                        \"grantfunctioncode\": \"\",//新增时，需要生成id\n" +
                "                        \"markcode\": \"\",//授权功能标记\n" +
                "                        \"grantfunctionname\": \"授权功能名称\",\n" +
                "                        \"functioncategory\": \"2\",//表单类型  \n" +
                "                        \"clientcategory\": \"2\",//ui用户端\n" +
                "                        \"functioncode\": \"\"//发布到pl_assignfunction的功能点 \n" +
                "                    }\n" +
                "                ],\n" +
                "                \"pagenavigations\": [//发布导航定义\n" +
                "                    {\n" +
                "                        \"parentid\": \"\",//发布表单到固定的某导航下\n" +
                "                        \"title\": \"\",//导航标题\n" +
                "                        \"category\": \"4\",//表单\n" +
                "                        \"id\": \"@genid:auto\",//发布的导航的主键，需要生成一个新的id\n" +
                "                        \"clientcategory\": \"2\",//ui用户端\n" +
                "                        \"pagecode\": \"\"//当前页面的编码\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"key\": \"ui_work_appview\",\n" +
                "            \"name\": \"采集表单查看\",\n" +
                "            \"code\": \"\",\n" +
                "            \"publishconf\": {//发布定义\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"key\": \"ui_work_webdetaillist\",\n" +
                "            \"name\": \"采集明细表单\",\n" +
                "            \"code\": \"\",\n" +
                "            \"publishconf\": {//发布定义\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"key\": \"ui_work_webdetailview\",\n" +
                "            \"name\": \"主管首页查看表单\",\n" +
                "            \"code\": \"\",\n" +
                "            \"publishconf\": {//发布定义\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String testJson="{\n" +
                "    \"resp_data\": \"{\\\"appcode\\\":\\\"sales\\\",\\\"logicmap\\\":[{\\\"code\\\":\\\"\\\",\\\"key\\\":\\\"\\\",\\\"name\\\":\\\"\\\"}],\\\"objectmap\\\":[\\\"{\\\\\\\"code\\\\\\\":\\\\\\\"getconfvaluebykey(kx_visit_commoncollect)\\\\\\\",\\\\\\\"key\\\\\\\":\\\\\\\"kx_visit_commoncollect\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"通用采集表\\\\\\\",\\\\\\\"publishconf\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"dataruleconf\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"{\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"datarules\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":[{\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"datarulecode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"datarulename\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"objectcode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"rules\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":[{\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"objectcode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"1098054025380761697\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"op\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"refobject\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"propertycode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"1098054025380761695\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"value\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"820000000000000000\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"}]}],\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"publishtype\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"2\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"}\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"offlineconf\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"{\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"grantfunctions\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":[\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"{\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"functioncategory\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"4\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"functioncode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"getconfvaluebykey(kx_visit_commoncollect)\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"grantfunctioncode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"grantfunctionname\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"授权功能名称\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"markcode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"parentgrantfunctioncode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"}\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"],\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"logiccode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"etconfvaluebykey(业务逻辑编码key)no such function\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"objectcode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"getconfvaluebykey(kx_visit_commoncollect)\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"offlinetype\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"1\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"publishtype\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"2\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"}\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"publishtype\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"2\\\\\\\\\\\\\\\"}\\\\\\\"}\\\"],\\\"uimap\\\":[\\\"{\\\\\\\"code\\\\\\\":\\\\\\\"我是获取的数据\\\\\\\",\\\\\\\"key\\\\\\\":\\\\\\\"ui_work_appedit\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"采集表单\\\\\\\",\\\\\\\"publishconf\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"functions\\\\\\\\\\\\\\\":[\\\\\\\\\\\\\\\"{\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"clientcategory\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"2\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"functioncategory\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"functioncode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"no such functionno such function\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"functionname\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"导出\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"}\\\\\\\\\\\\\\\"],\\\\\\\\\\\\\\\"grantfunctions\\\\\\\\\\\\\\\":[{\\\\\\\\\\\\\\\"clientcategory\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"2\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"functioncategory\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"2\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"functioncode\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"grantfunctioncode\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"grantfunctionname\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"授权功能名称\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"markcode\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"parentgrantfunctioncode\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"publishroles\\\\\\\\\\\\\\\":[\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\"]},{\\\\\\\\\\\\\\\"clientcategory\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"2\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"functioncategory\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"2\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"functioncode\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"grantfunctioncode\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"grantfunctionname\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"授权功能名称\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"markcode\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"parentgrantfunctioncode\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\"}],\\\\\\\\\\\\\\\"pagenavigations\\\\\\\\\\\\\\\":[\\\\\\\\\\\\\\\"{\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"category\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"4\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"clientcategory\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"2\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"id\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"1100239765363625984\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"pagecode\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"parentid\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"title\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"}\\\\\\\\\\\\\\\"],\\\\\\\\\\\\\\\"publishtype\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"1\\\\\\\\\\\\\\\"}\\\\\\\"}\\\",\\\"{\\\\\\\"code\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"key\\\\\\\":\\\\\\\"ui_work_appview\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"采集表单查看\\\\\\\",\\\\\\\"publishconf\\\\\\\":\\\\\\\"{}\\\\\\\"}\\\",\\\"{\\\\\\\"code\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"key\\\\\\\":\\\\\\\"ui_work_webdetaillist\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"采集明细表单\\\\\\\",\\\\\\\"publishconf\\\\\\\":\\\\\\\"{}\\\\\\\"}\\\",\\\"{\\\\\\\"code\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"key\\\\\\\":\\\\\\\"ui_work_webdetailview\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"主管首页查看表单\\\\\\\",\\\\\\\"publishconf\\\\\\\":\\\\\\\"{}\\\\\\\"}\\\"]}\"\n" +
                "}";
        JSONObject data =analysisFuntionUtil.getAnalysisResult(sourcejsonStr,targetjsonStr);

        System.out.println(data.toJSONString(data,SerializerFeature.SortField.MapSortField));
        Response response=RestResult.success(123456L, data);
        return  response;
    }

}
