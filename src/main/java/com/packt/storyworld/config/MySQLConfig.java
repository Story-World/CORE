package com.packt.storyworld.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(value = "com.packt.storyworld.repository.sql")
public class MySQLConfig {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "root";
	private static final String PROPERTY_NAME_DATABASE_URL = "jdbc:mysql://localhost:3306/storyworld?useSSL=false";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "root";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "org.hibernate.dialect.MySQL5Dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "true";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.packt.storyworld.domain.sql";
	private static final String PROPERTY_NAME_HIBERNATE_AUTO = "update";

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
		dataSource.setUrl(PROPERTY_NAME_DATABASE_URL);
		dataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
		dataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);
		return dataSource;
	}

	/*
	 * @Bean public SessionFactory sessionFactory() throws Exception { return
	 * new LocalSessionFactoryBuilder(dataSource()).scanPackages(
	 * PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN)
	 * .addProperties(getHibernateProperties()).buildSessionFactory(); }
	 */

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", PROPERTY_NAME_HIBERNATE_SHOW_SQL);
		properties.put("hibernate.dialect", PROPERTY_NAME_HIBERNATE_DIALECT);
		properties.put("hibernate.hbm2ddl.auto", PROPERTY_NAME_HIBERNATE_AUTO);
		return properties;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);
		factory.setDataSource(dataSource());
		factory.setJpaProperties(getHibernateProperties());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}
}
