package com.officerschool.courselottery.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.officerschool.courselottery.common.Utils.JwtUtil;
import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import com.officerschool.courselottery.common.models.dto.UserInfo;
import com.officerschool.courselottery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2024/4/3
 */
@RestController
@RequestMapping("/lottery/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody JSONObject req) {
        try {
            String userName = req.getString("userName");
            String password = req.getString("password");
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
                return CommonResult.fail(ErrorCodeEnum.LOGIN_FAIL);
            }
            UserInfo userInfo = userService.getUserInfo(userName, password);
            if (userInfo == null) {
                return CommonResult.fail(ErrorCodeEnum.LOGIN_FAIL);
            }

            String token = JwtUtil.createToken(userInfo);
            if (userService.updateUserToken(userInfo.getId(), token)) {
                JSONObject result = new JSONObject();
                result.put("token", token);
                result.put("phone", userInfo.getPhone());
                return CommonResult.createOK(result);
            }
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        } catch (Exception e) {
            logger.error("UserController#login error", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public CommonResult logout(HttpServletRequest request) {
        try {
            String id = request.getAttribute("id").toString();
            if (StringUtils.isBlank(id))
                return CommonResult.fail(ErrorCodeEnum.TOKEN_AUTHORIZE_ERROR);
            if (userService.invalidateToken(id))
                return CommonResult.createOK();

            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        } catch (Exception e) {
            logger.error("UserController#logout error", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }
}
