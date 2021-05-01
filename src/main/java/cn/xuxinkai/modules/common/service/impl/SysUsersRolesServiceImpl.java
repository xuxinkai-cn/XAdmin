package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysUsersRolesDao;
import cn.xuxinkai.modules.common.entity.SysUsersRoles;
import cn.xuxinkai.modules.common.service.SysUsersRolesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户角色关联(SysUsersRoles)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Service("sysUsersRolesService")
public class SysUsersRolesServiceImpl implements SysUsersRolesService {
    @Resource
    private SysUsersRolesDao sysUsersRolesDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public SysUsersRoles queryById(Long userId) {
        return this.sysUsersRolesDao.queryById(userId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUsersRoles> queryAllByLimit(int offset, int limit) {
        return this.sysUsersRolesDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysUsersRoles 实例对象
     * @return 实例对象
     */
    @Override
    public SysUsersRoles insert(SysUsersRoles sysUsersRoles) {
        this.sysUsersRolesDao.insert(sysUsersRoles);
        return sysUsersRoles;
    }

    /**
     * 修改数据
     *
     * @param sysUsersRoles 实例对象
     * @return 实例对象
     */
    @Override
    public SysUsersRoles update(SysUsersRoles sysUsersRoles) {
        this.sysUsersRolesDao.update(sysUsersRoles);
        return this.queryById(sysUsersRoles.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return this.sysUsersRolesDao.deleteById(userId) > 0;
    }
}