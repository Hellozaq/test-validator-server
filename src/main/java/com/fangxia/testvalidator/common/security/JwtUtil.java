package com.fangxia.testvalidator.common.security;

import com.fangxia.testvalidator.common.config.JwtConfig;
import com.fangxia.testvalidator.common.constant.UserConstants;
import com.fangxia.testvalidator.common.exception.ExpiredTokenException;
import com.fangxia.testvalidator.common.exception.InvalidTokenException;
import static com.fangxia.testvalidator.common.constant.UserConstants.*;

import com.fangxia.testvalidator.common.exception.InvalidUserException;
import com.fangxia.testvalidator.common.util.EntityUtil;
import com.fangxia.testvalidator.usermanager.model.eo.UserEO;
import com.fangxia.testvalidator.usermanager.model.vo.LoginResponseVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtConfig jwtConfig;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserEO user) {
        long expiration = user.getUserType() == ADMIN ? ADMIN_ACCESS_TOKEN_EXPIRES_TIME : USER_ACCESS_TOKEN_EXPIRES_TIME;

        return Jwts.builder()
                .setSubject(user.getId())
                .claim("username", user.getUsername())
                .claim("userType", user.getUserType())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(UserEO user) {
        if (user.getUserType() == ADMIN) {
            throw new InvalidUserException("Admin user can not generate a refresh token");
        }
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("username", user.getUsername())
                .claim("userType", user.getUserType())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + USER_REFRESH_TOKEN_EXPIRES_TIME))
                .signWith(key)
                .compact();
    }

    public String validateAndGetId(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException eje) {
            throw new ExpiredTokenException("Token expired", eje);
        } catch (JwtException je) {
            throw new InvalidTokenException("InvalidEmailException token", je);
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public UserEO getUserFromToken(String token) {
        UserEO user = new UserEO();
        user.setId(validateAndGetId(token));
        user.setUsername(getUsername(token));
        user.setUserType(getUserType(token));
        return user;
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token).get("username", String.class);
    }

    public int getUserType(String token) {
        return getClaimsFromToken(token).get("userType", Integer.class);
    }

}
