package com.multisource.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author JunWu
 * 数据源/事物 配置
 */
@Configuration
public class DataSourceConfig {

    @Value("${datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "userDataSource")
    @ConfigurationProperties(prefix = "datasource.user")
    public DataSource ttDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "studentDataSource")
    @ConfigurationProperties(prefix = "datasource.student")
    public DataSource wsDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     * 为userDataSource数据源创建事物管理器
     *
     * @param dataSource
     * @return
     */
    @Bean("userTransaction")
    public PlatformTransactionManager ttTransactionManager(@Qualifier("userDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 为studentDataSource数据源创建事物管理器
     *
     * @param dataSource
     * @return
     */
    @Bean("studentTransaction")
    public PlatformTransactionManager wsTransactionManager(@Qualifier("studentDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
