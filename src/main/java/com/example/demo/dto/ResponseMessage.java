package com.example.demo.dto;

import lombok.Data;

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

    public static <T> ResponseMessage<T> success(T data) {
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setCode(200);
        responseMessage.setMessage("成功");
        responseMessage.setData(data);
        return responseMessage;
    }
}