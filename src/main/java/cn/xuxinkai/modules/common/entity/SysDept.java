package cn.xuxinkai.modules.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门(SysDept)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:50:58
 */
@Data
public class SysDept implements Serializable {
    private static final long serialVersionUID = -18065474628082198L;
    /**
    * ID
    */
    private Long deptId;
    /**
    * 上级部门
    */
    private Long pid;
    /**
    * 子部门数目
    */
    private Integer subCount;
    /**
    * 名称
    */
    private String name;
    /**
    * 排序
    */
    private Integer deptSort;
    /**
    * 状态
    */
    private Boolean enabled;
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