package com.xuanwu.apaas.master.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuanwu.apaas.core.utils.PageInfo;
import com.xuanwu.apaas.core.utils.http.response.RestErrorType;
import com.xuanwu.apaas.core.utils.http.response.RestResult;
import com.xuanwu.apaas.master.service.VisitWorkService;
import com.xuanwu.apaas.master.service.VisitWorkStepService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拜访流程rest服务
 * @author yechh
 * @create 2019/2/222
 */
@Component
@Path("/visitwork")
@Api(value="拜访流程rest服务",produces="application/json")
public class VisitWorkRest {
    private Logger logger = LoggerFactory.getLogger(VisitWorkRest.class);

    @Autowired
    private VisitWorkService visitWorkService;

    @Autowired
    private VisitWorkStepService visitWorkStepService;


    @ApiOperation(value = "职位查询", notes = "职位查询接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getposition")
    public Response getPosition(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map){
        try {
            List<Map<String,Object>> res = visitWorkService.getPosition(map);
            return RestResult.success(reqId,res);
        } catch (Exception e) {
            logger.error("getposition error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());

        }
    }


    @ApiOperation(value = "拜访流及所属拜访步骤保存", notes = "拜访流保存接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map) {
        try {
            String msg = visitWorkService.save(map);
            JSONObject paramsObj = new JSONObject(map);
            JSONArray jsonArray =  paramsObj.getJSONArray("steps");
            String workid=MapUtils.getString(map,"workid");
            for(int i=0,len=jsonArray.size();i<len;i++){
                JSONObject temp=  jsonArray.getJSONObject(i);
                temp.put("workid",workid);
                msg=visitWorkStepService.save(temp);
            }
            if(StringUtils.isNotEmpty(msg)) {
                return RestResult.failure(reqId,RestErrorType.BIZ_ERROR,msg);
            }
            return RestResult.success(reqId,"success");
        } catch(Exception e) {
            logger.error("save error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @ApiOperation(value = "拜访流及所属拜访步骤获取", notes = "拜访流获取接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get")
    public Response getTemplate(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map) {
        try {
            Long workid = MapUtils.getLong(map, "workid");
            if(workid == null || workid.equals(0L)) {
                return RestResult.failure(reqId, RestErrorType.BIZ_ERROR, "参数拜访流主键workid为空");
            }
            Map res = visitWorkService.get(workid);
            List stepRes=visitWorkStepService.getList(workid);
            res.put("steps",stepRes);
            return RestResult.success(reqId,res);
        } catch(Exception e) {
            logger.error("get error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @ApiOperation(value = "拜访流列表", notes = "拜访流列表接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pageList")
    public Response pageList(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, Map map) {
        try {
            if(map==null){
                map=new HashMap();
            }
            PageInfo datas = visitWorkService.pageList(map);
            return RestResult.success(reqId,datas);
        }
        catch (Exception e) {
            logger.error("pageList error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }


}
