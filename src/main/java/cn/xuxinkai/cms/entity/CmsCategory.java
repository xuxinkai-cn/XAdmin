package cn.xuxinkai.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (CmsCategory)实体类
 *
 * @author makejava
 * @since 2021-04-05 16:38:38
 */
public class CmsCategory implements Serializable {
    private static final long serialVersionUID = 990964199206995141L;
    /**
    * 栏目ID
    */
    private Integer categoryId;
    /**
    * 栏目名称
    */
    private String categoryName;
    /**
    * 栏目别名
    */
    private String categorySlug;
    /**
    * 栏目描述
    */
    private String categoryDes;
    /**
    * 该栏目下已发布文章数量
    */
    private Integer articleNum;
    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;
    /**
    * 修改时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date updateTime;
    /**
    * 创建人用户名
    */
    private String createBy;
    /**
    * 修改人用户名
    */
    private String updateBy;

    public CmsCategory() {
    }

    public CmsCategory(Integer categoryId, int articleNum) {
        this.categoryId = categoryId;
        this.articleNum = articleNum;
    }

    @Override
    public String toString() {
        return "CmsCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categorySlug='" + categorySlug + '\'' +
                ", categoryDes='" + categoryDes + '\'' +
                ", articleNum=" + articleNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public String getCategoryDes() {
        return categoryDes;
    }

    public void setCategoryDes(String categoryDes) {
        this.categoryDes = categoryDes;
    }

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
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

}