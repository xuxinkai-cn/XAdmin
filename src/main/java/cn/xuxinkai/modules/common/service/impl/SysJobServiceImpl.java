package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysJobDao;
import cn.xuxinkai.modules.common.entity.SysJob;
import cn.xuxinkai.modules.common.service.SysJobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 岗位(SysJob)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:06
 */
@Service("sysJobService")
public class SysJobServiceImpl implements SysJobService {
    @Resource
    private SysJobDao sysJobDao;

    /**
     * 通过ID查询单条数据
     *
     * @param jobId 主键
     * @return 实例对象
     */
    @Override
    public SysJob queryById(Long jobId) {
        return this.sysJobDao.queryById(jobId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysJob> queryAllByLimit(int offset, int limit) {
        return this.sysJobDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysJob 实例对象
     * @return 实例对象
     */
    @Override
    public SysJob insert(SysJob sysJob) {
        this.sysJobDao.insert(sysJob);
        return sysJob;
    }

    /**
     * 修改数据
     *
     * @param sysJob 实例对象
     * @return 实例对象
     */
    @Override
    public SysJob update(SysJob sysJob) {
        this.sysJobDao.update(sysJob);
        return this.queryById(sysJob.getJobId());
    }

    /**
     * 通过主键删除数据
     *
     * @param jobId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long jobId) {
        return this.sysJobDao.deleteById(jobId) > 0;
    }

    /**
     * 通过userId获取用户岗位集合-联表查询
     *
     * @param userId 用户id
     * @return {@link Set <SysJob>}
     */
    @Override
    public Set<SysJob> getJobsByUserId(Long userId) {
        return sysJobDao.queryByUserId(userId);
    }
}