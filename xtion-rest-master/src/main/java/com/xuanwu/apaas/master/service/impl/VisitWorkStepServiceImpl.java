package com.xuanwu.apaas.master.service.impl;

import com.xuanwu.apaas.core.dao.Operate;
import com.xuanwu.apaas.core.domain.SessionInfo;
import com.xuanwu.apaas.core.server.filter.SessionTokenFilter;
import com.xuanwu.apaas.master.dao.factory.TenantOperateFactory;
import com.xuanwu.apaas.master.domain.PlVisitWorkStep;
import com.xuanwu.apaas.master.service.VisitWorkStepService;
import com.xuanwu.apaas.master.util.ParamUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class VisitWorkStepServiceImpl implements VisitWorkStepService {
    @Override
    public List getList(Long workid) throws Exception{
        Map<String,Object> map=new HashMap();
        map.put("workid",workid);
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        return  oper.list("getList",PlVisitWorkStep.class, map);
    }

    @Override
    public String save(Map map) throws Exception {
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        Long stepid=MapUtils.getLong(map,"stepid");
        boolean isExist = oper.isExist("isExist", PlVisitWorkStep.class, map);
        if(!map.containsKey("uimap")){
            map.put("uimap","[]");
        }
        if(!map.containsKey("rules")){
            map.put("rules","[]");
        }
        if(!map.containsKey("settings")){
            map.put("settings","[]");
        }
        if(isExist){
            if(!map.containsKey("platupdatetime")){
                map.put("platupdatetime",new Date());
            }
            oper.update("update",PlVisitWorkStep.class,ParamUtil.format(map));
        }else {
            if(!map.containsKey("platcreatetime")){
                map.put("platcreatetime",new Date());
            }
            map.put("platupdateop",info.getAccountCode());
            if(!map.containsKey("seq")){
                map.put("seq","1");
            }
            map.put("platcreateop",info.getAccountCode());
            map.put("platstatus","1");
            map.put("status","1");
            map.put("updateaccountname",info.getUserName());
            if(!map.containsKey("platupdatetime")){
                map.put("platupdatetime",new Date());
            }

            oper.insert("insert", PlVisitWorkStep.class, ParamUtil.format(map));
        }
        return null;
    }
}
