package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysQuartzJobDao;
import cn.xuxinkai.modules.common.entity.SysQuartzJob;
import cn.xuxinkai.modules.common.service.SysQuartzJobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务(SysQuartzJob)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Service("sysQuartzJobService")
public class SysQuartzJobServiceImpl implements SysQuartzJobService {
    @Resource
    private SysQuartzJobDao sysQuartzJobDao;

    /**
     * 通过ID查询单条数据
     *
     * @param jobId 主键
     * @return 实例对象
     */
    @Override
    public SysQuartzJob queryById(Long jobId) {
        return this.sysQuartzJobDao.queryById(jobId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysQuartzJob> queryAllByLimit(int offset, int limit) {
        return this.sysQuartzJobDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysQuartzJob 实例对象
     * @return 实例对象
     */
    @Override
    public SysQuartzJob insert(SysQuartzJob sysQuartzJob) {
        this.sysQuartzJobDao.insert(sysQuartzJob);
        return sysQuartzJob;
    }

    /**
     * 修改数据
     *
     * @param sysQuartzJob 实例对象
     * @return 实例对象
     */
    @Override
    public SysQuartzJob update(SysQuartzJob sysQuartzJob) {
        this.sysQuartzJobDao.update(sysQuartzJob);
        return this.queryById(sysQuartzJob.getJobId());
    }

    /**
     * 通过主键删除数据
     *
     * @param jobId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long jobId) {
        return this.sysQuartzJobDao.deleteById(jobId) > 0;
    }
}