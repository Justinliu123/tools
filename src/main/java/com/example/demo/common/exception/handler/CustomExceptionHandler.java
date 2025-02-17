package com.example.demo.common.exception.handler;

import com.example.demo.common.exception.GeneralException;
import com.example.demo.common.exception.ResponseMessage;
import com.example.demo.common.exception.SystemErrorCode;
import com.example.demo.enums.ErrorEnum;
import com.netflix.client.ClientException;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Set;

/**
 * 异常统一处理助手：
 * (1)统一处理系统所有异常
 * (2)统一处理异常国际化
 * (3)各个业务模块尽量只抛出两类异常：自定义异常GeneralException、参数校验异常ConstraintViolationException
 */
@ControllerAdvice
@SuppressWarnings("all")
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    private static final String LOAD_BALANCER_CONTEXT_MESSAGE = "Load balancer does not have available server for client: ";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    /**
     * 参数校验消息体异常处理国际化
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logger.error("Method argument not valid, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);

        String errMessage;
        ResponseMessage responseMessage = null;

        try {
            List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
            StringBuilder sb = new StringBuilder();
            int size = objectErrors.size();
            //消息体中参数可能有多个，逐个处理异常国际化
            for (int i = 0; i < size; i++) {
                ObjectError objectError = objectErrors.get(i);
                errMessage = objectError.getDefaultMessage();
                sb.append(errMessage);
                if (i < size - 1) {
                    sb.append(", ");
                }
            }
            logger.error("errDetail: {}", sb);
            responseMessage = ResponseMessage.error(SystemErrorCode.INVALID_PARAM, sb.toString());
        } catch (Exception e) {
            logger.error("Format error message failed, ", e);
            responseMessage = handleSystemErrorCode(SystemErrorCode.INVALID_PARAM, e.getMessage());
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("HTTP method not supported, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.METHOD_NOT_ALLOWED, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("HTTP media type not supported, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.MEDIA_NOT_SUPPORTED, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Missing path variable, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Missing servlet request parameter, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.INVALID_PARAM, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Servlet request binding exception, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.INVALID_PARAM, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Conversion not supported, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        logger.error("Type mismatch, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.INVALID_PARAM, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("HTTP message not readable, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.INVALID_PARAM, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("HTTP message not writable, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Missing servlet request part, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.INVALID_PARAM, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        logger.error("Bing exception, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.INVALID_PARAM, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("No handler found exception, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
            AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        logger.error("Async request timeout exception, {}-{}, {}",
                ((ServletWebRequest) webRequest).getRequest().getMethod(),
                ((ServletWebRequest) webRequest).getRequest().getRequestURI(),
                ex);
        if (webRequest instanceof ServletWebRequest) {
            ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
            HttpServletRequest request = servletWebRequest.getRequest();
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                if (logger.isErrorEnabled()) {
                    logger.error("Async timeout for " + request.getMethod() + " [" + request.getRequestURI() + "]");
                }
            }
        }

        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.REQUEST_TIMEOUT, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Internal exception, {}-{}, {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex);
        ResponseMessage responseMessage = handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping("/filterError")
    public ResponseMessage<Object> error(ServletRequest request) {
        if(request.getAttribute("filterError") instanceof GeneralException) {
            throw (GeneralException) request.getAttribute("filterError");
        } else {
            throw new GeneralException(ErrorEnum.FILTER_EXCEPTION);
        }
    }

    /**
     * 异常统一处理国际化：
     * (1)自定义异常GeneralException
     * (2)web应用异常WebApplicationException
     * (3)Hystrix异常HystrixBadRequestException
     * (4)参数校验异常ConstraintViolationException
     * (5)其他异常
     */
    @ResponseBody
    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseMessage handle(Exception e) {
        logger.error("Global exception handler, ", e);
        ResponseMessage ret;
        if (e instanceof GeneralException) {//自定义异常GeneralException
            ret = handleGeneralException((GeneralException) e);
        } else if (e instanceof WebApplicationException) {//web应用异常WebApplicationException
            return handleWenApplicationException((WebApplicationException) e);
        } else if (e instanceof HystrixBadRequestException) {//Hystrix异常HystrixBadRequestException
            ret = this.handleHystrixBadRequestException(e);
        } else if (e instanceof ConstraintViolationException) {//参数校验异常ConstraintViolationException
            ret = handleConstraintViolationException((ConstraintViolationException) e);
        } else if (e instanceof RuntimeException) {//参数校验异常ConstraintViolationException
            ret = handleClientException((RuntimeException) e);
        } else {//其他异常视为系统异常
            ret = handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, e.getMessage());
        }
        return ret;
    }

    private ResponseMessage handleClientException(RuntimeException e) {
        Throwable throwable = e.getCause();
        if (throwable == null) {
            return handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, e.getMessage());
        }
        if (throwable instanceof ClientException
                && throwable.getMessage() != null
                && throwable.getMessage().contains(LOAD_BALANCER_CONTEXT_MESSAGE)) {

            response.setStatus(SystemErrorCode.FORWARDING_ERROR.getHttpCode());

            String client = throwable.getMessage().replace(LOAD_BALANCER_CONTEXT_MESSAGE, "");

            return ResponseMessage.error(SystemErrorCode.FORWARDING_ERROR, "未找到可用的服务" + String.format("[%s]", client));
        }

        return handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, e.getMessage());
    }

    private ResponseMessage handleHystrixBadRequestException(Exception e) {
        Throwable throwable = e.getCause();
        if (throwable == null) {
            return handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, e.getMessage());
        } else {
            if (throwable instanceof WebApplicationException) {
                return handleWenApplicationException((WebApplicationException) throwable);
            } else {
                response.setStatus(SystemErrorCode.SYSTEM_ERROR.getHttpCode());
                return handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, throwable.getMessage());
            }
        }
    }

    private ResponseMessage handleWenApplicationException(WebApplicationException webApplicationException) {
        response.setStatus(webApplicationException.getResponse().getStatus());

        Object entity = webApplicationException.getResponse().getEntity();
        if (entity instanceof ResponseMessage) {
            return (ResponseMessage) entity;
        }

        return handleSystemErrorCode(SystemErrorCode.SYSTEM_ERROR, webApplicationException.getMessage());
    }

    private ResponseMessage handleConstraintViolationException(ConstraintViolationException e) {
        response.setStatus(SystemErrorCode.INVALID_PARAM.getHttpCode());
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            int i = 0;
            int size = constraintViolations.size();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                String message = constraintViolation.getMessage();
                sb.append(message);
                if (size > 1 && i < size - 1) {
                    sb.append(", ");
                }
                i++;
            }
        }
        ResponseMessage responseMessage = ResponseMessage.error(SystemErrorCode.INVALID_PARAM, sb.toString());
        return responseMessage;
    }

    private ResponseMessage handleSystemErrorCode(SystemErrorCode systemErrorCode, String errDetail) {
        response.setStatus(systemErrorCode.getHttpCode());
        String messageKey = "default_" + systemErrorCode.name();
        String message = systemErrorCode.getMsg();
        ResponseMessage responseMessage = ResponseMessage.error(systemErrorCode, message);
        return responseMessage;
    }

    private ResponseMessage handleGeneralException(GeneralException e) {
        response.setStatus(e.getGeneralErrorCode().getHttpCode());
        ResponseMessage responseMessage = ResponseMessage.error(e.getGeneralErrorCode(), e.getGeneralErrorCode().getMessage());
        responseMessage.setData(e.getData());
        return responseMessage;
    }

}
