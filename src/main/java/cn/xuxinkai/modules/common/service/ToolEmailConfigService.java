package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.ToolEmailConfig;

import java.util.List;

/**
 * 邮箱配置(ToolEmailConfig)表服务接口
 *
 * @author makejava
 * @since 2021-03-18 14:05:37
 */
public interface ToolEmailConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param configId 主键
     * @return 实例对象
     */
    ToolEmailConfig queryById(Long configId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ToolEmailConfig> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param toolEmailConfig 实例对象
     * @return 实例对象
     */
    ToolEmailConfig insert(ToolEmailConfig toolEmailConfig);

    /**
     * 修改数据
     *
     * @param toolEmailConfig 实例对象
     * @return 实例对象
     */
    ToolEmailConfig update(ToolEmailConfig toolEmailConfig);

    /**
     * 通过主键删除数据
     *
     * @param configId 主键
     * @return 是否成功
     */
    boolean deleteById(Long configId);

}