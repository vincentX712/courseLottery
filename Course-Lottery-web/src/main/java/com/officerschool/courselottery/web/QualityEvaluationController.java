package com.officerschool.courselottery.web;

import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import com.officerschool.courselottery.common.models.req.*;
import com.officerschool.courselottery.common.models.res.ClassDetailRes;
import com.officerschool.courselottery.common.models.res.ClassRes;
import com.officerschool.courselottery.common.models.res.TeacherRes;
import com.officerschool.courselottery.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/lottery/qualityEvaluation")
public class QualityEvaluationController {
    private final Logger logger = LoggerFactory.getLogger(QualityEvaluationController.class);

    @Resource
    private ClassService classService;

    @Resource
    private TeacherService teacherService;

    @RequestMapping(value = "/classes", method = RequestMethod.GET)
    public CommonResult classes(PageReq req) {
        try {
            return CommonResult.createOK(classService.getClasses(req));
        } catch (Exception e) {
            logger.error("QualityEvaluationController#classes error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/class/insert", method = RequestMethod.POST)
    public CommonResult classInsert(@RequestBody ClassReq req){
        try {
            return CommonResult.createOK(classService.classInsert(req));
        } catch (Exception e) {
            logger.error("QualityEvaluationController#classInsert error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/class/modify", method = RequestMethod.POST)
    public CommonResult classModify(@RequestBody ClassReq req){
        try {
            return CommonResult.createOK(classService.classModify(req));
        } catch (Exception e) {
            logger.error("QualityEvaluationController#classInsert error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/class/delete", method = RequestMethod.POST)
    public CommonResult classDelete(@RequestBody DeleteReq req){
        try {
            if(req.getId()==null){
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            }
            return CommonResult.createOK(classService.classDelete(req.getId()));
        } catch (Exception e) {
            logger.error("QualityEvaluationController#teacherInsert error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/teacherScore/list", method = RequestMethod.GET)
    public CommonResult classTeacherScore(Integer classId) {
        try {
            if(classId==null){
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            }
            ClassRes classRes=classService.getOneClass(classId);
            List<TeacherRes> teacherResList = classService.getClassTeachersScore(classId);
            ClassDetailRes classDetailRes = new ClassDetailRes();
            classDetailRes.setClassRes(classRes);
            classDetailRes.setList(teacherResList);
            return CommonResult.createOK(classDetailRes);
        } catch (Exception e) {
            logger.error("QualityEvaluationController#teacherScoreList error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/teacher/list", method = RequestMethod.GET)
    public CommonResult classTeachers(Integer classId) {
        try {
            if(classId==null){
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            }
            ClassRes classRes=classService.getOneClass(classId);
            List<TeacherRes> teacherResList = classService.getClassTeachers(classId);
            ClassDetailRes classDetailRes = new ClassDetailRes();
            classDetailRes.setClassRes(classRes);
            classDetailRes.setList(teacherResList);
            return CommonResult.createOK(classDetailRes);
        } catch (Exception e) {
            logger.error("QualityEvaluationController#teacherList error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/teacher/insert", method = RequestMethod.POST)
    public CommonResult teacherInsert(@RequestBody TeacherReq req){
        try {
            if(req.getName()==null || req.getClassId()==null){
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            }
            return CommonResult.createOK(classService.teacherInsert(req));
        } catch (Exception e) {
            logger.error("QualityEvaluationController#teacherInsert error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/teacher/delete", method = RequestMethod.POST)
    public CommonResult teacherDelete(@RequestBody DeleteReq req){
        try {
            if(req.getId()==null){
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            }
            return CommonResult.createOK(classService.teacherDelete(req.getId()));
        } catch (Exception e) {
            logger.error("QualityEvaluationController#teacherInsert error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/teacher/score/commit", method = RequestMethod.POST)
    public CommonResult scoreCommit(@RequestBody ScoreCommitReq req){
        try {
            return CommonResult.createOK(classService.scoreCommit(req));
        } catch (Exception e){
            logger.error("QualityEvaluationController#scoreCommit error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/teacher/score/export", method = RequestMethod.GET)
    public void scoreExport(HttpServletResponse response, Integer classId){
        try {
            classService.scoreExport(response,classId);
        } catch (Exception e){
            logger.error("excelExport error", e);
        }
    }

    @RequestMapping(value = "/schoolTerm/teacher/score/export", method = RequestMethod.GET)
    public void scoreExport(HttpServletResponse response, String startTime,String endTime){
        try {
            classService.teacherScoreExport(response,startTime,endTime);
        } catch (Exception e){
            logger.error("excelExport error", e);
        }
    }
}
