package cn.xuxinkai.modules.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuxinkai
 */
@Slf4j
@Component
public class SysSpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;
    private static final List<SysCallBack> CALL_BACKS = new ArrayList<>();
    private static boolean addCallback = true;

    /**
     * 针对 某些初始化方法，在SpringContextHolder 未初始化时 提交回调方法。
     * 在SpringContextHolder 初始化后，进行回调使用
     *
     * @param sysCallBack 回调函数
     */
    public synchronized static void addCallBacks(SysCallBack sysCallBack) {
        if (addCallback) {
            SysSpringContextHolder.CALL_BACKS.add(sysCallBack);
        } else {
            log.warn("SysCallBack：{} 已无法添加！立即执行", sysCallBack.getCallBackName());
            sysCallBack.executor();
        }
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取SpringBoot 配置信息
     *
     * @param property     属性key
     * @param defaultValue 默认值
     * @param requiredType 返回类型
     * @return /
     */
    public static <T> T getProperties(String property, T defaultValue, Class<T> requiredType) {
        T result = defaultValue;
        try {
            result = getBean(Environment.class).getProperty(property, requiredType);
        } catch (Exception ignored) {}
        return result;
    }

    /**
     * 获取SpringBoot 配置信息
     *
     * @param property 属性key
     * @return /
     */
    public static String getProperties(String property) {
        return getProperties(property, null, String.class);
    }

    /**
     * 获取SpringBoot 配置信息
     *
     * @param property     属性key
     * @param requiredType 返回类型
     * @return /
     */
    public static <T> T getProperties(String property, Class<T> requiredType) {
        return getProperties(property, null, requiredType);
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext属性未注入, 请在applicationContext" +
                    ".xml中定义SpringContextHolder或在SpringBoot启动类中注册SpringContextHolder.");
        }
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    private static void clearHolder() {
        log.debug("清除SpringContextHolder中的ApplicationContext:"
                + applicationContext);
        applicationContext = null;
    }

    @Override
    public void destroy() {
        SysSpringContextHolder.clearHolder();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SysSpringContextHolder.applicationContext != null) {
            log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SysSpringContextHolder.applicationContext);
        }
        SysSpringContextHolder.applicationContext = applicationContext;
        if (addCallback) {
            for (SysCallBack sysCallBack : SysSpringContextHolder.CALL_BACKS) {
                sysCallBack.executor();
            }
            CALL_BACKS.clear();
        }
        SysSpringContextHolder.addCallback = false;
    }
}


