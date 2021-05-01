package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.SysDict;

import java.util.List;

/**
 * 数据字典(SysDict)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:02
 */
public interface SysDictService {

    /**
     * 通过ID查询单条数据
     *
     * @param dictId 主键
     * @return 实例对象
     */
    SysDict queryById(Long dictId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDict> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysDict 实例对象
     * @return 实例对象
     */
    SysDict insert(SysDict sysDict);

    /**
     * 修改数据
     *
     * @param sysDict 实例对象
     * @return 实例对象
     */
    SysDict update(SysDict sysDict);

    /**
     * 通过主键删除数据
     *
     * @param dictId 主键
     * @return 是否成功
     */
    boolean deleteById(Long dictId);

}