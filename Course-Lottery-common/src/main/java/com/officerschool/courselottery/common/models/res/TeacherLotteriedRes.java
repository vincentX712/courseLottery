package com.officerschool.courselottery.common.models.res;

import lombok.Data;

import java.util.List;

@Data
public class TeacherLotteriedRes {

    private Boolean isLotteried;

    private Integer lotteriedCount;

    private List<SchedulesRes> schedulesRes;

}
