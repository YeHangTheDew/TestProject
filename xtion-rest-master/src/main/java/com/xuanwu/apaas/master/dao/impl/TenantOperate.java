package com.xuanwu.apaas.master.dao.impl;
import com.xuanwu.apaas.core.dao.Operate;
import com.xuanwu.apaas.core.dao.factory.LocalSession;
import com.xuanwu.apaas.dbrouter.util.DataBaseType;
import com.xuanwu.apaas.master.dao.factory.TenantSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author rongdi
 * @description mybatis操作实现类
 * @date 2015年11月4日
 */
@Configuration
public class TenantOperate extends Operate {

	private SqlSessionFactory sqlSessionFactory;

	static TenantSessionFactory tenantSessionFactory;

	public TenantOperate() {
	}

	public TenantOperate(Long tenantCode, Long productCode, DataBaseType dbType) {
		sqlSessionFactory = tenantSessionFactory.getTenantDruidSessionFactory(tenantCode, productCode, dbType);
	}
	
	@Override
	public SqlSession getCurrentSession() {
		return LocalSession.getCurrentSqlSession(sqlSessionFactory);
	}

	@Override
	public SqlSession openSession() {
		return sqlSessionFactory.openSession();
	}
	
	@Autowired
	public void setXwSessionFactory(TenantSessionFactory tenantSessionFactory) {
		TenantOperate.tenantSessionFactory = tenantSessionFactory;
	}

}
