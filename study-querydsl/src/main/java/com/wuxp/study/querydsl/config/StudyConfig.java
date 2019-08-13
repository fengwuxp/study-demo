package com.wuxp.study.querydsl.config;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wuxp.study.cache.generator.DefaultWildcardEvictCacheKeyGenerator;
import com.wuxp.study.cache.generator.DefaultWildcardPutCacheKeyGenerator;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.wuxp.study.querydsl.repositories"})
@EntityScan(basePackages = {"com.wuxp.study.querydsl.entities"})
@EnableCaching
public class StudyConfig {


    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }


    @Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public KeyGenerator wildcardPutCacheKeyGenerator(){
        return new DefaultWildcardPutCacheKeyGenerator();
    }

    @Bean
    public KeyGenerator wildcardEvictCacheKeyGenerator(){
        return new DefaultWildcardEvictCacheKeyGenerator();
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
//
//        return builder.dataSource(dataSource)
//                .packages("com.wxup.study")
//                .build();
//    }

//    @Bean
//    public JpaTransactionManager transactionManager(EntityManagerFactory factory) {
//        return new JpaTransactionManager(factory);
//    }

//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
}
