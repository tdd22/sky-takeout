package com.tdd.skytakeout.security;


import com.tdd.skytakeout.common.exception.BusinessException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    public JwtAuthenticationInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String authorization =
                request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null
                || !authorization.startsWith(BEARER_PREFIX)) {
            throw new BusinessException(
                    HttpStatus.UNAUTHORIZED,
                    40102,
                    "缺少访问令牌"
            );
        }

        String token = authorization
                .substring(BEARER_PREFIX.length())
                .trim();

        if (token.isEmpty()) {
            throw new BusinessException(
                    HttpStatus.UNAUTHORIZED,
                    40102,
                    "缺少访问令牌"
            );
        }

        try {
            Long employeeId = jwtService.parseEmployeeId(token);
            EmployeeContext.setCurrentEmployeeId(employeeId);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            throw new BusinessException(
                    HttpStatus.UNAUTHORIZED,
                    40103,
                    "访问令牌无效或已过期"
            );
        }
    }


    //Mvc出来之后,要对EmployeeContext进行处理。
    //Web服务器会复用线程。如果不清理，线程处理下一个请求时可能残留上一个员工的身份，造成严重越权
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception exception
    ) {
        EmployeeContext.clear();
    }
}
