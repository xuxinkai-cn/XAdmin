package cn.xuxinkai.cms.dao;

import cn.xuxinkai.cms.entity.CmsFriendlink;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CmsFriendlink)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-04 14:49:48
 */
@Mapper
public interface CmsFriendlinkDao {

    /**
     * 通过ID查询单条数据
     *
     * @param friendlinkId 主键
     * @return 实例对象
     */
    CmsFriendlink queryById(Integer friendlinkId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsFriendlink> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param cmsFriendlink 实例对象
     * @return 对象列表
     */
    List<CmsFriendlink> queryAll(CmsFriendlink cmsFriendlink);

    /**
     * 新增数据
     *
     * @param cmsFriendlink 实例对象
     * @return 影响行数
     */
    int insert(CmsFriendlink cmsFriendlink);

    /**
     * 修改数据
     *
     * @param cmsFriendlink 实例对象
     * @return 影响行数
     */
    int update(CmsFriendlink cmsFriendlink);

    /**
     * 通过主键删除数据
     *
     * @param friendlinkId 主键
     * @return 影响行数
     */
    int deleteById(Integer friendlinkId);

    /**
     * 分页查询友情链接
     *
     * @param pageUtil 页面跑龙套
     * @return {@link List<CmsFriendlink>}
     */
    List<CmsFriendlink> queryAllByPageUtil(SysPageQueryUtils pageUtil);

    /**
     * 获取总数
     *
     * @param fieldParams 场参数
     * @return {@link Long}
     */
    Long queryTotalByField(Map<String, Object> fieldParams);
}