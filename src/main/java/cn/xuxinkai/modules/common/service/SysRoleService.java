package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.dto.UserDto;
import cn.xuxinkai.modules.common.entity.SysRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
public interface SysRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    SysRole queryById(Long roleId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRole> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    SysRole insert(SysRole sysRole);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    boolean deleteById(Long roleId);

    /**
     * 通过userId获取该用户所拥有的角色集合-联表查询
     *
     * @param userId 用户id
     * @return {@link Set<SysRole>}
     */
    Set<SysRole> getRolesByUserId(Long userId);

    /**
     * 获取spring security需要的角色权限
     *
     * @param user 用户
     * @return {@link List<GrantedAuthority>}
     */
    List<GrantedAuthority> mapToGrantedAuthorities(UserDto user);
}