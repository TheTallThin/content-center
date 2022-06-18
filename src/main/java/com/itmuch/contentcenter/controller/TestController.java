package com.itmuch.contentcenter.controller;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itmuch.contentcenter.feignclient.TestBaiduFeignClient;
import com.itmuch.contentcenter.pojo.vo.UserDTO;
import com.itmuch.contentcenter.rocketmq.MySource;
import com.itmuch.contentcenter.sentinelTest.TestControllerBlockHandlerClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;


/**
 * @RefreshScope （nacos配置管理，自动刷新）
 * @author 何林冲
 */
@RestController
@RequestMapping("/test")
@Slf4j
@RefreshScope
public class TestController {

    @Autowired
    private TestBaiduFeignClient testBaiduFeignClient;


    @GetMapping("baidu")
    public String baiduIndex(){
        return this.testBaiduFeignClient.index();
    }


    @GetMapping("test-hot")
    @SentinelResource("hot")
    public String testHot(@RequestParam(required = false)String a
            ,@RequestParam(required = false)String b){

        return a+" "+b;
    }

    /**
     * 方法一 ，基础实现Sentinel
     * @param a
     * @return
     */
    @GetMapping("/test-Sentinel-api")
    public String testSentinelAPI(@RequestParam(required = false)String a){
        String resourceName = "test-Sentinel-api";
        //第二个参数是来源(流控规则-针对来源)
        ContextUtil.enter(resourceName,"test-wfw");
        Entry entry = null;
        try {
            //定义一个sentinel保护资源，名称是test-Sentinel-api
            entry = SphU.entry(resourceName);
            if(StringUtils.isBlank(a)){
                throw new IllegalArgumentException("a不能为空");
            }
            return a;
        }
        //如果被保护的资源被限流或者降级了，就会抛BlockException
        catch (BlockException e) {
            e.printStackTrace();
            log.warn("限流或者降级了！！",e);
            return "限流或者降级了";
        }catch (IllegalArgumentException e2){
            //统计IllegalArgumentException[发生的次数，发生占比...]
            Tracer.trace(e2);
            return "参数非法！";
        }
        finally {
            if(entry !=null ){
                //退出entry
                entry.exit();
            }
            ContextUtil.exit();
        }
    }

    /**
     * 方法二 ，用注解 实现Sentinel
     * @param a
     * @return
     */
    @GetMapping("test-sentinel-resource")
    @SentinelResource(value = "test-sentinel-resource"
            ,blockHandler = "block"
            ,blockHandlerClass = TestControllerBlockHandlerClass.class
            ,fallback = "fallback")
    public String testSentinelResource(@RequestParam(required = false)String a){

        if(StringUtils.isBlank(a)){
            throw new IllegalArgumentException("a cannot be blank.");
        }

        return a;

    }

    /***
     *处理限流或者降级
    [a, e]
     * @return {@link String}
     * @throws
     * @author 何林冲  @date 2022/5/8 23:10
     */
    public String block(String a,BlockException e){
        log.warn("限流或者降级了！！",e);
        return "限流或者降级了block";
    }
    /***
     *处理降级
     *  - sentinel 1.6 可以处理Throwable 不管什么异常都可以
    [a]
     * @return {@link String}
     * @throws
     * @author 何林冲  @date 2022/5/8 23:10
     */
    public String fallback(String a){
        log.warn("限流或者降级了！！");
        return "限流或者降级了fallback";
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test-rest-template-sentinel/{userId}")
    public UserDTO test(@PathVariable Integer userId){

        return this.restTemplate.
                getForObject("http://user-center/users/{userId}",
                        UserDTO.class, userId);
    }


    /**
     *  参数，RestTemplate 实现token传递
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/tokenRelay/{userId}")
    public ResponseEntity<UserDTO> tokenRelay(@PathVariable Integer userId,HttpServletRequest request){
        // 1.从header里面获取token
        String token = request.getHeader("X-Token");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Token", token);
        ResponseEntity<UserDTO> exchange = this.restTemplate
                .exchange("http://user-center/users/{userId}",
                        HttpMethod.GET,
                        new HttpEntity<>(httpHeaders),
                        UserDTO.class,
                        userId
                );
        return exchange;
    }


    @Resource
    private Source source;

    /**
     *  springCloud stream 实现
     * @return
     */
    @GetMapping("/test-stream")
    public String testStream(){
        this.source.output()
                .send(
                        MessageBuilder
                        .withPayload("消息体")
                        .build()
                );
        return "成功！！";
    }

   /* @Resource
    private MySource mySource;

    *//**
     *  springCloud stream 实现
     * @return
     *//*
    @GetMapping("/test-stream2")
    public String testStream2(){
        this.mySource.output()
                .send(
                        MessageBuilder
                                .withPayload("消息体")
                                .build()
                );
        return "成功！！";
    }*/


    @Value("${your.config}")
    private String youConfig;

    @GetMapping("/test-config")
    public String testConfig(){

        return this.youConfig;
    }
}
