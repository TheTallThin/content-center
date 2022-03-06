package com.itmuch.contentcenter.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分享表
 * </p>
 *
 * @author hlc
 * @since 2022-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Share对象", description="分享表")
public class Share implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "发布人id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否原创 0:否 1:是")
    @TableField("is_original")
    private Boolean isOriginal;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "概要信息")
    private String summary;

    @ApiModelProperty(value = "价格（需要的积分）")
    private Integer price;

    @ApiModelProperty(value = "下载地址")
    @TableField("download_url")
    private String downloadUrl;

    @ApiModelProperty(value = "下载数 ")
    @TableField("buy_count")
    private Integer buyCount;

    @ApiModelProperty(value = "是否显示 0:否 1:是")
    @TableField("show_flag")
    private Boolean showFlag;

    @ApiModelProperty(value = "审核状态 NOT_YET: 待审核 PASSED:审核通过 REJECTED:审核不通过")
    @TableField("audit_status")
    private String auditStatus;

    @ApiModelProperty(value = "审核不通过原因")
    private String reason;


}
