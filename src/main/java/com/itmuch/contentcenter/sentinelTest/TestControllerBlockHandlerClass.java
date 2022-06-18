package com.itmuch.contentcenter.sentinelTest;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;


/** 可以通用的block
 * @author 何林冲
 */
@Slf4j
public class TestControllerBlockHandlerClass {

    /***
     *处理限流或者降级
     [a, e]
     * @return {@link String}
     * @throws
     * @author 何林冲  @date 2022/5/8 23:10
     */
    public static String block(String a, BlockException e){
        log.warn("限流或者降级了！！",e);
        return "限流或者降级了block";
    }
}
