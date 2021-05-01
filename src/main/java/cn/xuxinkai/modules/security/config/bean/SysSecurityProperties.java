package cn.xuxinkai.modules.security.config.bean;

import lombok.Data;

/**
 * @author xuxinkai
 */
@Data
public class SysSecurityProperties {
    /**
     * Request Headers ： Authorization
     */
    private String RequestHeader;

    /**
     * 必须使用最少88位的Base64对该令牌进行编码
     */
    private String TokenSecret;

    /**
     * 令牌过期时间 此处单位/毫秒
     */
    private Long TokenTtl;

    /**
     * 验证码 key
     */
    private String CodeKey;

    /**
     * RSA 私钥
     */
    private String PrivateKey;
}
