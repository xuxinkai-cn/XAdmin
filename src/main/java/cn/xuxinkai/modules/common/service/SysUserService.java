package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.dto.UserDto;
import cn.xuxinkai.modules.common.dto.UserInfoDto;
import cn.xuxinkai.modules.common.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * 系统用户(SysUser)表服务接口
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
public interface SysUserService extends UserDetailsService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUser queryById(Long userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser insert(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

    /**
     * 重写 spring security 方法
     *
     * @param username 用户名
     * @return {@link UserDetailDto}
     */
    @Override
    UserDetailDto loadUserByUsername(String username);

    /**
     * 通过用户名组装UserDto
     *
     * @param username 用户名
     * @return {@link UserDto}
     */
    UserDto getUserDtoByUsername(String username);

    /**
     * 校验用户手机号是不是唯一手机号
     *
     * @return boolean
     */
    boolean isUniquePhone(String phone);

    /**
     * 更新用户信息
     *
     * @param userInfoDto 用户信息dto
     * @return boolean
     */
    boolean updateByUserInfo(UserInfoDto userInfoDto);

    /**
     * 更改密码
     *
     * @param oldPass 古老的传递
     * @param newPass 新通
     * @return boolean
     */
    boolean changePassword(String oldPass, String newPass) throws Exception;

    /**
     * 更换绑定的邮箱
     *
     * @param email    电子邮件
     * @param password 密码
     * @param code     验证码
     * @return boolean
     */
    boolean changeEmail(String email, String password, String code) throws Exception;

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return int
     */
    int update(SysUser sysUser);
}