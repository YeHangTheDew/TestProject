package com.xuanwu.apaas.master.service.impl;

import com.xuanwu.apaas.core.dao.Operate;
import com.xuanwu.apaas.core.domain.SessionInfo;
import com.xuanwu.apaas.core.server.filter.SessionTokenFilter;
import com.xuanwu.apaas.core.utils.PageInfo;
import com.xuanwu.apaas.core.utils.ParamMap;
import com.xuanwu.apaas.master.dao.factory.TenantOperateFactory;
import com.xuanwu.apaas.master.domain.PlSceneTemplate;
import com.xuanwu.apaas.master.service.MasterSceneTemplateService;
import com.xuanwu.apaas.master.util.AnalysisFuntionUtil;
import com.xuanwu.apaas.master.util.ParamUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author yechh
 * @create 2019/2/20
 */
@Service
public class MasterSceneTemplateServiceImpl implements MasterSceneTemplateService {
    @Autowired
    AnalysisFuntionUtil analysisFuntionUtil;

    @Override
    public String save(Map map) throws Exception {
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());

        Long templatecode = MapUtils.getLong(map, "templatecode");
        if(templatecode == null || templatecode.equals(0L)) {
            return "参数模板编码templatecode为空";
        }

        String name = MapUtils.getString(map,"name");
        //默认为1，之后从其他表取值
        if(!map.containsKey("category")){
            map.put("category","1");
        }
        if(!map.containsKey("seq")){
            map.put("seq","1");
        }

        if(StringUtils.isEmpty(name)) {
            return "参数模板名称为空";
        }

        boolean isExistName = oper.isExist("isExistName", PlSceneTemplate.class, map);
        if(isExistName) {
            return "模板名称存在相同";
        }

        boolean isExist = oper.isExist("isExist", PlSceneTemplate.class, map);



        if(isExist) {
            map.put("updateaccountname",info.getUserName());
            map.put("updatetime",new Date());
            oper.update("update", PlSceneTemplate.class, ParamUtil.format(map));
        } else {
            map.put("createaccountname",info.getUserName());
            map.put("createtime",new Date());
            map.put("updateaccountname",info.getUserName());
            map.put("updatetime",new Date());
            oper.insert("insert", PlSceneTemplate.class, ParamUtil.format(map));
        }
        return null;
    }

    @Override
    public Map get(Long sceneTemplateCode) throws Exception {

        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        ParamMap map = ParamMap.getInstance().add("templatecode", sceneTemplateCode);
        return oper.singleResult("get", PlSceneTemplate.class, map);
    }

    @Override
    public PageInfo pageList(Map map) throws Exception {

        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());

        PageInfo<Map<String,Object>> page = oper.pageList("list",PlSceneTemplate.class,map);
        return page;
    }

    @Override
    public String checkName(Map map) throws Exception {
        String name = MapUtils.getString(map,"name");

        if(StringUtils.isEmpty(name)) {
            return "参数模板名称为空";
        }
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());

        boolean isExistName = oper.isExist("isExistName", PlSceneTemplate.class, map);
        if(isExistName) {
            return "模板名称存在相同";
        }
        return null;
    }

    @Override
    public String remove(Map map) throws Exception {

        String templatecodes = MapUtils.getString(map, "templatecodes");
        if(StringUtils.isEmpty(templatecodes)) {
            return "参数模板编码串templatecodes为空";
        }

        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        oper.delete("delete", PlSceneTemplate.class,map);

        return null;
    }

    @Override
    public PageInfo getByCategory(Map map) throws Exception {
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        return oper.pageList("getByCategory",PlSceneTemplate.class, map);
    }

}

