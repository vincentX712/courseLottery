package com.officerschool.courselottery.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Data
@TableName("t_expert")
public class ExpertDO {

    private Integer id;

    private String name;
}
