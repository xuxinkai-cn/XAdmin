package cn.xuxinkai.modules.security.config.bean;

import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Objects;

/**
 * 登录验证码相关配置类
 *
 * @author xuxinkai
 */
@Data
public class SysLoginProperties {

    /**
     * 账号单用户 登录
     */
    private boolean singleLogin = false;

    private SysLoginCode sysLoginCode;
    /**
     * 用户登录信息缓存
     */
    private boolean cacheEnable = false;

    public boolean isSingleLogin() {
        return singleLogin;
    }

    public boolean isCacheEnable() {
        return cacheEnable;
    }

    /**
     * 获取验证码生产类，默认使用算术验证码
     *
     * @return /
     */
    public Captcha getCaptcha() {
        if (Objects.isNull(sysLoginCode)) {
            sysLoginCode = new SysLoginCode();
            if (Objects.isNull(sysLoginCode.getCodeType())) {
                sysLoginCode.setCodeType(SysLoginCodeEnum.arithmetic);
            }
        }
        return switchCaptcha(sysLoginCode);
    }

    /**
     * 依据配置信息生产验证码
     *
     * @param sysLoginCode 验证码配置信息
     * @return /
     */
    private Captcha switchCaptcha(SysLoginCode sysLoginCode) {
        Captcha captcha;
        synchronized (this) {
            switch (sysLoginCode.getCodeType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new ArithmeticCaptcha(sysLoginCode.getWidth(), sysLoginCode.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(sysLoginCode.getLength());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(sysLoginCode.getWidth(), sysLoginCode.getHeight());
                    captcha.setLen(sysLoginCode.getLength());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(sysLoginCode.getWidth(), sysLoginCode.getHeight());
                    captcha.setLen(sysLoginCode.getLength());
                    break;
                case gif:
                    captcha = new GifCaptcha(sysLoginCode.getWidth(), sysLoginCode.getHeight());
                    captcha.setLen(sysLoginCode.getLength());
                    break;
                case spec:
                    captcha = new SpecCaptcha(sysLoginCode.getWidth(), sysLoginCode.getHeight());
                    captcha.setLen(sysLoginCode.getLength());
                    break;
                default:
                    throw new RuntimeException("验证码配置信息错误！正确配置查看 SysLoginCodeEnum ");
            }
        }
        if(StringUtils.isNotBlank(sysLoginCode.getFontName())){
            captcha.setFont(new Font(sysLoginCode.getFontName(), Font.PLAIN, sysLoginCode.getFontSize()));
        }
        return captcha;
    }
}
