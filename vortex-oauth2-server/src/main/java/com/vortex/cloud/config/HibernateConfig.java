package com.vortex.cloud.config;

import com.vortex.cloud.vfs.common.spring.SpringContextHolder;
import com.vortex.cloud.vfs.data.enums.DBTypeEnum;
import com.vortex.cloud.vfs.data.util.StaticDBType;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;
    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.show_sql}")
    private String showSql;
    @Value("${hibernate.format_sql}")
    private String formatSql;
    @Value("${hibernate.current_session_context_class}")
    private String sessionContextClass;
    @Value("${hibernate.packagesToScan}")
    private String packagesToScan;

    // 其中 dataSource 框架会自动为我们注入
    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setHibernateProperties(buildHibernateProperties());
        sessionFactory.setPackagesToScan(packagesToScan);
        return sessionFactory;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    protected Properties buildHibernateProperties() {
        Properties hibernateProperties = new Properties();

        try {
            if (MySQLDialect.class.isAssignableFrom(Class.forName(dialect))) {
                StaticDBType.dbType = DBTypeEnum.mysql.toString();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.show_sql", showSql);
        hibernateProperties.setProperty("hibernate.format_sql", formatSql);
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        hibernateProperties.setProperty("hibernate.current_session_context_class", sessionContextClass);

        return hibernateProperties;
    }

    @Bean
    @Lazy(value = false)
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
