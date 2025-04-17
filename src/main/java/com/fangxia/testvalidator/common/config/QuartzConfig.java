package com.fangxia.testvalidator.common.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Collection;

@Configuration
public class QuartzConfig {

    @Autowired
    private JobFactory jobFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setOverwriteExistingJobs(true);

        Collection<JobDetail> jobDetails = applicationContext.getBeansOfType(JobDetail.class).values();
        Collection<Trigger> triggers = applicationContext.getBeansOfType(Trigger.class).values();

        schedulerFactoryBean.setJobDetails(jobDetails.toArray(new JobDetail[0]));
        schedulerFactoryBean.setTriggers(triggers.toArray(new Trigger[0]));

        return schedulerFactoryBean;
    }
}
