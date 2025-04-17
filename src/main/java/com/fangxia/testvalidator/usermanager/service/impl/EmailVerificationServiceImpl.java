package com.fangxia.testvalidator.usermanager.service.impl;

import com.fangxia.testvalidator.common.exception.InvalidEmailException;
import com.fangxia.testvalidator.common.exception.SQLFailureException;
import com.fangxia.testvalidator.common.util.CodeUtil;
import com.fangxia.testvalidator.usermanager.mapper.EmailVerificationMapper;
import com.fangxia.testvalidator.usermanager.model.eo.EmailVerificationEO;
import com.fangxia.testvalidator.usermanager.service.EmailVerificationIService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class EmailVerificationServiceImpl
    extends ServiceImpl<EmailVerificationMapper, EmailVerificationEO>
    implements EmailVerificationIService {

    @Override
    public boolean isTooFrequent(String email) {
        return baseMapper.isTooFrequent(email);
    }

    @Override
    public boolean insertOrUpdateCode(String email, String code) {

        EmailVerificationEO existing = lambdaQuery()
            .eq(EmailVerificationEO::getEmail, email)
            .one();

        EmailVerificationEO record = new EmailVerificationEO();
        record.setEmail(email);
        record.setVerificationCode(code);
        record.setExpiredTime(LocalDateTime.now().plusMinutes(15));

        if (existing != null) {
            record.setId(existing.getId());
            record.setUpdatedTime(LocalDateTime.now());
        } else {
            record.setId(UUID.randomUUID().toString());
            record.setCreatedTime(LocalDateTime.now());
            record.setUpdatedTime(LocalDateTime.now());
        }

        return saveOrUpdate(record);

    }

    @Override
    public boolean matchVerificationCode(String email, String code) {

        EmailVerificationEO existing = lambdaQuery()
            .eq(EmailVerificationEO::getEmail, email)
            .one();

        if(existing == null) {
            throw new InvalidEmailException("The given email does not match our record.");
        }

        if(existing.getExpiredTime().isBefore(LocalDateTime.now())) {
            throw new InvalidEmailException("The given code has expired or invalid.");
        }

        boolean match = existing.getVerificationCode().equals(code);

        if(match) {
            baseMapper.deleteByEmail(email);
            return true;
        }

        return false;

    }

    @Override
    public String generateVerificationCode(String email) {

        if (StringUtils.isEmpty(email)) {
            log.error("Email is empty");
            throw new InvalidEmailException("Email is empty.");
        }

        String verificationCode = CodeUtil.generateVerificationCode();

        if(insertOrUpdateCode(email, verificationCode)) {
            return verificationCode;
        }
        throw new SQLFailureException("Failed to generate verification code.");

    }

    @Override
    public void deleteExpiredCodes() {
        baseMapper.deleteExpiredCodes();
    }

}
