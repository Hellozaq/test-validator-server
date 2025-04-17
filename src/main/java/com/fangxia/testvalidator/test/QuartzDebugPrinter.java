package com.fangxia.testvalidator.test;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Slf4j
@Component
public class QuartzDebugPrinter {

    private final Scheduler scheduler;

    public QuartzDebugPrinter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printQuartzJobs() throws SchedulerException {
        log.info("=== Registered Quartz Jobs ===");
        for (String group : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
                log.info("Job: {}.{}", jobKey.getGroup(), jobKey.getName());
            }
        }
        for (String group : scheduler.getTriggerGroupNames()) {
            for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(group))) {
                Trigger trigger = scheduler.getTrigger(triggerKey);
                log.info("Trigger: {} - next fire time: {}", triggerKey.getName(), trigger.getNextFireTime());
            }
        }
    }
}
