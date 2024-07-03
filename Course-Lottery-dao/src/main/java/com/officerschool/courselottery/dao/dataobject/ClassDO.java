package com.officerschool.courselottery.dao.dataobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("t_class")
public class ClassDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(name = "班次名称")
    private String name;

    @Excel(name = "状态")
    private Integer status;

    private Integer teacherNum;

    @Excel(name = "学年")
    private String schoolYear;

    @Excel(name = "学期")
    private String schoolTerm;

    private String otherSuggestion;
}
