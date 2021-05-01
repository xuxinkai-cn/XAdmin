package cn.xuxinkai.modules.logger.aspect;

import cn.hutool.json.JSONUtil;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.entity.SysLog;
import cn.xuxinkai.modules.common.service.SysLogService;
import cn.xuxinkai.modules.common.util.SysRequestHolder;
import cn.xuxinkai.modules.common.util.SysStringUtils;
import cn.xuxinkai.modules.common.util.SysThrowableUtils;
import cn.xuxinkai.modules.logger.enumerate.SysLogTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志切面 aspect 类
 *
 * @author xuxinkai
 * @date 2020/12/25
 */
@Component
@Aspect
@Slf4j
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 创建线程局部变量-当前时间，以便计算请求消耗的时间
     */
    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点,使用注解作为切入点
     */
    @Pointcut("@annotation(cn.xuxinkai.modules.logger.annotation.SysLog)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        //设置当前线程的请求开始时间
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Long consumeTime = System.currentTimeMillis() - currentTime.get();
        //移除线程变量
        currentTime.remove();
        saveLog(SysLogTypeEnum.LOG_INFO.getMyLogType(),joinPoint,consumeTime,"");
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Long consumeTime = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();
        saveLog(SysLogTypeEnum.LOG_ERROR.getMyLogType(),(ProceedingJoinPoint)joinPoint,consumeTime, SysThrowableUtils.getStackTrace(e));
    }

    /**
     * 保存日志
     */
    public void saveLog(String logType,ProceedingJoinPoint joinPoint, Long consumeTime, String exceptionDetail){
        //获取servlet请求
        HttpServletRequest request = SysRequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        cn.xuxinkai.modules.logger.annotation.SysLog aopLog = method.getAnnotation(cn.xuxinkai.modules.logger.
                annotation.SysLog.class);
        // 方法名称
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
        //方法参数
        String params = getParameter(method, joinPoint.getArgs());
        //请求ip
        String requestIp = SysStringUtils.getIp(request);
        //构建sysLog
        SysLog sysLog = new SysLog(aopLog.value(),logType,
                methodName,params,requestIp,consumeTime,getUsername(), SysStringUtils.getCityInfo(requestIp), SysStringUtils.getBrowser(request),exceptionDetail,new Timestamp(System.currentTimeMillis()));
        sysLogService.insert(sysLog);
    }


    public String getUsername() {
        try {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
            return userDetailDto.getUsername();
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>(16);
                String key = parameters[i].getName();
                if (!SysStringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.isEmpty()) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }
}


