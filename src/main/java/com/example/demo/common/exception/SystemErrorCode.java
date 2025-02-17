package com.example.demo.common.exception;


import com.example.demo.common.exception.errorcode.annotations.ECGetCode;
import com.example.demo.common.exception.errorcode.annotations.ECGetHTTPStatus;
import com.example.demo.common.exception.errorcode.annotations.ECGetMessage;
import com.example.demo.common.exception.errorcode.annotations.ErrorCode;

/**
 * 系统级错误
 * */
@ErrorCode("default")
public enum SystemErrorCode {
    SUCCESS(200, 200, "成功"),
    SYSTEM_ERROR(1, 500, "系统内部错误"),
    FORBIDDEN(2, 403, "没有操作权限"),
    UNAUTHORIZED(3, 401, "未登录"),
    NOT_FOUND(4, 404, "资源未找到"),
    INVALID_PARAM(5, 400, "参数不合法"),
    LICENSE_FORBIDDEN(6, 403, "证书未购买该模块，或该模块时效已到期"),
    LICENSE_EXPIRED(7, 403, "证书已过期"),
    METHOD_NOT_ALLOWED(8, 405, "HTTP方法不被允许"),
    MEDIA_NOT_SUPPORTED(9, 415, "不支持的HTTP媒体类型"),
    FORWARDING_ERROR(10, 502, "网关转发失败"),
    GATEWAY_TIMEOUT(11, 504, "业务请求超时"),
    TOO_MANY_REQUESTS(12, 429, "请求过于频繁"),
    REQUEST_TIMEOUT(13,504, "未找到可用的服务"),
    TOKEN_RENEWAL(14, 401, "token更换响应"),
    ;

    private final Integer code;
    private final Integer httpCode;
    private final String msg;

    SystemErrorCode(Integer code, Integer httpCode, String msg) {
        this.code = code;
        this.httpCode = httpCode;
        this.msg = msg;
    }

    @ECGetCode
    public Integer getCode() {
        return code;
    }

    @ECGetHTTPStatus
    public Integer getHttpCode() {
        return httpCode;
    }

    @ECGetMessage
    public String getMsg() {
        return msg;
    }
}
