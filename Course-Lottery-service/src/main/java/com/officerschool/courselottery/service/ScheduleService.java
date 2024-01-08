package com.officerschool.courselottery.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.officerschool.courselottery.common.Utils.TimeUtil;
import com.officerschool.courselottery.common.models.req.SchedulesPageReq;
import com.officerschool.courselottery.common.models.res.SchedulesRes;
import com.officerschool.courselottery.dao.dataobject.ScheduleDO;
import com.officerschool.courselottery.dao.mapper.ScheduleMapper;
import com.officerschool.courselottery.service.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/25
 */

@Service
public class ScheduleService {

    private final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Resource
    private ScheduleMapper scheduleMapper;

    public PageInfo<SchedulesRes> getSchedules(SchedulesPageReq req) {
        int pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        int pageSize = req.getPageSize() == null ? 20 : req.getPageSize();

        PageHelper.startPage(pageNum, pageSize);

        String sql = "select * , t_expert.name as e_name from t_schedule " +
                "left join t_course on t_schedule.course_id = t_course.id " +
                "left join t_expert on t_schedule.expert_id = t_expert.id " +
                "where ";
        if (StringUtils.isNotBlank(req.getDate()))
            sql += "t_course.date='" + req.getDate() + "'";
        else
            sql += "t_course.date='" + TimeUtil.getCurrentDate() + "' ";

        if (StringUtils.isNotBlank(req.getTeacherTitle()))
            sql += " and t_course.teacher_title='" + req.getTeacherTitle() + "' ";

        if (StringUtils.isNotBlank(req.getLesson()))
            sql += " and t_course.lesson like '%" + req.getLesson() + "%' ";

        if (StringUtils.isNotBlank(req.getMajor()))
            sql += " and t_course.major like '%" + req.getMajor() + " %' ";
        sql += "order by t_schedule.course_id";
        List<Map<String, Object>> list = scheduleMapper.getScheduleList(sql);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageInfo<SchedulesRes> resPage = PageUtil.convertPageInfo2PageInfoVo(pageInfo);

        List<SchedulesRes> resList = new ArrayList<>();
        for (Map<String, Object> mapItem : list) {
            SchedulesRes res = new SchedulesRes();
            res.setId(Integer.valueOf(mapItem.get("id").toString()));
            res.setCourseId(Integer.valueOf(mapItem.get("course_id").toString()));
            res.setExpertId(Integer.valueOf(mapItem.get("expert_id").toString()));
//            res.setTeacherId(Integer.valueOf(mapItem.get("teacher_id").toString()));
            res.setEvaluation(mapItem.get("evaluation") == null ? "" : mapItem.get("evaluation").toString());
            res.setDate(mapItem.get("date") == null ? "" : mapItem.get("date").toString());
            res.setWeek(mapItem.get("week") == null ? "" : mapItem.get("week").toString());
            res.setLesson(mapItem.get("lesson").toString());
            res.setMajor(mapItem.get("major").toString());
            res.setCampusName(mapItem.get("campus_name").toString());
            res.setClassroom(mapItem.get("classroom").toString());
            res.setTeacherName(mapItem.get("teacher_name").toString());
            res.setTitle(mapItem.get("teacher_title").toString());
            res.setEducation(mapItem.get("teacher_education").toString());
            res.setAge(mapItem.get("teacher_age").toString());
            res.setExpertName(mapItem.get("e_name").toString());
            res.setNodeId(Integer.valueOf(mapItem.get("node_id").toString()));
            if(mapItem.get("notes")!=null){
                res.setNotes(mapItem.get("notes").toString());
            }
            resList.add(res);
        }
        resPage.setList(resList);

        return resPage;
    }

    public void exportToExcel(HttpServletResponse response,SchedulesPageReq req){
        try {
            List<SchedulesRes> scheduleResList = getSchedulesExecel(req);
            //设置信息头，告诉浏览器内容为excel类型
            response.setHeader("content-Type", "application/vnd.ms-excel");
            //文件名称
            String fileName = "听课表.xlsx";
            //sheet名称
            String sheetName = "听课表";
            fileName = new String(fileName.getBytes(), "ISO-8859-1");

            //设置下载名称
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            //字节流输出
            ServletOutputStream out = response.getOutputStream();
            //设置excel参数
            ExportParams params = new ExportParams();
            //设置sheet名
            params.setSheetName(sheetName);
            //设置标题
//        params.setTitle("听课表");

            //转成对应的类型
//        List<ScheduleDO> exportUsers = changeType(users);
            //导入excel
            Workbook workbook = ExcelExportUtil.exportExcel(params, SchedulesRes.class, scheduleResList);
            //写入
            workbook.write(out);
        } catch (Exception e) {
            logger.error("ScheduleService#exportToExcel error: ", e);
        }
    }

    private List<SchedulesRes> getSchedulesExecel(SchedulesPageReq req) {
        String sql = "select * ,  t_expert.name as e_name from t_schedule " +
                "left join t_course on t_schedule.course_id = t_course.id " +
                "left join t_expert on t_schedule.expert_id = t_expert.id " + "where ";
        if (StringUtils.isNotBlank(req.getDate()))
            sql += "t_course.date='" + req.getDate() + "' ";
        else
            sql += "t_course.date='" + TimeUtil.getCurrentDate() + "' ";

        if (StringUtils.isNotBlank(req.getTeacherTitle()))
            sql += " and t_course.teacher_title like '%'" + req.getTeacherTitle() + "%' ";

        if (StringUtils.isNotBlank(req.getLesson()))
            sql += " and t_course.lesson like '%" + req.getLesson() + "%' ";

        if (StringUtils.isNotBlank(req.getMajor()))
            sql += " and t_course.major like '%" + req.getMajor() + " %' ";
        sql += "order by t_schedule.course_id";

        List<Map<String, Object>> list = scheduleMapper.getScheduleList(sql);

        List<SchedulesRes> resList = new ArrayList<>();
        for (Map<String, Object> mapItem : list) {
            SchedulesRes res = new SchedulesRes();
            res.setId(Integer.valueOf(mapItem.get("id").toString()));
            res.setCourseId(Integer.valueOf(mapItem.get("course_id").toString()));
            res.setExpertId(Integer.valueOf(mapItem.get("expert_id").toString()));
//            res.setTeacherId(Integer.valueOf(mapItem.get("teacher_id").toString()));
            res.setEvaluation(mapItem.get("evaluation") == null ? "" : mapItem.get("evaluation").toString());
            res.setDate(mapItem.get("date") == null ? "" : mapItem.get("date").toString());
            res.setWeek(mapItem.get("week") == null ? "" : mapItem.get("week").toString());
            res.setLesson(mapItem.get("lesson").toString());
            res.setMajor(mapItem.get("major").toString());
            res.setCampusName(mapItem.get("campus_name").toString());
            res.setClassroom(mapItem.get("classroom").toString());
            res.setTeacherName(mapItem.get("teacher_name").toString());
            res.setTitle(mapItem.get("teacher_title").toString());
            res.setEducation(mapItem.get("teacher_education").toString());
            res.setAge(mapItem.get("teacher_age").toString());
            res.setExpertName(mapItem.get("e_name").toString());
            res.setNodeId(Integer.valueOf(mapItem.get("node_id").toString()));
            if(mapItem.get("notes")!=null){
                res.setNotes(mapItem.get("notes").toString());
            }
            resList.add(res);
        }

        return resList;
    }

}
