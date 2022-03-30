package com.itmuch.contentcenter.controller;


import com.itmuch.contentcenter.pojo.ShareVo;
import com.itmuch.contentcenter.service.impl.ShareServiceImpl;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 分享表 前端控制器
 * </p>
 *
 * @author hlc
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/shares")
public class ShareController {


    @Resource
    private ShareServiceImpl shareService;


    @GetMapping("/{id}")
        public ShareVo queryShareById(@PathVariable Integer id){
        return this.shareService.queryShareById(id);
    }

}
