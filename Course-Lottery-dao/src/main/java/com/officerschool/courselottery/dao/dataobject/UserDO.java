package com.officerschool.courselottery.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2024/4/3
 */

@Data
@TableName("t_user")
public class UserDO {

    private String id;

    private String name;

    private String phone;

    private String password;

    private Integer state;

    private String token;

    private Timestamp createTime;

    private Timestamp updateTime;
}
