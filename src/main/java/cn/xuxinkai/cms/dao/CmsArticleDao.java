package cn.xuxinkai.cms.dao;

import cn.xuxinkai.cms.dto.ArchiveDto;
import cn.xuxinkai.cms.dto.ArticleWithCategoryDto;
import cn.xuxinkai.cms.entity.CmsArticle;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CmsArticle)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-12 14:46:55
 */
@Mapper
public interface CmsArticleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param articleId 主键
     * @return 实例对象
     */
    CmsArticle queryById(Integer articleId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsArticle> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param cmsArticle 实例对象
     * @return 对象列表
     */
    List<CmsArticle> queryAll(CmsArticle cmsArticle);

    /**
     * 新增数据
     *
     * @param cmsArticle 实例对象
     * @return 影响行数
     */
    int insert(CmsArticle cmsArticle);

    /**
     * 修改数据
     *
     * @param cmsArticle 实例对象
     * @return 影响行数
     */
    int update(CmsArticle cmsArticle);

    /**
     * 通过主键删除数据
     *
     * @param articleId 主键
     * @return 影响行数
     */
    int deleteById(Integer articleId);

    /**
     * 分页联表查询文章列表
     *
     * @param pageUtil
     * @return {@link List<ArticleWithCategoryDto>}
     */
    List<ArticleWithCategoryDto> queryAllByPageUtil(SysPageQueryUtils pageUtil);

    /**
     * 获取总数
     *
     * @param fieldParams
     * @return {@link Long}
     */
    Long queryTotalByField(Map<String, Object> fieldParams);

    /**
     * 获取当前文章的上一篇文章
     *
     * @param articleId
     * @return {@link CmsArticle}
     */
    CmsArticle queryBeforeArticle(Integer articleId);

    /**
     * 获取当前文章的下一篇文章
     *
     * @param articleId
     * @return {@link CmsArticle}
     */
    CmsArticle queryAfterArticle(Integer articleId);

    /**
     * 获取year month
     *
     * @return {@link List<ArchiveDto>}
     */
    List<ArchiveDto> queryYearAndMonth();

    /**
     * 获取指定日期文章
     *
     * @param year
     * @param month
     * @return {@link List<CmsArticle>}
     */
    List<CmsArticle> queryArticleByYearAndMonth(@Param("year") String year, @Param("month") String month);

    /**
     * @param pageUtil
     * @return {@link List<ArticleWithCategoryDto>}
     */
    List<ArticleWithCategoryDto> queryAllByPageUtilAndTag(SysPageQueryUtils pageUtil);

    /**
     * @param field
     * @return {@link Long}
     */
    Long queryTotalByFieldAndTag(Map<String, Object> field);
}