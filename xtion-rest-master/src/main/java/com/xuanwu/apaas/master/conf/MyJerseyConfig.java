package com.xuanwu.apaas.master.conf;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.xuanwu.apaas.core.server.config.JerseyConfig;
import com.xuanwu.apaas.core.server.filter.RestLoggingFilter;
import com.xuanwu.apaas.core.server.filter.SessionTokenFilter;
import com.xuanwu.apaas.core.server.filter.TenantApiLoggingFilter;
import com.xuanwu.apaas.master.rest.*;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyJerseyConfig extends JerseyConfig {

    @Value("${spring.jersey.application-path:/}")
    private String apiPath;

    @Value("${spring.createDoc}")
    private boolean createDoc;

    @Value("${server.port}")
    private int port;

    @Value("${server.contextPath}")
    private String contextPath;

    @Value("${server.sessionTimeout}")
    private Integer sessionTimeout;

    @Value("${server.maxConnections}")
    private Integer maxConnections;

    @Value("${server.maxThreads}")
    private Integer maxThreads;

    @Value("${server.connectionTimeout}")
    private Integer connectionTimeout;

    @PostConstruct
    public void init() {
        // Register components where DI is needed
        if (createDoc) {
            this.configureSwagger();
        }
    }


    private void configureSwagger() {
        // 使用 localhost:port/swagger.json
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);
        BeanConfig config = new BeanConfig();
        config.setConfigId("ide");
        config.setTitle("IDE接口文档");
        config.setVersion("1.0");
        config.setContact("rongdi");
        config.setSchemes(new String[]{"http"});
        config.setBasePath(this.apiPath);
        config.setResourcePackage("com.xuanwu");
        config.setPrettyPrint(true);
        io.swagger.util.Json.mapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public MyJerseyConfig() {
        super();
        register(TestRest.class);
        register(SceneTemplateRest.class);
        register(SceneCaseRest.class);
        register(VisitWorkRest.class);
        register(SessionTokenFilter.class);
        register(RestLoggingFilter.class,1);
        register(SessionTokenFilter.class,2);
        register(TenantApiLoggingFilter.class,3);
    }

    @Bean
    public EmbeddedServletContainerFactory createEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        tomcatFactory.setPort(port);
        tomcatFactory.setContextPath(contextPath);
        tomcatFactory.setSessionTimeout(sessionTimeout);
        tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
        return tomcatFactory;
    }

    //用来进行tomcat容器调优用的
    class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
        @Override
        public void customize(Connector connector) {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            //设置最大连接数
            protocol.setMaxConnections(maxConnections == null ? 2000 : maxConnections);
            //设置最大线程数
            protocol.setMaxThreads(maxThreads == null ? 2000 : maxThreads);
            protocol.setConnectionTimeout(connectionTimeout == null ? 40000 : connectionTimeout);
        }
    }
}

