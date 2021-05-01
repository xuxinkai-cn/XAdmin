package cn.xuxinkai.modules.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志(SysLog)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:06
 */
@Data
public class SysLog implements Serializable {
    private static final long serialVersionUID = -89303210142385678L;
    /**
    * ID
    */
    private Long logId;
    /**
    * 描述
    */
    private String description;
    /**
    * 日志类型 INFO WARN ERROR
    */
    private String logType;
    /**
    * 方法名
    */
    private String method;
    /**
    * 参数
    */
    private String params;
    /**
    * 请求ip
    */
    private String requestIp;
    /**
    * 请求耗时
    */
    private Long time;
    /**
    * 操作用户
    */
    private String username;
    /**
    * 地址
    */
    private String address;
    /**
    * 浏览器
    */
    private String browser;
    /**
    * 异常详细
    */
    private String exceptionDetail;
    /**
    * 创建日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;

    public SysLog(String description, String logType, String method, String params, String requestIp, Long time, String username, String address, String browser, String exceptionDetail, Date createTime) {
        this.description = description;
        this.logType = logType;
        this.method = method;
        this.params = params;
        this.requestIp = requestIp;
        this.time = time;
        this.username = username;
        this.address = address;
        this.browser = browser;
        this.exceptionDetail = exceptionDetail;
        this.createTime = createTime;
    }

    public SysLog() {

    }
}