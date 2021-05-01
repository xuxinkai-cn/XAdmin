package cn.xuxinkai.modules.common.exception;

import cn.xuxinkai.modules.common.util.result.ResultCodeEnum;
import cn.xuxinkai.modules.common.util.result.SysResult;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

/**
 * 全局异常处理，返回 SysResult
 *
 * @author xuxinkai
 * @date 2021/01/01
 */
@RestControllerAdvice
@Slf4j
public class RestGlobalExceptionHandler {

    /**
     * 表单校验错误处理
     *
     * @param request       请求
     * @param handlerMethod 处理程序方法
     * @param e             e
     * @return {@link SysResult}
     */
    @ExceptionHandler(BindException.class)
    public SysResult handleFormException(HttpServletRequest request, HandlerMethod handlerMethod, BindException e) {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);
        return SysResultGenerator.genFailResult(e.getBindingResult().getFieldError().getDefaultMessage());
    }


    /**
     * json校验错误处理
     *
     * @param request       请求
     * @param handlerMethod 处理程序方法
     * @param e             e
     * @return {@link SysResult}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SysResult handleFormException(HttpServletRequest request, HandlerMethod handlerMethod, MethodArgumentNotValidException e) {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);
        return SysResultGenerator.genFailResult(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public SysResult handleValidationException(HttpServletRequest request, HandlerMethod handlerMethod, ValidationException e) {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);
        return SysResultGenerator.genFailResult(e.getMessage());
    }

    /**
     * 404
     *
     * @param e
     * @return {@link Object}
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public SysResult handlerNoFoundException(HttpServletRequest request, HandlerMethod handlerMethod, NoHandlerFoundException e) {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);
        return SysResultGenerator.genFailResult(ResultCodeEnum.GL404);
    }

    /**
     * AuthenticationException
     *
     * @param e
     * @return {@link Object}
     */
    @ExceptionHandler(AuthenticationException.class)
    public SysResult handlerAuthenticationException(HttpServletRequest request, HandlerMethod handlerMethod, AuthenticationException e) {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);
        return SysResultGenerator.genFailResult(e.getMessage());
    }


    @ExceptionHandler(BadRequestException.class)
    public SysResult handlerBadRequestException(HttpServletRequest request, HandlerMethod handlerMethod, BadRequestException e) {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);
        return SysResultGenerator.genFailResult(e.getMessage());
    }


    /**
     * 500
     */
    @ExceptionHandler(Exception.class)
    public SysResult handlerException(HttpServletRequest request, HandlerMethod handlerMethod, Exception e) {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);
        return SysResultGenerator.genFailResult(ResultCodeEnum.GL500);
    }


    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public SysResult handleThrowable(HttpServletRequest request, HandlerMethod handlerMethod, Throwable e) {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e.getMessage());
        return SysResultGenerator.genFailResult(ResultCodeEnum.GL500);
    }
}

