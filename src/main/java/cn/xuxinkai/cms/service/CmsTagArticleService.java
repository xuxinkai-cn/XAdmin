package cn.xuxinkai.cms.service;

import cn.xuxinkai.cms.entity.CmsTagArticle;

import java.util.List;

/**
 * (CmsTagArticle)表服务接口
 *
 * @author makejava
 * @since 2021-03-25 21:45:57
 */
public interface CmsTagArticleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CmsTagArticle queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsTagArticle> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param cmsTagArticle 实例对象
     * @return 实例对象
     */
    CmsTagArticle insert(CmsTagArticle cmsTagArticle);

    /**
     * 修改数据
     *
     * @param cmsTagArticle 实例对象
     * @return 实例对象
     */
    CmsTagArticle update(CmsTagArticle cmsTagArticle);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 解除标签与文章绑定
     *
     * @param tagId 标签id
     */
    void unbindTagWithArticle(Integer tagId,Integer articleId);

    /**
     * 获取标签下文章数量
     *
     * @param tagId
     * @return int
     */
    int queryCount(Integer tagId);

    /**
     * 通过文章id查询
     *
     * @param articleId
     * @return {@link List<CmsTagArticle>}
     */
    List<CmsTagArticle> queryByArticleId(Integer articleId);
}