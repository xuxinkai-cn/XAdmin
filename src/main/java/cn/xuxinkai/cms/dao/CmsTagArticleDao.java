package cn.xuxinkai.cms.dao;

import cn.xuxinkai.cms.entity.CmsTagArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CmsTagArticle)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-25 21:45:57
 */
@Mapper
public interface CmsTagArticleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CmsTagArticle queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsTagArticle> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param cmsTagArticle 实例对象
     * @return 对象列表
     */
    List<CmsTagArticle> queryAll(CmsTagArticle cmsTagArticle);

    /**
     * 新增数据
     *
     * @param cmsTagArticle 实例对象
     * @return 影响行数
     */
    int insert(CmsTagArticle cmsTagArticle);

    /**
     * 修改数据
     *
     * @param cmsTagArticle 实例对象
     * @return 影响行数
     */
    int update(CmsTagArticle cmsTagArticle);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 按标签id删除
     *
     * @param tagId 标签id
     */
    void deleteByTagId(Integer tagId);

    /**
     * 解除关联
     *
     * @param tagId
     * @param articleId
     */
    void deleteByTagIdOrArticleId(Integer tagId, Integer articleId);
}