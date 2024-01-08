package com.officerschool.courselottery.dao.dataobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Data
@TableName("t_schedule")
public class ScheduleDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(name = "course_id")
    private Integer courseId;

    @Excel(name = "expert_id")
    private Integer expertId;

//    @Excel(name = "teacher_id")
//    private Integer teacherId;

    @Excel(name = "evaluation")
    private String evaluation; // 评价等级

}
