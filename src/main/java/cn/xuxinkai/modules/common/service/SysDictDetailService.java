package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.SysDictDetail;

import java.util.List;

/**
 * 数据字典详情(SysDictDetail)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:06
 */
public interface SysDictDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param detailId 主键
     * @return 实例对象
     */
    SysDictDetail queryById(Long detailId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDictDetail> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysDictDetail 实例对象
     * @return 实例对象
     */
    SysDictDetail insert(SysDictDetail sysDictDetail);

    /**
     * 修改数据
     *
     * @param sysDictDetail 实例对象
     * @return 实例对象
     */
    SysDictDetail update(SysDictDetail sysDictDetail);

    /**
     * 通过主键删除数据
     *
     * @param detailId 主键
     * @return 是否成功
     */
    boolean deleteById(Long detailId);

}