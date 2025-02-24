package org.example.expert.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "토큰이 없습니다");
            return false;
        }

        String token = jwtUtil.substringToken(authHeader);

        try {
            Claims claims = jwtUtil.extractClaims(token);
            logger.info("요청 시각 ={}, URL ={}", claims.getIssuedAt(), request.getRequestURI());
        }catch (RuntimeException e) {
            logger.info("검증 실패");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return false;
        }

        return true;
    }
}
