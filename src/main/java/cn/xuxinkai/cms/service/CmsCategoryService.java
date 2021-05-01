package cn.xuxinkai.cms.service;

import cn.xuxinkai.cms.entity.CmsCategory;
import cn.xuxinkai.modules.common.util.result.SysPageResult;

import java.util.List;

/**
 * (CmsCategory)表服务接口
 *
 * @author makejava
 * @since 2021-04-05 16:38:40
 */
public interface CmsCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param categoryId 主键
     * @return 实例对象
     */
    CmsCategory queryById(Integer categoryId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsCategory> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param cmsCategory 实例对象
     * @return 实例对象
     */
    CmsCategory insert(CmsCategory cmsCategory);

    /**
     * 修改数据
     *
     * @param cmsCategory 实例对象
     * @return 实例对象
     */
    CmsCategory update(CmsCategory cmsCategory);

    /**
     * 通过主键删除数据
     *
     * @param categoryId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer categoryId);

    /**
     * 新增栏目
     *
     * @param cmsCategory cms的类别
     * @return {@link Boolean}
     */
    Boolean addCategory(CmsCategory cmsCategory);

    /**
     * 分页获取所有的栏目
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult<CmsCategory>}
     */
    SysPageResult<CmsCategory> getAllCategory(int page, int size, String sort);

    /**
     * 更新栏目信息
     *
     * @param cmsCategory cms的类别
     * @return {@link Boolean}
     */
    Boolean updateCategory(CmsCategory cmsCategory);

    /**
     * 获取栏目列表
     *
     * @return {@link List<CmsCategory>}
     */
    List<CmsCategory> getCategoryList();

    /**
     * @param cmsCategoryQuery
     * @return {@link List<CmsCategory>}
     */
    List<CmsCategory> query(CmsCategory cmsCategoryQuery);
}