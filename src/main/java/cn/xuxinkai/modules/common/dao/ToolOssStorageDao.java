package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.ToolOssStorage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OSS文件存储(ToolOssStorage)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-08 17:05:32
 */
@Mapper
public interface ToolOssStorageDao {

    /**
     * 通过ID查询单条数据
     *
     * @param storageId 主键
     * @return 实例对象
     */
    ToolOssStorage queryById(Long storageId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ToolOssStorage> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param toolOssStorage 实例对象
     * @return 对象列表
     */
    List<ToolOssStorage> queryAll(ToolOssStorage toolOssStorage);

    /**
     * 新增数据
     *
     * @param toolOssStorage 实例对象
     * @return 影响行数
     */
    int insert(ToolOssStorage toolOssStorage);

    /**
     * 修改数据
     *
     * @param toolOssStorage 实例对象
     * @return 影响行数
     */
    int update(ToolOssStorage toolOssStorage);

    /**
     * 通过主键删除数据
     *
     * @param storageId 主键
     * @return 影响行数
     */
    int deleteById(Long storageId);

}