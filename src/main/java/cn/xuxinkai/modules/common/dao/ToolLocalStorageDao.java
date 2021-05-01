package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.ToolLocalStorage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 本地存储(ToolLocalStorage)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-07 22:10:42
 */
@Mapper
public interface ToolLocalStorageDao {

    /**
     * 通过ID查询单条数据
     *
     * @param storageId 主键
     * @return 实例对象
     */
    ToolLocalStorage queryById(Long storageId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ToolLocalStorage> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param toolLocalStorage 实例对象
     * @return 对象列表
     */
    List<ToolLocalStorage> queryAll(ToolLocalStorage toolLocalStorage);

    /**
     * 新增数据
     *
     * @param toolLocalStorage 实例对象
     * @return 影响行数
     */
    int insert(ToolLocalStorage toolLocalStorage);

    /**
     * 修改数据
     *
     * @param toolLocalStorage 实例对象
     * @return 影响行数
     */
    int update(ToolLocalStorage toolLocalStorage);

    /**
     * 通过主键删除数据
     *
     * @param storageId 主键
     * @return 影响行数
     */
    int deleteById(Long storageId);
}