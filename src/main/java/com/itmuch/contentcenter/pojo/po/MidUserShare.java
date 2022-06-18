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
 * 用户-分享中间表【描述用户购买的分享】
 * </p>
 *
 * @author hlc
 * @since 2022-03-06
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mid_user_share")
@ApiModel(value="MidUserShare对象", description="用户-分享中间表【描述用户购买的分享】")
public class MidUserShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "share.id")
    @TableField("share_id")
    private Integer shareId;

    @ApiModelProperty(value = "user.id")
    @TableField("user_id")
    private Integer userId;


}
