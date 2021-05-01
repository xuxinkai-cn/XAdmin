package cn.xuxinkai.modules.security.security;

import cn.xuxinkai.modules.common.util.result.ResultCodeEnum;
import cn.xuxinkai.modules.common.util.result.SysResponseUtils;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 路由接口权限认证处理，登录成功后，要访问接口如果无权限，就直接响应403
 *
 * @author xuxinkai
 * @date 2020/12/31
 */
@Component
public class SysAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 路由接口权限认证处理，登录成功后，要访问接口如果无权限，就直接响应403
     *
     * @param request  请求
     * @param response 响应
     * @param e        e
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        SysResponseUtils.out(response, SysResultGenerator.genFailResult(ResultCodeEnum.GL403));
    }
}
