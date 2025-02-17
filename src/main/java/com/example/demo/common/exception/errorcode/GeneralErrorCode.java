package com.example.demo.common.exception.errorcode;

import java.io.Serializable;

public class GeneralErrorCode implements Serializable {
    private static final long serialVersionUID = -3768050852788374286L;
    private Integer code;
    private Integer httpCode;
    private String message;
    private String service;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
