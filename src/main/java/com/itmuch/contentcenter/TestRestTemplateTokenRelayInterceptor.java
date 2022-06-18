package com.itmuch.contentcenter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 使用 ClientHttpRequestInterceptor RestTemplate 传递token(全局)
 * @author 何林冲
 */
public class TestRestTemplateTokenRelayInterceptor
implements ClientHttpRequestInterceptor{
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        // 1.从header里面获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("X-Token");
        HttpHeaders headers = httpRequest.getHeaders();
        headers.add("X-Token", token);

        // 保证请求继续执行
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
