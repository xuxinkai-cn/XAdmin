package cn.xuxinkai.cms.dao;

import cn.xuxinkai.cms.entity.CmsTag;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CmsTag)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-05 19:45:44
 */
@Mapper
public interface CmsTagDao {

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    CmsTag queryById(Integer tagId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsTag> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param cmsTag 实例对象
     * @return 对象列表
     */
    List<CmsTag> queryAll(CmsTag cmsTag);

    /**
     * 新增数据
     *
     * @param cmsTag 实例对象
     * @return 影响行数
     */
    int insert(CmsTag cmsTag);

    /**
     * 修改数据
     *
     * @param cmsTag 实例对象
     * @return 影响行数
     */
    int update(CmsTag cmsTag);

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 影响行数
     */
    int deleteById(Integer tagId);

    /**
     * 分页查询标签
     *
     * @param pageUtil 页面跑龙套
     * @return {@link List<CmsTag>}
     */
    List<CmsTag> queryAllByPageUtil(SysPageQueryUtils pageUtil);

    /**
     * 查询总数
     *
     * @param o o
     * @return {@link Long}
     */
    Long queryTotalByField(Object o);

    /**
     * 查询文章数大于0的标签列表
     *
     * @return {@link List<CmsTag>}
     */
    List<CmsTag> queryTagCloud();
}