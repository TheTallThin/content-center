package com.itmuch.RibbonConfiguration;

import com.itmuch.contentcenter.configuration.NacosSameClusterWeightedRule;
import com.itmuch.contentcenter.configuration.NacosWeightedRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule(){
        //RandomRule 负载均衡:随机选择server
        //return new RandomRule();

        //自己写的基于权重的负载均衡算法
        //return new NacosWeightedRule();

        //集群  基于权重的负载均衡算法
        return new NacosSameClusterWeightedRule();
    }


/*    @Bean
    public IPing ping(){
        return new PingUrl();
    }*/
}
