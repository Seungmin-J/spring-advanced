package org.example.expert.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.expert.domain.common.exception.UnauthorizedAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    private final JwtUtil jwtUtil;

    public LogInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            logger.info("토큰 없음");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 없음");
            return false;
        }

        String token = jwtUtil.substringToken(authHeader);

        Claims claims = jwtUtil.extractClaims(token);
        logger.info("요청 시각 ={}, URL ={}", claims.getIssuedAt(), request.getRequestURI());

        String userRole = claims.get("userRole", String.class);
        logger.info("userRole={}", userRole);

        if (!"ADMIN".equals(userRole)) {
            throw new UnauthorizedAccessException("관리자만 접근 가능합니다");
        }

        if (!authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bearer 형식 불일치");
            return false;
        }

        return true;
    }

}
