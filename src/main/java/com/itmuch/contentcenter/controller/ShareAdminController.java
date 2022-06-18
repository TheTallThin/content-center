package com.itmuch.contentcenter.controller;


import com.itmuch.contentcenter.auth.CheckAuthorization;
import com.itmuch.contentcenter.pojo.po.Share;
import com.itmuch.contentcenter.pojo.vo.ShareAuditDTO;
import com.itmuch.contentcenter.service.IShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/shares")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareAdminController {

    private final IShareService shareService;


    @PutMapping("audit/{id}")
    @CheckAuthorization("admin")
    public Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO auditDTO, HttpServletRequest request){

        //进行判断，是否是admin,是:进行执行，否:没有权限（这是土方法，新方法:使用AOP进行权限的验证）
        Object role = request.getAttribute("role");
        // TODO 认证、授权
        return this.shareService.audiById(id,auditDTO);
    }
}
