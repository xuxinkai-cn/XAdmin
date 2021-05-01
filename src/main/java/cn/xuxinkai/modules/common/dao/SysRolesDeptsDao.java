package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.SysRolesDepts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色部门关联(SysRolesDepts)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Mapper
public interface SysRolesDeptsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    SysRolesDepts queryById(Long roleId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRolesDepts> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysRolesDepts 实例对象
     * @return 对象列表
     */
    List<SysRolesDepts> queryAll(SysRolesDepts sysRolesDepts);

    /**
     * 新增数据
     *
     * @param sysRolesDepts 实例对象
     * @return 影响行数
     */
    int insert(SysRolesDepts sysRolesDepts);

    /**
     * 修改数据
     *
     * @param sysRolesDepts 实例对象
     * @return 影响行数
     */
    int update(SysRolesDepts sysRolesDepts);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    int deleteById(Long roleId);

}