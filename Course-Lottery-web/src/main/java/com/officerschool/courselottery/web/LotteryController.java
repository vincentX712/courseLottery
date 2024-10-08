package com.officerschool.courselottery.web;

import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import com.officerschool.courselottery.common.models.req.*;
import com.officerschool.courselottery.service.CourseService;
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
@RequestMapping("/lottery/course")
public class LotteryController {

    private final Logger logger = LoggerFactory.getLogger(LotteryController.class);

    @Resource
    private ExpertService expertService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private LotteryService lotteryService;

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

    @RequestMapping(value = "/teacherScheduleSearch", method = RequestMethod.GET)
    public CommonResult teacherScheduleSearch(ConfirmLotteryReq req) {
        try {
            if (StringUtils.isBlank(req.getCourseId().toString()))
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            return CommonResult.createOK(scheduleService.isCourseTeacherHasLotteried(req.getCourseId()));
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

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public CommonResult courses(CoursesPageReq req) {
        try {
            return CommonResult.createOK(courseService.getCourses(req));
        } catch (Exception e) {
            logger.error("LotteryController#schedules error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/schedule/delete", method = RequestMethod.POST)
    public CommonResult scheduleDelete(@RequestBody DeleteReq req) {
        try {
            return CommonResult.createOK(scheduleService.deleteSchedule(req));
        } catch (Exception e) {
            logger.error("LotteryController#scheduleDelete error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }
}
