package com.officerschool.courselottery.common.models.res;

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

    private Integer teacherId;

    private String evaluation;

    private String date;

    private String week;

    private String lesson;

    private String major;

    private String campusName;

    private String classroom;

    private String teacherName;

    private String title;

    private String education;

    private String age;

    private String expertName;

    private Integer nodeId;

    private String notes;
}
