package cn.xuxinkai.modules.common.dao;

import cn.xuxinkai.modules.common.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Mapper
public interface SysUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUser queryById(Long userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysUser 实例对象
     * @return 对象列表
     */
    List<SysUser> queryAll(SysUser sysUser);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int insert(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

    /**
     * 通过用户名查询SysUser
     *
     * @param username 用户名
     * @return {@link SysUser}
     */
    SysUser queryByUsername(String username);

    /**
     * 通过手机号查询SysUser
     *
     * @param phone 电话
     * @return {@link List<SysUser>}
     */
    SysUser queryByPhone(String phone);


    /**
     * 更新用户信息
     *
     * @param sysUser sysUser
     * @return int 影响行数
     */
    int update(SysUser sysUser);
}