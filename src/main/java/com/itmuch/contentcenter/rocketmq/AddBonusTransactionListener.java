package com.itmuch.contentcenter.rocketmq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itmuch.contentcenter.mapper.RocketmqTransactionLogMapper;
import com.itmuch.contentcenter.pojo.po.RocketmqTransactionLog;
import com.itmuch.contentcenter.pojo.vo.ShareAuditDTO;
import com.itmuch.contentcenter.service.impl.ShareServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

/** MQ Server（mq的事务）
 * @author 何林冲
 */
@RocketMQTransactionListener(txProducerGroup = "tx-add-bonus-group")
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    private final ShareServiceImpl shareService;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;
    /**
     * 执行本地事务
     * @param msg
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object o) {
        // rocketMQ 分布式事务
        /*MessageHeaders headers = msg.getHeaders();
        String  transactionId = (String)headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer share_id = Integer.valueOf((String)headers.get("share_id"));
        try {
            this.shareService.audiByIdWithRocketMqLog(share_id, (ShareAuditDTO)o,transactionId);
            // 成功就提交
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            // 失败就回滚
            return RocketMQLocalTransactionState.ROLLBACK;

        }*/

        // SpringCloudStream + rocketMQ 实现分布式事务
        MessageHeaders headers = msg.getHeaders();
        String  transactionId = (String)headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer share_id = Integer.valueOf((String)headers.get("share_id"));
        // 有个小坑 ,获得的是字符串，要反序列化。
        String dto = (String)headers.get("dto");
        ShareAuditDTO shareAuditDTO = JSON.parseObject(dto, ShareAuditDTO.class);
        try {
            this.shareService.audiByIdWithRocketMqLog(share_id, shareAuditDTO,transactionId);
            // 成功就提交
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            // 失败就回滚
            return RocketMQLocalTransactionState.ROLLBACK;

        }

    }

    /**
     * 本地事业检查接口
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        MessageHeaders headers = msg.getHeaders();
        String  transactionId = (String)headers.get(RocketMQHeaders.TRANSACTION_ID);
        RocketmqTransactionLog transaction_id = this.rocketmqTransactionLogMapper.selectOne(new QueryWrapper<RocketmqTransactionLog>().eq("transaction_Id", transactionId));
        if (transaction_id != null) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
