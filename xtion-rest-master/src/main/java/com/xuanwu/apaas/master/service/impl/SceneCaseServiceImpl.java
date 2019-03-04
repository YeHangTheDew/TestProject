package com.xuanwu.apaas.master.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xuanwu.apaas.core.dao.Operate;
import com.xuanwu.apaas.core.domain.SessionInfo;
import com.xuanwu.apaas.core.server.filter.SessionTokenFilter;
import com.xuanwu.apaas.core.utils.IdWorker;
import com.xuanwu.apaas.core.utils.PageInfo;
import com.xuanwu.apaas.core.utils.ParamMap;
import com.xuanwu.apaas.master.dao.factory.TenantOperateFactory;
import com.xuanwu.apaas.master.domain.PlSceneCase;
import com.xuanwu.apaas.master.service.SceneCaseService;
import com.xuanwu.apaas.master.util.ParamUtil;
import com.xuanwu.apaas.metasdk.dao.factory.MetaOperateFactory;
import com.xuanwu.apaas.metasdk.meta.domain.MetaPageProtocol;
import com.xuanwu.apaas.metasdk.service.BizFunctionService;
import com.xuanwu.apaas.metasdk.service.impl.BaseService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
/**
 * @author yechh
 * @create 2019/2/21
 */
@Service
public class SceneCaseServiceImpl extends BaseService implements SceneCaseService {

    @Autowired
    private BizFunctionService functionService;

    @Autowired
    private IdWorker idWorker;
    @Override
    public String save(Map map) throws Exception {
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());

        Long casecode = MapUtils.getLong(map, "casecode");
        if(casecode == null || casecode.equals(0L)) {
            return "参数实例编码casecode为空";
        }
        String casename = MapUtils.getString(map,"casename");
        if(!map.containsKey("status")){
            map.put("status","1");
        }
        if(!map.containsKey("publishstatus")){
            map.put("publishstatus","2");
        }

        if(StringUtils.isEmpty(casename)) {
            return "参数实例名称为空";
        }
        boolean isExistName = oper.isExist("isExistName", PlSceneCase.class, map);
        if(isExistName) {
            return "模板名称存在相同";
        }

        boolean isExist = oper.isExist("isExist", PlSceneCase.class, map);



        if(isExist) {
            map.put("updateaccountname",info.getUserName());
            map.put("updatetime",new Date());
            oper.update("update", PlSceneCase.class, ParamUtil.format(map));
        } else {
            map.put("updateaccountname",info.getUserName());
            map.put("updatetime",new Date());
            map.put("createaccountname",info.getUserName());
            map.put("createtime",new Date());
            oper.insert("insert", PlSceneCase.class, ParamUtil.format(map));
        }
        return null;
    }

    @Override
    public Map get(Long casecode) throws Exception {

        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        ParamMap map = ParamMap.getInstance().add("casecode", casecode);
        return oper.singleResult("get", PlSceneCase.class, map);
    }

    @Override
    public void savePro(MetaPageProtocol protocol) throws Exception {
        Operate oper = MetaOperateFactory.createReadAndWriteableOperate();
        boolean isUpdate = oper.isExist("isExist",MetaPageProtocol.class,protocol);
        if(isUpdate) {
            protocol.setUpdatetime(new Date());
            oper.update("update",MetaPageProtocol.class,protocol);
        } else {
            protocol.setCreatetime(new Date());
            protocol.setUpdatetime(new Date());
            oper.insert("insert",MetaPageProtocol.class,protocol);
        }
    }

    @Override
    public String saveFun(Map map, long functiondefid) throws Exception {

        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        String functionCode=MapUtils.getString(map,"functioncode");
        if(StringUtils.isBlank(functionCode)){
                return "功能编码为空";
        }

        //拼装grantfunction的入参
        ParamMap params = ParamMap.getInstance().
                add("marktype",map.get("marktype")).
                add("grantfunctioncode",map.get("grantfunctioncode")).
                add("grantfunctionname", map.get("grantfunctionname")).
                add("parentgrantfunctioncode", map.get("parentgrantfunctioncode")).
                add("functiondefid", functiondefid).
                add("pagecode",map.get("pagecode")).
                add("functioncode",map.get("functioncode")).
                add("clientcategory", map.get("clientcategory")).
                add("productcode",info.getProductCode());
        params.put("updateaccountname",info.getUserName());
        params.put("updatetime",new Date());
        params.put("createtime",new Date());
        params.put("status", 1);

        Integer seq = oper.singleResult("getMaxSeqGrantFunction", PlSceneCase.class,
                ParamMap.getInstance().add("productcode", info.getProductCode()).add("clientcategory", map.get("clientcategory")));
        params.put("seq", (seq == null) ? 1 : seq);

        boolean isExistGrant=oper.isExist("isExistGrantFunction",PlSceneCase.class,map);
        boolean isSigenleGrant=oper.isExist("isSigelGrantFunction",PlSceneCase.class,map);

        if(!isExistGrant&&!isSigenleGrant) {
            oper.insert("insertGrantFunction",PlSceneCase.class,params);
        }

        boolean isExistAssignFunction=oper.isExist("isExistAssignFunction",PlSceneCase.class,map);


/*
        BizFunction bizFunction=functionService.findBizFunctionByCode(MetaModelType.IMPLEMENT_CONFIG.getType(), info.getProductVersionCode(), info.getTenantCode(), MapUtils.getLong(map,"functionCode"));
*/

        //拼装AssignFunction的入参
        ParamMap assignParams=ParamMap.getInstance().add("assignfunctioncode",idWorker.genId()).add("grantfunctioncode",map.get("grantfunctioncode"))
                .add("functioncode",map.get("functioncode")).add("createtime",new Date()).add("updatetime",new Date()).add("createop",info.getAccountCode()).
                        add("updateop",info.getAccountCode()).add("functiondefid", functiondefid).
                add("functioncategory",map.get("functioncategory")).
                        add("metaobjectcode",map.get("metaobjectcode")).add("pagecode",map.get("pagecode"));

        assignParams.put("platcreatetime",new Date());
        assignParams.put("platcreateop",info.getAccountCode());
        assignParams.put("updateaccountname",info.getUserName());
        assignParams.put("updatetime",new Date());
        assignParams.put("createtime",new Date());
        if (!isExistAssignFunction){
            oper.insert("insertAssignFunction",PlSceneCase.class,assignParams);
        }

        return null;
    }

    @Override
    public void savePageNavigation(JSONObject map, long functiondefid) throws Exception{
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());

        ParamMap pagenaParams=ParamMap.getInstance().
                add("parentid",map.get("parentid")).
                add("title",map.get("title")).
                add("category",map.get("category")).
                add("id",map.get("id")).
                add("clientcategory",map.get("clientcategory")).
                add("pagecode",map.get("pagecode")).
                add("datacode",map.get("datacode")).
                add("description",map.get("description")).
                add("key",map.get("key")).
                add("functioncode",map.get("functioncode"));


        pagenaParams.put("functiondefid",functiondefid);
        pagenaParams.put("platcreatetime",new Date());
        pagenaParams.put("platcreateop",info.getAccountCode());
        pagenaParams.put("updateaccountname",info.getUserName());
        pagenaParams.put("updatetime",new Date());
        pagenaParams.put("platstatus","1");
        pagenaParams.put("status","1");
        pagenaParams.put("marktype","1");
        Long key=MapUtils.getLong(map,"key");

        String parentid=MapUtils.getString(map,"parentid");
        String keypath="";
        if(parentid != null) {
            Map<String,Object> pdata = oper.singleResult("getPageNavigation",PlSceneCase.class,ParamMap.getInstance().add("id",parentid));
            String pkeypath = MapUtils.getString(pdata,"keypath");
            keypath = pkeypath+key+".";
        } else {
            keypath = keypath + ".";
        }
        pagenaParams.put("keypath",keypath);
        Integer seq=oper.singleResult("getMaxSeqPageNavigation",PlSceneCase.class,pagenaParams);
        pagenaParams.put("seq",(seq == null) ? 1 : seq);
        pagenaParams.put("level",getCount(keypath));

        boolean isExistPage=oper.isExist("isExistPageNavigation",PlSceneCase.class,map);

        boolean isSiglePage=oper.isExist("isSinglePageNavigation",PlSceneCase.class,map);

        if(!isExistPage&&!isSiglePage){
            oper.insert("insertPageNavigation",PlSceneCase.class,pagenaParams);
        }

    }

    private int getCount(String keypath) {
        String[] array = keypath.split("\\.");
        if (array != null)
        {
            return array.length;
        }
        return  0;
    }

    @Override
    public long getfunctiondefid(String appcode) throws Exception {
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        ParamMap paramMap=ParamMap.getInstance().add("appcode",appcode);
        long functiondefid=oper.singleResult("getFunctiondefid",PlSceneCase.class,paramMap);
        return functiondefid;
    }

    @Override
    public PageInfo pageList(Map map) throws Exception {

        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());

        PageInfo<Map<String,Object>> page = oper.pageList("list",PlSceneCase.class,map);
        return page;
    }


}
