package com.officerschool.courselottery.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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

    private Time date;

    private String week;

    private Integer nodeId;

    private String nodeName;

    private String lesson;

    private String major;

    private Integer teacherId;

    private Integer campusId;

    private String campusName;

    private String classroom;

    private String notes;
}
