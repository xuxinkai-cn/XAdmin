package cn.xuxinkai.modules.common.annotation;

import java.lang.annotation.*;

/**
 * 标记需要匿名访问的请求接口
 *
 * @author xuxinkai
 * @date 2020/12/30
 */
@Inherited
@Documented
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {

}
