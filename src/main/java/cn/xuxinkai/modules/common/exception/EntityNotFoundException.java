package cn.xuxinkai.modules.common.exception;

import org.springframework.util.StringUtils;

/**
 * 实体类相关异常
 *
 * @author xuxinkai
 * @date 2021/03/05
 */
public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4946297502848854385L;

    public EntityNotFoundException(Class clazz, String field, String val) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " "+ val + " does not exist";
    }
}
