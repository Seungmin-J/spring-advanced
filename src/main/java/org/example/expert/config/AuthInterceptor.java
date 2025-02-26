package org.example.expert.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.expert.domain.common.exception.UnauthorizedAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private final JwtUtil jwtUtil;

    public AuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String bearerToken = request.getHeader("Authorization");
        if (!bearerToken.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bearer 형식 불일치");
            return false;
        }

        if (bearerToken == null) {
            logger.info("토큰 없음");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 없음");
            return false;
        }

        String token = jwtUtil.substringToken(bearerToken);

        Claims claims = jwtUtil.extractClaims(token);

        String userRole = claims.get("userRole", String.class);
        logger.info("userRole={}", userRole);

        if (!"ADMIN".equals(userRole)) {
            throw new UnauthorizedAccessException("관리자만 접근 가능합니다");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
