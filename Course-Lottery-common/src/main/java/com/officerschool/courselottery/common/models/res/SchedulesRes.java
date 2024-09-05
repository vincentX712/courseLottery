package com.officerschool.courselottery.common.models.res;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author : create by xiangwenchao@zhejianglab.com
 * @version : v1.0
 * @date : 2023/12/25
 */
@Data
public class SchedulesRes {

    private Integer id;

    private Integer courseId;

    private Integer expertId;

//    private Integer teacherId;

    @Excel(name = "专家")
    private String expertName;

    @Excel(name = "日期")
    private String date;

    @Excel(name = "星期")
    private String week;

    @Excel(name = "节次")
    private Integer nodeId;

    @Excel(name = "课程名称")
    private String lesson;

    @Excel(name = "课程属性")
    private String lessonAttribute;

    @Excel(name = "专业（人数）")
    private String major;

    private String campusName;

    @Excel(name = "教员姓名")
    private String teacherName;

    @Excel(name = "教员职称")
    private String title;

    @Excel(name = "教员学历")
    private String education;

    @Excel(name = "教员年龄")
    private String age;

    @Excel(name = "教室")
    private String classroom;

    @Excel(name = "备注")
    private String notes;

//    @Excel(name = "是否思政课程")
    private Boolean isPolitics;

//    @Excel(name = "评级")
    private String evaluation;

    @Excel(name = "抽课时间")
    private String ctime;
}
