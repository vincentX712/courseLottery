package com.officerschool.courselottery.common.models;

import com.officerschool.courselottery.common.enums.ErrorCodeEnum;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
public class CommonResult {

    private int code;
    private String msg;
    private Object data;

    public static CommonResult createOK(Object data) {
        return new CommonResult(0, "ok", data);
    }

    public static CommonResult createOKWithMsg(String msg, Object data) {
        return new CommonResult(0, msg, data);
    }

    public static CommonResult createOK() {
        return new CommonResult(0, "ok", null);
    }

    public static CommonResult fail(int code, String msg) {
        return new CommonResult(code, msg, null);
    }

    public static CommonResult fail(ErrorCodeEnum errorCodeEnum) {
        return new CommonResult(errorCodeEnum.getCode(), errorCodeEnum.getMsg(), null);
    }

    public static CommonResult fail() {
        return new CommonResult(-1002, "网络开小差了，请稍后再试", null);
    }

    public static CommonResult fail(int code) {
        return new CommonResult(code, "网络开小差了，请稍后再试", null);
    }

    public CommonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
