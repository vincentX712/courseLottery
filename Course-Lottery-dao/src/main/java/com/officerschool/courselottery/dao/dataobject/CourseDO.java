package com.officerschool.courselottery.dao.dataobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Data
@TableName("t_course")
public class CourseDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(name = "日期", importFormat = "yyyy-MM-dd")
    private Date date;

    @Excel(name = "星期")
    private String week;

    @Excel(name = "节次（填入单节id）")
    private Integer nodeId;

//    @Excel(name = "节次名")
//    private String nodeName;

    @Excel(name = "课程名称")
    private String lesson;

    @Excel(name = "专业（人数）")
    private String major;

//    @Excel(name = "教员id")
//    private Integer teacherId;

    @Excel(name="教员姓名")
    private String teacherName;

    @Excel(name="教员职称")
    private String teacherTitle;

    @Excel(name="教员学历")
    private String teacherEducation;

    @Excel(name="教员年龄")
    private String teacherAge;

    @Excel(name = "校区id（1：富春校区，2：西溪校区）")
    private Integer campusId;

    @Excel(name = "校区名称")
    private String campusName;

    @Excel(name = "教室")
    private String classroom;

    @Excel(name = "备注")
    private String notes;
}
