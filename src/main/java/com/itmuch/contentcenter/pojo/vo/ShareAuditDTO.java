package com.itmuch.contentcenter.pojo.vo;


import com.itmuch.contentcenter.pojo.enums.AuditStatusEnum;
import lombok.Data;

@Data
public class ShareAuditDTO {
    /**
     * 审核状态
     */
    private AuditStatusEnum auditStatusEnum;
    /**
     * 原因
     */
    private String reason;

}
