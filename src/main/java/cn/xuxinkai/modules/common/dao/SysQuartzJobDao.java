package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.SysQuartzJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务(SysQuartzJob)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Mapper
public interface SysQuartzJobDao {

    /**
     * 通过ID查询单条数据
     *
     * @param jobId 主键
     * @return 实例对象
     */
    SysQuartzJob queryById(Long jobId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysQuartzJob> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysQuartzJob 实例对象
     * @return 对象列表
     */
    List<SysQuartzJob> queryAll(SysQuartzJob sysQuartzJob);

    /**
     * 新增数据
     *
     * @param sysQuartzJob 实例对象
     * @return 影响行数
     */
    int insert(SysQuartzJob sysQuartzJob);

    /**
     * 修改数据
     *
     * @param sysQuartzJob 实例对象
     * @return 影响行数
     */
    int update(SysQuartzJob sysQuartzJob);

    /**
     * 通过主键删除数据
     *
     * @param jobId 主键
     * @return 影响行数
     */
    int deleteById(Long jobId);

}