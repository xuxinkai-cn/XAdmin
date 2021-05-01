package cn.xuxinkai.modules.security.security;

import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.util.SysPasswordUtils;
import cn.xuxinkai.modules.common.util.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 自定义认证处理：包含自定义密码校验规则
 *
 * @author 人间不值得<xuxinkai.cn>
 * @date 2020/10/19
 */
@Slf4j
@Component
public class SysAuthenticationProvider implements AuthenticationProvider {

    /**
     * 身份验证
     *
     * @param authentication 身份验证
     * @return {@link Authentication}* @throws AuthenticationException 身份验证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取上下文中传递的UserDetailDto，该实体是前端输入的用户名从数据库查询出来的对应实体
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //获取上下文中用户输入的密码
        String password = (String) authentication.getCredentials();
        log.info("用户输入的密码为：{},加密后密码为：{}，数据库中的密码为：{}",password,SysPasswordUtils.useWordpressEncrypt(password),userDetailDto.getPassword());
        // 验证密码
        if (!SysPasswordUtils.isValidWordpressPassword(password, userDetailDto.getPassword())) {
            throw new BadCredentialsException(ResultCodeEnum.GL103.getMessage());
        }
        //继续在Spring Security 上下文传递 myUserDetails, password, myUserDetails.getAuthorities()
        return new UsernamePasswordAuthenticationToken(userDetailDto, password, userDetailDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

