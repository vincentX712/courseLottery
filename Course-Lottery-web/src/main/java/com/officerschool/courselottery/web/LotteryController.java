package com.officerschool.courselottery.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 抽课系统
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/19
 */
@RestController
@RequestMapping("/lottery")
public class LotteryController {

    private final Logger logger = LoggerFactory.getLogger(LotteryController.class);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "choukexitong";
    }
}
