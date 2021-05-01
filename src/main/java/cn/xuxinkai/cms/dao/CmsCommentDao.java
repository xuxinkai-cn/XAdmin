package cn.xuxinkai.cms.dao;

import cn.xuxinkai.cms.dto.CommentDto;
import cn.xuxinkai.cms.entity.CmsComment;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CmsComment)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-20 10:20:01
 */
@Mapper
public interface CmsCommentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    CmsComment queryById(Integer commentId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsComment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param cmsComment 实例对象
     * @return 对象列表
     */
    List<CmsComment> queryAll(CmsComment cmsComment);

    /**
     * 新增数据
     *
     * @param cmsComment 实例对象
     * @return 影响行数
     */
    int insert(CmsComment cmsComment);

    /**
     * 修改数据
     *
     * @param cmsComment 实例对象
     * @return 影响行数
     */
    int update(CmsComment cmsComment);

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 影响行数
     */
    int deleteById(Integer commentId);

    /**
     * 统计某篇文章下的评论数
     *
     * @param articleId
     * @return {@link Integer}
     */
    Integer countByArticleId(Integer articleId);

    /**
     * 分页查询评论
     *
     * @param pageUtil
     * @param status
     * @return {@link List<CommentDto>}
     */
    List<CommentDto> queryAllByPageUtil(SysPageQueryUtils pageUtil, @Param("status") String status);

    /**
     * 获取符合条件的评论总数
     *
     * @param field
     * @param status
     * @return {@link Long}
     */
    Long queryTotalByField(@Param("field") Map<String, Object> field, @Param("status") String status);

    /**
     * 通过文章id删除评论
     *
     * @param articleId
     */
    void deleteByArticleId(Integer articleId);
}