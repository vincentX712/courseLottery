package com.officerschool.courselottery.dao.dataobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Data
@TableName("t_expert")
public class ExpertDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(name = "专家姓名")
    private String name;

    private String relateMajor;

    private Integer priority;

    private Integer status;
}
