package com.xuanwu.apaas.master.rest;

import com.xuanwu.apaas.core.utils.PageInfo;
import com.xuanwu.apaas.core.utils.http.response.RestErrorType;
import com.xuanwu.apaas.core.utils.http.response.RestResult;
import com.xuanwu.apaas.master.service.MasterSceneTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场景模板rest服务
 * @author yechh
 * @create 2019/2/20
 */
@Path("/scenetemplate")
@Component
@Api(value="场景模板rest服务",produces="application/json")
public class SceneTemplateRest {

    private Logger logger = LoggerFactory.getLogger(SceneTemplateRest.class);

    @Autowired
    private MasterSceneTemplateService masterSceneTemplateService;


    @ApiOperation(value = "采集类获取", notes = "场景模板采集类获取接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getCategory")
    public Response getCategory(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map){
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> data=new HashMap<>();
        data.put("categorycode","1");
        data.put("categoryname","采集类");
        list.add(data);
        return RestResult.success(reqId,list);

    }

    @ApiOperation(value = "根据采集类获取场景模板", notes = "场景模板获取接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getByCategory")
    public Response getbyCategory(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map){
        try {
            PageInfo res = masterSceneTemplateService.getByCategory(map);
            return RestResult.success(reqId,res.getData());
        } catch(Exception e) {
            logger.error("get error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }

    }


    @ApiOperation(value = "场景模板列表获取", notes = "场景模板获取接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pageList")
    public Response pageList(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map) {
        try {
            PageInfo datas = masterSceneTemplateService.pageList(map);
            return RestResult.success(reqId,datas);
        }
        catch (Exception e) {
            logger.error("pageList error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/checkName")
    public Response checkName( @HeaderParam("req_id") long reqId,  Map map) {

        try {
            String msg = masterSceneTemplateService.checkName(map);
            if(StringUtils.isNotEmpty(msg)) {
                return RestResult.failure(reqId, RestErrorType.BIZ_ERROR,msg);
            }
        }catch (Exception e) {
            logger.error("checkName error:", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
        return RestResult.success(reqId,"success");
    }


    @ApiOperation(value = "场景模板保存", notes = "场景模板保存接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map) {
        try {
            String msg = masterSceneTemplateService.save(map);
            if(StringUtils.isNotEmpty(msg)) {
                return RestResult.failure(reqId,RestErrorType.BIZ_ERROR,msg);
            }
            return RestResult.success(reqId,"success");
        } catch(Exception e) {
            logger.error("save error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @ApiOperation(value = "场景模板获取", notes = "场景模板获取接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get")
    public Response getTemplate(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map) {
        try {
            Long templatecode = MapUtils.getLong(map, "templatecode");
            if(templatecode == null || templatecode.equals(0L)) {
                return RestResult.failure(reqId, RestErrorType.BIZ_ERROR, "参数模板编码templatecode为空");
            }
            Map res = masterSceneTemplateService.get(templatecode);
            return RestResult.success(reqId,res);
        } catch(Exception e) {
            logger.error("get error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @ApiOperation(value = "场景模板删除", notes = "场景模板删除接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/remove")
    public Response remove(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map) {
        try {
            String msg = masterSceneTemplateService.remove(map);
            if(StringUtils.isNotEmpty(msg)) {
                return RestResult.failure(reqId,RestErrorType.BIZ_ERROR,msg);
            }
            return RestResult.success(reqId,"success");

        } catch (Exception e) {
            logger.error("delete error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
