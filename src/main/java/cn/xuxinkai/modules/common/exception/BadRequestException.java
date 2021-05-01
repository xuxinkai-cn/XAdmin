package cn.xuxinkai.modules.common.exception;

import lombok.Getter;

/**
 *  统一异常信息处理
 *
 * @author xuxinkai
 * @date 2021/03/05
 */
@Getter
public class BadRequestException extends RuntimeException{

    private static final long serialVersionUID = 7953881562498147725L;

    public BadRequestException(String msg){
        super(msg);
    }
}
