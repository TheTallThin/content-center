package com.itmuch.contentcenter.sentinelTest;


import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * sentinel 实现区分来源（流控或者授权里面的来源）
 * 后面测试用不上，先注释掉
 * @author 何林冲
 */
//@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        // 从请求参数中获取名为origin的参数并返回
        // 如果获取不到origin参数，那边就异常
        // http://localhost:8099/shares/1?origin=browser
        // 不介意生产机这么写，url回非常的不好看，建议放在head里面。
        String origin = request.getParameter("origin");
        if(StringUtils.isBlank(origin)){
            throw new IllegalArgumentException("origin must be specified");
        }
        return origin;
    }
}
