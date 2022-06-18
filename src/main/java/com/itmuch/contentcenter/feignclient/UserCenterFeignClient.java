package com.itmuch.contentcenter.feignclient;


//import com.itmuch.contentcenter.feignclient.fallbackFactory.UserCenterFeignClientFallbackFactory;
import com.itmuch.contentcenter.pojo.vo.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author 何林冲
 * 方法配置feign日志
 * @FeignClient(name = "user-center",configuration = UserCenterFeignConfiguration.class)
 *
 * a: feign配置流控，降级逻辑，流控或者降级会走了UserCenterFeignClientFallback
 * @FeignClient(name = "user-center",fallback = UserCenterFeignClientFallback.class)
 *
 * b:feign配置流控，降级逻辑，UserCenterFeignClientFallbackFactory
 * a，b区别：b可以获取异常，a没有（a和b只能存在一个）
 * @FeignClient(name = "user-center",fallbackFactory = UserCenterFeignClientFallbackFactory.class)
 */
@FeignClient(name = "user-center"
        //,fallback = UserCenterFeignClientFallback.class
      //  ,fallbackFactory = UserCenterFeignClientFallbackFactory.class

)
public interface UserCenterFeignClient {

    /***
     * http://user-center/users/{id}
     *
     *  方式1： feign实现token传递 @RequestHeader("X-Token")String token  (传出一个X-Token)
     *  方式1：feign 拦截器拦截token并传递 类：TokenRelayRequestIntecepor
    [id]
     * @return {@link UserDTO}
     * @throws
     * @author 何林冲  @date 2022/3/25 16:05
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id
            //,@RequestHeader("X-Token")String token
    );
}
