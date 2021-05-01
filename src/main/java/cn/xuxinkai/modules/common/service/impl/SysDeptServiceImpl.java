package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysDeptDao;
import cn.xuxinkai.modules.common.dto.UserDto;
import cn.xuxinkai.modules.common.entity.SysDept;
import cn.xuxinkai.modules.common.entity.SysRole;
import cn.xuxinkai.modules.common.enumerate.DataScopeEnum;
import cn.xuxinkai.modules.common.service.SysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 部门(SysDept)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:01
 */
@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {
    @Resource
    private SysDeptDao sysDeptDao;

    /**
     * 通过ID查询单条数据
     *
     * @param deptId 主键
     * @return 实例对象
     */
    @Override
    public SysDept queryById(Long deptId) {
        return this.sysDeptDao.queryById(deptId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysDept> queryAllByLimit(int offset, int limit) {
        return this.sysDeptDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysDept 实例对象
     * @return 实例对象
     */
    @Override
    public SysDept insert(SysDept sysDept) {
        this.sysDeptDao.insert(sysDept);
        return sysDept;
    }

    /**
     * 修改数据
     *
     * @param sysDept 实例对象
     * @return 实例对象
     */
    @Override
    public SysDept update(SysDept sysDept) {
        this.sysDeptDao.update(sysDept);
        return this.queryById(sysDept.getDeptId());
    }

    /**
     * 通过主键删除数据
     *
     * @param deptId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long deptId) {
        return this.sysDeptDao.deleteById(deptId) > 0;
    }

    /**
     * 获取部门id-数据权限-dataScopes
     *
     * @param user 用户
     * @return {@link List<Long>}
     */
    @Override
    public List<Long> getDeptIds(UserDto user) {
        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();
        // 获取角色对应的部门ID
        for (SysRole sysRole : user.getRoles()) {
            DataScopeEnum dataScopeEnum = DataScopeEnum.find(sysRole.getDataScope());
            switch (Objects.requireNonNull(dataScopeEnum)) {
                case THIS_LEVEL:
                    deptIds.add(user.getDept().getDeptId());
                    break;
                case CUSTOMIZE:
                    deptIds.addAll(getCustomize(deptIds, sysRole));
                    break;
                default:
                    return new ArrayList<>(deptIds);
            }
        }
        return new ArrayList<>(deptIds);
    }

    /**
     * 获得自定义的数据权限
     *
     * @param deptIds 部门id
     * @param sysRole 系统的作用
     * @return {@link Set<Long>}
     */
    private Set<Long> getCustomize(Set<Long> deptIds, SysRole sysRole) {
        //获取当前传入角色的关联的部门集合
        Set<SysDept> sysDeptSet = sysDeptDao.queryByRoleId(sysRole.getRoleId());
        //遍历部门集合，进行处理
        for (SysDept sysDept : sysDeptSet) {
            deptIds.add(sysDept.getDeptId());
            //获取二级部门
            List<SysDept> deptChildren = sysDeptDao.queryByPid(sysDept.getDeptId());
            //如果有二级部门，需要继续向下查询，获取所有的子部门
            if (deptChildren != null && 0 != deptChildren.size()) {
                deptIds.addAll(getDeptChildren(deptChildren));
            }
        }
        return deptIds;
    }

    /**
     * 获取所有的子部门
     *
     * @param sysDeptList 系统部门列表
     * @return {@link List<Long>}
     */
    private List<Long> getDeptChildren(List<SysDept> sysDeptList) {
        List<Long> list = new ArrayList<>();
        sysDeptList.forEach(sysDept -> {
                    if (sysDept != null && sysDept.getEnabled()) {
                        List<SysDept> sysDepts = sysDeptDao.queryByPid(sysDept.getDeptId());
                        if (sysDepts.size() != 0) {
                            list.addAll(getDeptChildren(sysDepts));
                        }
                        list.add(sysDept.getDeptId());
                    }
                }
        );
        return list;
    }
}