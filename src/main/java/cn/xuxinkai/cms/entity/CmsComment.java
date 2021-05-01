package cn.xuxinkai.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (CmsComment)实体类
 *
 * @author makejava
 * @since 2021-04-20 10:20:00
 */
public class CmsComment implements Serializable {
    private static final long serialVersionUID = -56480649488180909L;
    /**
    * 评论id
    */
    private Integer commentId;
    /**
    * 父级评论id，顶级评论为0
    */
    private Integer commentPid;
    /**
    * 文章id
    */
    private Integer articleId;
    /**
    * 评论人昵称
    */
    private String nickname;
    /**
    * 评论人邮箱
    */
    private String email;
    /**
    * 评论者主页地址
    */
    private String url;
    /**
    * 评论内容
    */
    private String commentContent;
    /**
    * 评论状态：0 审核通过，1待审核，2审核未通过；
    */
    private Integer status;
    /**
    * 评论创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;


    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentPid() {
        return commentPid;
    }

    public void setCommentPid(Integer commentPid) {
        this.commentPid = commentPid;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
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

}