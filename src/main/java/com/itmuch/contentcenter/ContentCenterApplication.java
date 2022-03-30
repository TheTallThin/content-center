package com.itmuch.contentcenter;

import com.itmuch.contentcenter.feignclient.UserCenterFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * @author 何林冲
 * 扫描Mybatis 哪些包里面的接口
 * @MapperScan("com.itmuch.contentcenter.mapper")
 * 使用 Feign实现远程HTTP调用
 * @EnableFeignClients
 * Feign  代码全局配置
 *@EnableFeignClients(defaultConfiguration = UserCenterFeignConfiguration.class)
 */
@MapperScan("com.itmuch.contentcenter.mapper")
@SpringBootApplication
@EnableFeignClients
public class ContentCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }




    /***
     *  @LoadBalanced 负载均衡算法
    []
     * @return {@link RestTemplate}
     * @throws
     * @author 何林冲  @date 2022/3/25 16:10
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
