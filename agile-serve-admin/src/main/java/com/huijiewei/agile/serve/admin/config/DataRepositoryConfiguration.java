package com.huijiewei.agile.serve.admin.config;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.huijiewei.agile", repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
public class DataRepositoryConfiguration {
}
