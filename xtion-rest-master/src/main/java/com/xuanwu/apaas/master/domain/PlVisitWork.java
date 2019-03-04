package com.xuanwu.apaas.master.domain;

import java.util.Date;

/**
 * 租户库-拜访流定义(pl_visitwork)
 * @author yechh
 * @create 2019/2/21
 */
public class PlVisitWork {
    private Date platcreatetime;
    private Date platupdatetime;
    private Integer platcreateop;
    private Integer platupdateop;
    private Integer platstatus;
    private Integer workid;
    private String workname;
    private String descr;
    private String appcode;
    private Integer refpositionid;
    private Integer status;
    private Integer seq;
    private Integer isdefault;
    private Date starttime;
    private Date endtime;
    private String rules;
    private String settings;
    private String createaccountname;
    private String updateaccountname;

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

    public Integer getWorkid() {
        return workid;
    }

    public void setWorkid(Integer workid) {
        this.workid = workid;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    public Integer getRefpositionid() {
        return refpositionid;
    }

    public void setRefpositionid(Integer refpositionid) {
        this.refpositionid = refpositionid;
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

    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
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
