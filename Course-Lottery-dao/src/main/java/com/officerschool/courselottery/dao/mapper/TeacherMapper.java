package com.officerschool.courselottery.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.officerschool.courselottery.dao.dataobject.CourseDO;
import com.officerschool.courselottery.dao.dataobject.TeacherDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
public interface TeacherMapper extends BaseMapper<TeacherDO> {
    @Select("${sql}")
    List<TeacherDO> getTeacherList(@Param("sql") String sql);
}
