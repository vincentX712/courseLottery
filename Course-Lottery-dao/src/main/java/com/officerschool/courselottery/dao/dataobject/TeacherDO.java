package com.officerschool.courselottery.dao.dataobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Data
@TableName("t_teacher")
public class TeacherDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(name = "教员姓名")
    private String name;

    @Excel(name = "班次id")
    private String classId;

    private Integer totalScore; //总分

    private Integer totalNum;//总人数

}
