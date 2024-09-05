package com.officerschool.courselottery.common.models.req;

import lombok.Data;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/26
 */
@Data
public class CoursesPageReq {

    private Integer pageNum;

    private Integer pageSize;

    private String teacherTitle;

    private String teacherName;

    private String date; // 默认当天

    private String lesson; // 课程名，支持模糊查询

    private String major;  // 专业名，支持模糊查询

    private String campusId;

    private String nodeId;

    private Boolean isPolitics;

    private Boolean isAcademicClass;  // 是否学历班

    private Integer expertId;
}
