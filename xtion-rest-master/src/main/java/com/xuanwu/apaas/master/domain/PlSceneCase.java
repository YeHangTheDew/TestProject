package com.xuanwu.apaas.master.domain;

import java.util.Date;

/**
 * 租户库-场景实例数据(pl_scenecase)
 * @author yechh
 * @create 2019/2/21
 */
public class PlSceneCase {
    private Integer casecode;
    private Integer templatecode;
    private String casename;
    private String casedescr;
    private String contextjson;
    private String mappingjson;
    private String uimap;
    private Integer status;
    private Integer publishstatus;
    private Date createtime;
    private Date updatetime;
    private String createaccountname;
    private String updateaccountname;

    public Integer getCasecode() {
        return casecode;
    }

    public void setCasecode(Integer casecode) {
        this.casecode = casecode;
    }

    public Integer getTemplatecode() {
        return templatecode;
    }

    public void setTemplatecode(Integer templatecode) {
        this.templatecode = templatecode;
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getCasedescr() {
        return casedescr;
    }

    public void setCasedescr(String casedescr) {
        this.casedescr = casedescr;
    }

    public String getContextjson() {
        return contextjson;
    }

    public void setContextjson(String contextjson) {
        this.contextjson = contextjson;
    }

    public String getMappingjson() {
        return mappingjson;
    }

    public void setMappingjson(String mappingjson) {
        this.mappingjson = mappingjson;
    }

    public String getUimap() {
        return uimap;
    }

    public void setUimap(String uimap) {
        this.uimap = uimap;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPublishstatus() {
        return publishstatus;
    }

    public void setPublishstatus(Integer publishstatus) {
        this.publishstatus = publishstatus;
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
