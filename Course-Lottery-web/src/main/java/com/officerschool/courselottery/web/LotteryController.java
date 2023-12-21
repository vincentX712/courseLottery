package com.officerschool.courselottery.web;

import com.alibaba.fastjson.JSON;
import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import com.officerschool.courselottery.common.models.req.ExpertsPageReq;
import com.officerschool.courselottery.service.ExpertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @Resource
    private ExpertService expertService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public CommonResult test() {
        return CommonResult.createOK("okk系统");
    }

    @RequestMapping(value = "/experts", method = RequestMethod.GET)
    public CommonResult experts(ExpertsPageReq req) {
        try {
            return CommonResult.createOK(expertService.getExpertList(req));
        } catch (Exception e) {
            logger.error("LotteryController#experts error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }
}
