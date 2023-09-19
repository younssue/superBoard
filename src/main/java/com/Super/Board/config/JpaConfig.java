package com.Super.Board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        // basePackages는 패키지 경로에 대한 설정, 해당 경로를 포함하는 하위 패키지를 모두 스캔
        basePackages = {"com.Super.Board.user.repository"},
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "tmJpa"
)
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.Super.Board.user.repository");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql", "true");// Added for format_sql
        properties.put("hibernate.use_sql_comment", "true");// Added for use_sql_comments
        properties.put("hibernate.hbm2ddl.auto", "update"); // Added for ddl-auto
        properties.put("hibernate.show_sql", "true"); // Added for show_sql

        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name="tmJpa")
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean((dataSource)).getObject());
        return transactionManager;
    }
}
