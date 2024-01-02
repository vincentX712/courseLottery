package com.officerschool.courselottery.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.officerschool.courselottery.common.Utils.TimeUtil;
import com.officerschool.courselottery.common.models.req.CoursesPageReq;
import com.officerschool.courselottery.common.models.res.CoursesRes;
import com.officerschool.courselottery.dao.mapper.CourseMapper;
import com.officerschool.courselottery.service.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    private final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Resource
    CourseMapper courseMapper;
    public PageInfo<CoursesRes> getCourses(CoursesPageReq req){
        int pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        int pageSize = req.getPageSize() == null ? 20 : req.getPageSize();

        PageHelper.startPage(pageNum, pageSize);
        String sql = "select * , t_teacher.name as t_name from t_course " +
                "left join t_teacher on t_course.teacher_id = t_teacher.id " +
                "where ";
        if (StringUtils.isNotBlank(req.getDate()))
            sql += "t_course.date='" + req.getDate() + "'";
        else
            sql += "t_course.date='" + TimeUtil.getCurrentDate() + "'";

        if (StringUtils.isNotBlank(req.getTeacherTitle()))
            sql += " and t_teacher.title='" + req.getTeacherTitle() + "'";

        if (StringUtils.isNotBlank(req.getLesson()))
            sql += " and t_course.lesson like '%" + req.getLesson() + "%'";

        if (StringUtils.isNotBlank(req.getMajor()))
            sql += " and t_course.major like '%" + req.getMajor() + " %'";

        if(StringUtils.isNotBlank(req.getCampusId())){
            sql += " and t_course.campus_id='" + req.getCampusId() + "'";
        }

        System.out.println(sql);

        List<Map<String, Object>> courseList = courseMapper.getCoursesList(sql);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(courseList);
        PageInfo<CoursesRes> resPage = PageUtil.convertPageInfo2PageInfoVo(pageInfo);
        List<CoursesRes> resList = new ArrayList<>();
        for (Map<String, Object> mapItem : courseList) {
            CoursesRes res = new CoursesRes();
            res.setId(Integer.valueOf(mapItem.get("id").toString()));
            res.setLesson(mapItem.get("lesson").toString());
            res.setWeek(mapItem.get("week").toString());
            res.setDate(mapItem.get("date").toString());
            res.setNodeId(Integer.valueOf(mapItem.get("node_id").toString()));
            res.setMajor(mapItem.get("major").toString());
            res.setTeacherName(mapItem.get("t_name").toString());
            res.setTitle(mapItem.get("title").toString());
            res.setEducation(mapItem.get("education").toString());
            res.setAge(mapItem.get("age").toString());
            res.setClassroom(mapItem.get("classroom").toString());
            res.setCampusName(mapItem.get("campus_name").toString());
            if(mapItem.get("notes")!=null){
                res.setNotes(mapItem.get("notes").toString());
            }
            resList.add(res);
        }
        resPage.setList(resList);
        return resPage;
    }
}
