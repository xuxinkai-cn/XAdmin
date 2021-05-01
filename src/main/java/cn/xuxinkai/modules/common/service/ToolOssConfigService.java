package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.ToolOssConfig;

import java.util.List;

/**
 * OSS配置表(ToolOssConfig)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 17:05:32
 */
public interface ToolOssConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param configId 主键
     * @return 实例对象
     */
    ToolOssConfig queryById(Long configId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ToolOssConfig> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param toolOssConfig 实例对象
     * @return 实例对象
     */
    ToolOssConfig insert(ToolOssConfig toolOssConfig);

    /**
     * 修改数据
     *
     * @param toolOssConfig 实例对象
     * @return 实例对象
     */
    ToolOssConfig update(ToolOssConfig toolOssConfig);

    /**
     * 通过主键删除数据
     *
     * @param configId 主键
     * @return 是否成功
     */
    boolean deleteById(Long configId);

}