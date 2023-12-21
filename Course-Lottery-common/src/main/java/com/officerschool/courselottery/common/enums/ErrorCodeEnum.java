package com.officerschool.courselottery.common.enums;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
public enum ErrorCodeEnum {

    REQUEST_PARAM_ERROR(-1001, "入参异常，请检查后再试"),

    REQUEST_PARAM_NULL(-1001, "入参为空"),

    LOGIN_FAIL(-1001, "登录失败，请检查用户名和密码"),

    TOKEN_REQUEST_ERROR(-1003, "token生成错误"),

    TOKEN_AUTHORIZE_ERROR(-1004, "token验证失败"),

    TOKEN_EXPIRE_ERROR(-1005, "token过期"),

    CRON_EXPRESSION_INVALID(-1006, "cron表达式不合法"),

    TOO_MANY_UNFINISHED_TASK(-1007, "进行中的任务过多，请稍后再试"),

    DATABASE_CONNECT_FAILED(-1008, "数据库连接不上"),

    NAME_NOT_VALID(-1009, "文件名需以ktr或kjb结尾"),

    NAME_LENGTH_TOO_LONG(-1010, "文件名过长"),

    SERVER_ERROR(-1002, "网络开小差了，请稍后再试"),

    CANNOT_EDIT(-1011, "运行中的任务，不能编辑"),

    ILLEGAL_STATE_TRANSFER(-1012, "当前状态不支持该操作");

    private final int code;

    private final String msg;

    public static int REQUEST_ERROR_CODE = -1001;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
