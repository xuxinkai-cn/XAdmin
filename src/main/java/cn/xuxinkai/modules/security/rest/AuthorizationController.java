package cn.xuxinkai.modules.security.rest;

import cn.hutool.core.util.IdUtil;
import cn.xuxinkai.modules.common.annotation.rest.AnonymousGetMapping;
import cn.xuxinkai.modules.common.annotation.rest.AnonymousPostMapping;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.dto.UserDto;
import cn.xuxinkai.modules.common.dto.UserLoginDto;
import cn.xuxinkai.modules.common.service.SysUserService;
import cn.xuxinkai.modules.common.util.SysRedisUtils;
import cn.xuxinkai.modules.common.util.SysRsaUtils;
import cn.xuxinkai.modules.common.util.SysTokenUtils;
import cn.xuxinkai.modules.common.util.result.ResultCodeEnum;
import cn.xuxinkai.modules.common.util.result.SysResult;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import cn.xuxinkai.modules.logger.annotation.SysLog;
import cn.xuxinkai.modules.security.config.bean.SysLoginCodeEnum;
import cn.xuxinkai.modules.security.config.bean.SysLoginProperties;
import cn.xuxinkai.modules.security.config.bean.SysSecurityProperties;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 系统授权认证
 *
 * @author xuxinkai
 * @date 2020/12/30
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Api(tags = "系统：系统授权接口")
public class AuthorizationController {

    private final SysRedisUtils sysRedisUtils;
    private final SysTokenUtils sysTokenUtils;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final SysLoginProperties sysLoginProperties;

    private final SysSecurityProperties sysSecurityProperties;

    @Autowired
    private SysUserService sysUserService;

    @SysLog("获取验证码")
    @ApiOperation("获取验证码")
    @AnonymousGetMapping(value = "/code")
    public SysResult getCode() {
        // 获取运算的结果
        Captcha captcha = sysLoginProperties.getCaptcha();
        String uuid = sysSecurityProperties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if ((captcha.getCharType() - 1 == SysLoginCodeEnum.arithmetic.ordinal()) && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存到 redis
        sysRedisUtils.set(uuid, captchaValue, sysLoginProperties.getSysLoginCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<>(2);
        imgResult.put("img", captcha.toBase64());
        imgResult.put("uuid", uuid);
        return SysResultGenerator.genSuccessResult(imgResult);
    }

    @SysLog("登录授权")
    @ApiOperation("登录授权")
    @AnonymousPostMapping(value = "/login")
    public SysResult login(@Validated @RequestBody UserLoginDto userLoginDto) throws Exception {
        log.info("登录的用户名："+userLoginDto.getUsername()+"密码："+userLoginDto.getPassword());
        // 密码解密
        String password = SysRsaUtils.decryptByPrivateKey(sysSecurityProperties.getPrivateKey(), userLoginDto.getPassword());
        userLoginDto.setPassword(password);
        // 查询验证码
        String code = (String) sysRedisUtils.get(userLoginDto.getUuid());
        // 清除验证码
        sysRedisUtils.del(userLoginDto.getUuid());
        //校验验证码
        if (StringUtils.isBlank(code) || !userLoginDto.getCode().equalsIgnoreCase(code)) {
            return SysResultGenerator.genFailResult(ResultCodeEnum.GL102);
        }
        //根据用户名获取数据库里面正确的UserDetailDto
        UserDetailDto userDetailDto = sysUserService.loadUserByUsername(userLoginDto.getUsername());

        //进入Spring Security 过滤器链
        //UsernamePasswordAuthenticationToken继承AbstractAuthenticationToken实现Authentication,所以当在页面中输入用户名和密码之后首先会进入到UsernamePasswordAuthenticationToken验证(Authentication)，然后生成的Authentication会被交由AuthenticationManager来进行管理，而AuthenticationManager管理一系列的AuthenticationProvider，而每一个Provider都会通过UserDetailsService和UserDetail来返回一个以Username Password AuthenticationToken实现的带用户名和密码以及权限的Authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetailDto, userLoginDto.getPassword(), userDetailDto.getAuthorities());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //离开过滤器链

        // 生成令牌
        HashMap<String, Object> claims = new HashMap<>(16);
        claims.put("role", authentication.getAuthorities());
        String token = sysTokenUtils.createToken(authentication.getName(), claims);
        log.info("生成的鉴权token：{}",token);

        //更新Redis中的token
        String tokenKey = userDetailDto.getUser().getUserId().toString() + "-token";
        sysRedisUtils.set(tokenKey, token, sysSecurityProperties.getTokenTtl(), TimeUnit.MILLISECONDS);

        Map<String, Object> resultMapData = new HashMap<>(2);
        resultMapData.put("token", token);
        resultMapData.put("user", userDetailDto);
        return SysResultGenerator.genSuccessResult(resultMapData, "登录成功");
    }

    @SysLog("退出登录")
    @ApiOperation("退出登录")
    @GetMapping(value = "/logout")
    public SysResult logout() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //获取UserDto
        UserDto userDto = userDetailDto.getUser();
        log.info("用户{}退出登录", userDto.getUsername());
        //删除Redis中存储的token
        String tokenKey = userDetailDto.getUser().getUserId().toString() + "-token";
        sysRedisUtils.del(tokenKey);
        if (sysRedisUtils.get(tokenKey) == null) {
            //返回删除成功的标识符，前端也需要清除本地缓存的token
            return SysResultGenerator.genSuccessResult("退出成功");
        }
        return SysResultGenerator.genFailResult("退出失败");
    }

    @SysLog("获取用户信息")
    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public SysResult getUserInfo() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto oldUserDetailDto = (UserDetailDto) authentication.getPrincipal();
        //根据用户名获取最新的UserDetailDto
        UserDetailDto userDetailDto = sysUserService.loadUserByUsername(oldUserDetailDto.getUsername());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetailDto, userDetailDto.getPassword(), userDetailDto.getAuthorities()));
        return SysResultGenerator.genSuccessResult(userDetailDto);
    }
}