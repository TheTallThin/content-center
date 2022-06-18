package com.itmuch.contentcenter.feignclient.fallback;

import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import com.itmuch.contentcenter.pojo.vo.UserDTO;
import org.springframework.stereotype.Component;

/** feign流控或者降级会走这样(不可以捕获异常)
 * @author 何林冲
 */
/*@Component
public class UserCenterFeignClientFallback implements UserCenterFeignClient{

    @Override
    public UserDTO findById(Integer id) {
        //逻辑 更改了WxNickname的值
        UserDTO userDTO = new UserDTO();
        userDTO.setWxNickname("一个默认用户");
        return userDTO;
    }
}*/
