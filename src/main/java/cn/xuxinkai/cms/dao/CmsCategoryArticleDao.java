package cn.xuxinkai.cms.dao;

import cn.xuxinkai.cms.entity.CmsCategoryArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CmsCategoryArticle)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-25 21:45:57
 */
@Mapper
public interface CmsCategoryArticleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CmsCategoryArticle queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsCategoryArticle> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param cmsCategoryArticle 实例对象
     * @return 对象列表
     */
    List<CmsCategoryArticle> queryAll(CmsCategoryArticle cmsCategoryArticle);

    /**
     * 新增数据
     *
     * @param cmsCategoryArticle 实例对象
     * @return 影响行数
     */
    int insert(CmsCategoryArticle cmsCategoryArticle);

    /**
     * 修改数据
     *
     * @param cmsCategoryArticle 实例对象
     * @return 影响行数
     */
    int update(CmsCategoryArticle cmsCategoryArticle);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 按类别id删除
     *
     * @param categoryId 类别id
     */
    void deleteByCategoryId(Integer categoryId);

    /**
     * 删除关联
     *
     * @param categoryId
     * @param articleId
     */
    void deleteByCategoryIdOrArticleId(Integer categoryId, Integer articleId);
}