package cn.xuxinkai.cms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.dao.CmsCategoryArticleDao;
import cn.xuxinkai.cms.entity.CmsCategoryArticle;
import cn.xuxinkai.cms.service.CmsCategoryArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CmsCategoryArticle)表服务实现类
 *
 * @author makejava
 * @since 2021-03-25 21:45:57
 */
@Service("cmsCategoryArticleService")
public class CmsCategoryArticleServiceImpl implements CmsCategoryArticleService {
    @Resource
    private CmsCategoryArticleDao cmsCategoryArticleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CmsCategoryArticle queryById(Integer id) {
        return this.cmsCategoryArticleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<CmsCategoryArticle> queryAllByLimit(int offset, int limit) {
        return this.cmsCategoryArticleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param cmsCategoryArticle 实例对象
     * @return 实例对象
     */
    @Override
    public CmsCategoryArticle insert(CmsCategoryArticle cmsCategoryArticle) {
        this.cmsCategoryArticleDao.insert(cmsCategoryArticle);
        return cmsCategoryArticle;
    }

    /**
     * 修改数据
     *
     * @param cmsCategoryArticle 实例对象
     * @return 实例对象
     */
    @Override
    public CmsCategoryArticle update(CmsCategoryArticle cmsCategoryArticle) {
        this.cmsCategoryArticleDao.update(cmsCategoryArticle);
        return this.queryById(cmsCategoryArticle.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.cmsCategoryArticleDao.deleteById(id) > 0;
    }

    /**
     * 按类别id删除
     *
     * @param categoryId 类别id
     */
    @Override
    public void deleteByCategoryId(Integer categoryId) {
        cmsCategoryArticleDao.deleteByCategoryId(categoryId);
    }

    /**
     * 解除文章与栏目绑定
     *
     * @param categoryId 类别id
     */
    @Override
    public void unbindCategoryWithArticle(Integer categoryId,Integer articleId) {
        cmsCategoryArticleDao.deleteByCategoryIdOrArticleId(categoryId,articleId);
    }

    /**
     * 获取指定栏目下文章数
     *
     * @param categoryId
     * @return int
     */
    @Override
    public int queryCount(Integer categoryId) {
        return cmsCategoryArticleDao.queryAll(new CmsCategoryArticle(categoryId)).size();
    }

    /**
     * 通过文章id查询
     *
     * @param articleId
     * @return {@link CmsCategoryArticle}
     */
    @Override
    public CmsCategoryArticle queryByArticleId(Integer articleId) {
        CmsCategoryArticle categoryArticle = new CmsCategoryArticle();
        categoryArticle.setArticleId(articleId);
        List<CmsCategoryArticle> cmsCategoryArticleList =  cmsCategoryArticleDao.queryAll(categoryArticle);
        if(ObjectUtil.isNotEmpty(cmsCategoryArticleList)){
            return cmsCategoryArticleList.get(0);
        }
        return null;
    }
}