package com.xuanwu.apaas.master.service;

import java.util.List;
import java.util.Map;

public interface VisitWorkStepService {
    List getList(Long workid) throws Exception;

    String save(Map map) throws Exception;
}
