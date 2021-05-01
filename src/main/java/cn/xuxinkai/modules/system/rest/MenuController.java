package cn.xuxinkai.modules.system.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.modules.common.dto.MenuDto;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.entity.SysMenu;
import cn.xuxinkai.modules.common.service.SysMenuService;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import cn.xuxinkai.modules.common.util.result.SysResult;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import cn.xuxinkai.modules.logger.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author xuxinkai
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
@Api(tags = "SYSTEM：菜单管理")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @SysLog("获取管理后台菜单")
    @ApiOperation("获取管理后台菜单")
    @GetMapping(value = "/getMenu")
    public SysResult buildMenuTree() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        List<MenuDto> menuDtoList = sysMenuService.findByUserId(userDetailDto.getUser().getUserId());
        List<MenuDto> menuDtoTree = sysMenuService.buildTree(menuDtoList);
        return SysResultGenerator.genSuccessResult(sysMenuService.buildMenu(menuDtoTree));
    }

    @SysLog("获取顶级菜单列表")
    @ApiOperation("获取顶级菜单列表")
    @GetMapping(value = "/getTopMenuList")
    public SysResult getTopMenuList(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort){
        SysPageResult<MenuDto> menuDtoSysPageResult = sysMenuService.getTopMenu(page,size,sort);
        return SysResultGenerator.genSuccessResult(menuDtoSysPageResult);
    }

    @SysLog("根据pid获取下一次菜单列表")
    @ApiOperation("根据pid获取下一次菜单列表")
    @GetMapping(value = "/getSubMenu")
    public SysResult getSubMenuList(HttpServletRequest request, @RequestParam("pid") Long pid){
        List<MenuDto> subMenuDtoSysPageResult = sysMenuService.getSubMenu(pid);
        return SysResultGenerator.genSuccessResult(subMenuDtoSysPageResult);
    }

    @SysLog("根据menuIds获取同级菜单及其上级菜单")
    @ApiOperation("根据menuIds获取同级菜单及其上级菜单")
    @PostMapping(value = "/getSuperiorMenu")
    public SysResult getSuperiorMenu(HttpServletRequest request, @RequestBody List<Long> menuIds){
        Set<MenuDto> menuDtoSet = new LinkedHashSet<>();
        if(ObjectUtil.isNotEmpty(menuIds)){
            for (Long menuId : menuIds) {
                //根据menuId获取对应的菜单信息
                MenuDto menuDto = sysMenuService.getMenuByMenuId(menuId);
                menuDtoSet.addAll(sysMenuService.getSuperior(menuDto, new ArrayList<>()));
            }
            return SysResultGenerator.genSuccessResult(sysMenuService.buildTree(new ArrayList<>(menuDtoSet)));
        }
        return SysResultGenerator.genSuccessResult(sysMenuService.getSubMenu(0L));
    }

    @SysLog("新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping(value = "/addMenu")
    public SysResult addMenu(HttpServletRequest request, @RequestBody MenuDto menuDto){
        //TODO : 需要保证某些字段必须存在，此处校验后续补充
        if(ObjectUtil.isNotNull(menuDto.getMenuId())){
            return SysResultGenerator.genFailResult("该menuId已存在");
        }
        //写入数据库
        if(Boolean.TRUE.equals(sysMenuService.addSysMenu(menuDto))){
            return SysResultGenerator.genSuccessResult("菜单新增成功");
        }
        return SysResultGenerator.genFailResult("菜单新增失败~~");
    }

    @SysLog("删除菜单")
    @ApiOperation("删除菜单")
    @PostMapping(value = "/deleteMenu")
    public SysResult deleteMenu(HttpServletRequest request, @RequestBody Set<Long> menuIds){
        //构建一个需要删除的菜单集合
        Set<SysMenu> sysMenuSet = new HashSet<>(32);
        for (Long menuId : menuIds) {
            //根据pid = menuID获取所有下一级菜单
            List<MenuDto> menuDtoList = sysMenuService.getSubMenu(menuId);
            List<SysMenu> sysMenuList = new ArrayList<>();
            for(MenuDto menuDto : menuDtoList){
                SysMenu sysMenu = new SysMenu();
                BeanUtil.copyProperties(menuDto,sysMenu);
                sysMenuList.add(sysMenu);
            }
            //获取当前菜单
            sysMenuSet.add(sysMenuService.queryById(menuId));
            //递归获取传入菜单的所有子菜单，返回结果包含传入菜单
            sysMenuSet = sysMenuService.getChildMenu(sysMenuList, sysMenuSet);
        }
        log.info("要删除的菜单为:{}",sysMenuSet.toString());
        sysMenuService.delete(sysMenuSet);
        return SysResultGenerator.genSuccessResult("删除成功");
    }

    @SysLog("更新菜单")
    @ApiOperation("更新菜单")
    @PostMapping(value = "/updateMenu")
    public SysResult updateMenu(HttpServletRequest request, @RequestBody MenuDto menuDto){
        sysMenuService.updateMenu(menuDto);
        return SysResultGenerator.genSuccessResult("更新成功");
    }
}
