package com.itmuch.contentcenter.feignclient;


import com.itmuch.contentcenter.pojo.vo.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-center")
public interface TestUserCenterFeignClient {


    /***
     *@SpringQueryMap  了解更多请求：https://www.imooc.com/article/289000
    [userDTO]
     * @return {@link UserDTO}
     * @throws
     * @author 何林冲  @date 2022/3/30 11:02
     */
    @GetMapping("/users/q")
    UserDTO query(@SpringQueryMap UserDTO userDTO);

}
