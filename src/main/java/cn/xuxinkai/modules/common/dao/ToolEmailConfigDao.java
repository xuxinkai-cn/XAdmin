package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.ToolEmailConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 邮箱配置(ToolEmailConfig)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-18 14:05:37
 */
@Mapper
public interface ToolEmailConfigDao {

    /**
     * 通过ID查询单条数据
     *
     * @param configId 主键
     * @return 实例对象
     */
    ToolEmailConfig queryById(Long configId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ToolEmailConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param toolEmailConfig 实例对象
     * @return 对象列表
     */
    List<ToolEmailConfig> queryAll(ToolEmailConfig toolEmailConfig);

    /**
     * 新增数据
     *
     * @param toolEmailConfig 实例对象
     * @return 影响行数
     */
    int insert(ToolEmailConfig toolEmailConfig);

    /**
     * 修改数据
     *
     * @param toolEmailConfig 实例对象
     * @return 影响行数
     */
    int update(ToolEmailConfig toolEmailConfig);

    /**
     * 通过主键删除数据
     *
     * @param configId 主键
     * @return 影响行数
     */
    int deleteById(Long configId);

}