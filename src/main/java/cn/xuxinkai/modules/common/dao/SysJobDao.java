package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.SysJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 岗位(SysJob)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-03 15:51:06
 */
@Mapper
public interface SysJobDao {

    /**
     * 通过ID查询单条数据
     *
     * @param jobId 主键
     * @return 实例对象
     */
    SysJob queryById(Long jobId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysJob> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysJob 实例对象
     * @return 对象列表
     */
    List<SysJob> queryAll(SysJob sysJob);

    /**
     * 新增数据
     *
     * @param sysJob 实例对象
     * @return 影响行数
     */
    int insert(SysJob sysJob);

    /**
     * 修改数据
     *
     * @param sysJob 实例对象
     * @return 影响行数
     */
    int update(SysJob sysJob);

    /**
     * 通过主键删除数据
     *
     * @param jobId 主键
     * @return 影响行数
     */
    int deleteById(Long jobId);

    /**
     * 通过userId获取用户岗位集合-联表查询
     *
     * @param userId 用户id
     * @return {@link Set<SysJob>}
     */
    Set<SysJob> queryByUserId(Long userId);
}