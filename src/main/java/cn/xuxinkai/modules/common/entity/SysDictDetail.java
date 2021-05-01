package cn.xuxinkai.modules.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典详情(SysDictDetail)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:06
 */
@Data
public class SysDictDetail implements Serializable {
    private static final long serialVersionUID = 427124259259124181L;
    /**
    * ID
    */
    private Long detailId;
    /**
    * 字典id
    */
    private Long dictId;
    /**
    * 字典标签
    */
    private String label;
    /**
    * 字典值
    */
    private String value;
    /**
    * 排序
    */
    private Integer dictSort;
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