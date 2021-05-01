package cn.xuxinkai.modules.security.config;

import cn.xuxinkai.modules.security.config.bean.SysLoginProperties;
import cn.xuxinkai.modules.security.config.bean.SysSecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 统一配置类
 * 自动读取配置转换为Pojo
 *
 * @author xuxinkai
 * @date 2020/12/30
 */
@Configuration
public class ConfigBeanConfiguration {

    /**
     * 登录相关配置
     *
     * @return {@link SysLoginProperties}
     */
    @Bean
    @ConfigurationProperties(prefix = "login", ignoreUnknownFields = true)
    public SysLoginProperties loginProperties() {
        return new SysLoginProperties();
    }

    /**
     * 安全相关配置
     *
     * @return {@link SysSecurityProperties}
     */
    @Bean
    @ConfigurationProperties(prefix = "jwt", ignoreUnknownFields = true)
    public SysSecurityProperties securityProperties() {
        return new SysSecurityProperties();
    }

}

