package com.officerschool.courselottery.dao.dataobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
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

    @Excel(name = "id")
    private Integer id;

    @Excel(name = "name")
    private String name;

    @Excel(name = "title")
    private String title; //职称

    @Excel(name = "education")
    private String education; // 学历

    @Excel(name = "age")
    private Integer age;
}
