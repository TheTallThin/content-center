package com.itmuch.contentcenter.controller;


import com.itmuch.contentcenter.auth.CheckLogin;
import com.itmuch.contentcenter.pojo.vo.ShareVo;
import com.itmuch.contentcenter.service.impl.ShareServiceImpl;
import org.springframework.web.bind.annotation.*;

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


    @CheckLogin
    @GetMapping("/{id}")
        public ShareVo queryShareById(
                @PathVariable Integer id
            //,@RequestHeader("X-Token")String token
    ){
        return this.shareService.queryShareById(id
                //,token
        );
    }

}
