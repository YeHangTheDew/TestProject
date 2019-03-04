package com.xuanwu.apaas.master.service;

import com.xuanwu.apaas.core.utils.PageInfo;

import java.util.List;
import java.util.Map;

public interface VisitWorkService {
    String save(Map map) throws Exception;

    Map get(Long workid) throws Exception;

    PageInfo pageList(Map map) throws Exception;

    List getPosition(Map map) throws Exception;
}
