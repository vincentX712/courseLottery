package com.officerschool.courselottery.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import com.officerschool.courselottery.common.models.req.ConfirmLotteryReq;
import com.officerschool.courselottery.common.models.req.ExpertsPageReq;
import com.officerschool.courselottery.common.models.req.LotteryReq;
import com.officerschool.courselottery.common.models.req.SchedulesPageReq;
import com.officerschool.courselottery.service.ExpertService;
import com.officerschool.courselottery.service.LotteryService;
import com.officerschool.courselottery.service.ScheduleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private LotteryService lotteryService;

    @RequestMapping(value = "/experts", method = RequestMethod.GET)
    public CommonResult experts(ExpertsPageReq req) {
        try {
            return CommonResult.createOK(expertService.getExpertList(req));
        } catch (Exception e) {
            logger.error("LotteryController#experts error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/lottery", method = RequestMethod.POST)
    public CommonResult lottery(@RequestBody LotteryReq req) {
        try {
            if (StringUtils.isBlank(req.getExpertId().toString())) {
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            }
            return CommonResult.createOK(lotteryService.lottery(req));
        } catch (Exception e) {
            logger.error("LotteryController#lottery error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public CommonResult confirmLottery(@RequestBody ConfirmLotteryReq req) {
        try {
            if (StringUtils.isBlank(req.getExpertId().toString()) || StringUtils.isBlank(req.getCourseId().toString()))
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);

            return CommonResult.createOK(lotteryService.confirmLottery(req));
        } catch (Exception e) {
            logger.error("LotteryController#confirmLottery error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/schedules", method = RequestMethod.GET)
    public CommonResult schedules(SchedulesPageReq req) {
        try {
            return CommonResult.createOK(scheduleService.getSchedules(req));
        } catch (Exception e) {
            logger.error("LotteryController#schedules error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

}
