package com.xuanwu.apaas.master.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xuanwu.apaas.core.domain.SessionInfo;
import com.xuanwu.apaas.core.server.filter.SessionTokenFilter;
import com.xuanwu.apaas.core.utils.JsonUtil;
import com.xuanwu.apaas.core.utils.PageInfo;
import com.xuanwu.apaas.core.utils.http.response.RestErrorType;
import com.xuanwu.apaas.core.utils.http.response.RestResult;
import com.xuanwu.apaas.master.service.MasterSceneTemplateService;
import com.xuanwu.apaas.master.service.SceneCaseService;
import com.xuanwu.apaas.master.util.AnalysisFuntionUtil;
import com.xuanwu.apaas.metasdk.meta.domain.MetaPageProtocol;
import com.xuanwu.apaas.metasdk.service.BizPageService;
import com.xuanwu.apaas.metasdk.service.PageProtocolService;
import com.xuanwu.apaas.uisdk.service.LanguageService;
import com.xuanwu.apaas.uisdk.service.UITransferService;
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
import java.util.*;

/**
 * 场景实例数据rest服务
 * @author yechh
 * @create 2019/2/20
 */
@Component
@Path("/scenecase")
@Api(value="场景实例数据rest服务",produces="application/json")
public class SceneCaseRest {

    private Logger logger = LoggerFactory.getLogger(SceneCaseRest.class);

    @Autowired
    private SceneCaseService sceneCaseService;

    @Autowired
    private MasterSceneTemplateService masterSceneTemplateService;

    @Autowired
    private BizPageService bizPageService;

    @Autowired
    AnalysisFuntionUtil analysisFuntionUtil;

    @Autowired
    LanguageService languageService;
    @Autowired
    UITransferService uiTransferService;
    @Autowired
    PageProtocolService pageProtocolService;

    @ApiOperation(value = "场景实例列表", notes = "场景实例列表接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pageList")
    public Response pageList(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, LinkedHashMap map) {
        try {
            if(map==null){
                map=new LinkedHashMap();
            }
            PageInfo datas = sceneCaseService.pageList(map);
            return RestResult.success(reqId,datas);
        }
        catch (Exception e) {
            logger.error("pageList error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }



    @ApiOperation(value = "场景实例保存", notes = "场景实例保存接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, LinkedHashMap map) {
        try {
            String msg = sceneCaseService.save(map);
            if(StringUtils.isNotEmpty(msg)) {
                return RestResult.failure(reqId,RestErrorType.BIZ_ERROR,msg);
            }
            return RestResult.success(reqId,"success");
        } catch(Exception e) {
            logger.error("save error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }



    @ApiOperation(value = "场景实例获取", notes = "场景实例获取接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get")
    public Response getCase(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, LinkedHashMap map) {
        try {
            Long casecode = MapUtils.getLong(map, "casecode");
            if(casecode == null || casecode.equals(0L)) {
                return RestResult.failure(reqId, RestErrorType.BIZ_ERROR, "参数实例编码casecode为空");
            }
            Map res = sceneCaseService.get(casecode);
            return RestResult.success(reqId,res);
        } catch(Exception e) {
            logger.error("get error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @ApiOperation(value="实例发布" ,notes="实例发布接口")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/publish")
    public Response publish(@ApiParam(value="请求id") @HeaderParam("req_id") Long reqId, LinkedHashMap map){
        try {
        SessionInfo info = SessionTokenFilter.getSession();
        JSONObject uiJson=new JSONObject(map);
        JSONArray jsonArray = uiJson.getJSONArray("uis");

        for(int i=0,len=jsonArray.size();i<len;i++){
            JSONObject temp=  jsonArray.getJSONObject(i);
            //保存配置协议
            Map<String,Object> page =bizPageService.savePage(info.getMetamodeltype(),info.getProductVersionCode(),info.getTenantCode(),temp);

            MetaPageProtocol pageProtocol = new MetaPageProtocol(MapUtils.getLong(page,"metamodelcode"),MapUtils.getLong(page,"code"));
            String confjson = MapUtils.getString(page,"confjson","{}");
            Map<String,Object> reportDesc = MapUtils.getMap(map,"reportdesc");
            //ui协议转换
            if(null == reportDesc){
                org.json.JSONObject pageJson = uiTransferService.uiConfig2ui(new org.json.JSONObject(confjson),info.getProductVersionCode(),info.getTenantCode(),info.getProductCode());
                //保存ui协议中需要翻译的
                languageService.filterAndSave(info.getTenantCode(),info.getProductCode(),pageJson);
                pageProtocol.setProtocol(pageJson.toString());
            }else{
                reportDesc.remove("info");
                reportDesc.remove("clientcategorycode");
                reportDesc.remove("directorytypecode");
                reportDesc.remove("pagetemplatecode");
                reportDesc.remove("confjson");
                reportDesc.remove("status");
                String rp = JsonUtil.serialize(map);
                pageProtocol.setProtocol(rp);
            }

            //保存转换后的ui协议
            uiTransferService.savePageProtocal(pageProtocol);
            pageProtocolService.clearUiCache(info.getTenantCode(), info.getProductVersionCode(), MapUtils.getLong(page, "code"));
        }
            pageProtocolService.clearAllCache(info.getProductVersionCode());
        Map caseMap=sceneCaseService.get(MapUtils.getLong(map,"casecode"));
        if(MapUtils.getIntValue(caseMap,"publishstatus")==1){
            return RestResult.success(reqId,"success :update ui");
        }
        caseMap=explainMappingjson(caseMap);
        caseMap.put("publishstatus","1");
        sceneCaseService.save(caseMap);
        } catch (Exception e) {
            logger.error("get error,occured by", e);
            return RestResult.failure(reqId,RestErrorType.INTERNAL_SERVER_ERROR,e.getMessage());
        }
        return RestResult.success(reqId,"publish success");

    }

    /**
     * create by: yechh
     * description: 从contextjson解析mappingjson并授权、发布导航定义
     * create time: 2019/2/26 16:54
     * @return
     */
    public Map explainMappingjson(Map caseMap) throws Exception{
        Map templateMap=masterSceneTemplateService.get(MapUtils.getLong(caseMap,"templatecode"));
        String contextJosnStr= MapUtils.getString(caseMap,"contextjson");
        String mappingJsonStr=MapUtils.getString(templateMap,"mappingjson");
        JSONObject jsonObj=analysisFuntionUtil.getAnalysisResult(contextJosnStr,mappingJsonStr);
        caseMap.put("mappingjson",jsonObj.toJSONString(jsonObj,SerializerFeature.SortField.MapSortField));
        String appcode=jsonObj.getString("appcode");
        long functiondefid=sceneCaseService.getfunctiondefid(appcode);

        JSONArray mpjsonA=jsonObj.getJSONArray("uimap");
        List<Map<String,String>> uiList=new ArrayList<>();
        for(int i=0;i<mpjsonA.size();i++){
            Map uiMap=new LinkedHashMap();
            JSONObject mptemp=mpjsonA.getJSONObject(i);
            uiMap.put("key",mptemp.getString("key"));
            uiMap.put("code",mptemp.getString("code"));
            uiList.add(uiMap);
            saveFun(mptemp.getJSONObject("publishconf"),functiondefid);
        }
        String uijson = JSON.toJSONString(uiList);
        caseMap.put("uimap",uijson);
        return caseMap;

    }

    /**
     * create by: yechh
     * description: 授权、发布导航定义
     * create time: 2019/2/26 17:26
     * @return
     */
    public void saveFun(JSONObject jsonObject,long functiondefid) throws Exception{
        JSONArray grantList=jsonObject.getJSONArray("grantfunctions");
        JSONArray pageList=jsonObject.getJSONArray("pagenavigations");
        for(int i=0;i<grantList.size();i++){
            JSONObject jsObj=grantList.getJSONObject(i);
            sceneCaseService.saveFun(jsObj,functiondefid);
        }
        for (int i=0;i<pageList.size();i++)
        {
            JSONObject jsObj=pageList.getJSONObject(i);
            sceneCaseService.savePageNavigation(jsObj,functiondefid);
        }

    }



}
