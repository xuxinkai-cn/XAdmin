package cn.xuxinkai.modules.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xuxinkai.modules.common.dao.SysMenuDao;
import cn.xuxinkai.modules.common.dto.MenuDto;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.entity.SysMenu;
import cn.xuxinkai.modules.common.entity.SysRole;
import cn.xuxinkai.modules.common.exception.BadRequestException;
import cn.xuxinkai.modules.common.service.SysMenuService;
import cn.xuxinkai.modules.common.service.SysRoleService;
import cn.xuxinkai.modules.common.service.SysRolesMenusService;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import cn.xuxinkai.modules.system.rest.vo.MenuMetaVo;
import cn.xuxinkai.modules.system.rest.vo.MenuVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统菜单(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Slf4j
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuDao sysMenuDao;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRolesMenusService sysRolesMenusService;

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    @Override
    public SysMenu queryById(Long menuId) {
        return this.sysMenuDao.queryById(menuId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysMenu> queryAllByLimit(int offset, int limit) {
        return this.sysMenuDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insert(SysMenu sysMenu) {
        return this.sysMenuDao.insert(sysMenu) > 0 ;
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysMenu update(SysMenu sysMenu) {
        this.sysMenuDao.update(sysMenu);
        return this.queryById(sysMenu.getMenuId());
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long menuId) {
        return this.sysMenuDao.deleteById(menuId) > 0;
    }

    /**
     * 通过roleId获取菜单和权限列表
     *
     * @param roleId 角色id
     * @return {@link List<SysMenu>}
     */
    @Override
    public List<SysMenu> getMenuByRoleId(Long roleId) {
        return sysMenuDao.queryByRoleId(roleId);
    }

    /**
     * 通过userId获取用户所拥有的菜单
     *
     * @param userId 用户id
     * @return {@link List<MenuDto>}
     */
    @Override
    public List<MenuDto> findByUserId(Long userId) {
        log.debug("进入findByUserId METHOD");
        log.info("userId:{}", userId);
        Set<SysRole> sysRoleSet = sysRoleService.getRolesByUserId(userId);
        log.info("roles为:{}", sysRoleSet);
        Set<Long> roleIds = sysRoleSet.stream().map(SysRole::getRoleId).collect(Collectors.toSet());
        log.info("用户拥有的角色集合为：{}", roleIds);
        LinkedHashSet<SysMenu> sysMenuLinkedHashSet = sysMenuDao.queryByRoleIdsAndType(roleIds, 2);
        List<MenuDto> menuDtoList = new ArrayList<>();
        sysMenuLinkedHashSet.forEach(sysMenu -> {
            MenuDto menuDto = new MenuDto();
            BeanUtil.copyProperties(sysMenu, menuDto);
            menuDtoList.add(menuDto);
        });
        log.info("menuDtoList为:{}", menuDtoList);
        return menuDtoList;
    }

    /**
     * 构建菜单tree
     *
     * @param menuDtoList 菜单dto列表
     * @return {@link List<MenuDto>}
     */
    @Override
    public List<MenuDto> buildTree(List<MenuDto> menuDtoList) {
        List<MenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDto menuDTO : menuDtoList) {
            //顶级节点pid = 0
            if (menuDTO.getPid() == 0) {
                trees.add(menuDTO);
            }
            //遍历获取当前节点的下级节点
            for (MenuDto it : menuDtoList) {
                if (menuDTO.getMenuId().equals(it.getPid())) {
                    //todo : bug-如果设置了不可见，此处应该加以判断进行隐藏，不然管理后台菜单展示有问题
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getMenuId());
                }
            }
        }
        if (trees.isEmpty()) {
            trees = menuDtoList.stream().filter(s -> !ids.contains(s.getMenuId())).collect(Collectors.toList());
        }
        return trees;
    }

    /**
     * 构建前端需要的菜单数据
     *
     * @param menuDtoTree 菜单dto树
     * @return {@link Object}
     */
    @Override
    public List<MenuVo> buildMenu(List<MenuDto> menuDtoTree) {
        List<MenuVo> list = new LinkedList<>();
        menuDtoTree.forEach(menuDTO -> {
                    if (menuDTO != null) {
                        List<MenuDto> childrenMenuDtoList = menuDTO.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponent()) ? menuDTO.getComponent() : menuDTO.getTitle());
                        // 一级目录需要加斜杠，不然会报警告
                        menuVo.setPath(menuDTO.getPid() == 0 ? "/" + menuDTO.getPath() : menuDTO.getPath());
                        menuVo.setHidden(menuDTO.getHidden());
                        // 如果不是外链
                        if (!menuDTO.getIframe()) {
                            if (menuDTO.getPid() == 0) {
                                menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                            } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(menuDTO.getTitle(), menuDTO.getIcon(), !menuDTO.getCache()));
                        if (childrenMenuDtoList != null && childrenMenuDtoList.size() != 0) {
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenu(childrenMenuDtoList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if (menuDTO.getPid() == 0) {
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if (!menuDTO.getIframe()) {
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    /**
     * 分页获得顶级菜单
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult <MenuDto>}
     */
    @Override
    public SysPageResult<MenuDto> getTopMenu(int page, int size, String sort) {
        Map<String, Object> fieldParams = new HashMap<>(16);
        fieldParams.put("pid", 0);
        Map<String, Object> params = new HashMap<>(16);
        //查询限定条件-此处是限定pid = 0
        params.put("field", fieldParams);
        //第几页
        params.put("pageNum", page);
        //每页几条
        params.put("pageSize", size);
        //排序方式
        params.put("orderByClaus", sort.replace(",", " "));
        //构建翻页
        SysPageQueryUtils pageUtil = new SysPageQueryUtils(params);
        log.info(pageUtil.toString());
        List<SysMenu> sysMenuList = sysMenuDao.queryAllByPageUtil(pageUtil);
        //todo: entity 转为 DTO 需要提取公共方法
        List<MenuDto> menuDtoList = new ArrayList<>();
        sysMenuList.forEach(sysMenu -> {
            MenuDto menuDto = new MenuDto();
            BeanUtil.copyProperties(sysMenu, menuDto);
            menuDtoList.add(menuDto);
        });
        log.info("menuDtoList为:{}", menuDtoList);
        Long total = sysMenuDao.queryTotalByField(fieldParams);
        return new SysPageResult<>(menuDtoList, total, pageUtil.getPageSize(), pageUtil.getPageNum());
    }

    /**
     * 根据pid获取菜单列表
     *
     * @param pid pid
     * @return {@link SysPageResult<MenuDto>}
     */
    @Override
    public List<MenuDto> getSubMenu(Long pid) {
        SysMenu querySysMenu = new SysMenu();
        querySysMenu.setPid(pid);
        List<SysMenu> sysMenuList = sysMenuDao.queryAll(querySysMenu);
        List<MenuDto> menuDtoList = new ArrayList<>();
        sysMenuList.forEach(sysMenu -> {
            MenuDto menuDto = new MenuDto();
            BeanUtil.copyProperties(sysMenu, menuDto);
            menuDtoList.add(menuDto);
        });
        return menuDtoList;
    }

    /**
     * 根据menuId获取菜单
     *
     * @param menuId 菜单id
     * @return {@link List<MenuDto>}
     */
    @Override
    public MenuDto getMenuByMenuId(Long menuId) {
        SysMenu sysMenu = sysMenuDao.queryById(menuId);
        MenuDto menuDto = new MenuDto();
        BeanUtil.copyProperties(sysMenu, menuDto);
        return menuDto;
    }

    /**
     * 递归获取菜单
     *
     * @param menuDto     菜单dto
     * @param menuDtoList 菜单dto列表
     * @return {@link List<MenuDto>}
     */
    @Override
    public List<MenuDto> getSuperior(MenuDto menuDto, List<MenuDto> menuDtoList) {
        //获取同级菜单
        menuDtoList.addAll(getSubMenu(menuDto.getPid()));
        if(menuDto.getPid() == 0){
            return menuDtoList;
        }
        return getSuperior(getMenuByMenuId(menuDto.getPid()), menuDtoList);
    }

    /**
     * 添加系统菜单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addSysMenu(MenuDto menuDto){
        //菜单标题不能重复
        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setTitle(menuDto.getTitle());
        if(ObjectUtil.isNotEmpty(sysMenuDao.queryAll(sysMenu1))){
            throw new BadRequestException("标题已存在");
        }
        //如果设置了组件名称，组件名称不能重复
        SysMenu sysMenu2 = new SysMenu();
        sysMenu2.setName(menuDto.getName());
        if(StringUtils.isNotBlank(menuDto.getName()) && ObjectUtil.isNotEmpty(sysMenuDao.queryAll(sysMenu2))){
                throw new BadRequestException("组件名称已存在");
        }
        //如果设置了外链，需要校验外链开头
        if(Boolean.TRUE.equals(menuDto.getIframe())){
            String http = "http://";
            String https = "https://";
            if (!(menuDto.getPath().toLowerCase().startsWith(http)||menuDto.getPath().toLowerCase().startsWith(https))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        //新增的菜单子节点数目为0
        menuDto.setSubCount(0);
        //根据业务构建SysMenu
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(menuDto, sysMenu);
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        sysMenu.setCreateBy(userDetailDto.getUsername());
        sysMenu.setUpdateBy(userDetailDto.getUsername());
        sysMenu.setCreateTime(new DateTime());
        sysMenu.setUpdateTime(new DateTime());
        sysMenuDao.insert(sysMenu);
        // 更新父节点菜单数目
        updateFatherNodeSubCountByPid(menuDto.getPid());
        log.info(sysMenu.toString());
        return true;
    }

    /**
     * 递归获取传入菜单的所有子菜单，返回结果包含传入菜单
     *
     * @param sysMenuList   菜单列表
     * @param sysMenuSet 系统菜单设置
     * @return {@link Set<SysMenu>}
     */
    @Override
    public Set<SysMenu> getChildMenu(List<SysMenu> sysMenuList, Set<SysMenu> sysMenuSet) {
        for (SysMenu sysMenu : sysMenuList) {
            sysMenuSet.add(sysMenu);
            SysMenu sysMenuChild = new SysMenu();
            sysMenuChild.setPid(sysMenu.getMenuId());
            List<SysMenu> menus = sysMenuDao.queryAll(sysMenuChild);
            if(ObjectUtil.isNotNull(menus) && ObjectUtil.isNotEmpty(menus)){
                getChildMenu(menus, sysMenuSet);
            }
        }
        return sysMenuSet;
    }

    /**
     * 删除菜单
     *
     * @param sysMenuSet 系统菜单设置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<SysMenu> sysMenuSet) {
        for (SysMenu sysMenu : sysMenuSet) {
            //todo : 此处可能需要清理一些数据缓存-后续完善
            //解除菜单和角色的绑定
            sysRolesMenusService.unbindRoleWithMenu(sysMenu.getMenuId());
            //删除菜单
            sysMenuDao.deleteById(sysMenu.getMenuId());
            //更新父级菜单的subCount
            updateFatherNodeSubCountByPid(sysMenu.getPid());
        }
    }

    /**
     * 更新菜单
     *
     * @param menuDto 菜单dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(MenuDto menuDto) {
        //首先校验menuId != pid
        if(menuDto.getMenuId().equals(menuDto.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        //然后校验menuId是否存在
        if(ObjectUtil.isNull(menuDto.getMenuId())){
            throw new BadRequestException("menuId错误");
        }
        //菜单标题不能重复
        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setTitle(menuDto.getTitle());
       List<SysMenu>  sysMenuList1 = sysMenuDao.queryAll(sysMenu1);
        if(ObjectUtil.isNotEmpty(sysMenuList1)){
            for(SysMenu sysMenu : sysMenuList1){
                if(!sysMenu.getMenuId().equals(menuDto.getMenuId())){
                    throw new BadRequestException("标题已存在");
                }
            }
        }
        //如果设置了组件名称，组件名称不能重复
        if(StringUtils.isNotBlank(menuDto.getName())){
            SysMenu sysMenu2 = new SysMenu();
            sysMenu2.setName(menuDto.getName());
            List<SysMenu> sysMenuList2 = sysMenuDao.queryAll(sysMenu2);
            if(ObjectUtil.isNotEmpty(sysMenuList2)){
                for(SysMenu sysMenu : sysMenuList2){
                    if(!sysMenu.getMenuId().equals(menuDto.getMenuId())){
                        throw new BadRequestException("组件名称已存在");
                    }
                }
            }
        }
        //外链菜单相关规则校验
        if(Boolean.TRUE.equals(menuDto.getIframe())){
            String http = "http://";
            String https = "https://";
            if (!(menuDto.getPath().toLowerCase().startsWith(http)||menuDto.getPath().toLowerCase().startsWith(https))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        //获取数据库现有的数据
        SysMenu oldSysMenu = sysMenuDao.queryById(menuDto.getMenuId());
        // 记录的父节点ID
        Long oldPid = oldSysMenu.getPid();
        Long newPid = menuDto.getPid();
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //更新老数据
        oldSysMenu.setTitle(menuDto.getTitle());
        oldSysMenu.setComponent(menuDto.getComponent());
        oldSysMenu.setPath(menuDto.getPath());
        oldSysMenu.setIcon(menuDto.getIcon());
        oldSysMenu.setIframe(menuDto.getIframe());
        oldSysMenu.setPid(menuDto.getPid());
        oldSysMenu.setMenuSort(menuDto.getMenuSort());
        oldSysMenu.setCache(menuDto.getCache());
        oldSysMenu.setHidden(menuDto.getHidden());
        oldSysMenu.setName(menuDto.getName());
        oldSysMenu.setPermission(menuDto.getPermission());
        oldSysMenu.setType(menuDto.getType());
        oldSysMenu.setUpdateBy(userDetailDto.getUsername());
        oldSysMenu.setUpdateTime(new DateTime());
        //更新数据
        sysMenuDao.update(oldSysMenu);
        // pid改变时，需要计算新旧父级菜单节点数目
        if(!oldPid.equals(newPid)){
            updateFatherNodeSubCountByPid(oldPid);
            updateFatherNodeSubCountByPid(newPid);
        }
        // todo : 如果有缓存，需要清理缓存-后续待完善
    }


    /**
     *更新父节点的subCount
     *
     * @param pid pid
     */
    private void updateFatherNodeSubCountByPid(Long pid) {
        //如果是顶级菜单，就没必要更新
        if(pid != 0L){
            //首选获取所有子节点数目
            SysMenu sysMenu = new SysMenu();
            sysMenu.setPid(pid);
            int subCount = sysMenuDao.queryAll(sysMenu).size();
            sysMenu.setPid(null);
            sysMenu.setMenuId(pid);
            sysMenu.setSubCount(subCount);
            sysMenuDao.update(sysMenu);
        }
    }

}