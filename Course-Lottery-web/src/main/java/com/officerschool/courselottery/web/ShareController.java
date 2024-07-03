package com.officerschool.courselottery.web;

import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import com.officerschool.courselottery.service.ShareService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 6/24/24
 */
@RestController
@RequestMapping("/lottery/share")
public class ShareController {

    private final Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Resource
    private ShareService shareService;

    @RequestMapping(value = "/shareUrl", method = RequestMethod.GET)
    public CommonResult shareUrl(String id) {
        try {
            if (StringUtils.isBlank(id))
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            return CommonResult.createOK(shareService.shareUrl(id));
        } catch (Exception e) {
            logger.error("ShareController#shareUrl error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/originUrl", method = RequestMethod.GET)
    public CommonResult originUrl(String cipher) {
        try {
            if (StringUtils.isBlank(cipher))
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            return CommonResult.createOK(shareService.originUrl(cipher));
        } catch (Exception e) {
            logger.error("ShareController#originUrl error: ", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }
}
