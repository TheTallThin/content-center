package com.itmuch.contentcenter.feignclient;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/** feign的配置类
 * @author 何林冲
 *
 * 这个类别加@Configuration注解了，否则必须挪到@ComponentScan以外
 */
public class UserCenterFeignConfiguration {


    @Bean
    public Logger.Level level(){
        // 让feign打印所有请求的细节
        return Logger.Level.FULL;
    }

}
