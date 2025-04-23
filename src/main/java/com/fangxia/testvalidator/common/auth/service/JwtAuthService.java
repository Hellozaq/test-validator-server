package com.fangxia.testvalidator.common.auth.service;

import com.fangxia.testvalidator.common.auth.model.AuthenticatedUser;
import com.fangxia.testvalidator.common.constant.UserConstants;
import com.fangxia.testvalidator.common.exception.ExpiredTokenException;
import com.fangxia.testvalidator.common.exception.InvalidTokenException;
import com.fangxia.testvalidator.common.exception.InvalidUserException;
import com.fangxia.testvalidator.common.security.JwtUtil;
import static com.fangxia.testvalidator.common.constant.UserConstants.*;

import com.fangxia.testvalidator.common.util.EntityUtil;
import com.fangxia.testvalidator.usermanager.model.eo.UserEO;
import com.fangxia.testvalidator.usermanager.model.vo.LoginResponseVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final JwtUtil jwtUtil;

    public AuthenticatedUser authenticate(String authorizationHeader) {
        if(StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Authorization header is empty or invalid");
        }

        String token = authorizationHeader.substring(7);

        try {
            UserEO userEO = jwtUtil.getUserFromToken(token);

            return new AuthenticatedUser(userEO);
        } catch (ExpiredTokenException | InvalidTokenException te) {
            throw te;
        }  catch (Exception e) {
            throw new InvalidTokenException("Unexpected error during token validation", e);
        }

    }

    public String refreshAccessToken(String refreshToken) {
        if(StringUtils.isEmpty(refreshToken)) {
            throw new InvalidTokenException("Refresh token is missing");
        }
        try {
            String id = jwtUtil.validateAndGetId(refreshToken);
            int userType = jwtUtil.getUserType(refreshToken);

            if(userType == ADMIN) {
                throw new InvalidUserException("Admin user can not generate a refresh token");
            }

            return jwtUtil.generateAccessToken(jwtUtil.getUserFromToken(refreshToken));
        } catch (ExpiredTokenException | InvalidTokenException te) {
            throw te;
        } catch (Exception e) {
            throw new InvalidTokenException("Unexpected error during token validation", e);
        }
    }

    public LoginResponseVO generateLoginResponse(UserEO user, HttpServletResponse response) {

        LoginResponseVO responseVO = new LoginResponseVO();

        String accessToken = jwtUtil.generateAccessToken(user);
        responseVO.setAccessToken(accessToken);

        if(user.getUserType() != UserConstants.ADMIN) {
            String refreshToken = jwtUtil.generateRefreshToken(user);

            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
//            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setSecure(false);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge((int) (UserConstants.USER_REFRESH_TOKEN_EXPIRES_TIME / 1000));

            log.info("Refresh Token: {}", refreshToken);

//            refreshTokenCookie.setAttribute("SameSite", "Strict");
            refreshTokenCookie.setAttribute("SameSite", "Lax");

            response.addCookie(refreshTokenCookie);
        }

        responseVO.setUser(EntityUtil.eoToVo(user));

        return responseVO;

    }



}
