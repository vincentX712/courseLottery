package com.officerschool.courselottery.common.models.req;

import lombok.Data;
import org.springframework.asm.SpringAsmInfo;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Data
public class ExpertsPageReq {

    private Integer pageNum;

    private Integer pageSize;
}
