package com.multisource.demo.config;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author JunWu
 * mybatis配置
 */
public class MybatisConfig {

    @Configuration
    @MapperScan(basePackages = {"com.multisource.demo.usermapper"},
            sqlSessionFactoryRef = "sqlSessionFactoryUser",
            sqlSessionTemplateRef = "sqlSessionTemplateUser")
    public static class UserMybatisConfig {
        @Resource
        private DataSource userDataSource;

        @Value("${mybatis.user.mapper-locations:classpath:mapper/user/*.xml}")
        private String mapperLocations;

        @Value("${mybatis.user.log-impl:}")
        private String logImpl;

        @Value("${mybatis.user.type-alias-packages:}")
        private String typeAliasPackages;

        @Bean(name = "sqlSessionFactoryUser")
        public SqlSessionFactory sqlSessionFactoryTt() throws Exception {
            return buildSqlSessionFactory(logImpl, userDataSource, mapperLocations, typeAliasPackages);
        }

        @Bean(name = "sqlSessionTemplateUser")
        public SqlSessionTemplate sqlSessionTemplateTt(@Qualifier("sqlSessionFactoryUser") SqlSessionFactory sqlSessionFactoryUser) {
            return new SqlSessionTemplate(sqlSessionFactoryUser);
        }
    }

    @Configuration
    @MapperScan(basePackages = {"com.multisource.demo.studentmapper"},
            sqlSessionFactoryRef = "sqlSessionFactoryStudent",
            sqlSessionTemplateRef = "sqlSessionTemplateStudent")
    public static class StudentMybatisConfig {
        @Resource
        DataSource studentDataSource;

        @Value("${mybatis.student.mapper-locations:classpath:mapper/student/*.xml}")
        private String mapperLocations;

        @Value("${mybatis.student.log-impl}")
        private String logImpl;

        @Bean(name = "sqlSessionFactoryStudent")
        public SqlSessionFactory sqlSessionFactoryStudent() throws Exception {
            return buildSqlSessionFactory(logImpl, studentDataSource, mapperLocations, null);
        }

        @Bean(name = "sqlSessionTemplateStudent")
        public SqlSessionTemplate sqlSessionTemplateStudent(@Qualifier("sqlSessionFactoryStudent") SqlSessionFactory sqlSessionFactoryStudent) {
            return new SqlSessionTemplate(sqlSessionFactoryStudent);
        }
    }

    /**
     * 根据条件构建SqlSessionFactory
     *
     * @param logImpl
     * @param dataSource
     * @param mapperLocations
     * @return
     * @throws Exception
     */
    private static SqlSessionFactory buildSqlSessionFactory(String logImpl, DataSource dataSource, String mapperLocations, String typeAliasPackages) throws Exception {
        Class<? extends Log> logClass = null;
        if (!StringUtils.isEmpty(logImpl)) {
            //提前设置MS的日志类型
            logClass = (Class<? extends Log>) Class.forName(logImpl);
            LogFactory.useCustomLogging(logClass);
        }
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        if (!StringUtils.isEmpty(typeAliasPackages)) {
            factoryBean.setTypeAliasesPackage(typeAliasPackages);
        }
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        SqlSessionFactory sqlSessionFactory = factoryBean.getObject();
        if (logClass != null) {
            sqlSessionFactory.getConfiguration().setLogImpl(logClass);
        }
        return sqlSessionFactory;
    }
}
