package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.SysMenu;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统菜单(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Mapper
public interface SysMenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    SysMenu queryById(Long menuId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysMenu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysMenu 实例对象
     * @return 对象列表
     */
    List<SysMenu> queryAll(SysMenu sysMenu);

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 影响行数
     */
    int insert(SysMenu sysMenu);

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 影响行数
     */
    int update(SysMenu sysMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteById(Long menuId);

    /**
     * 通过roleId获取sysMenu
     *
     * @param roleId 角色id
     * @return {@link List<SysMenu>}
     */
    List<SysMenu> queryByRoleId(Long roleId);

    /**
     * 获取角色拥有的菜单
     *
     * @param roleIds 角色id
     * @param i       我
     * @return {@link LinkedHashSet<SysMenu>}
     */
    LinkedHashSet<SysMenu> queryByRoleIdsAndType(@Param("roleIds") Set<Long> roleIds, @Param("type") int type);

    /**
     * 分页查询符合条件的SysMenu
     *
     * @param pageUtil 页面跑龙套
     * @return {@link List<SysMenu>}
     */
    List<SysMenu> queryAllByPageUtil(SysPageQueryUtils pageUtil);

    /**
     * 获取符合条件的SysMenu 总数
     *
     * @param fieldParams 场参数
     * @return {@link Long}
     */
    Long queryTotalByField(Map<String, Object> fieldParams);
}