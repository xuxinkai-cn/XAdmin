package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysUsersJobsDao;
import cn.xuxinkai.modules.common.entity.SysUsersJobs;
import cn.xuxinkai.modules.common.service.SysUsersJobsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysUsersJobs)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Service("sysUsersJobsService")
public class SysUsersJobsServiceImpl implements SysUsersJobsService {
    @Resource
    private SysUsersJobsDao sysUsersJobsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public SysUsersJobs queryById(Long userId) {
        return this.sysUsersJobsDao.queryById(userId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUsersJobs> queryAllByLimit(int offset, int limit) {
        return this.sysUsersJobsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysUsersJobs 实例对象
     * @return 实例对象
     */
    @Override
    public SysUsersJobs insert(SysUsersJobs sysUsersJobs) {
        this.sysUsersJobsDao.insert(sysUsersJobs);
        return sysUsersJobs;
    }

    /**
     * 修改数据
     *
     * @param sysUsersJobs 实例对象
     * @return 实例对象
     */
    @Override
    public SysUsersJobs update(SysUsersJobs sysUsersJobs) {
        this.sysUsersJobsDao.update(sysUsersJobs);
        return this.queryById(sysUsersJobs.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return this.sysUsersJobsDao.deleteById(userId) > 0;
    }
}