package com.officerschool.courselottery.common.models.res;

import lombok.Data;

@Data
public class ClassRes {

    private Integer id;

    private String name;

    private Integer status;

    private Integer teacherNum;

    private Integer excellentNum;

    private Integer goodNum;

    private Integer passNum;

    private String schoolYear;

    private String schoolTerm;

    private String cipher;

    private String otherSuggestions;
}
