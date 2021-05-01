package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.SysUsersRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联(SysUsersRoles)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Mapper
public interface SysUsersRolesDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUsersRoles queryById(Long userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUsersRoles> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysUsersRoles 实例对象
     * @return 对象列表
     */
    List<SysUsersRoles> queryAll(SysUsersRoles sysUsersRoles);

    /**
     * 新增数据
     *
     * @param sysUsersRoles 实例对象
     * @return 影响行数
     */
    int insert(SysUsersRoles sysUsersRoles);

    /**
     * 修改数据
     *
     * @param sysUsersRoles 实例对象
     * @return 影响行数
     */
    int update(SysUsersRoles sysUsersRoles);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

}