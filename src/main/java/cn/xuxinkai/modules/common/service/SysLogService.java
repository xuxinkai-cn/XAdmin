package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.SysLog;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 系统日志(SysLog)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:08
 */
public interface SysLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param logId 主键
     * @return 实例对象
     */
    SysLog queryById(Long logId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysLog> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysLog 实例对象
     * @return 实例对象
     */
    @Async
    void insert(SysLog sysLog);

    /**
     * 修改数据
     *
     * @param sysLog 实例对象
     * @return 实例对象
     */
    SysLog update(SysLog sysLog);

    /**
     * 通过主键删除数据
     *
     * @param logId 主键
     * @return 是否成功
     */
    boolean deleteById(Long logId);

    /**
     * 获取用户日志
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult<Object>}
     */
    SysPageResult<SysLog> getUserLog(int page, int size, String sort);
}