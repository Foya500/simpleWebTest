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
        entityManagerFactoryRef = "entityManagerFactoryH2",  //EntityManagerFactory引用
        transactionManagerRef = "transactionManagerH2", //transactionManager引用
        basePackages = {"com.foya.h2.dao"})
public class H2DataSourceConfig {

	
	/**
     * 
     * @return
     */
	@Value("${h2.spring.datasource.password_encode}")
	public String password_encode;
	
	
    @Bean(name = "h2DataSource")
    @ConfigurationProperties(prefix = "h2.spring.datasource")
    public DataSource h2DataSource() {
        return DataSourceBuilder.create().password(password_encode).build();
    }


    @Bean(name = "h2JpaProperties")
    @ConfigurationProperties(prefix = "h2.spring.datasource")
    public JpaProperties h2JpaProperties() {
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
   
    @Bean(name = "entityManagerFactoryH2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryh2() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(h2DataSource());
        em.setPackagesToScan(
                new String[] { "com.foya.h2.entity" });


        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();

//        properties.put("hibernate.hbm2ddl.auto",
//               "create-drop");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        em.setJpaPropertyMap(properties);

        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(h2JpaProperties().getProperties());
        return em;
    }

    /**
     * 配置EntityManager實體
     * @param factory
     * @return 實體管理器
     */
    @Bean(name = "entityManagerH2")
    public EntityManager entityManagerH2(@Qualifier("entityManagerFactoryH2") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }
  
    @Bean(name = "transactionManagerH2")
    PlatformTransactionManager transactionManagerH2(@Qualifier("entityManagerFactoryH2") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
    
    @Bean(name = "jdbcTemplateH2")
    JdbcTemplate jdbcTemplateH2() {
        return new JdbcTemplate(h2DataSource());
    }
    
    //@PostConstruct
    private void initH2Db() {
        String sqlStatements[] = {
          "drop table employees if exists",
          "create table employees(id serial,first_name varchar(255),last_name varchar(255))",
          "insert into employees(first_name, last_name) values('Eugen','Paraschiv')",
          "insert into employees(first_name, last_name) values('Scott','Tiger')"
        };
     
        Arrays.asList(sqlStatements).forEach(sql -> {
        	jdbcTemplateH2().execute(sql);
        });
     
        // Query test data and print results
    }
    
    /**
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer() throws SQLException {
        return Server.createTcpServer(
          "-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }
    */
    
    
}
