package cn.xuxinkai.cms.dao;

import cn.xuxinkai.cms.entity.CmsCategory;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CmsCategory)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-05 16:38:39
 */
@Mapper
public interface CmsCategoryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param categoryId 主键
     * @return 实例对象
     */
    CmsCategory queryById(Integer categoryId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsCategory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param cmsCategory 实例对象
     * @return 对象列表
     */
    List<CmsCategory> queryAll(CmsCategory cmsCategory);

    /**
     * 新增数据
     *
     * @param cmsCategory 实例对象
     * @return 影响行数
     */
    int insert(CmsCategory cmsCategory);

    /**
     * 修改数据
     *
     * @param cmsCategory 实例对象
     * @return 影响行数
     */
    int update(CmsCategory cmsCategory);

    /**
     * 通过主键删除数据
     *
     * @param categoryId 主键
     * @return 影响行数
     */
    int deleteById(Integer categoryId);

    /**
     * 分页查询栏目
     *
     * @param pageUtil 页面跑龙套
     * @return {@link List<CmsCategory>}
     */
    List<CmsCategory> queryAllByPageUtil(SysPageQueryUtils pageUtil);

    /**
     * 获取总数目
     *
     * @param fieldParams 场参数
     * @return {@link Long}
     */
    Long queryTotalByField(Map<String, Object> fieldParams);

    /**
     * 获取栏目列表
     *
     * @return {@link List<CmsCategory>}
     */
    List<CmsCategory> queryCategoryList();
}