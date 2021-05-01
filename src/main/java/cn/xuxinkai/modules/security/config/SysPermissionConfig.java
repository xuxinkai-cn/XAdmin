package cn.xuxinkai.modules.security.config;

import cn.xuxinkai.modules.common.dto.UserDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口权限校验
 *
 * @author xuxinkai
 */
@Service(value = "sys")
@Slf4j
public class SysPermissionConfig {

    public Boolean check(String... permissions) {
        //todo: 获取登录用户信息需要重构
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetails = (UserDetailDto) authentication.getPrincipal();
        // 获取当前用户的所有权限
        List<String> sysPermissions = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        log.info("当前登录用户拥有的权限为:{}",sysPermissions);
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return sysPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(sysPermissions::contains);
    }

}
