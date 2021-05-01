package cn.xuxinkai.modules.common.config;

import cn.xuxinkai.modules.common.constant.SysConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuxinkai
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class SysFileProperties {
    /** 文件大小限制 */
    private Long maxSize;

    /** 头像大小限制 */
    private Long avatarMaxSize;

    private XadminPath mac;

    private XadminPath linux;

    private XadminPath windows;

    public XadminPath getPath(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith(SysConstant.WIN)) {
            return windows;
        } else if(os.toLowerCase().startsWith(SysConstant.MAC)){
            return mac;
        }
        return linux;
    }

    @Data
    public static class XadminPath {

        private String path;

        private String avatar;
    }
}
