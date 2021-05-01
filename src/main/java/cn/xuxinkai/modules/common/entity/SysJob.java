package cn.xuxinkai.modules.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 岗位(SysJob)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:06
 */
@Data
public class SysJob implements Serializable {
    private static final long serialVersionUID = -51988800038971601L;
    /**
    * ID
    */
    private Long jobId;
    /**
    * 岗位名称
    */
    private String name;
    /**
    * 岗位状态
    */
    private Boolean enabled;
    /**
    * 排序
    */
    private Integer jobSort;
    /**
    * 创建者
    */
    private String createBy;
    /**
    * 更新者
    */
    private String updateBy;
    /**
    * 创建日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

}