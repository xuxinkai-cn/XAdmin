package cn.xuxinkai.cms.entity;

import java.io.Serializable;

/**
 * (CmsCategoryArticle)实体类
 *
 * @author makejava
 * @since 2021-03-25 21:45:57
 */
public class CmsCategoryArticle implements Serializable {
    private static final long serialVersionUID = -77076038978247176L;
    
    private Integer id;
    /**
    * 栏目id
    */
    private Integer categoryId;
    /**
    * 文章id
    */
    private Integer articleId;

    public CmsCategoryArticle() {
    }

    public CmsCategoryArticle(Integer articleId, Integer categoryId) {
        this.categoryId = categoryId;
        this.articleId = articleId;
    }

    public CmsCategoryArticle(Integer categoryId) {
        this.categoryId = categoryId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

}