package com.itmuch.contentcenter.service;

import com.itmuch.contentcenter.mapper.ShareMapper;
import com.itmuch.contentcenter.pojo.Share;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmuch.contentcenter.pojo.ShareVo;

import javax.annotation.Resource;

/**
 * <p>
 * 分享表 服务类
 * </p>
 *
 * @author hlc
 * @since 2022-03-06
 */
public interface IShareService extends IService<Share> {


    ShareVo queryShareById(Integer id);


}
