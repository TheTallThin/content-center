package com.itmuch.contentcenter;

//import com.alibaba.csp.sentinel.adapter.spring.resttemplate.annotation.SentinelRestTemplate;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.itmuch.contentcenter.feignclient.UserCenterFeignConfiguration;
import com.itmuch.contentcenter.rocketmq.MySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


/**
 * @author 何林冲
 * 扫描Mybatis 哪些包里面的接口
 * @MapperScan("com.itmuch.contentcenter.mapper")
 * 使用 Feign实现远程HTTP调用
 * @EnableFeignClients
 * Feign  代码全局配置
 *@EnableFeignClients(defaultConfiguration = UserCenterFeignConfiguration.class)
 *
 * @EnableBinding(Source.class) 是springCloud stream配置(MQ生产者)
 */
@MapperScan("com.itmuch.contentcenter.mapper")
@SpringBootApplication
@EnableFeignClients
@EnableBinding({Source.class,
        //MySource.class
})
public class ContentCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }




    /***
     *  @LoadBalanced 负载均衡算法
     *  @SentinelRestTemplate(blockHandler = "block")  restTemplate整合 sentinel
     *  可以跟@SentinelResource一样，重构限流或者降级的值
     *
    []
     * @return {@link RestTemplate}
     * @throws
     * @author 何林冲  @date 2022/3/25 16:10
     */
    @LoadBalanced
    @Bean
    @SentinelRestTemplate(blockHandler = "block")
    public RestTemplate restTemplate(){

        /* 全局的请求的时候加了token*/
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(
                Collections.singletonList(
                        new TestRestTemplateTokenRelayInterceptor()
                )

        );
        return restTemplate;
        //return new RestTemplate();
    }
}
