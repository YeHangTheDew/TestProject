package com.xuanwu.apaas.master.service.impl;

import com.xuanwu.apaas.core.dao.Operate;
import com.xuanwu.apaas.core.domain.SessionInfo;
import com.xuanwu.apaas.core.server.filter.SessionTokenFilter;
import com.xuanwu.apaas.core.utils.PageInfo;
import com.xuanwu.apaas.core.utils.ParamMap;
import com.xuanwu.apaas.master.dao.factory.TenantOperateFactory;
import com.xuanwu.apaas.master.domain.PlVisitWork;
import com.xuanwu.apaas.master.service.VisitWorkService;
import com.xuanwu.apaas.master.util.ParamUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class VisitWorkServiceImpl implements VisitWorkService {

    @Override
    public String save(Map map) throws Exception {
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        Long workid=MapUtils.getLong(map, "workid");
        if(workid == null || workid.equals(0L)) {
            return "参数拜访流ID：workid为空";
        }
        boolean isExist = oper.isExist("isExist", PlVisitWork.class, map);
        if(!isExist){
            if(!map.containsKey("platcreatetime")){
                map.put("platcreatetime",new Date());
            }
            map.put("platcreateop",info.getAccountCode());
            map.put("createaccountname",info.getUserName());
            if(!map.containsKey("platupdatetime")){
                map.put("platupdatetime",new Date());
            }
            map.put("updateaccoutname",info.getUserName());
            map.put("platstatus","1");
            map.put("status","1");
            if(!map.containsKey("seq")){
                map.put("seq","1");
            }
            oper.insert("insert", PlVisitWork.class, ParamUtil.format(map));
        }else{
            if(!map.containsKey("platupdatetime")){
                map.put("platupdatetime",new Date());
            }
            map.put("updateaccoutname",info.getUserName());
            map.put("platupdateop",info.getAccountCode());
            oper.update("update",PlVisitWork.class,ParamUtil.format(map));
        }


        return null;
    }

    @Override
    public Map get(Long workid) throws Exception {
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        ParamMap map = ParamMap.getInstance().add("workid", workid);
        return oper.singleResult("get", PlVisitWork.class, map);
    }

    @Override
    public PageInfo pageList(Map map) throws Exception {
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        PageInfo<Map<String,Object>> page = oper.pageList("list",PlVisitWork.class,map);
        return page;
    }


    @Override
    public List getPosition(Map map) throws Exception {
        SessionInfo info = SessionTokenFilter.getSession();
        Operate oper= TenantOperateFactory.createWriteableOperate(info.getTenantCode(),info.getProductCode());
        return  oper.list("getPosition",PlVisitWork.class, map);

    }
}
