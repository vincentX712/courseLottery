package com.officerschool.courselottery.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.officerschool.courselottery.common.Utils.TimeUtil;
import com.officerschool.courselottery.common.models.req.SchedulesPageReq;
import com.officerschool.courselottery.common.models.res.SchedulesRes;
import com.officerschool.courselottery.dao.mapper.ScheduleMapper;
import com.officerschool.courselottery.service.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/25
 */

@Service
public class ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    public PageInfo<SchedulesRes> getSchedules(SchedulesPageReq req) {
        int pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        int pageSize = req.getPageSize() == null ? 20 : req.getPageSize();

        PageHelper.startPage(pageNum, pageSize);

        String sql = "select * , t_teacher.name as t_name, t_expert.name as e_name from t_schedule " +
                "left join t_course on t_schedule.course_id = t_course.id " +
                "left join t_teacher on t_schedule.teacher_id = t_teacher.id " +
                "left join t_expert on t_schedule.expert_id = t_expert.id " +
                "where ";
        if (StringUtils.isNotBlank(req.getDate()))
            sql += "t_course.date='" + req.getDate() + "'";
        else
            sql += "t_course.date='" + TimeUtil.getCurrentDate() + "'";

        if (StringUtils.isNotBlank(req.getTeacherTitle()))
            sql += " and t_course.teacher_title='" + req.getTeacherTitle() + "'";

        if (StringUtils.isNotBlank(req.getLesson()))
            sql += " and t_course.lesson like '%" + req.getLesson() + "%'";

        if (StringUtils.isNotBlank(req.getMajor()))
            sql += " and t_course.major like '%" + req.getMajor() + " %'";

        List<Map<String, Object>> list = scheduleMapper.getScheduleList(sql);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageInfo<SchedulesRes> resPage = PageUtil.convertPageInfo2PageInfoVo(pageInfo);

        List<SchedulesRes> resList = new ArrayList<>();
        for (Map<String, Object> mapItem : list) {
            SchedulesRes res = new SchedulesRes();
            res.setId(Integer.valueOf(mapItem.get("id").toString()));
            res.setCourseId(Integer.valueOf(mapItem.get("course_id").toString()));
            res.setExpertId(Integer.valueOf(mapItem.get("expert_id").toString()));
            res.setTeacherId(Integer.valueOf(mapItem.get("teacher_id").toString()));
            res.setEvaluation(mapItem.get("evaluation") == null ? "" : mapItem.get("evaluation").toString());
            res.setDate(mapItem.get("date") == null ? "" : mapItem.get("date").toString());
            res.setWeek(mapItem.get("week") == null ? "" : mapItem.get("week").toString());
            res.setLesson(mapItem.get("lesson").toString());
            res.setMajor(mapItem.get("major").toString());
            res.setCampusName(mapItem.get("campus_name").toString());
            res.setClassroom(mapItem.get("classroom").toString());
            res.setTeacherName(mapItem.get("t_name").toString());
            res.setTitle(mapItem.get("title").toString());
            res.setEducation(mapItem.get("education").toString());
            res.setAge(mapItem.get("age").toString());
            res.setExpertName(mapItem.get("e_name").toString());
            resList.add(res);
        }
        resPage.setList(resList);

        return resPage;
    }

}
