package com.tmax.cloud.superpx.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {
    @PersistenceContext(unitName = "primaryEntityManager")
    EntityManager primaryDataSourceEntityManager;

    @Bean
    public JPAQueryFactory primaryQueryFactory() {
        return new JPAQueryFactory(primaryDataSourceEntityManager);
    }
}
