package com.officerschool.courselottery.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/19
 */
@ComponentScan(basePackages = {"com.officerschool*"})
@SpringBootApplication
@MapperScan("com.officerschool.courselottery.dao.mapper")
@ServletComponentScan(basePackages = "com.officerschool.courselottery.web.filter")
public class CourseLotteryStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseLotteryStarterApplication.class, args);
        System.out.println("======================================SUCCESS======================================");
    }
}
