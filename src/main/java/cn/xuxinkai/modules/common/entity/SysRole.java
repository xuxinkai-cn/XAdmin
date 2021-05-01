package cn.xuxinkai.modules.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表(SysRole)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 740250183526137727L;
    /**
    * ID
    */
    private Long roleId;
    /**
    * 名称
    */
    private String name;
    /**
    * 角色级别
    */
    private Integer level;
    /**
    * 描述
    */
    private String description;
    /**
    * 数据权限
    */
    private String dataScope;
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