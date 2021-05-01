package cn.xuxinkai.modules.security.security;

import cn.xuxinkai.modules.common.util.result.ResultCodeEnum;
import cn.xuxinkai.modules.common.util.result.SysResponseUtils;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对未登录的请求进行直接响应处理的处理器
 * 把几种情况一次性在这里处理掉
 *
 * @author xuxinkai
 */
@Slf4j
@Component
public class SysAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 对无权访问直接响应
     *
     * @param httpServletRequest  http servlet请求
     * @param httpServletResponse http servlet响应
     * @param e                   e
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.core.AuthenticationException e) {
        if (e != null) {
            // 未登录
            SysResponseUtils.out(httpServletResponse, SysResultGenerator.genFailResult(ResultCodeEnum.GL100));
        }
    }
}