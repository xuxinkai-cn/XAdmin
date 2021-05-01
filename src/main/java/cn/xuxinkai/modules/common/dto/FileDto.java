package cn.xuxinkai.modules.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xuxinkai
 */
@Data
public class FileDto implements Serializable {

    private static final long serialVersionUID = -7419035789707543691L;

    /**
     * 文件真实的名称
     */
    private String realName;
    /**
     * 文件名
     */
    private String name;
    /**
     * 后缀
     */
    private String suffix;
    /**
     * 路径
     */
    private String path;
    /**
     * 类型
     */
    private String type;
    /**
     * 大小
     */
    private String size;
}
