package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.SysRolesDepts;

import java.util.List;

/**
 * 角色部门关联(SysRolesDepts)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
public interface SysRolesDeptsService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    SysRolesDepts queryById(Long roleId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRolesDepts> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysRolesDepts 实例对象
     * @return 实例对象
     */
    SysRolesDepts insert(SysRolesDepts sysRolesDepts);

    /**
     * 修改数据
     *
     * @param sysRolesDepts 实例对象
     * @return 实例对象
     */
    SysRolesDepts update(SysRolesDepts sysRolesDepts);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    boolean deleteById(Long roleId);

}