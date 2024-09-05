package com.officerschool.courselottery.web;

import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import com.officerschool.courselottery.common.models.req.*;
import com.officerschool.courselottery.service.CourseService;
import com.officerschool.courselottery.service.ExpertService;
import com.officerschool.courselottery.service.ScheduleService;
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
 * @date : 2024/9/4
 */
@RestController
@RequestMapping("/lottery/course/needLogin")
public class LotteryNeedLoginController {

    private final Logger logger = LoggerFactory.getLogger(LotteryNeedLoginController.class);

    @Resource
    private ExpertService expertService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private CourseService courseService;

    @RequestMapping(value = "/experts", method = RequestMethod.GET)
    public CommonResult experts(PageReq req) {
        try {
            return CommonResult.createOK(expertService.getExpertList(req));
        } catch (Exception e) {
            logger.error("LotteryController#experts error: ", e);
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

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public CommonResult courses(CoursesPageReq req) {
        try {
            return CommonResult.createOK(courseService.getCourses(req));
        } catch (Exception e) {
            logger.error("LotteryController#schedules error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/expert/insert", method = RequestMethod.POST)
    public CommonResult expertInsert(@RequestBody ExpertReq req){
        try {
            return CommonResult.createOK(expertService.expertInsert(req));
        } catch (Exception e) {
            logger.error("LotteryNeedLoginController#experts error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/expert/modify", method = RequestMethod.POST)
    public CommonResult expertModify(@RequestBody ExpertReq req){
        try {
            return CommonResult.createOK(expertService.expertModify(req));
        } catch (Exception e) {
            logger.error("LotteryNeedLoginController#experts error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/expert/delete", method = RequestMethod.POST)
    public CommonResult expertDelete(@RequestBody DeleteReq req) {
        try {
            return CommonResult.createOK(expertService.deleteExpert(req));
        } catch (Exception e) {
            logger.error("LotteryNeedLoginController#expertDelete error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/schedule/delete", method = RequestMethod.POST)
    public CommonResult scheduleDelete(@RequestBody DeleteReq req) {
        try {
            return CommonResult.createOK(scheduleService.deleteSchedule(req));
        } catch (Exception e) {
            logger.error("LotteryNeedLoginController#scheduleDelete error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/course/delete", method = RequestMethod.POST)
    public CommonResult courseDelete(@RequestBody DeleteReq req) {
        try {
            return CommonResult.createOK(courseService.deleteCourse(req));
        } catch (Exception e) {
            logger.error("LotteryNeedLoginController#courseDelete error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }
}
