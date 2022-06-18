package com.itmuch.contentcenter.service;

import com.itmuch.contentcenter.pojo.po.Share;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmuch.contentcenter.pojo.vo.ShareAuditDTO;
import com.itmuch.contentcenter.pojo.vo.ShareVo;

/**
 * <p>
 * 分享表 服务类
 * </p>
 *
 * @author hlc
 * @since 2022-03-06
 */
public interface IShareService extends IService<Share> {


    ShareVo queryShareById(Integer id
            //,String token
    );


    Share audiById(Integer id, ShareAuditDTO auditDTO);
}
