package com.officerschool.courselottery.common.models.req;

import lombok.Data;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Data
public class PageReq {

    private Integer pageNum;

    private Integer pageSize;

    private Integer status;

    private Integer classId;
}
