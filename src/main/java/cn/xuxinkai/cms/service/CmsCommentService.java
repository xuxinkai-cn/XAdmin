package cn.xuxinkai.cms.service;

import cn.xuxinkai.cms.dto.CommentDto;
import cn.xuxinkai.cms.entity.CmsComment;
import cn.xuxinkai.modules.common.util.result.SysPageResult;

import java.util.List;
import java.util.Map;

/**
 * (CmsComment)表服务接口
 *
 * @author makejava
 * @since 2021-04-20 10:20:02
 */
public interface CmsCommentService {


    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    CmsComment queryById(Integer commentId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsComment> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param cmsComment 实例对象
     * @return 实例对象
     */
    CmsComment insert(CmsComment cmsComment);

    /**
     * 修改数据
     *
     * @param cmsComment 实例对象
     * @return 实例对象
     */
    CmsComment update(CmsComment cmsComment);

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer commentId);

    /**
     * 创建评论
     *
     * @param commentDto
     * @return {@link Boolean}
     */
    Boolean addComment(CommentDto commentDto);

    /**
     * 获取某篇文章下的评论数-包含待审核，已通过，未通过的，包含子评论
     *
     * @param articleId
     * @return {@link Integer}
     */
    Integer countByArticleId(Integer articleId);

    /**
     * 获取文章下的评论树
     *
     * @param articleId
     * @return {@link List<CommentDto>}
     */
    List<CommentDto> getCommentTreeByArticleId(Integer articleId);

    /**
     * 分页查询评论
     *
     * @param page
     * @param size
     * @param sort
     * @param field
     * @param status
     * @return {@link SysPageResult<CommentDto>}
     */
    SysPageResult<CommentDto> getAllComment(int page, int size, String sort, Map<String, Object> field,String status);

    /**
     * 获取评论数量
     *
     * @param fieldMap
     * @return {@link Long}
     */
    Long countCommentNum(Map<String, Object> fieldMap);

    /**
     * 通过文章id删除评论
     *
     * @param articleId
     */
    void deleteByArticleId(Integer articleId);
}