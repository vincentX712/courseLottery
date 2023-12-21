package com.officerschool.courselottery.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Data
@TableName("t_schedule")
public class ScheduleDO {

    private Integer id;

    private Integer courseId;

    private Integer expertId;

    private String evaluation; // 评价等级
}
