package cn.xuxinkai.cms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (CmsArticle)实体类
 *
 * @author makejava
 * @since 2021-04-12 14:46:54
 */
public class CmsArticle implements Serializable {
    private static final long serialVersionUID = 612080560497462135L;
    /**
    * 文章id
    */
    private Integer articleId;
    /**
    * 标题
    */
    private String title;
    /**
    * 封面图
    */
    private String coverUrl;
    /**
    * 摘要
    */
    private String summary;
    /**
    * 文章内容-markdown格式
    */
    private transient Object contentMarkdown;
    /**
    * 文章内容-html格式
    */
    private transient Object contentHtml;
    /**
    * 文章已审核通过评论数量
    */
    private Integer commentNum;
    /**
    * 文章浏览量
    */
    private Integer viewNum;
    /**
    * 状态：0 已发布，1草稿，2已关闭，3回收站
    */
    private Integer status;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 修改时间
    */
    private Date updateTime;
    /**
    * 创建人用户名
    */
    private String createBy;
    /**
    * 修改人用户名
    */
    private String updateBy;

    public CmsArticle() {
    }

    public CmsArticle(Integer articleId, Integer viewNum) {
        this.articleId =articleId;
        this.viewNum = viewNum;
    }


    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Object getContentMarkdown() {
        return contentMarkdown;
    }

    public void setContentMarkdown(Object contentMarkdown) {
        this.contentMarkdown = contentMarkdown;
    }

    public Object getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(Object contentHtml) {
        this.contentHtml = contentHtml;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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