package com.example.demo.common.exception;

import com.example.demo.common.exception.errorcode.ErrorCodeFactory;
import com.example.demo.common.exception.errorcode.GeneralErrorCode;
import lombok.Data;

/**
 描述:
 HttpRequest请求返回的最外层对象,用一种统一的格式返回给前端
 * @author liupanlong

 */
@Data
public class ResponseMessage<T> {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 信息描述
     */
    private String message;

    /**
     * 具体的信息内容
     */
    private T data;

    public static <T> ResponseMessage<T> success(T object, String message) {
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setCode(SystemErrorCode.SUCCESS.getCode());
        if(message == null) {
            responseMessage.setMessage(SystemErrorCode.SUCCESS.getMsg());
        } else {
            responseMessage.setMessage(message);
        }
        responseMessage.setData(object);
        return responseMessage;
    }

    public static <T> ResponseMessage<T> success() {
        return success(null, null);
    }

    public static <T> ResponseMessage<T> success(T obj) {
        return success(obj, null);
    }

    public static <T> ResponseMessage<T>  success(String message) {
        return success(null, message);
    }

    public static <T> ResponseMessage<T> error(Object errorCode, String msg) {
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        GeneralErrorCode generalErrorCode = ErrorCodeFactory.createErrorCode(errorCode, msg);
        responseMessage.setCode(generalErrorCode.getCode());
        responseMessage.setMessage(generalErrorCode.getMessage());
        return responseMessage;
    }

    public static <T> ResponseMessage<T> error(Object errorCode){
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        GeneralErrorCode generalErrorCode = ErrorCodeFactory.createErrorCode(errorCode);
        responseMessage.setCode(generalErrorCode.getCode());
        responseMessage.setMessage(generalErrorCode.getMessage());
        return responseMessage;
    }

    public static <T> ResponseMessage<T> error(Object errorCode, T data){
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        GeneralErrorCode generalErrorCode = ErrorCodeFactory.createErrorCode(errorCode);
        responseMessage.setCode(generalErrorCode.getCode());
        responseMessage.setMessage(generalErrorCode.getMessage());
        responseMessage.setData(data);
        return responseMessage;
    }

    public static <T> ResponseMessage<T> error(GeneralErrorCode generalErrorCode) {
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setCode(generalErrorCode.getCode());
        responseMessage.setMessage(generalErrorCode.getMessage());
        return responseMessage;
    }

    public static <T> ResponseMessage<T> error(GeneralErrorCode generalErrorCode, T data) {
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setCode(generalErrorCode.getCode());
        responseMessage.setMessage(generalErrorCode.getMessage());
        responseMessage.setData(data);
        return responseMessage;
    }

    public static <T> ResponseMessage<T> error(GeneralErrorCode generalErrorCode, String message) {
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setCode(generalErrorCode.getCode());
        responseMessage.setMessage(message);
        return responseMessage;
    }
}
