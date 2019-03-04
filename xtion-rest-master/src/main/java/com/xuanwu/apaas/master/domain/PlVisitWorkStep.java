package com.xuanwu.apaas.master.domain;

import java.util.Date;

/**
 * 租户库-拜访流步骤定义(pl_visitworkstep)
 * @author yechh
 * @create 2019/2/21
 */
public class PlVisitWorkStep {
    private Date platcreatetime;
    private Date platupdatetime;
    private Integer platcreateop;
    private Integer platupdateop;
    private Integer platstatus;
    private Integer stepid;
    private Integer workid;
    private String stepname;
    private Integer casecode;
    private String uimap;
    private Integer status;
    private Integer seq;
    private Date starttime;
    private Date endtime;
    private Integer category;
    private String categoryname;
    private String rules;
    private String settings;

    public Date getPlatcreatetime() {
        return platcreatetime;
    }

    public void setPlatcreatetime(Date platcreatetime) {
        this.platcreatetime = platcreatetime;
    }

    public Date getPlatupdatetime() {
        return platupdatetime;
    }

    public void setPlatupdatetime(Date platupdatetime) {
        this.platupdatetime = platupdatetime;
    }

    public Integer getPlatcreateop() {
        return platcreateop;
    }

    public void setPlatcreateop(Integer platcreateop) {
        this.platcreateop = platcreateop;
    }

    public Integer getPlatupdateop() {
        return platupdateop;
    }

    public void setPlatupdateop(Integer platupdateop) {
        this.platupdateop = platupdateop;
    }

    public Integer getPlatstatus() {
        return platstatus;
    }

    public void setPlatstatus(Integer platstatus) {
        this.platstatus = platstatus;
    }

    public Integer getStepid() {
        return stepid;
    }

    public void setStepid(Integer stepid) {
        this.stepid = stepid;
    }

    public Integer getWorkid() {
        return workid;
    }

    public void setWorkid(Integer workid) {
        this.workid = workid;
    }

    public String getStepname() {
        return stepname;
    }

    public void setStepname(String stepname) {
        this.stepname = stepname;
    }

    public Integer getCasecode() {
        return casecode;
    }

    public void setCasecode(Integer casecode) {
        this.casecode = casecode;
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }
}
