package com.officerschool.courselottery.dao.dataobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
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

    private Integer id;

    @Excel(name = "date", importFormat = "yyyy-MM-dd")
    private Date date;

    @Excel(name = "week")
    private String week;

    @Excel(name = "node_id")
    private Integer nodeId;

    @Excel(name = "node_name")
    private String nodeName;

    @Excel(name = "lesson")
    private String lesson;

    @Excel(name = "major")
    private String major;

    @Excel(name = "teacher_id")
    private Integer teacherId;

    @Excel(name = "campus_id")
    private Integer campusId;

    @Excel(name = "campus_name")
    private String campusName;

    @Excel(name = "classroom")
    private String classroom;

    @Excel(name = "notes")
    private String notes;
}
