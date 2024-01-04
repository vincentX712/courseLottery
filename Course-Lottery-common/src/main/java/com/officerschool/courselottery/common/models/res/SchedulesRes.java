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

    @Excel(name = "课程ID")
    private Integer courseId;

    @Excel(name = "专家ID")
    private Integer expertId;

    @Excel(name = "教员ID")
    private Integer teacherId;

    @Excel(name = "评级")
    private String evaluation;

    @Excel(name = "日期")
    private String date;

    @Excel(name = "星期")
    private String week;

    @Excel(name = "课程名称")
    private String lesson;

    @Excel(name = "专业")
    private String major;

    @Excel(name = "校区")
    private String campusName;

    @Excel(name = "教室")
    private String classroom;

    @Excel(name = "教员姓名")
    private String teacherName;

    @Excel(name = "职称")
    private String title;

    @Excel(name = "学历")
    private String education;

    @Excel(name = "年龄")
    private String age;

    @Excel(name = "专家姓名")
    private String expertName;

    @Excel(name = "node_id")
    private Integer nodeId;

    @Excel(name = "备注")
    private String notes;
}
