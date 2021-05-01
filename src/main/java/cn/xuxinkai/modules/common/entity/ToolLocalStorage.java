package cn.xuxinkai.modules.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 本地存储(ToolLocalStorage)实体类
 *
 * @author makejava
 * @since 2021-04-07 22:10:42
 */
public class ToolLocalStorage implements Serializable {
    private static final long serialVersionUID = -30746736314071309L;
    /**
    * ID
    */
    private Long storageId;
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

    public ToolLocalStorage(String realName, String name, String suffix, String path, String type, String size) {
        this.realName = realName;
        this.name = name;
        this.suffix = suffix;
        this.path = path;
        this.type = type;
        this.size = size;
    }


    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}