package com.xuanwu.apaas.master.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuanwu.apaas.core.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析自定义函数，返回处理结果
 *
 * @author yechh
 * @create 2019/2/25
 */
@Component
public class AnalysisFuntionUtil {
    @Autowired
    IdWorker idWorker;

    public Map<String, Object> confMap=new ConcurrentHashMap<String, Object>();
    public Map<String,String> confnMap=new ConcurrentHashMap<>();

    public  JSONObject getAnalysisResult(String sourceJsonstr,String targetJsonStr){
        confMap.clear();
        confnMap.clear();
        getMap(sourceJsonstr);
        return  analysis(JSON.parseObject(targetJsonStr));
    }
    /**
     * create by: yechh
     * description: 解析JSONObject对象，解析内部函数并返回赋值后的JSONObject对象
     * create time:  13:53
     * @return JSONObject
     */
    public JSONObject analysis(JSONObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            String value=jsonObject.getString(key);
            if(value.startsWith("{")){
                JSONObject jsonObj= JSON.parseObject(value);
                jsonObject.put(key,analysis(jsonObj));
            }
            else if(value.contains("[")&&value.contains("@")){
                JSONArray jsonArray=jsonObject.getJSONArray(key);
                JSONArray newArray=new JSONArray();
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonArrObj= jsonArray.getJSONObject(i);
                    JSONObject revalue=analysis(jsonArrObj);
                    newArray.add(revalue);
                }
                jsonObject.put(key,newArray);
            }
            else if(value.contains("@")){
                jsonObject.put(key,resolve(value));
            }
        }
        return jsonObject;
    }
    /**
     * create by: yechh
     * description: 函数拆分赋值  @函数名：取值项 +代表连接 函数名：取值项
     * create time: 2019/2/26 13:57
     * @return String 返回函数结果
     */
    public Object resolve(String s) {
         if(s.startsWith("@")){
            s=s.substring(1,s.length());
         }
         Object reVaule="";
         if(!s.contains(":")){
             return s;
         }
         else if(s.contains("+")){
            String[] spiltArry= s.split("\\+");
            StringBuffer valueBuffer=new StringBuffer("");
            for(int i=0;i<spiltArry.length;i++){
                valueBuffer.append(resolve(spiltArry[i]));
            }
             reVaule=valueBuffer.toString();

         }else{
             String[] strArray=s.split(":");
             reVaule=getFunctionValue(strArray[0],strArray[1]);

         }
        return reVaule;
    }
    /**
     * create by: yechh
     * description: 获取函数值
     * create time: 2019/2/26 9:10
     * @return
     */
    public Object getFunctionValue(String key, String value){
        if("genid".equals(key)){
            return String.valueOf(genid());

        }else if("getconfvaluebykey".equals(key)){
            return getconfvaluebykey(value);

        }else if("getconfnamebykey".equals(key)){
            return getconfnamebykey(value);

        }
        else if("defaultvalue".equals(key)){
            return defaultvalue(value);
        }else{
            return "no such function";
        }

    }

    public long genid(){
        return idWorker.genId();
    }
    public Object getconfvaluebykey(String key){

        return confMap.get(key);
    }
    public String getconfnamebykey(String key){
        return  confnMap.get(key);
    }
    public String defaultvalue(String key){
        return key;
    }

    public  void getMap(String sourceJson){
        JSONObject jsonObject =JSON.parseObject(sourceJson);
        JSONArray items = jsonObject.getJSONArray("conf");
        JSONObject row = null;
        for(int i=0; i<items.size(); i++){
            row = items.getJSONObject(i);
            //confMap.put(row.getString("key"),row.getString("value"));
            confnMap.put(row.getString("key"),row.getString("name"));
            jsonObject.put(row.getString("key"),row.getString("value"));
        }
        jsonObject.remove("conf");
        confMap=jsonObject.getInnerMap();
    }
}
