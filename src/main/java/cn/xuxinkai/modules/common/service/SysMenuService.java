package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.dto.MenuDto;
import cn.xuxinkai.modules.common.entity.SysMenu;
import cn.xuxinkai.modules.common.util.result.SysPageResult;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
public interface SysMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    SysMenu queryById(Long menuId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysMenu> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysMenu 系统菜单
     * @return {@link Boolean}
     */
    Boolean insert(SysMenu sysMenu);

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    SysMenu update(SysMenu sysMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    boolean deleteById(Long menuId);

    /**
     * 通过roleId获取菜单和权限列表
     *
     * @param roleId 角色id
     * @return {@link List<SysMenu>}
     */
    List<SysMenu> getMenuByRoleId(Long roleId);

    /**
     * 通过userId获取用户所拥有的菜单
     *
     * @param userId 用户id
     * @return {@link List<MenuDto>}
     */
    List<MenuDto> findByUserId(Long userId);

    /**
     * 构建菜单tree
     *
     * @param menuDtoList 菜单dto列表
     * @return {@link List<MenuDto>}
     */
    List<MenuDto> buildTree(List<MenuDto> menuDtoList);

    /**
     * 根据菜单树构建 vue-router 需要的路由数据
     *
     * @param menuDtoTree 菜单dto树
     * @return {@link Object}
     */
    Object buildMenu(List<MenuDto> menuDtoTree);

    /**
     * 分页获得顶级菜单
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult<MenuDto>}
     */
    SysPageResult<MenuDto> getTopMenu(int page, int size, String sort);


    /**
     * 根据pid获取菜单列表
     *
     * @param pid pid
     * @return {@link List<MenuDto>}
     */
    List<MenuDto> getSubMenu(Long pid);


    /**
     * 根据menuId获取菜单
     *
     * @param menuId 菜单id
     * @return {@link MenuDto}
     */
    public MenuDto getMenuByMenuId(Long menuId);


    /**
     * 递归获取本级及上一级菜单
     *
     * @param menuDto     菜单dto
     * @param menuDtoList 菜单dto列表
     * @return {@link List<MenuDto>}
     */
    List<MenuDto> getSuperior(MenuDto menuDto, List<MenuDto> menuDtoList);

    /**
     * 添加系统菜单
     *
     * @param menuDto 菜单dto
     * @return {@link Boolean}
     */
    Boolean addSysMenu(MenuDto menuDto);

    /**
     * 递归获取传入菜单的所有子菜单，返回结果包含传入菜单
     *
     * @param sysMenuList   菜单列表
     * @param sysMenuSet 系统菜单设置
     * @return {@link Set<SysMenu>}
     */
    Set<SysMenu> getChildMenu(List<SysMenu> sysMenuList, Set<SysMenu> sysMenuSet);

    /**
     * 删除菜单
     *
     * @param sysMenuSet 系统菜单设置
     */
    void delete(Set<SysMenu> sysMenuSet);

    /**
     * 更新菜单
     *
     * @param menuDto 菜单dto
     */
    void updateMenu(MenuDto menuDto);
}