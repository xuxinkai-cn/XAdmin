package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.ToolEmailConfigDao;
import cn.xuxinkai.modules.common.entity.ToolEmailConfig;
import cn.xuxinkai.modules.common.service.ToolEmailConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 邮箱配置(ToolEmailConfig)表服务实现类
 *
 * @author makejava
 * @since 2021-03-18 14:05:37
 */
@Service("toolEmailConfigService")
public class ToolEmailConfigServiceImpl implements ToolEmailConfigService {
    @Resource
    private ToolEmailConfigDao toolEmailConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param configId 主键
     * @return 实例对象
     */
    @Override
    public ToolEmailConfig queryById(Long configId) {
        return this.toolEmailConfigDao.queryById(configId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ToolEmailConfig> queryAllByLimit(int offset, int limit) {
        return this.toolEmailConfigDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param toolEmailConfig 实例对象
     * @return 实例对象
     */
    @Override
    public ToolEmailConfig insert(ToolEmailConfig toolEmailConfig) {
        this.toolEmailConfigDao.insert(toolEmailConfig);
        return toolEmailConfig;
    }

    /**
     * 修改数据
     *
     * @param toolEmailConfig 实例对象
     * @return 实例对象
     */
    @Override
    public ToolEmailConfig update(ToolEmailConfig toolEmailConfig) {
        this.toolEmailConfigDao.update(toolEmailConfig);
        return this.queryById(toolEmailConfig.getConfigId());
    }

    /**
     * 通过主键删除数据
     *
     * @param configId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long configId) {
        return this.toolEmailConfigDao.deleteById(configId) > 0;
    }
}