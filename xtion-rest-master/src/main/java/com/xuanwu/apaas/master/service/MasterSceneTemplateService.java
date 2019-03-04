package com.xuanwu.apaas.master.service;

import com.xuanwu.apaas.core.utils.PageInfo;

import java.util.Map;

/**
 * @author yechh
 * @create 2019/2/20
 */
public interface MasterSceneTemplateService {

    String save(Map map) throws Exception;

    Map get(Long sceneTemplateCode) throws Exception;

    PageInfo pageList(Map map) throws Exception;

    String checkName(Map map) throws Exception;

    String remove(Map map) throws Exception;

    PageInfo getByCategory(Map map) throws Exception;

}
