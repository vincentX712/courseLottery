package com.officerschool.courselottery.common.models.res;

import lombok.Data;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/21
 */
@Data
public class LotteryRes {

    private Integer isNeedConfirm; // 0：不需要确认，直接抽课成功 1：该教员已被其他专家抽过课，需要确认

    private Integer expertId; // 专家id（isNeedConfirm=1时）

    private Integer courseId; // 课程id（isNeedConfirm=1时）

    private Integer code; // 0，抽课成功；else：失败

    private String message; // 提示信息，可用于前端展示
}
