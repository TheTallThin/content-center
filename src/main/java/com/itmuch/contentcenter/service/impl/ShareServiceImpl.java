package com.itmuch.contentcenter.service.impl;

import com.itmuch.contentcenter.pojo.Share;
import com.itmuch.contentcenter.mapper.ShareMapper;
import com.itmuch.contentcenter.service.IShareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分享表 服务实现类
 * </p>
 *
 * @author hlc
 * @since 2022-03-06
 */
@Service
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements IShareService {

}
