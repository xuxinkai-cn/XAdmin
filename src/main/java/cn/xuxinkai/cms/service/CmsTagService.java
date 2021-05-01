package cn.xuxinkai.cms.service;

import cn.xuxinkai.cms.entity.CmsTag;
import cn.xuxinkai.modules.common.util.result.SysPageResult;

import java.util.List;

/**
 * (CmsTag)表服务接口
 *
 * @author makejava
 * @since 2021-04-05 19:45:45
 */
public interface CmsTagService {

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    CmsTag queryById(Integer tagId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsTag> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param cmsTag 实例对象
     * @return 实例对象
     */
    CmsTag insert(CmsTag cmsTag);

    /**
     * 修改数据
     *
     * @param cmsTag 实例对象
     * @return 实例对象
     */
    CmsTag update(CmsTag cmsTag);

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer tagId);

    /**
     * 添加标签
     *
     * @param cmsTag cms标签
     * @return {@link Boolean}
     */
    Boolean addTag(CmsTag cmsTag);

    /**
     * 分页获取标签
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult<CmsTag>}
     */
    SysPageResult<CmsTag> getAllTag(int page, int size, String sort);

    /**
     * 更新标签
     *
     * @param cmsTag cms标签
     * @return {@link Boolean}
     */
    Boolean updateTag(CmsTag cmsTag);

    /**
     * 获取所有标签列表
     *
     * @return {@link List<CmsTag>}
     */
    List<CmsTag> getTags();

    /**
     * 获取标签云
     *
     * @return {@link List<CmsTag>}
     */
    List<CmsTag> getTagCloud();

    /**
     * @param cmsTagQuery
     * @return {@link List<CmsTag>}
     */
    List<CmsTag> query(CmsTag cmsTagQuery);
}