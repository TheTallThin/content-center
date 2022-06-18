package com.itmuch.contentcenter.auth;


import com.itmuch.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * @author 何林冲
 * @Aspect 表示这是一个切面
 */
@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthAspect {

    private final JwtOperator jwtOperator;

    /**
     * @param point
     * @return
     */
    @Around("@annotation(com.itmuch.contentcenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {

        checkToken();
        // 结束这个切面
        return point.proceed();
    }

    private void checkToken() {
        try {
            HttpServletRequest request = getHttpServletRequest();
            String token = request.getHeader("X-Token");
            // 2.校验token是否合法，如果合法就放行，否则就抛异常
            Boolean aBoolean = jwtOperator.validateToken(token);
            if (!aBoolean) {
                throw new SecurityException("token不合法！");
            }

            // 3.如果校验成功，那么就将用户的信息设置到request的attribute里面
            Claims claimsFromToken = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id", claimsFromToken.get("id"));
            request.setAttribute("wxNickName", claimsFromToken.get("wxNickName"));
            request.setAttribute("role", claimsFromToken.get("role"));
        } catch (Throwable throwable) {
            throw new SecurityException("token不合法！");
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        // 1.从header里面获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        return attributes.getRequest();

    }

    @Around("@annotation(com.itmuch.contentcenter.auth.CheckAuthorization)")
    public Object CheckAuthorization(ProceedingJoinPoint point) throws Throwable {
        try {
            // 1.验证token是否合法
            this.checkToken();
            // 2.验证用户角色是否匹配
            HttpServletRequest request = getHttpServletRequest();
            String role = (String) request.getAttribute("role");
            // 3.拿到CheckAuthorization接口定义的value的值
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);
            String value = annotation.value();

            if (!Objects.equals(role, value)) {
                throw new SecurityException("用户无权访问！");
            }

        } catch (Throwable throwable) {
            throw new SecurityException("用户无权访问！", throwable);
        }
        return point.proceed();
    }

}
