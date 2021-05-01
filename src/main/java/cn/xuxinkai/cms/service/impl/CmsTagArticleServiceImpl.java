package cn.xuxinkai.cms.service.impl;

import cn.xuxinkai.cms.dao.CmsTagArticleDao;
import cn.xuxinkai.cms.entity.CmsTagArticle;
import cn.xuxinkai.cms.service.CmsTagArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CmsTagArticle)表服务实现类
 *
 * @author makejava
 * @since 2021-03-25 21:45:57
 */
@Service("cmsTagArticleService")
public class CmsTagArticleServiceImpl implements CmsTagArticleService {
    @Resource
    private CmsTagArticleDao cmsTagArticleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CmsTagArticle queryById(Integer id) {
        return this.cmsTagArticleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<CmsTagArticle> queryAllByLimit(int offset, int limit) {
        return this.cmsTagArticleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param cmsTagArticle 实例对象
     * @return 实例对象
     */
    @Override
    public CmsTagArticle insert(CmsTagArticle cmsTagArticle) {
        this.cmsTagArticleDao.insert(cmsTagArticle);
        return cmsTagArticle;
    }

    /**
     * 修改数据
     *
     * @param cmsTagArticle 实例对象
     * @return 实例对象
     */
    @Override
    public CmsTagArticle update(CmsTagArticle cmsTagArticle) {
        this.cmsTagArticleDao.update(cmsTagArticle);
        return this.queryById(cmsTagArticle.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.cmsTagArticleDao.deleteById(id) > 0;
    }

    /**
     * 解除标签与文章绑定
     *
     * @param tagId     标签id
     * @param articleId
     */
    @Override
    public void unbindTagWithArticle(Integer tagId,Integer articleId) {
        cmsTagArticleDao.deleteByTagIdOrArticleId(tagId,articleId);
    }

    /**
     * 获取标签下文章数量
     *
     * @param tagId
     * @return int
     */
    @Override
    public int queryCount(Integer tagId) {
        return cmsTagArticleDao.queryAll(new CmsTagArticle(tagId)).size();
    }

    /**
     * 通过文章id查询
     *
     * @param articleId
     * @return {@link List<CmsTagArticle>}
     */
    @Override
    public List<CmsTagArticle> queryByArticleId(Integer articleId) {
        CmsTagArticle tagArticle = new CmsTagArticle();
        tagArticle.setArticleId(articleId);
        return cmsTagArticleDao.queryAll(tagArticle);
    }
}