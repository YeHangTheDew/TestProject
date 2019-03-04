package com.xuanwu.apaas.master.server;


import com.xuanwu.apaas.core.conf.DataSourceProperties;
import com.xuanwu.apaas.core.server.filter.SwaggerFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * springboot-jersey的启动类
 *
 * @author yechh
 * @date 2019/2/20
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.xuanwu.apaas"})
@PropertySource(value = {DataSourceProperties.configPath + "application.properties"})
@SuppressWarnings("unchecked")
public class JerseyApplication {

    @Bean
    public FilterRegistrationBean swaggerFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        SwaggerFilter swaggerFilter = new SwaggerFilter();
        registrationBean.setFilter(swaggerFilter);
        List<String> urlPatterns = new ArrayList();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }


    public static void main(String[] args) {
        SpringApplication.run(JerseyApplication.class, args);
    }


}
