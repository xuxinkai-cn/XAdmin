package cn.xuxinkai.cms.entity;

import java.io.Serializable;

/**
 * (CmsTagArticle)实体类
 *
 * @author makejava
 * @since 2021-03-25 21:45:57
 */
public class CmsTagArticle implements Serializable {
    private static final long serialVersionUID = -77387206696926667L;
    
    private Integer id;
    /**
    * 文章id
    */
    private Integer articleId;
    /**
    * 标签id
    */
    private Integer tagId;

    public CmsTagArticle() {
    }

    public CmsTagArticle(Integer articleId, Integer tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }

    public CmsTagArticle(Integer tagId) {
        this.tagId = tagId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

}