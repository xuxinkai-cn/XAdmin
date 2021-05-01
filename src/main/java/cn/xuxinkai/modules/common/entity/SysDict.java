package cn.xuxinkai.modules.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典(SysDict)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:01
 */
@Data
public class SysDict implements Serializable {
    private static final long serialVersionUID = -15738171428924483L;
    /**
    * ID
    */
    private Long dictId;
    /**
    * 字典名称
    */
    private String name;
    /**
    * 描述
    */
    private String description;
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
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;


}