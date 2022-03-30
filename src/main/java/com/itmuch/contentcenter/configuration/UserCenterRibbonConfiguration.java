package com.itmuch.contentcenter.configuration;
import com.itmuch.RibbonConfiguration.RibbonConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author 何林冲
 */
@Configuration
//user-center  负载均衡：随机选择一个  RandomRule
//@RibbonClient(name = "user-center", configuration = RibbonConfiguration.class)

//全局配置
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class UserCenterRibbonConfiguration {
}
