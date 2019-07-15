package com.foya.conf;

import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryOracle",  //EntityManagerFactory引用
        transactionManagerRef = "transactionManagerOracle", //transactionManager引用
        basePackages = {"com.foya.dao"})
public class OracleDataSourceConfig {

	
	/**
     * 
     * @return
     */
	@Value("${oracle.spring.datasource.password_encode}")
	public String password_encode;
	
	
    @Primary
    @Bean(name = "oracleDataSource")
    @ConfigurationProperties(prefix = "oracle.spring.datasource")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().password(password_encode).build();
    }

    
    @Primary
    @Bean(name = "primaryJpaProperties")
    @ConfigurationProperties(prefix = "oracle.spring.datasource")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }




    /**
     * 配置EntityManagerFactory實體
     * @param builder
     * @return 實體管理工廠
     * packages     掃描@Entity註釋的軟件包名稱
     * persistenceUnit
     * properties       標準JPA或供應商特定配置的通用屬性。 這些屬性覆蓋構造函數中提供的任何值。
     */
    @Primary
    @Bean(name = "entityManagerFactoryOracle")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryOracle() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(oracleDataSource());
        em.setPackagesToScan(
                new String[] { "com.foya.entity" });


        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();

//        properties.put("hibernate.hbm2ddl.auto",
//               "create-drop");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.Oracle12cDialect");
        em.setJpaPropertyMap(properties);

        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(jpaProperties().getProperties());
        return em;
    }

    /**
     * 配置EntityManager實體
     * @param factory
     * @return 實體管理器
     */
    @Primary
    @Bean(name = "entityManagerOracle")
    public EntityManager entityManager(@Qualifier("entityManagerFactoryOracle") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }
    @Primary
    @Bean(name = "transactionManagerOracle")
    PlatformTransactionManager transactionManagerOracle(@Qualifier("entityManagerFactoryOracle") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
    
    
    @Bean(name = "jdbcTemplate")
    JdbcTemplate jdbcTemplateOracle() {
        return new JdbcTemplate(oracleDataSource());
    }
    
    /**
    @PostConstruct
    private void initH2Db() {
        String sqlStatements[] = {
          "drop table employees if exists",
          "create table employees(id serial,first_name varchar(255),last_name varchar(255))",
          "insert into employees(first_name, last_name) values('Eugen','Paraschiv')",
          "insert into employees(first_name, last_name) values('Scott','Tiger')"
        };
     
        Arrays.asList(sqlStatements).forEach(sql -> {
        	jdbcTemplateOracle().execute(sql);
        });
     
        // Query test data and print results
    }
    */
}
