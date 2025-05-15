package com.fangxia.testvalidator.common.security;

import com.fangxia.testvalidator.common.auth.model.AuthenticatedUser;
import com.fangxia.testvalidator.common.auth.service.JwtAuthService;
import com.fangxia.testvalidator.common.exception.ExpiredTokenException;
import com.fangxia.testvalidator.common.exception.InvalidTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthService jwtAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (StringUtils.isNotEmpty(header) && header.startsWith("Bearer ")) {
            try {
                AuthenticatedUser user = jwtAuthService.authenticate(header);

                request.setAttribute("userId", user.userEO().getId());
                request.setAttribute("username", user.userEO().getUsername());
                request.setAttribute("userType", user.userEO().getUserType());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredTokenException ete) {
                log.warn("Expired JWT token: {}", ete.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired token");
                return;
            } catch (InvalidTokenException ite) {
                log.warn("Invalid JWT token: {}", ite.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            } catch (Exception e) {
                log.error("Unexpected error during JWT processing", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Token processing failed");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
