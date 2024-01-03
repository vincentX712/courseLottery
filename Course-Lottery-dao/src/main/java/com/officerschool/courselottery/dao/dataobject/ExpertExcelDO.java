package com.officerschool.courselottery.dao.dataobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author : create by xiangwenchao@zhejianglab.com
 * @version : v1.0
 * @date : 2024/1/2
 */
@Data
public class ExpertExcelDO {

    @Excel(name = "name")
    private String name;
}
