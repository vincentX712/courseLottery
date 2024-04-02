package com.officerschool.courselottery.web;

import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import com.officerschool.courselottery.common.models.req.SchedulesPageReq;
import com.officerschool.courselottery.service.CourseService;
import com.officerschool.courselottery.service.ExpertService;
import com.officerschool.courselottery.service.ScheduleService;
import com.officerschool.courselottery.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2024/1/2
 */
@RestController
@RequestMapping("/lottery/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Resource
    private ExpertService expertService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private CourseService courseService;

    @Resource
    private ScheduleService scheduleService;

    @RequestMapping(value = "/excelImport/experts", method = RequestMethod.POST)
    public CommonResult excelImportExperts(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null)
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            // 验证文件格式
            String fileName = file.getOriginalFilename();
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx"))
                return CommonResult.fail(ErrorCodeEnum.FILE_FORMAT_ERROR);

            return CommonResult.createOK(expertService.importExcel(file));
        } catch (Exception e) {
            logger.error("FileController#excelImportExperts error", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/excelImport/teachers", method = RequestMethod.POST)
    public CommonResult excelImportTeachers(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null)
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            // 验证文件格式
            String fileName = file.getOriginalFilename();
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx"))
                return CommonResult.fail(ErrorCodeEnum.FILE_FORMAT_ERROR);

            return CommonResult.createOK(teacherService.importExcel(file));
        } catch (Exception e) {
            logger.error("FileController#excelImportExperts error", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/excelImport/courses", method = RequestMethod.POST)
    public CommonResult excelImportCourses(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null)
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            // 验证文件格式
            String fileName = file.getOriginalFilename();
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx"))
                return CommonResult.fail(ErrorCodeEnum.FILE_FORMAT_ERROR);

            return CommonResult.createOK(courseService.importExcel(file));
        } catch (Exception e) {
            logger.error("FileController#excelImportCourses error", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/excelImport/schedules", method = RequestMethod.POST)
    public CommonResult excelImportSchedules(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null)
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            // 验证文件格式
            String fileName = file.getOriginalFilename();
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx") && !fileName.endsWith(".csv"))
                return CommonResult.fail(ErrorCodeEnum.FILE_FORMAT_ERROR);

            return CommonResult.createOK(scheduleService.importExcel(file));
        } catch (Exception e) {
            logger.error("FileController#excelImportCourses error", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/excelExport/schedules", method = RequestMethod.GET)
    public void excelExport(HttpServletResponse response, SchedulesPageReq req) {
        try {
            scheduleService.exportToExcel(response,req);
        } catch (Exception e) {
            logger.error("excelExport error", e);
        }
    }
}
