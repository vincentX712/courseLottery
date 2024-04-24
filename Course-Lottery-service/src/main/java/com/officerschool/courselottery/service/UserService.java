package com.officerschool.courselottery.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.officerschool.courselottery.common.models.dto.UserInfo;
import com.officerschool.courselottery.dao.dataobject.UserDO;
import com.officerschool.courselottery.dao.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2024/4/3
 */

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public UserInfo getUserInfo(String phone, String password) {
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
            return null;
        }

        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        queryWrapper.eq("password", password);
        queryWrapper.eq("state", 0);
        return convertToUserInfo(userMapper.selectOne(queryWrapper));
    }

    public boolean updateUserToken(String userId, String token) {
        if (userId == null || userId.isEmpty())
            return false;
        UserDO userDO = new UserDO();
        userDO.setToken(token);
        userDO.setId(userId);
        userDO.setMtime(new Timestamp(System.currentTimeMillis()));
        return userMapper.updateById(userDO) > 0;
    }

    public boolean invalidateToken(String id) {
        UserDO userDO = new UserDO();
        userDO.setToken("expired");
        userDO.setId(id);
        userDO.setMtime(new Timestamp(System.currentTimeMillis()));
        return userMapper.updateById(userDO) > 0;
    }

    private UserInfo convertToUserInfo(UserDO userDO) {
        if (userDO == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userDO.getId());
        userInfo.setName(userDO.getName());
        userInfo.setPhone(userDO.getPhone());
        return userInfo;
    }
}
