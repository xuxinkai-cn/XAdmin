package cn.xuxinkai.modules.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * OSS配置表(ToolOssConfig)实体类
 *
 * @author makejava
 * @since 2021-04-08 17:05:32
 */
public class ToolOssConfig implements Serializable {
    private static final long serialVersionUID = -78292861047757861L;
    /**
    * ID
    */
    private Long configId;
    /**
    * oss服务商
    */
    private String ossType;
    /**
    * Bucket 识别符
    */
    private String bucket;
    /**
    * accessKey
    */
    private String accessKey;
    /**
    * secretKey
    */
    private String secretKey;
    /**
    * 外链域名
    */
    private String host;
    /**
    * 空间类型
    */
    private String type;
    /**
    * 机房
    */
    private String zone;
    /**
    * 创建人
    */
    private String createBy;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 修改人
    */
    private String updateBy;
    /**
    * 修改时间
    */
    private Date updateTime;


    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
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

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}