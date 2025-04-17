package com.fangxia.testvalidator.usermanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangxia.testvalidator.common.auth.service.JwtAuthService;
import com.fangxia.testvalidator.common.model.ApiResponse;
import com.fangxia.testvalidator.common.security.BCryptUtil;
import com.fangxia.testvalidator.common.service.EmailService;
import com.fangxia.testvalidator.common.util.EntityUtil;
import com.fangxia.testvalidator.usermanager.model.dto.UserDTO;
import com.fangxia.testvalidator.usermanager.model.eo.UserEO;
import com.fangxia.testvalidator.usermanager.service.EmailVerificationIService;
import com.fangxia.testvalidator.usermanager.service.UserIService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.fangxia.testvalidator.common.constant.ApiConstants.AUTHENTICATION_URL;

@RestController
@RequestMapping(AUTHENTICATION_URL)
@RequiredArgsConstructor
public class UserAuthController {

    private final EmailVerificationIService emailVerificationIService;

    private final UserIService userIService;

    private final EmailService emailService;

    private final JwtAuthService jwtAuthService;

    @GetMapping("/send-verify-code")
    @Operation(summary = "Send a verification email")
    public ApiResponse<?> sendVerifyCode(@RequestParam("email") String email) {

        UserEO user = userIService.getOne(new QueryWrapper<UserEO>().eq("email", email));

        if(user != null) {
            return ApiResponse.failure("Email is already registered.");
        }

        if(emailVerificationIService.isTooFrequent(email)) {
            return ApiResponse.tooManyRequests("Email sent too frequent, try again in 2 minutes.");
        }

        String verificationCode = emailVerificationIService.generateVerificationCode(email);
        if(!StringUtils.isEmpty(verificationCode)) {
            emailService.sendVerificationCode(email, verificationCode);
        }

        return ApiResponse.success("Verification code has been sent to: "  + email);

    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ApiResponse<?> register(@RequestBody UserDTO userDTO, HttpServletResponse response) {

        UserEO userEmail = userIService.getOne(new QueryWrapper<UserEO>().eq("email", userDTO.getEmail()));
        if(userEmail != null) {
            return ApiResponse.failure("Email is already registered.");
        }

        UserEO userName = userIService.getOne(new QueryWrapper<UserEO>().eq("username", userDTO.getUsername()));
        if(userName != null) {
            return ApiResponse.failure("Username is taken.");
        }

        if(!emailVerificationIService.matchVerificationCode(userDTO.getEmail(), userDTO.getVerificationCode())) {
            return ApiResponse.failure("Invalid verification code.");
        }

        UserEO user = EntityUtil.dtoToEo(userDTO);
        user.setId(UUID.randomUUID().toString());
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());

        if(userIService.save(user)) {
            return ApiResponse.success(jwtAuthService.generateLoginResponse(user, response));
        }

        return ApiResponse.failure("Unknown Error has occurred.");

    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody UserDTO userDTO, HttpServletResponse response) {

        UserEO emailUser = userIService.getOne(new QueryWrapper<UserEO>().eq("email", userDTO.getLoginUser()));
        UserEO nameUser = userIService.getOne(new QueryWrapper<UserEO>().eq("username", userDTO.getLoginUser()));

        UserEO user = emailUser != null ? emailUser : nameUser;

        if(user == null) {
            return ApiResponse.failure("Invalid login or password.");
        }

        if(!BCryptUtil.matches(userDTO.getPassword(), user.getPassword())) {
            return ApiResponse.failure("Invalid login or password.");
        }

        return ApiResponse.success(jwtAuthService.generateLoginResponse(user, response));

    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
            .path("/")
            .maxAge(0)
            .httpOnly(true)
            .secure(true)
            .sameSite("Strict")
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
        return ApiResponse.success("Logged out successfully.");
    }

}
