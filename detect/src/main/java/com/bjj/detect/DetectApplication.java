package com.bjj.detect;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.Async;

@Async
@MapperScan("com.bjj.detect.dao")
@SpringBootApplication
public class DetectApplication extends SpringBootServletInitializer {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public static void main(String[] args) {
        SpringApplication.run(DetectApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DetectApplication.class);
    }

    //</editor-fold>

}
