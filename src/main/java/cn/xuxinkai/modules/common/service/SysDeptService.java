package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.dto.UserDto;
import cn.xuxinkai.modules.common.entity.SysDept;

import java.util.List;

/**
 * 部门(SysDept)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:00
 */
public interface SysDeptService {

    /**
     * 通过ID查询单条数据
     *
     * @param deptId 主键
     * @return 实例对象
     */
    SysDept queryById(Long deptId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDept> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysDept 实例对象
     * @return 实例对象
     */
    SysDept insert(SysDept sysDept);

    /**
     * 修改数据
     *
     * @param sysDept 实例对象
     * @return 实例对象
     */
    SysDept update(SysDept sysDept);

    /**
     * 通过主键删除数据
     *
     * @param deptId 主键
     * @return 是否成功
     */
    boolean deleteById(Long deptId);

    /**
     * 获取部门id-数据权限-dataScopes
     *
     * @param user 用户
     * @return {@link List<Long>}
     */
    List<Long> getDeptIds(UserDto user);
}