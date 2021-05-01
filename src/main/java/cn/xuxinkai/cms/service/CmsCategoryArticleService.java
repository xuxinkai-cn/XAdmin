package cn.xuxinkai.cms.service;

import cn.xuxinkai.cms.entity.CmsCategoryArticle;

import java.util.List;

/**
 * (CmsCategoryArticle)表服务接口
 *
 * @author makejava
 * @since 2021-03-25 21:45:57
 */
public interface CmsCategoryArticleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CmsCategoryArticle queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsCategoryArticle> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param cmsCategoryArticle 实例对象
     * @return 实例对象
     */
    CmsCategoryArticle insert(CmsCategoryArticle cmsCategoryArticle);

    /**
     * 修改数据
     *
     * @param cmsCategoryArticle 实例对象
     * @return 实例对象
     */
    CmsCategoryArticle update(CmsCategoryArticle cmsCategoryArticle);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 按类别id删除
     *
     * @param categoryId 类别id
     */
    void deleteByCategoryId(Integer categoryId);

    /**
     * 解除文章与栏目绑定
     *
     * @param categoryId 类别id
     * @param articleId
     */
    void unbindCategoryWithArticle(Integer categoryId,Integer articleId);

    /**
     * 获取指定栏目下文章数
     *
     * @param categoryId
     * @return int
     */
    int queryCount(Integer categoryId);

    /**
     * 通过文章id查询
     *
     * @param articleId
     * @return {@link CmsCategoryArticle}
     */
    CmsCategoryArticle queryByArticleId(Integer articleId);
}