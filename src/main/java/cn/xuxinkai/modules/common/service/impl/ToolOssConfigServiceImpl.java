package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.ToolOssConfigDao;
import cn.xuxinkai.modules.common.entity.ToolOssConfig;
import cn.xuxinkai.modules.common.service.ToolOssConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * OSS配置表(ToolOssConfig)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 17:05:32
 */
@Service("toolOssConfigService")
public class ToolOssConfigServiceImpl implements ToolOssConfigService {
    @Resource
    private ToolOssConfigDao toolOssConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param configId 主键
     * @return 实例对象
     */
    @Override
    public ToolOssConfig queryById(Long configId) {
        return this.toolOssConfigDao.queryById(configId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ToolOssConfig> queryAllByLimit(int offset, int limit) {
        return this.toolOssConfigDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param toolOssConfig 实例对象
     * @return 实例对象
     */
    @Override
    public ToolOssConfig insert(ToolOssConfig toolOssConfig) {
        this.toolOssConfigDao.insert(toolOssConfig);
        return toolOssConfig;
    }

    /**
     * 修改数据
     *
     * @param toolOssConfig 实例对象
     * @return 实例对象
     */
    @Override
    public ToolOssConfig update(ToolOssConfig toolOssConfig) {
        this.toolOssConfigDao.update(toolOssConfig);
        return this.queryById(toolOssConfig.getConfigId());
    }

    /**
     * 通过主键删除数据
     *
     * @param configId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long configId) {
        return this.toolOssConfigDao.deleteById(configId) > 0;
    }
}