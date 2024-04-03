package com.officerschool.courselottery.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2024/4/3
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody JSONObject req) {
        try {
            String userName = req.getString("userName");
            String password = req.getString("password");
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
                return CommonResult.fail(ErrorCodeEnum.LOGIN_FAIL);
            }
            return CommonResult.createOK();
        } catch (Exception e) {
            logger.error("UserController#login error", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public CommonResult logout(HttpServletRequest request) {
        return CommonResult.createOK();
    }
}
