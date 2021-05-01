package cn.xuxinkai.cms.dto;

import cn.xuxinkai.cms.entity.CmsCategory;
import cn.xuxinkai.cms.entity.CmsTag;
import cn.xuxinkai.modules.common.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xuxinkai
 */
@Data
public class ArticleDto implements Serializable {
    private static final long serialVersionUID = -4074793770216294778L;

    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 标题
     */
    @NotNull(message = "标题不能为空")
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
    @NotNull(message = "文章内容不能为空")
    private transient Object contentMarkdown;
    /**
     * 文章内容-html格式
     */
    @NotNull(message = "文章内容不能为空")
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
     * 栏目-目前一篇文章只能属于一个栏目
     */
    private CmsCategory category;


    /**
     * 文章作者信息
     */
    private UserDto author;


    /**
     * 关联的标签ID列表
     */
    private List<Integer> tagIds;

    /**
     * 文章标签
     */
    private List<CmsTag> cmsTagList;

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
