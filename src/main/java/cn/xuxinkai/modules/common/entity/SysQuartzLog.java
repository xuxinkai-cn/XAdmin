package cn.xuxinkai.modules.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务日志(SysQuartzLog)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Data
public class SysQuartzLog implements Serializable {
    private static final long serialVersionUID = 651225866757173870L;
    /**
    * ID
    */
    private Long logId;
    
    private String beanName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;
    
    private String cronExpression;
    
    private String exceptionDetail;
    
    private Boolean isSuccess;
    
    private String jobName;
    
    private String methodName;
    
    private String params;
    
    private Long time;
}