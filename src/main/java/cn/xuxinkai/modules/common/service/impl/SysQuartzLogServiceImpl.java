package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysQuartzLogDao;
import cn.xuxinkai.modules.common.entity.SysQuartzLog;
import cn.xuxinkai.modules.common.service.SysQuartzLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务日志(SysQuartzLog)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Service("sysQuartzLogService")
public class SysQuartzLogServiceImpl implements SysQuartzLogService {
    @Resource
    private SysQuartzLogDao sysQuartzLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param logId 主键
     * @return 实例对象
     */
    @Override
    public SysQuartzLog queryById(Long logId) {
        return this.sysQuartzLogDao.queryById(logId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysQuartzLog> queryAllByLimit(int offset, int limit) {
        return this.sysQuartzLogDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysQuartzLog 实例对象
     * @return 实例对象
     */
    @Override
    public SysQuartzLog insert(SysQuartzLog sysQuartzLog) {
        this.sysQuartzLogDao.insert(sysQuartzLog);
        return sysQuartzLog;
    }

    /**
     * 修改数据
     *
     * @param sysQuartzLog 实例对象
     * @return 实例对象
     */
    @Override
    public SysQuartzLog update(SysQuartzLog sysQuartzLog) {
        this.sysQuartzLogDao.update(sysQuartzLog);
        return this.queryById(sysQuartzLog.getLogId());
    }

    /**
     * 通过主键删除数据
     *
     * @param logId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long logId) {
        return this.sysQuartzLogDao.deleteById(logId) > 0;
    }
}