package cn.xuxinkai.modules.security.config.bean;

import lombok.Data;

/**
 * 验证码配置类
 *
 * @author xuxinkai
 */
@Data
public class SysLoginCode {
    /**
     * 验证码配置
     */
    private SysLoginCodeEnum codeType;
    /**
     * 验证码有效期 2分钟
     */
    private Long expiration = 2L;
    /**
     * 验证码内容长度
     */
    private int length = 2;
    /**
     * 验证码宽度
     */
    private int width = 111;
    /**
     * 验证码高度
     */
    private int height = 36;
    /**
     * 验证码字体
     */
    private String fontName;
    /**
     * 字体大小
     */
    private int fontSize = 25;

    public SysLoginCodeEnum getCodeType() {
        return codeType;
    }
}
