package com.itmuch.contentcenter.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import com.itmuch.contentcenter.pojo.Share;
import com.itmuch.contentcenter.mapper.ShareMapper;
import com.itmuch.contentcenter.pojo.ShareVo;
import com.itmuch.contentcenter.pojo.UserDTO;
import com.itmuch.contentcenter.service.IShareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * <p>
 * 分享表 服务实现类
 * </p>
 *
 * @author hlc
 * @since 2022-03-06
 */
@Service
@Slf4j
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements IShareService {


    @Resource
    private ShareMapper shareMapper;

    @Resource
    private RestTemplate  restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private UserCenterFeignClient userCenterFeignClient;

    @Override
    public ShareVo queryShareById(Integer id) {
        Share share = this.shareMapper.selectById(id);
        Integer userId = share.getUserId();
        ShareVo shareVo = new ShareVo();
        //所有的用户中心实例的信息
       /* List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        String url = instances.stream().map(string->string.getUri().toString()+"/users/{id}")
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("当前没有实例！！"));
                 log.info("请求的目标地址："+url);
        List<String> list = instances.stream().map(string->string.getUri().toString()+"/users/{id}")
                .collect(Collectors.toList());
        //随机拿到下标
        int i = ThreadLocalRandom.current().nextInt(list.size());
        log.info("请求的目标地址："+list.get(i));
*/

        //有返回状态码 getForEntity
       /* ResponseEntity<UserDTO> users = this.restTemplate.getForEntity("http://user-center/users/{id}",UserDTO.class,userId);
        shareVo.setWxNickname(users.getBody().getWxNickname());*/
        // 消息装配
        BeanUtils.copyProperties(share,shareVo);

        //Feign 实现HTTP调用
        UserDTO userDTO = this.userCenterFeignClient.findById(userId);
        shareVo.setWxNickname(userDTO.getWxNickname());
        return shareVo;
    }
}
