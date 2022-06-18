package com.itmuch.contentcenter.interceptor;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 传递token的拦截器
 * @author 何林冲
 */
public class TokenRelayRequestIntecepor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 1.获取到token
        // 1.从header里面获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader("X-Token");;
        if (StringUtils.isNotBlank(token)) {
            // 2.将token传递
            requestTemplate.header("X-Token", token);
        }




    }
}
