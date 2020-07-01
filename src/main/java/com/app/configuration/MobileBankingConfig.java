package com.app.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.app.repository.mobilebanking", 
		entityManagerFactoryRef = "mobileBankingEntityManagerFactory",
		transactionManagerRef = "mobileBankingTransactionManager"
)
public class MobileBankingConfig {

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.mobilebanking")
	public DataSourceProperties mobileBankingDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.mobilebanking.configuration")
	public DataSource mobileBankingDataSource() {
		return mobileBankingDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
	@Primary
	@Bean(name = "mobileBankingEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean mobileBankingEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(mobileBankingDataSource()).packages("com.app.entity.mobilebanking", "com.app.entity.corebankingdummy").build();
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager mobileBankingTransactionManager(
		final @Qualifier("mobileBankingEntityManagerFactory") LocalContainerEntityManagerFactoryBean mobileBankingEntityManagerFactory
	) {
		return new JpaTransactionManager(mobileBankingEntityManagerFactory.getObject());
	}
	
}
