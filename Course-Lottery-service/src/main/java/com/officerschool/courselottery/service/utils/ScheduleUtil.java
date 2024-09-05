package com.officerschool.courselottery.service.utils;

import com.officerschool.courselottery.common.Utils.TimeUtil;
import com.officerschool.courselottery.common.models.req.SchedulesPageReq;
import org.apache.commons.lang3.StringUtils;

public class ScheduleUtil {
    public static String politicsLesson = "('军人心理教育','习近平强军思想','习近平新时代中国特色社会主义思想','人民军队历史与优良传统','军队基层政治工作'," +
            "'习近平新时代中国特色社会主义思想概论','军人心理教育训练','中国共产党历史','马克思主义基本原理','军人思想道德与法治')";
    public String getSchedulesSql(SchedulesPageReq req){
        String sql = "select * , t_expert.name as e_name from t_schedule " +
                "left join t_course on t_schedule.course_id = t_course.id " +
                "left join t_expert on t_schedule.expert_id = t_expert.id " +
                "where 1 ";
        if (StringUtils.isNotBlank(req.getDate()))
            sql += " and t_course.date='" + req.getDate() + "'";
        else
            sql += " and t_course.date>='" + TimeUtil.getBefore7Day() + "' ";

        if (StringUtils.isNotBlank(req.getTeacherTitle()))
            sql += " and t_course.teacher_title='" + req.getTeacherTitle() + "' ";

        if (StringUtils.isNotBlank(req.getLesson()))
            sql += " and t_course.lesson like '%" + req.getLesson() + "%' ";

        if (StringUtils.isNotBlank(req.getTeacherName()))
            sql += " and t_course.teacher_name like '%" + req.getTeacherName() + "%' ";

        if (req.getExpertId()!=null){
            sql += " and t_schedule.expert_id='" + req.getExpertId() + "' ";
        }

        if (StringUtils.isNotBlank(req.getMajor()))
            sql += " and t_course.major like '%" + req.getMajor() + " %' ";
        sql += " order by t_schedule.ctime desc";
        return sql;
    }
}
