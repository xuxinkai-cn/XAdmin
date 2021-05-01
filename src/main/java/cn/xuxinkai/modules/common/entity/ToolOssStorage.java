package cn.xuxinkai.modules.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * OSS文件存储(ToolOssStorage)实体类
 *
 * @author makejava
 * @since 2021-04-08 17:05:32
 */
public class ToolOssStorage implements Serializable {
    private static final long serialVersionUID = -45761397575430345L;
    /**
    * ID
    */
    private Long storageId;
    
    private String ossType;
    /**
    * Bucket 识别符
    */
    private String bucket;
    /**
    * 文件真实名称
    */
    private String realName;
    /**
    * 文件名称
    */
    private String name;

    /**
    * 文件后缀
    */
    private String suffix;
    /**
    * 文件地址
    */
    private String path;
    /**
    * 文件类型：私有或公开
    */
    private String type;
    /**
    * 文件大小
    */
    private String size;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 创建人
    */
    private String createBy;
    /**
    * 上传或同步的时间
    */
    private Date updateTime;
    /**
    * 修改人
    */
    private String updateBy;

    public ToolOssStorage() {

    }

    public ToolOssStorage(String ossType, String bucket, String path, String type) {
        this.ossType = ossType;
        this.bucket = bucket;
        this.path = path;
        this.type = type;
    }


    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    public String getOssType() {
        return ossType;
    }

    public void setOssType(String ossType) {
        this.ossType = ossType;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

}