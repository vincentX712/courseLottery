package com.officerschool.courselottery.common.models.req;

import lombok.Data;

@Data
public class ClassReq {

    private Integer id;

    private String name;

    private Integer status;

    private String schoolYear;

    private String schoolTerm;

}
