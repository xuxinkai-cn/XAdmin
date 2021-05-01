package cn.xuxinkai.cms.service;

import cn.xuxinkai.cms.dto.ArchiveDto;
import cn.xuxinkai.cms.dto.ArticleDto;
import cn.xuxinkai.cms.dto.ArticleWithCategoryDto;
import cn.xuxinkai.cms.entity.CmsArticle;
import cn.xuxinkai.modules.common.util.result.SysPageResult;

import java.util.List;
import java.util.Map;

/**
 * (CmsArticle)表服务接口
 *
 * @author makejava
 * @since 2021-03-26 18:02:59
 */
public interface CmsArticleService {

    /**
     * 通过ID查询单条数据
     *
     * @param articleId 主键
     * @return 实例对象
     */
    CmsArticle queryById(Integer articleId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CmsArticle> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param cmsArticle 实例对象
     * @return 实例对象
     */
    CmsArticle insert(CmsArticle cmsArticle);

    /**
     * 修改数据
     *
     * @param cmsArticle 实例对象
     * @return 实例对象
     */
    CmsArticle update(CmsArticle cmsArticle);

    /**
     * 通过主键删除数据
     *
     * @param articleId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer articleId);

    /**
     * 保存文章
     *
     * @param articleDto
     * @return {@link Boolean}
     */
    Boolean saveArticle(ArticleDto articleDto);

    /**
     * 获取文章列表
     *
     * @param page 第几页
     * @param size 梳理
     * @param sort 排序
     * @param field 限定字段
     * @return {@link SysPageResult<ArticleWithCategoryDto>}
     */
    SysPageResult<ArticleWithCategoryDto> getAllArticle(int page, int size, String sort, Map<String, Object> field);

    /**
     * 获取文章详情
     *
     * @param articleId
     * @return {@link ArticleDto}
     */
    ArticleDto getArticleInfo(Integer articleId);

    /**
     * 更新标签下文章数量
     *
     * @param tagId
     */
    void updateTagArticleNum(Integer tagId);

    /**
     * 更新栏目下文章数量
     *
     * @param categoryId
     */
    void updateCategoryArticleNum(Integer categoryId);

    /**
     * 获取当前文章的上一篇文章
     *
     * @param articleId
     * @return {@link CmsArticle}
     */
    CmsArticle getBeforeArticle(Integer articleId);

    /**
     * 获取当前文章的下一篇文章
     *
     * @param articleId
     * @return {@link CmsArticle}
     */
    CmsArticle getAfterArticle(Integer articleId);

    /**
     * 文章阅读量+1
     *
     * @param articleId
     */
    void updateArticleViewNum(Integer articleId,Integer newViewNum);

    /**
     * 更新文章下评论数
     *
     * @param articleId
     */
    void updateArticleCommentNum(Integer articleId);

    /**
     * 获取文章数量
     *
     * @param fieldMap
     * @return {@link Long}
     */
    Long countArticleNum(Map<String, Object> fieldMap);

    /**
     * 归档
     *
     * @return {@link Object}
     */
    List<ArchiveDto> getArchive();

    /**
     * 分页查询指定tag下的文章列表
     *
     * @param page
     * @param size
     * @param sort
     * @param field
     * @return {@link SysPageResult<ArticleWithCategoryDto>}
     */
    SysPageResult<ArticleWithCategoryDto> getAllArticleByTag(int page, int size, String sort, Map<String, Object> field);
}