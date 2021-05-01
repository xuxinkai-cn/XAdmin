package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.SysLog;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统日志(SysLog)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-03 15:51:07
 */
@Mapper
public interface SysLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param logId 主键
     * @return 实例对象
     */
    SysLog queryById(Long logId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysLog 实例对象
     * @return 对象列表
     */
    List<SysLog> queryAll(SysLog sysLog);

    /**
     * 新增数据
     *
     * @param sysLog 实例对象
     * @return 影响行数
     */
    int insert(SysLog sysLog);

    /**
     * 修改数据
     *
     * @param sysLog 实例对象
     * @return 影响行数
     */
    int update(SysLog sysLog);

    /**
     * 通过主键删除数据
     *
     * @param logId 主键
     * @return 影响行数
     */
    int deleteById(Long logId);

    /**
     * 分页查询操作日志
     *
     * @param pageUtil 页面跑龙套
     * @return {@link List<SysLog>}
     */
    List<SysLog> queryAllByPageUtil(@Param("pageUtil") SysPageQueryUtils pageUtil);

    /**
     * 根据field获取总数
     *
     * @param fieldParams 场参数
     * @return {@link Long}
     */
    Long queryTotalByField(@Param("fieldParams") Map<String, Object> fieldParams);
}