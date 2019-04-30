package com.dripop.core.config;

import com.dripop.core.jpa.BaseRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by liyou on 2017/9/22.
 */
@Configuration
@EntityScan(basePackages = {"com.dripop","com.dripop"})
@EnableJpaRepositories(basePackages = {"com.drip","com.dripop"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class
)
public class JpaConfiguration {
}
