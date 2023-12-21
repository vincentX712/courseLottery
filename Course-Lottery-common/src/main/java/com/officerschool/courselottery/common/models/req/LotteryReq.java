package com.officerschool.courselottery.common.models.req;

import lombok.Data;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/21
 */
@Data
public class LotteryReq {

    private String title; // 教师职称

    private Integer nodeId; // 节次

    private Integer campusId; // 校区

    private Integer expertId; // 抽课专家
}
