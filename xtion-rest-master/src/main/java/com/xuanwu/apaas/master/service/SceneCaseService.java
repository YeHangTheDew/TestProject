package com.xuanwu.apaas.master.service;

import com.alibaba.fastjson.JSONObject;
import com.xuanwu.apaas.core.utils.PageInfo;
import com.xuanwu.apaas.metasdk.meta.domain.MetaPageProtocol;

import java.util.Map;
/**
 * @author yechh
 * @create 2019/2/21
 */
public interface SceneCaseService {

    String save(Map map) throws Exception;

    PageInfo pageList(Map map) throws Exception;

    Map get(Long caseCode) throws Exception;


    void savePro(MetaPageProtocol protocol) throws Exception;

    String saveFun(Map jsonMap, long functiondefid) throws Exception;

    void savePageNavigation(JSONObject jsObj, long functiondefid) throws Exception;

    long getfunctiondefid(String appcode) throws Exception;
}
