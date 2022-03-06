package com.itmuch.contentcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author 何林冲
 */ //扫描Mybatis 哪些包里面的接口
@MapperScan("com.itmuch.contentcenter.mapper")
@SpringBootApplication
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

}
