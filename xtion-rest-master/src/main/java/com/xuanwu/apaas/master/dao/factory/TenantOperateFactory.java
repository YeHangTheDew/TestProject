package com.xuanwu.apaas.master.dao.factory;
import com.xuanwu.apaas.core.dao.Operate;
import com.xuanwu.apaas.dbrouter.util.DataBaseType;
import com.xuanwu.apaas.master.dao.impl.TenantOperate;

/**
 * @author rongdi
 * @create 2017-04-25 15:08
 **/
public class TenantOperateFactory {

    public static Operate createReadableOperate(Long tenantCode, Long productCode) {
        return new TenantOperate(tenantCode, productCode, DataBaseType.READ);
    }

    public static Operate createWriteableOperate(Long tenantCode, Long productCode) {
        return new TenantOperate(tenantCode, productCode, DataBaseType.READ_AND_WRITE);
    }


    public static Operate createReportOperate(Long tenantCode, Long productCode) {
        return new TenantOperate(tenantCode, productCode, DataBaseType.REPORT);
    }
}
