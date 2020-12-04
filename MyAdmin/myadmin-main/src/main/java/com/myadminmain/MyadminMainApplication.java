package com.myadminmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 功能描述: 项目启动类
 * @author cdfan
 * @date 2020/7/17 10:49
 */
@SpringBootApplication(
    exclude = {
        //去除activiti中引入的SecurityAutoConfiguration，防止启动报错
        org.activiti.spring.boot.SecurityAutoConfiguration.class
    }
)
@EnableCaching
public class MyadminMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyadminMainApplication.class, args);
    }

}
