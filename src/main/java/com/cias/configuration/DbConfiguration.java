package com.cias.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cias.entity.ApplicationLabel;
import com.cias.entity.AuditHistory;
import com.cias.entity.Banks;
import com.cias.entity.QueryData;
import com.cias.entity.ReportQueueData;
import com.cias.entity.RequiredField;
import com.cias.entity.TellerMaster;
import com.cias.entity.ViewInfo;
import com.cias.utility.AES;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")

public class DbConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	

	/* --------- session factory for prod ------------- git*/
	@Bean(name = "basicDataSource")
	public DataSource mySqlDataSource() throws Exception {
		BasicDataSource dataSource = new BasicDataSource();
		String appName = env.getProperty("application.name");
		dataSource.setDriverClassName(env.getProperty(appName + ".driverClassName"));
		dataSource.setUrl(env.getProperty(appName + ".databaseurl"));
		dataSource.setUsername(env.getProperty(appName + ".username"));
	    dataSource.setPassword(AES.decrypt(env.getProperty(appName + ".password")));
		//dataSource.setPassword(env.getProperty(appName + ".password"));
		return dataSource;
	}

	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean getSessionFactory(@Qualifier("basicDataSource") DataSource dataSource) {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(dataSource);
		localSessionFactoryBean.setAnnotatedClasses(ViewInfo.class,ApplicationLabel.class, TellerMaster.class, RequiredField.class, AuditHistory.class, QueryData.class, Banks.class,ReportQueueData.class);
		localSessionFactoryBean.setHibernateProperties(getHibernateProperties());
		return localSessionFactoryBean;
	}

	/* ---------end of session factory for prod report ------------- */
	protected Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", env.getProperty("Banc-Edge.dialect"));
		properties.put("hibernate.c3p0.min_size", 1);
		properties.put("hibernate.c3p0.max_size", 300);
		properties.put("hibernate.c3p0.timeout", 50);
		properties.put("hibernate.c3p0.max_statements", 5);
		properties.put("hibernate.c3p0.hibernate.c3p0.idle_test_period", 3000);
		properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
		return properties;
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}

}
