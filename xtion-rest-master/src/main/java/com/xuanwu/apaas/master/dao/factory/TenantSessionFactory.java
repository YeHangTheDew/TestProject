package com.xuanwu.apaas.master.dao.factory;
import com.xuanwu.apaas.core.utils.ArrayUtils;
import com.xuanwu.apaas.core.utils.Delimiters;
import com.xuanwu.apaas.dbrouter.util.DataBaseType;
import com.xuanwu.apaas.dbrouter.util.DataSourceRouterUtil;
import com.xuanwu.apaas.master.conf.TenantDataSourceProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据源的工厂类
 *
 * @author rongdi
 * @Date 2016年12月2日
 * @Version 1.0.0
 */
@Component
public class TenantSessionFactory {

    @Autowired
    TenantDataSourceProperties properties;

    private Map<String, SqlSessionFactory> factoryMap = new ConcurrentHashMap<String, SqlSessionFactory>();

    private ReentrantLock lock = new ReentrantLock();

    private static final Logger logger = LoggerFactory
            .getLogger(TenantSessionFactory.class);

    private static SqlSessionFactory currSqlSessionFactory;

    private static TenantDataSource tenantDataSource = new TenantDataSource();

    public SqlSessionFactory getTenantDruidSessionFactory(Long tenantCode, Long productCode, DataBaseType dbType) {

        String key = tran2Key(tenantCode,productCode,dbType);
        tenantDataSource.setKey(key);
        try {
            DataSource dataSource = DataSourceRouterUtil.getDataSource(tenantCode, dbType, productCode);
            tenantDataSource.addDataSouce(key,dataSource);
            lock.lock();
            if (currSqlSessionFactory == null) {
                SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
                sessionFactoryBean.setDataSource(tenantDataSource);
                sessionFactoryBean.setTransactionFactory(new JdbcTransactionFactory());
                String scanLocation = properties.getScanLocation();
                Resource[] resources = getResources(scanLocation);
                sessionFactoryBean.setMapperLocations(resources);
                sessionFactoryBean.setConfigLocation(getConfigResources());
                currSqlSessionFactory = sessionFactoryBean.getObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Get enterprise:{} session factory failed cause:{}", tenantCode, e);
        } finally {
            lock.unlock();
        }
        if (currSqlSessionFactory == null) {
            String errorMsg = "Get enterprise session factory is null, with entNumber: "
                    + tenantCode + ",systemCode: " + productCode;
            logger.error(errorMsg);
            throw new NullPointerException(errorMsg);
        }

        return currSqlSessionFactory;
    }

    public void clearFactoryMap() {
        if (factoryMap != null) {
            factoryMap.clear();
        }
    }


    private void buildTenantDruidSessionFactory(Long tenantCode, Long productCode, DataBaseType dbType) {
        try {
            DataSource dataSource = DataSourceRouterUtil.getDataSource(tenantCode, dbType, productCode);
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource);
            sessionFactoryBean.setTransactionFactory(new JdbcTransactionFactory());
            String scanLocation = properties.getScanLocation();
            Resource[] resources = getResources(scanLocation);
            sessionFactoryBean.setMapperLocations(resources);
            sessionFactoryBean.setConfigLocation(getConfigResources());
            String key = tran2Key(tenantCode, productCode, dbType);
            factoryMap.put(key, sessionFactoryBean.getObject());
        } catch (Exception e) {
            String errorMsg = "Get enterprise session factory is null, with entNumber: "
                    + tenantCode + ",systemCode: " + productCode;
            logger.error(errorMsg);
            throw new NullPointerException(errorMsg);
        }
    }

    private Resource getConfigResources() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("classpath:mybatis-config.xml")[0];
    }

    private Resource[] getResources(String scanLocation) throws IOException {
        String[] strs = scanLocation.split(Delimiters.SICOLON);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource[]> rss = new ArrayList<Resource[]>();
        Resource[] resources = null;
        for (String str : strs) {
            try {
                resources = resolver.getResources(str);
            } catch (Exception e) {
                continue;
            }
            if (resources != null && resources.length > 0) {
                rss.add(resources);
            }
        }
        return ArrayUtils.addAll(new Resource[]{},
                rss.toArray(new Resource[rss.size()][]));
    }

    private String tran2Key(Long tenantCode,
                            Long productCode, DataBaseType dbType) {
        StringBuilder key = new StringBuilder();
        key.append(tenantCode).append(Delimiters.UNDLER_LINE);
        key.append(productCode).append(Delimiters.UNDLER_LINE);
        key.append(dbType.getDBType());
        return key.toString();
    }
}
