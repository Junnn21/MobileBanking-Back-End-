package com.app.configuration;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.app.repository.corebankingdummy",
		entityManagerFactoryRef = "coreBankingDummyEntityManagerFactory",
		transactionManagerRef = "coreBankingDummyTransactionManager"
)
public class CoreBankingDummyConfig {

	@Bean
	@ConfigurationProperties("spring.datasource.corebankingdummy")
	public DataSourceProperties coreBankingDummyDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@ConfigurationProperties("spring.datasource.corebankingdummy.configuration")
	public DataSource coreBankingDummyDataSource() {
		return coreBankingDummyDataSourceProperties().initializeDataSourceBuilder().type(BasicDataSource.class).build();
	}
	
	@Bean(name = "coreBankingDummyEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean coreBankingDummyEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(coreBankingDummyDataSource()).packages("com.app.entity.corebankingdummy", "com.app.entity.mobilebanking").build();
	}
	
	@Bean
	public PlatformTransactionManager coreBankingDummyTransactionManager(
			final @Qualifier("coreBankingDummyEntityManagerFactory") LocalContainerEntityManagerFactoryBean coreBankingDummyEntityManagerFactory
	) {
		return new JpaTransactionManager(coreBankingDummyEntityManagerFactory.getObject());	
	}
}
