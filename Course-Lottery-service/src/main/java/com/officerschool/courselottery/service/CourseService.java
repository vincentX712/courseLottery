package com.officerschool.courselottery.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.officerschool.courselottery.common.Utils.TimeUtil;
import com.officerschool.courselottery.common.models.req.CoursesPageReq;
import com.officerschool.courselottery.common.models.res.CoursesRes;
import com.officerschool.courselottery.dao.dataobject.CourseDO;
import com.officerschool.courselottery.dao.mapper.CourseMapper;
import com.officerschool.courselottery.dao.mapper.ScheduleMapper;
import com.officerschool.courselottery.service.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseService extends ServiceImpl<CourseMapper, CourseDO> {

    private final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    public PageInfo<CoursesRes> getCourses(CoursesPageReq req){
        int pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        int pageSize = req.getPageSize() == null ? 20 : req.getPageSize();

        PageHelper.startPage(pageNum, pageSize);
        String sql = "select * from t_course " +
                "where ";
        if (StringUtils.isNotBlank(req.getDate()))
            sql += "date='" + req.getDate() + "' ";
        else
            sql += "date='" + TimeUtil.getCurrentDate() + "' ";

        if (StringUtils.isNotBlank(req.getTeacherTitle()))
            sql += " and teacher_title like '%" + req.getTeacherTitle() + "%' ";

        if (StringUtils.isNotBlank(req.getLesson()))
            sql += " and lesson like '%" + req.getLesson() + "%' ";

        if (StringUtils.isNotBlank(req.getMajor()))
            sql += " and major like '%" + req.getMajor() + "%' ";

        if(StringUtils.isNotBlank(req.getCampusId())){
            sql += " and campus_id='" + req.getCampusId() + "' ";
        }

        if(StringUtils.isNotBlank(req.getNodeId())){
            sql += " and node_id='" + req.getNodeId() + "' ";
        }

        sql += " order by node_id";
        List<Map<String, Object>> courseList = courseMapper.getCoursesList(sql);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(courseList);
        PageInfo<CoursesRes> resPage = PageUtil.convertPageInfo2PageInfoVo(pageInfo);

        // 根据课程id列表，获取听课专家信息
        List<Integer> courseIdList = courseList.stream().map(mapItem -> Integer.valueOf(mapItem.get("id").toString())).collect(Collectors.toList());
        Map<String, String> courseIdExpertNameMap = new HashMap<>();
        if (!courseIdList.isEmpty()) {
            String expertSql = "select * from t_schedule, t_expert where t_schedule.expert_id = t_expert.id and t_schedule.course_id in("
                    + StringUtils.join(courseIdList, ",") + ") ";
            List<Map<String, Object>> expertList = scheduleMapper.getScheduleList(expertSql);
//            System.out.println(expertSql);
            for (Map<String, Object> expertMap : expertList) {
                String val = courseIdExpertNameMap.get(expertMap.get("course_id").toString());
                if(val == null){
                    courseIdExpertNameMap.put(expertMap.get("course_id").toString(), expertMap.get("name").toString());
                }else{
                    courseIdExpertNameMap.put(expertMap.get("course_id").toString(), val + "，" + expertMap.get("name").toString());
                }

            }
        }

        List<CoursesRes> resList = new ArrayList<>();
        for (Map<String, Object> mapItem : courseList) {
            CoursesRes res = new CoursesRes();
            res.setId(Integer.valueOf(mapItem.get("id").toString()));
            res.setLesson(mapItem.get("lesson").toString());
            res.setWeek(mapItem.get("week").toString());
            res.setDate(mapItem.get("date").toString());
            res.setNodeId(Integer.valueOf(mapItem.get("node_id").toString()));
            res.setMajor(mapItem.get("major").toString());
            res.setTeacherName(mapItem.get("teacher_name").toString());
            res.setTitle(mapItem.get("teacher_title").toString());
            res.setEducation(mapItem.get("teacher_education").toString());
            res.setAge(mapItem.get("teacher_age").toString());
            res.setClassroom(mapItem.get("classroom").toString());
            res.setCampusName(mapItem.get("campus_name").toString());
            if(mapItem.get("notes")!=null){
                res.setNotes(mapItem.get("notes").toString());
            }
            res.setExpertName(courseIdExpertNameMap.get(mapItem.get("id").toString()));
            resList.add(res);
        }
        resPage.setList(resList);
        return resPage;
    }

    public boolean importExcel(MultipartFile file) {
        try {
            List<CourseDO> result = ExcelImportUtil.importExcel(file.getInputStream(), CourseDO.class, new ImportParams());
            return saveBatch(result);
        } catch (Exception e) {
            logger.error("CourseService#importExcel error: ", e);
            return false;
        }
    }
}
