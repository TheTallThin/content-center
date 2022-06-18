package com.itmuch.contentcenter.feignclient.fallbackFactory;

import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import com.itmuch.contentcenter.pojo.vo.UserDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** feign流控或者降级会走这里(可以捕获异常)
 * @author 何林冲
 */
/*@Component
@Slf4j
public class UserCenterFeignClientFallbackFactory
implements FallbackFactory<UserCenterFeignClient> {
    @Override
    public UserCenterFeignClient create(Throwable throwable) {
        return new UserCenterFeignClient() {
            @Override
            public UserDTO findById(Integer id) {
                log.warn("远程调用被限流/降级了",throwable);
                UserDTO userDTO = new UserDTO();
                userDTO.setWxNickname("一个默认用户");
                return userDTO;
            }
        };
    }
}*/
