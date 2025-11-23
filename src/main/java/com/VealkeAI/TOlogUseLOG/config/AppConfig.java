package com.VealkeAI.TOlogUseLOG.config;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return  new RestTemplate();
    }

    //AI code. I don't fucking know to configure this shit for save jobs in db. It doesn't want to work in application.yaml
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setOverwriteExistingJobs(true);
        return factory;
    }

    @Bean
    @Primary
    public Scheduler quartzScheduler(SchedulerFactoryBean factory) throws Exception {
        return factory.getScheduler();
    }
}
