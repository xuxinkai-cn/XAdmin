package cn.xuxinkai.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (CmsTag)实体类
 *
 * @author makejava
 * @since 2021-04-05 19:45:43
 */
public class CmsTag implements Serializable {
    private static final long serialVersionUID = -75897461243100455L;
    /**
    * 标签id
    */
    private Integer tagId;
    /**
    * 标签名称
    */
    private String tagName;
    /**
    * 标签别名
    */
    private String tagSlug;
    /**
    * 标签描述
    */
    private String tagDes;
    /**
    * 标签下文章数量
    */
    private Integer articleNum;
    /**
    * 标签创建人
    */
    private String createBy;
    /**
    * 更新人
    */
    private String updateBy;
    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;
    /**
    * 更新时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date updateTime;

    public CmsTag() {
    }

    public CmsTag(Integer tagId, int articleNum) {
        this.tagId = tagId;
        this.articleNum = articleNum;
    }


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagSlug() {
        return tagSlug;
    }

    public void setTagSlug(String tagSlug) {
        this.tagSlug = tagSlug;
    }

    public String getTagDes() {
        return tagDes;
    }

    public void setTagDes(String tagDes) {
        this.tagDes = tagDes;
    }

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
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

    @Override
    public String toString() {
        return "CmsTag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", tagSlug='" + tagSlug + '\'' +
                ", tagDes='" + tagDes + '\'' +
                ", articleNum=" + articleNum +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}