package com.officerschool.courselottery.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.officerschool.courselottery.common.Utils.TimeUtil;
import com.officerschool.courselottery.common.models.req.ConfirmLotteryReq;
import com.officerschool.courselottery.common.models.req.LotteryReq;
import com.officerschool.courselottery.common.models.res.LotteryRes;
import com.officerschool.courselottery.dao.dataobject.CourseDO;
import com.officerschool.courselottery.dao.dataobject.ScheduleDO;
import com.officerschool.courselottery.dao.mapper.CourseMapper;
import com.officerschool.courselottery.dao.mapper.ScheduleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/25
 */
@Service
public class LotteryService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private CourseMapper courseMapper;

    public LotteryRes lottery(LotteryReq req) {
        if (StringUtils.isBlank(req.getExpertId().toString()))
            return null;
        // 1. 随机抽课
        LotteryRes res = new LotteryRes();

        // 该专家已抽过的课
        List<Integer> listCourseId = hasLotteriedCourseId(req.getExpertId());

        List<CourseDO> courseList;

        if (StringUtils.isNotBlank(req.getTitle())) {  // 需要连表查询
            String sql = "select * from t_course left join t_teacher on t_course.teacher_id=t_teacher.id where title='" + req.getTitle() + "'";
            if (StringUtils.isNotBlank(req.getCampusId().toString()))
                sql += " and campus_id=" + req.getCampusId();
            if (StringUtils.isNotBlank(req.getNodeId().toString()))
                sql += " and node_id="+ req.getNodeId();
            if (!listCourseId.isEmpty())
                sql += " and course_id not in (" + listCourseId.stream().map(Object::toString).collect(Collectors.joining(","));

            sql += " and date='" + TimeUtil.getCurrentDate() + "'";

            courseList = courseMapper.getCourseList(sql);
        } else {
            QueryWrapper<CourseDO> queryWrapper = new QueryWrapper<>();
            if (StringUtils.isNotBlank(req.getCampusId().toString()))
                queryWrapper.eq("campus_id", req.getCampusId());

            if (StringUtils.isNotBlank(req.getNodeId().toString()))
                queryWrapper.eq("node_id", req.getNodeId());

            if (!listCourseId.isEmpty())
                queryWrapper.notIn("course_id", listCourseId);

            queryWrapper.eq("date", TimeUtil.getCurrentDate());

            courseList = courseMapper.selectList(queryWrapper);
        }

        if (courseList.isEmpty()) {
            res.setCode(2);
            res.setMessage("没有可选课程！");
            return res;
        }
        // 随机选取一条记录
        int index = new Random().nextInt(courseList.size());
        CourseDO course = courseList.get(index);

        // 2. 判断抽中的课程对应的教员是否已被其他专家抽过课
        if (StringUtils.isBlank(String.valueOf(course.getTeacherId()))) {
            res.setCode(1);
            res.setMessage("该课程的教员信息为空！");
            return res;
        }
        if (isCourseTeacherHasLotteried(course.getTeacherId())) {
            res.setIsNeedConfirm(1);
            res.setExpertId(req.getExpertId());
            res.setCourseId(course.getId());
            res.setCode(0);
            return res;
        }

        // 3. 添加抽课记录，抽课成功
        ScheduleDO schedule = new ScheduleDO();
        schedule.setExpertId(req.getExpertId());
        schedule.setCourseId(course.getId());
        schedule.setTeacherId(course.getTeacherId());
        scheduleMapper.insert(schedule);
        res.setIsNeedConfirm(0);
        res.setCode(0);
        return res;
    }

    // 该专家是否已抽过课
    private boolean hasLotteried(int expertId) {
        QueryWrapper<ScheduleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("expert_id", expertId);
        return scheduleMapper.selectCount(queryWrapper) > 0;
    }

    // 该专家已抽过的课id
    private List<Integer> hasLotteriedCourseId(int expertId) {
        QueryWrapper<ScheduleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("expert_id", expertId);

        return scheduleMapper.selectList(queryWrapper).stream().map(ScheduleDO::getCourseId).collect(Collectors.toList());
    }


    // 该教员是否已被抽过课
    private boolean isCourseTeacherHasLotteried(int teacherId) {
        if (StringUtils.isBlank(String.valueOf(teacherId)))
            return false;
        QueryWrapper<ScheduleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        return scheduleMapper.selectCount(queryWrapper) > 0;
    }

    public boolean confirmLottery(ConfirmLotteryReq req) {
        QueryWrapper<CourseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", req.getCourseId());
        CourseDO course = courseMapper.selectOne(queryWrapper);

        ScheduleDO schedule = new ScheduleDO();
        schedule.setExpertId(req.getExpertId());
        schedule.setCourseId(req.getCourseId());
        schedule.setTeacherId(course.getTeacherId());
        return scheduleMapper.insert(schedule) > 0;
    }

}
