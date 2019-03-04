package com.xuanwu.apaas.master.dao.factory;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rongdi
 * @create 2017-04-25 15:08
 **/
public class TenantDataSource extends AbstractRoutingDataSource {
    private final static Map<String, DataSource> tenantDataSources = new ConcurrentHashMap<>();
    private final static ThreadLocal<String> currKey = new ThreadLocal<String>();
    public void setKey(String key) {
        currKey.set(key);
    }
    public String getKey() {
        return currKey.get();
    }
    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getDataSource().getConnection(username, password);
    }
    public void addDataSouce(String key,DataSource dataSource) {
        tenantDataSources.put(key,dataSource);
    }
    public DataSource getDataSource() {
        return tenantDataSources.get(getKey());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

}
