package com.officerschool.courselottery.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.officerschool.courselottery.dao.dataobject.ScheduleDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
public interface ScheduleMapper extends BaseMapper<ScheduleDO> {

    @Select("${sql}")
    List<Map<String, Object>> getScheduleList(String sql);

    @Select("select * from t_schedule")
    List<ScheduleDO> selectAll();
}
