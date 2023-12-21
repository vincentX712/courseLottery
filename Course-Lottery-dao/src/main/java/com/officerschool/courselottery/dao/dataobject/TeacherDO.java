package com.officerschool.courselottery.dao.dataobject;

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

    private Integer id;

    private String name;

    private String title; //职称

    private String education; // 学历

    private Integer age;
}
