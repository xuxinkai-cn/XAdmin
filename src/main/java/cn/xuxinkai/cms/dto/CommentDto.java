package cn.xuxinkai.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xuxinkai
 */
@Data
public class CommentDto implements Serializable {
    private static final long serialVersionUID = 1444983206480553927L;

    /**
     * 评论id
     */
    private Integer commentId;

    /**
     * 父级评论id，顶级评论为0
     */
    private Integer commentPid;

    /**
     * 被回复昵称
     */
    private String pnickname;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章id
     */
    @NotNull(message = "文章id不能为空")
    private Integer articleId;
    /**
     * 评论人昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    /**
     * 评论人邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;
    /**
     * 评论者主页地址
     */
    private String url;
    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
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

    private List<CommentDto> commentChildren;

}
