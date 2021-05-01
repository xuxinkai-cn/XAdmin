package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.SysUsersJobs;

import java.util.List;

/**
 * (SysUsersJobs)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
public interface SysUsersJobsService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUsersJobs queryById(Long userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUsersJobs> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysUsersJobs 实例对象
     * @return 实例对象
     */
    SysUsersJobs insert(SysUsersJobs sysUsersJobs);

    /**
     * 修改数据
     *
     * @param sysUsersJobs 实例对象
     * @return 实例对象
     */
    SysUsersJobs update(SysUsersJobs sysUsersJobs);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

}