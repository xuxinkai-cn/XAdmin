package cn.xuxinkai.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (CmsFriendlink)实体类
 *
 * @author makejava
 * @since 2021-04-04 14:49:45
 */
public class CmsFriendlink implements Serializable {
    private static final long serialVersionUID = 647633176502564020L;
    /**
    * 友情链接id
    */
    private Integer friendlinkId;
    /**
    * 友链名称
    */
    private String friendlinkName;
    /**
    * 链接地址
    */
    private String friendlinkUrl;
    /**
    * 描述
    */
    private String friendlinkDes;
    /**
    * 状态：0审核通过，1待审核，2审核未通过；
    */
    private Integer status;
    
    private String createBy;
    
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date updateTime;


    public Integer getFriendlinkId() {
        return friendlinkId;
    }

    public void setFriendlinkId(Integer friendlinkId) {
        this.friendlinkId = friendlinkId;
    }

    public String getFriendlinkName() {
        return friendlinkName;
    }

    public void setFriendlinkName(String friendlinkName) {
        this.friendlinkName = friendlinkName;
    }

    public String getFriendlinkUrl() {
        return friendlinkUrl;
    }

    public void setFriendlinkUrl(String friendlinkUrl) {
        this.friendlinkUrl = friendlinkUrl;
    }

    public String getFriendlinkDes() {
        return friendlinkDes;
    }

    public void setFriendlinkDes(String friendlinkDes) {
        this.friendlinkDes = friendlinkDes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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