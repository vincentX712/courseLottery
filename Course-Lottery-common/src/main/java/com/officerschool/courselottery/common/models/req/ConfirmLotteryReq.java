package com.officerschool.courselottery.common.models.req;

import lombok.Data;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/21
 */
@Data
public class ConfirmLotteryReq {

    private Integer expertId;

    private Integer courseId;
}
