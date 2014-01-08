package org.devshred.hazelcast.config;

import java.util.Properties;

import javax.annotation.Resource;

import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories("org.devshred.hazelcast.persistence")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class JpaHibernateConfiguration {
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DLL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

	@Autowired
	private DataSource dataSource;

	@Resource
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
			new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));

		entityManagerFactoryBean.setJpaProperties(hibProperties());

		return entityManagerFactoryBean;
	}

	private Properties hibProperties() {
		final Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DLL_AUTO, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DLL_AUTO));
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
}
