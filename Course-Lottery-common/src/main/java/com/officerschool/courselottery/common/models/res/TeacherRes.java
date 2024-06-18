package com.officerschool.courselottery.common.models.res;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class TeacherRes {

    private Integer id;

    @Excel(name = "教员姓名")
    private String name;

    private String classId;

    @Excel(name = "总分")
    private Integer totalScore; //总分

    @Excel(name = "总人数")
    private Integer totalNum;//总人数

}
