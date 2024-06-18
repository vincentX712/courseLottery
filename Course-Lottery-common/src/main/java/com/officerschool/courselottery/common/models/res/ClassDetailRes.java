package com.officerschool.courselottery.common.models.res;

import lombok.Data;

import java.util.List;

@Data
public class ClassDetailRes {
    private ClassRes classRes;

    private List<TeacherRes> list;
}
