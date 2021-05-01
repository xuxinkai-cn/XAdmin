package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysRolesMenusDao;
import cn.xuxinkai.modules.common.entity.SysRolesMenus;
import cn.xuxinkai.modules.common.service.SysRolesMenusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色菜单关联(SysRolesMenus)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Service("sysRolesMenusService")
public class SysRolesMenusServiceImpl implements SysRolesMenusService {
    @Resource
    private SysRolesMenusDao sysRolesMenusDao;

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    @Override
    public SysRolesMenus queryById(Long menuId) {
        return this.sysRolesMenusDao.queryById(menuId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRolesMenus> queryAllByLimit(int offset, int limit) {
        return this.sysRolesMenusDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysRolesMenus 实例对象
     * @return 实例对象
     */
    @Override
    public SysRolesMenus insert(SysRolesMenus sysRolesMenus) {
        this.sysRolesMenusDao.insert(sysRolesMenus);
        return sysRolesMenus;
    }

    /**
     * 修改数据
     *
     * @param sysRolesMenus 实例对象
     * @return 实例对象
     */
    @Override
    public SysRolesMenus update(SysRolesMenus sysRolesMenus) {
        this.sysRolesMenusDao.update(sysRolesMenus);
        return this.queryById(sysRolesMenus.getMenuId());
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long menuId) {
        return this.sysRolesMenusDao.deleteById(menuId) > 0;
    }

    /**
     * 解除菜单和角色的绑定
     *
     * @param menuId 菜单id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unbindRoleWithMenu(Long menuId) {
        sysRolesMenusDao.deleteById(menuId);
    }
}