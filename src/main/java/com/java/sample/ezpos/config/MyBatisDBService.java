package com.java.sample.ezpos.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages="com.java.sample.ezpos.mapper",sqlSessionFactoryRef="mybatisSqlSessionFactory")
public class MyBatisDBService {

	@Value("${mybatis.mapperLocations}")
	private String mapperLocation;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = "mybatisDatasource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource datasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "mybatisSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("mybatisDatasource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
		return bean.getObject();
	}
	
	@Bean(name="mybatisTransactionManager")
	public DataSourceTransactionManager transactionManager(@Qualifier("mybatisDatasource")DataSource dataSource) {
	    return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name="mybatisSqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mybatisSqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
	    return new SqlSessionTemplate(sqlSessionFactory);
	}

}
