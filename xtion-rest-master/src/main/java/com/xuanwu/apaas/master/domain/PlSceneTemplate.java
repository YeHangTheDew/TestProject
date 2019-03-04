package com.xuanwu.apaas.master.domain;

import java.util.Date;

/**
 * 租户库-场景模板
 * @author yechh
 * @create 2019/2/20
 */
public class PlSceneTemplate {

    private Long templatecode;

    private String name;

    private String descr;

    //新增
    private String mappingjson;


    private String objectjson;

    private String logicjson;

    private String  uijson;

    /**
     * 暂时默认为1，后续可能通过类别表获取
     */
    private Integer category=1;

    private String categroyname;

    private String contextjson;

    private Integer status;

    private Integer seq;

    private Date createtime;
    private Date updatetime;

    private String createaccountname;
    private String updateaccountname;


    public Long getTemplatecode() {
        return templatecode;
    }

    public void setTemplatecode(Long templatecode) {
        this.templatecode = templatecode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getObjectjson() {
        return objectjson;
    }

    public void setObjectjson(String objectjson) {
        this.objectjson = objectjson;
    }

    public String getLogicjson() {
        return logicjson;
    }

    public void setLogicjson(String logicjson) {
        this.logicjson = logicjson;
    }

    public String getUijson() {
        return uijson;
    }

    public void setUijson(String uijson) {
        this.uijson = uijson;
    }

    public String getContextjson() {
        return contextjson;
    }

    public void setContextjson(String contextjson) {
        this.contextjson = contextjson;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getMappingjson() {
        return mappingjson;
    }

    public void setMappingjson(String mappingjson) {
        this.mappingjson = mappingjson;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategroyname() {
        return categroyname;
    }

    public void setCategroyname(String categroyname) {
        this.categroyname = categroyname;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreateaccountname() {
        return createaccountname;
    }

    public void setCreateaccountname(String createaccountname) {
        this.createaccountname = createaccountname;
    }

    public String getUpdateaccountname() {
        return updateaccountname;
    }

    public void setUpdateaccountname(String updateaccountname) {
        this.updateaccountname = updateaccountname;
    }
}
