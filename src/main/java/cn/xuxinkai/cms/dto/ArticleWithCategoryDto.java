package cn.xuxinkai.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuxinkai
 */
@Data
public class ArticleWithCategoryDto implements Serializable {
    private static final long serialVersionUID = 6220199727508021914L;
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
}
