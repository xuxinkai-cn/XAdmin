package cn.xuxinkai.modules.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.modules.common.dao.SysUserDao;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.dto.UserDto;
import cn.xuxinkai.modules.common.dto.UserInfoDto;
import cn.xuxinkai.modules.common.entity.SysUser;
import cn.xuxinkai.modules.common.exception.BadRequestException;
import cn.xuxinkai.modules.common.service.SysDeptService;
import cn.xuxinkai.modules.common.service.SysJobService;
import cn.xuxinkai.modules.common.service.SysRoleService;
import cn.xuxinkai.modules.common.service.SysUserService;
import cn.xuxinkai.modules.common.util.SysPasswordUtils;
import cn.xuxinkai.modules.common.util.SysRedisUtils;
import cn.xuxinkai.modules.common.util.SysRsaUtils;
import cn.xuxinkai.modules.security.config.bean.SysLoginProperties;
import cn.xuxinkai.modules.security.config.bean.SysSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 系统用户(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysLoginProperties sysLoginProperties;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysJobService sysJobService;

    @Autowired
    private SysDeptService sysDeptService;

    @Resource
    private SysSecurityProperties sysSecurityProperties;

    @Resource
    private SysRedisUtils sysRedisUtils;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(Long userId) {
        return this.sysUserDao.queryById(userId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return this.sysUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser insert(SysUser sysUser) {
        this.sysUserDao.insert(sysUser);
        return sysUser;
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return this.sysUserDao.deleteById(userId) > 0;
    }

    /**
     * 重写spring security的方法
     *
     * @param username 用户名
     * @return {@link UserDetailDto}
     */
    @Override
    public UserDetailDto loadUserByUsername(String username) {
        UserDetailDto userDetailDto = null;

        UserDto user = getUserDtoByUsername(username);
        if (ObjectUtil.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在！");
        } else {
            if (user.getEnabled() == 0) {
                throw new BadRequestException("账号未激活！");
            }
            //user,dataScopes,authorities
            userDetailDto = new UserDetailDto(
                    user,
                    sysDeptService.getDeptIds(user),
                    sysRoleService.mapToGrantedAuthorities(user)
            );
        }
        return userDetailDto;
    }

    /**
     * 通过用户名组装UserDto
     *
     * @param username 用户名
     * @return {@link UserDto}
     */
    @Override
    public UserDto getUserDtoByUsername(String username) {
        UserDto userDto = new UserDto();
        SysUser sysUser = sysUserDao.queryByUsername(username);

        //在拷贝bean属性时，需要先判断sysUser
        if (Objects.isNull(sysUser)) {
            return null;
        }

        //sysUser entity 复制进 UserDto里面
        BeanUtil.copyProperties(sysUser, userDto);
        //获取用户角色
        userDto.setRoles(sysRoleService.getRolesByUserId(sysUser.getUserId()));
        //获取用户岗位
        userDto.setJobs(sysJobService.getJobsByUserId(sysUser.getUserId()));
        //获取用户部门
        userDto.setDept(sysDeptService.queryById(sysUser.getDeptId()));
        return userDto;
    }

    /**
     * 校验用户手机号是不是唯一手机号
     *
     * @return boolean
     */
    @Override
    public boolean isUniquePhone(String phone) {
        SysUser sysUser = sysUserDao.queryByPhone(phone);
        return ObjectUtil.isNull(sysUser) || sysUser.getPhone().equals(phone);
    }

    /**
     * 更新用户信息
     *
     * @param userInfoDto 用户信息dto
     * @return boolean
     */
    @Override
    public boolean updateByUserInfo(UserInfoDto userInfoDto) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        SysUser sysUser = new SysUser();
        //todo：如果是管理员修改的用户信息，这个地方userId有问题
        sysUser.setUserId(userDetailDto.getUser().getUserId());
        sysUser.setNickName(userInfoDto.getNickName());
        sysUser.setGender(userInfoDto.getGender());
        sysUser.setPhone(userInfoDto.getPhone());
        sysUser.setUpdateBy(userDetailDto.getUsername());
        sysUser.setUpdateTime(new DateTime());
        //todo:修改UserDetail
        return sysUserDao.update(sysUser) > 0;
    }

    /**
     * 更改密码
     *
     * @param oldPass 古老的传递
     * @param newPass 新通
     * @return boolean
     */
    @Override
    public boolean changePassword(String oldPass, String newPass) throws Exception {
        //首先对参数解密
        String oldPassDec = SysRsaUtils.decryptByPrivateKey(sysSecurityProperties.getPrivateKey(), oldPass);
        String newPassDec = SysRsaUtils.decryptByPrivateKey(sysSecurityProperties.getPrivateKey(), newPass);
        //首先获取用户信息，然后校验oldPass，没问题就更新密码
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        if (SysPasswordUtils.isValidWordpressPassword(oldPassDec, userDetailDto.getPassword())) {
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userDetailDto.getUser().getUserId());
            sysUser.setPassword(SysPasswordUtils.useWordpressEncrypt(newPassDec));
            return sysUserDao.update(sysUser) > 0;
        }
        return false;
    }

    /**
     * 更换绑定的邮箱
     *
     * @param email    电子邮件
     * @param password 密码
     * @param code     验证码
     * @return boolean
     */
    @Override
    public boolean changeEmail(String email, String password, String code) throws Exception {
        //首先对密码进行校验，再就是根据邮箱从redis获取code校验，最后是更新邮箱
        String passDec = SysRsaUtils.decryptByPrivateKey(sysSecurityProperties.getPrivateKey(), password);
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        if (SysPasswordUtils.isValidWordpressPassword(passDec, userDetailDto.getPassword()) && sysRedisUtils.get(email).toString().equals(code)) {
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userDetailDto.getUser().getUserId());
            sysUser.setEmail(email);
            return sysUserDao.update(sysUser) > 0;
        }
        return false;
    }

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return int
     */
    @Override
    public int update(SysUser sysUser) {
        return sysUserDao.update(sysUser);
    }

}
