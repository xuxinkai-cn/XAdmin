package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.SysJob;

import java.util.List;
import java.util.Set;

/**
 * 岗位(SysJob)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:06
 */
public interface SysJobService {

    /**
     * 通过ID查询单条数据
     *
     * @param jobId 主键
     * @return 实例对象
     */
    SysJob queryById(Long jobId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysJob> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysJob 实例对象
     * @return 实例对象
     */
    SysJob insert(SysJob sysJob);

    /**
     * 修改数据
     *
     * @param sysJob 实例对象
     * @return 实例对象
     */
    SysJob update(SysJob sysJob);

    /**
     * 通过主键删除数据
     *
     * @param jobId 主键
     * @return 是否成功
     */
    boolean deleteById(Long jobId);

    /**
     * 通过userId获取用户岗位集合-联表查询
     *
     * @param userId 用户id
     * @return {@link Set<SysJob>}
     */
    Set<SysJob> getJobsByUserId(Long userId);
}