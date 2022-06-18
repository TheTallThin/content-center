package com.itmuch.contentcenter.auth;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 验证用户是否有权限访问
 * @author 何林冲
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthorization {

    String value();
}
