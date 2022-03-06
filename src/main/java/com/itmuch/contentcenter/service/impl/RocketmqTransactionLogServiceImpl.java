package com.itmuch.contentcenter.service.impl;

import com.itmuch.contentcenter.pojo.RocketmqTransactionLog;
import com.itmuch.contentcenter.mapper.RocketmqTransactionLogMapper;
import com.itmuch.contentcenter.service.IRocketmqTransactionLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RocketMQ事务日志表 服务实现类
 * </p>
 *
 * @author hlc
 * @since 2022-03-06
 */
@Service
public class RocketmqTransactionLogServiceImpl extends ServiceImpl<RocketmqTransactionLogMapper, RocketmqTransactionLog> implements IRocketmqTransactionLogService {

}
