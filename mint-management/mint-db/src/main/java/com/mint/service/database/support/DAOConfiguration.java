package com.mint.service.database.support;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.mint.common.exception.MintException;

/**
 * DAO 核心组件注入
 * 
 * @author ningfanchu
 *
 */
@Configuration("DAOConfiguration")
@ConditionalOnProperty(prefix = "spring.datasource", value="url")
public class DAOConfiguration {
	
	private static final Logger LOG = LoggerFactory.getLogger(DAOConfiguration.class);
	
	@Autowired
	private Environment environment;
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) throws MintException {
        try {
             LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
             localContainerEntityManagerFactoryBean.setDataSource(dataSource);
             localContainerEntityManagerFactoryBean.setPackagesToScan("com.mint.service");
             localContainerEntityManagerFactoryBean.setPersistenceUnitName("entityManagerFactory");
             localContainerEntityManagerFactoryBean.getJpaPropertyMap().put("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
             HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
             jpaVendorAdapter.setGenerateDdl(true);
             String showSql = environment.getProperty("spring.jpa.show-sql");
             jpaVendorAdapter.setShowSql(showSql == null ? false : Boolean.valueOf(showSql));
             jpaVendorAdapter.setDatabasePlatform(environment.getProperty("spring.jpa.properties.hibernate.dialect"));
             localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
             return localContainerEntityManagerFactoryBean;
        } catch (Exception e) {
        	LOG.error(e.getMessage());
        	return new LocalContainerEntityManagerFactoryBean();
        }
    }
	
	@Bean
	public HibernateTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(entityManagerFactory.unwrap(SessionFactory.class));
//		transactionManager.setEntityInterceptor(new HibernateAuditingInterceptor());
		return transactionManager;
	}
	
}
