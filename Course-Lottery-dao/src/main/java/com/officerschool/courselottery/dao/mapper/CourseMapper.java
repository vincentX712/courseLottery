package com.officerschool.courselottery.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.officerschool.courselottery.dao.dataobject.CourseDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
public interface CourseMapper extends BaseMapper<CourseDO> {

    @Select("${sql}")
    List<CourseDO> getCourseList(@Param("sql") String sql);
    @Select("${sql}")
    List<Map<String, Object>> getCoursesList(String sql);
}
