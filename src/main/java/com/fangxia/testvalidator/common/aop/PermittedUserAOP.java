package com.fangxia.testvalidator.common.aop;

import com.fangxia.testvalidator.common.annotation.PermittedUsers;

import static com.fangxia.testvalidator.common.constant.UserConstants.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PermittedUserAOP {

    private final HttpServletRequest request;

    @Before("@annotation(requiresUserType)")
    public void checkPermission(PermittedUsers requiresUserType) {
        Object attr = request.getAttribute("userType");

        if (!(attr instanceof Integer userType)) {
            throw new SecurityException("Unauthorized: userType not found or invalid in request.");
        }

        for (int allowed : requiresUserType.value()) {
            if (allowed == userType) {
                return;
            }
        }

        String type = switch (userType) {
            case ADMIN -> "Admin";
            case TEACHER -> "Teacher";
            case ASSISTANT -> "Assistant";
            case STUDENT -> "Student";
            default -> throw new SecurityException("Unauthorized: userType not found or invalid in request.");
        };

        throw new SecurityException("Access denied for " + type);
    }

}
