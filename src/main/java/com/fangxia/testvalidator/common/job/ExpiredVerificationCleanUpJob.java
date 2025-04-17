package com.fangxia.testvalidator.common.job;

import com.fangxia.testvalidator.usermanager.service.EmailVerificationIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpiredVerificationCleanUpJob implements Job {

    private final EmailVerificationIService emailVerificationIService;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("Start to clean up expired verification tokens");
        emailVerificationIService.deleteExpiredCodes();
        log.info("Finished to clean up expired verification tokens");
    }

}
