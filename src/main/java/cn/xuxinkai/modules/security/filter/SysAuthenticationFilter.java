package cn.xuxinkai.modules.security.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.service.SysUserService;
import cn.xuxinkai.modules.common.util.SysRedisUtils;
import cn.xuxinkai.modules.common.util.SysTokenUtils;
import cn.xuxinkai.modules.common.util.result.ResultCodeEnum;
import cn.xuxinkai.modules.common.util.result.SysResponseUtils;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import cn.xuxinkai.modules.security.config.bean.SysSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 路由访问鉴权 - 访问需要认证的接口都需要经过此过滤器，会解析请求头中的token,再解析token得到用户信息,再存到SecurityContextHolder中
 * 1、如果有Token的请求头（可以自已定义名字），取出token，解析token，解析成功说明token正确，将解析出来的用户信息放到SpringSecurity的上下文中
 * 2、如果有Token的请求头，解析token失败（无效token，或者过期失效），取不到用户信息，放行进入后面的过滤器处理
 * 3、没有accessToken的请求头，放行进入后面的过滤器处理
 *
 * @author xuxinkai
 * @date 2021/01/04
 */
@Slf4j
@Component
public class SysAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    SysSecurityProperties sysSecurityProperties;

    @Autowired
    private SysTokenUtils sysTokenUtils;

    @Autowired
    private SysRedisUtils sysRedisUtils;

    @Autowired
    private SysUserService sysUserService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        //获取请求头中的token
        String token = request.getHeader(sysSecurityProperties.getRequestHeader());

        if (ObjectUtil.isNotNull(token)) {
            log.info("请求头携带的token为:\n{}", token);
            //log.info(request.getInputStream().toString());
            //判断token有没有过期
            if (sysTokenUtils.isExpiration(token)) {
                SysResponseUtils.out(response, SysResultGenerator.genFailResult(ResultCodeEnum.GL101));
                return;
            }
            //解析token，获取用户名
            String username = sysTokenUtils.parseToken(token).getSubject();
            log.info("当前token解析出的用户名：{}", username);
            if (ObjectUtil.isNotNull(username) && ObjectUtil.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetailDto userDetailDto = sysUserService.loadUserByUsername(username);
                log.info("通过username获取的用户信息：{}",userDetailDto);
                Object redisToken =  sysRedisUtils.get(userDetailDto.getUser().getUserId().toString() + "-token");
                if ( ObjectUtil.isNotNull(userDetailDto) && ObjectUtil.isNotNull(redisToken) && redisToken.toString().equals(token)) {
                    // todo : 此处后面需要实现token续期
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetailDto, userDetailDto.getPassword(), userDetailDto.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

}
