package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysRoleDao;
import cn.xuxinkai.modules.common.dto.UserDto;
import cn.xuxinkai.modules.common.entity.SysMenu;
import cn.xuxinkai.modules.common.entity.SysRole;
import cn.xuxinkai.modules.common.service.SysMenuService;
import cn.xuxinkai.modules.common.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public SysRole queryById(Long roleId) {
        return this.sysRoleDao.queryById(roleId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRole> queryAllByLimit(int offset, int limit) {
        return this.sysRoleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public SysRole insert(SysRole sysRole) {
        this.sysRoleDao.insert(sysRole);
        return sysRole;
    }


    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long roleId) {
        return this.sysRoleDao.deleteById(roleId) > 0;
    }

    /**
     * 通过userId获取该用户所拥有的角色集合
     *
     * @param userId 用户id
     * @return {@link Set <SysRole>}
     */
    @Override
    public Set<SysRole> getRolesByUserId(Long userId) {
        //从sys_users_roles sys_role中查询该用户拥有的sysRole
        return sysRoleDao.queryByUserId(userId);
    }

    /**
     * 获取spring security需要的角色权限
     *
     * @param user 用户
     * @return {@link List< GrantedAuthority >}
     */
    @Override
    public List<GrantedAuthority> mapToGrantedAuthorities(UserDto user) {
        Set<String> permissions = new HashSet<>();
        // 如果是管理员直接返回
        if (user.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        Set<SysRole> sysRoleSet = sysRoleDao.queryByUserId(user.getUserId());
        permissions = sysRoleSet.stream().flatMap(sysRole -> sysMenuService.getMenuByRoleId(sysRole.getRoleId()).stream()).filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(SysMenu::getPermission).collect(Collectors.toSet());
        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}