package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysRolesDeptsDao;
import cn.xuxinkai.modules.common.entity.SysRolesDepts;
import cn.xuxinkai.modules.common.service.SysRolesDeptsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色部门关联(SysRolesDepts)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Service("sysRolesDeptsService")
public class SysRolesDeptsServiceImpl implements SysRolesDeptsService {
    @Resource
    private SysRolesDeptsDao sysRolesDeptsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public SysRolesDepts queryById(Long roleId) {
        return this.sysRolesDeptsDao.queryById(roleId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRolesDepts> queryAllByLimit(int offset, int limit) {
        return this.sysRolesDeptsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysRolesDepts 实例对象
     * @return 实例对象
     */
    @Override
    public SysRolesDepts insert(SysRolesDepts sysRolesDepts) {
        this.sysRolesDeptsDao.insert(sysRolesDepts);
        return sysRolesDepts;
    }

    /**
     * 修改数据
     *
     * @param sysRolesDepts 实例对象
     * @return 实例对象
     */
    @Override
    public SysRolesDepts update(SysRolesDepts sysRolesDepts) {
        this.sysRolesDeptsDao.update(sysRolesDepts);
        return this.queryById(sysRolesDepts.getRoleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long roleId) {
        return this.sysRolesDeptsDao.deleteById(roleId) > 0;
    }
}