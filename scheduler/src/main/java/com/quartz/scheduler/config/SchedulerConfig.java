package com.quartz.scheduler.config;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.quartz.Scheduler;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Slf4j
@Profile("!test")
public class SchedulerConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(final ApplicationContext applicationContext,
                                                     final DataSource dataSource,
                                                     final QuartzProperties quartzProperties) {

        log.info("********** In QUARTZ CONFIG class  - {}", quartzProperties);
        final var jobFactory = new SchedulerJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        final var factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory);
        factory.setDataSource(dataSource);

        final var properties = new Properties();
        properties.putAll(quartzProperties.getProperties());
        factory.setQuartzProperties(properties);

        return factory;
    }

    @Bean
    public Scheduler scheduler(final SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }
}

class SchedulerJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
    }
}

