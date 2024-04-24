package com.officerschool.courselottery.common.models.req;

import lombok.Data;

@Data
public class ExpertReq {
    private Integer id;
    private String name;
    private Integer status;
    private Integer priority;
    private String relateMajor;
}
