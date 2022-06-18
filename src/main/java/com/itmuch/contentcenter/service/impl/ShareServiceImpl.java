package com.itmuch.contentcenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import com.itmuch.contentcenter.mapper.RocketmqTransactionLogMapper;
import com.itmuch.contentcenter.pojo.enums.AuditStatusEnum;
import com.itmuch.contentcenter.pojo.messaging.UserAddBonusMsgDTO;
import com.itmuch.contentcenter.pojo.po.RocketmqTransactionLog;
import com.itmuch.contentcenter.pojo.po.Share;
import com.itmuch.contentcenter.mapper.ShareMapper;
import com.itmuch.contentcenter.pojo.vo.ShareAuditDTO;
import com.itmuch.contentcenter.pojo.vo.ShareVo;
import com.itmuch.contentcenter.pojo.vo.UserDTO;
import com.itmuch.contentcenter.service.IShareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

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

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private RocketmqTransactionLogMapper transactionLogMapper;

    /**
     * SpringCloudStream
     */
    @Resource
    private Source source;

    @Override
    public ShareVo queryShareById(Integer id
            //,String token
    ) {
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
        UserDTO userDTO = this.userCenterFeignClient.findById(userId
                //,token
        );
        shareVo.setWxNickname(userDTO.getWxNickname());
        return shareVo;
    }

    //@Transactional(rollbackFor = Exception.class)
    @Override
    public Share audiById(Integer id, ShareAuditDTO auditDTO) {
        // 1.查询share是否存在，不存在或者当前的audit_status != NO_YET,那么就抛异常
        Share share = this.shareMapper.selectById(id);
        if(share == null){
            throw new IllegalArgumentException("参数非法！该分享 不存在！");
        }
        if(!Objects.equals("NOT_YET", share.getAuditStatus())){
            throw new IllegalArgumentException("参数非法！该分享已经审核通过或者审核不通过！");
        }

        // 3.如果是PASS,那边发送消息给rocketmq(mq事务AddBonusTransactionListener)
        // 让用户中心去消费,并为发布人添加积分
        if(AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())){
            // 发送半消息
            String transactionId = UUID.randomUUID().toString();

            // SpringCloudStream + rocketMQ 实现分布式事务
            this.source.output()
                    .send(
                    MessageBuilder
                            .withPayload(UserAddBonusMsgDTO.builder()
                                    .userId(share.getUserId())
                                    .bonus(50)
                                    .build()
                            )
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                            .setHeader("share_id", id)
                            .setHeader("dto", JSON.toJSONString(auditDTO))
                            .build()
                    );

            // rocketMQ 实现分布式事务
            /*this.rocketMQTemplate.sendMessageInTransaction("tx-add-bonus-group",
                    "add-bonus",
                    MessageBuilder
                            .withPayload(UserAddBonusMsgDTO.builder()
                                    .userId(share.getUserId())
                                    .bonus(50)
                                    .build()
                            )
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                            .setHeader("share_id", id)
                            .build()
                    ,auditDTO);*/
        }
        // 如果不是PASS，就直接审核资源
        else{
            this.auditByIdOnDB(id,auditDTO);
        }
/*
        this.rocketMQTemplate.convertAndSend(
                "add-bonus",
                UserAddBonusMsgDTO.builder()
                .userId(share.getUserId())
                .bonus(50)
                .build());*/
        return share;
    }

    /**
     *  审核资源，将状态设为PASS/REJECT
     * @param id
     * @param share
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdOnDB(Integer id, ShareAuditDTO share) {
        Share share1 = Share.builder()
                .id(id)
                .auditStatus(share.getAuditStatusEnum().toString())
                .reason(share.getReason())
                .build();
        this.shareMapper.updateById(share1);

        // 4.把share 写到缓存
    }

    @Transactional(rollbackFor = Exception.class)
    public void audiByIdWithRocketMqLog(Integer id, ShareAuditDTO share,String transactionId){
        // 审核资源，将状态设为PASS/REJECT
        this.auditByIdOnDB(id,share);
        // 添加RocketMQ事务日志表
        this.transactionLogMapper.insert(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .log("审核分享...")
                        .build()
        );
    }


}
