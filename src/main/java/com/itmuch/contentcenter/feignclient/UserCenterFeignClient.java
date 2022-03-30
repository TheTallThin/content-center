package com.itmuch.contentcenter.feignclient;


import com.itmuch.contentcenter.pojo.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 何林冲
 * 方法配置feign日志   configuration = UserCenterFeignConfiguration.class
 * @FeignClient(name = "user-center",configuration = UserCenterFeignConfiguration.class)
 *
 */
@FeignClient(name = "user-center")
public interface UserCenterFeignClient {

    /***
     * http://user-center/users/{id}
    [id]
     * @return {@link UserDTO}
     * @throws
     * @author 何林冲  @date 2022/3/25 16:05
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
