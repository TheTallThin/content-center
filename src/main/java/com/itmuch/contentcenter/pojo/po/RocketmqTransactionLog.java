package com.itmuch.contentcenter.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * RocketMQ事务日志表
 * </p>
 *
 * @author hlc
 * @since 2022-03-06
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rocketmq_transaction_log")
@ApiModel(value="RocketmqTransactionLog对象", description="RocketMQ事务日志表")
public class RocketmqTransactionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "事务id")
    @TableField("transaction_Id")
    private String transactionId;

    @ApiModelProperty(value = "日志")
    private String log;


}
