package com.example.demo.enums;


import com.example.demo.common.exception.errorcode.annotations.ECGetCode;
import com.example.demo.common.exception.errorcode.annotations.ECGetHTTPStatus;
import com.example.demo.common.exception.errorcode.annotations.ECGetMessage;
import com.example.demo.common.exception.errorcode.annotations.ErrorCode;

@ErrorCode("demo")
public enum ErrorEnum {
    /**
     * 错误信息提示
     */
    NO_NEW_VER(20001, 200, "已是最新版本"),
    PARAMETER_REPEAT(40001, 400, "参数不能重复"),
    OBJECT_NULL(40002, 400, "对象不存在"),
    PARAMETER_ERROR(40003, 400, "参数错误"),
    FILE_UPLOAD_ERROR(40004, 400, "文件上传错误"),
    USER_ABSENCE(40005, 400, "用户名或密码错误"),
    NO_OPERATION_PERMISSION(40006, 400, "无操作权限"),
    RE_SET_PASSWORD_NOT_MATCH(40007, 400, "两次输入密码不一致"),
    OLD_PASSWORD_ERROR(40008, 400, "原密码错误"),
    USER_HAS_REGISTERED(40009, 400, "同用户名已注册"),
    PARAMETER_NOT_NULL(40010, 400, "必填参数不能为空"),
    INTERFACE_UNAVAILABLE(40011, 400, "该业务不可使用此接口"),
    IMMUTABLE_VALUES(40012, 400, "存在不可修改的值"),
    REPETITIVE_OPERATION(40013, 400, "重复操作"),
    ACCESS_DENIED(40014, 400, "无操作权限"),
    NOT_PREFIX_OPERATION(40015, 400, "有前置操作未完成"),
    UNCOLLECTED(40016, 400, "未收藏该公告"),
    IRREVOCABLE(40017, 400, "该公告有标记项，无法取消"),
    MISSING_PHONE_NUMBER(40018, 400, "请先补充您的手机号信息，订阅数据需要通过你的手机号推送至钉钉"),
    ERROR_PHONE_NUMBER(40019, 400, "您填写的手机号无法绑定到企业钉钉，您后续订阅的信息可能会推送失败"),
    NO_VIP_ACCESS_PERMISSION(40020, 400, "非VIP用户无法操作，请升级后操作（如果您已经是VIP用户，请尝试重新登录后联系管理员）"),
    NOT_EXIST_ORDER(40021, 400, "不存在该订单"),
    PARAMETER_OUT_RANGE(40022, 400, "传入参数不在给定范围内"),
    NOT_EXIST_WECHAT_PAY_INFO(40023, 400, "用户的微信支付相关信息不存在，无法发起支付"),
    NOT_EXIST_PRODUCT(40024, 400, "商品不存在"),
    MULTI_PARTY_LOGIN(40025, 400, "其他设备登录"),
    EXPIRED_TOKEN(40026, 400, "token已过期，请重新登录"),
    LABEL_EXIST(40027, 400, "标签已存在"),
    USER_NAME_CANNOT_BE_EMPTY(40028, 400, "用户名不能为空"),
    DATA_INSERTION_FAILURE(50001, 500, "数据处理失败"),
    ANNOUNCEMENT_FILE_NOT_EXIST(50002,500, "招标公告文件不存在"),
    FILTER_EXCEPTION(50003, 500, "过滤器异常"),
    NO_LOGIN_INFORMATION(50004, 500, "登录信息不存在，请退出重新登录"),
    USE_AT_THE_SPECIFIED_TIME(50005, 500, "请在规则的时间使用该功能，当前时间段不支持"),
    ;

    private final Integer code;
    private final Integer httpCode;
    private final String message;

    ErrorEnum(Integer code, Integer httpCode, String message) {
        this.code = code;
        this.httpCode = httpCode;
        this.message = message;
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
    public String getMessage() {
        return message;
    }

}
