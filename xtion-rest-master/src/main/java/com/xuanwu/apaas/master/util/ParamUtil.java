package com.xuanwu.apaas.master.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParamUtil {

    public static Map format(Map map) {
        List<Object > list=new ArrayList<>();
        map.forEach((k, y) -> {
            if(y instanceof String && "".equals(y)){
                if(StringUtils.isBlank(y.toString()))
               list.add(k);
            }
        });
        for(int i=0;i<list.size();i++){
            map.remove(list.get(i));

        }
        return map;
    }

}
