package cn.xuxinkai.cms.controller.rest;

import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.entity.CmsFriendlink;
import cn.xuxinkai.cms.service.CmsFriendlinkService;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import cn.xuxinkai.modules.common.util.result.SysResult;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import cn.xuxinkai.modules.logger.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 友链管理
 *
 * @author xuxinkai
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/friendlink")
@RequiredArgsConstructor
@Api(tags = "CMS：友链管理")
public class FriendLinkController {

    @Autowired
    private CmsFriendlinkService cmsFriendlinkService;

    @SysLog("新增友情链接")
    @ApiOperation("新增友情链接")
    @PostMapping(value = "/addFriendlink")
    public SysResult addFriendlink(HttpServletRequest request, @RequestBody CmsFriendlink cmsFriendlink) {
        //TODO : 需要保证某些字段必须存在，此处校验后续补充
        //新增时，传过来的cmsFriendlink没有friendlinkId
        if (ObjectUtil.isNotNull(cmsFriendlink.getFriendlinkId())) {
            return SysResultGenerator.genFailResult("该friendlinkId已存在");
        }
        //写入数据库
        if (Boolean.TRUE.equals(cmsFriendlinkService.addFriendlink(cmsFriendlink))) {
            return SysResultGenerator.genSuccessResult("友链新增成功");
        }
        return SysResultGenerator.genFailResult("友链新增失败~~");
    }

    @SysLog("获取友情链接列表")
    @ApiOperation("获取友情链接列表")
    @GetMapping(value = "/getFriendlink")
    public SysResult getFriendlink(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort){
        SysPageResult<CmsFriendlink> cmsFriendlinkSysPageResult = cmsFriendlinkService.getAllFriendlinK(page,size,sort);
        return SysResultGenerator.genSuccessResult(cmsFriendlinkSysPageResult);
    }

    @SysLog("删除友情链接列表")
    @ApiOperation("删除友情链接列表")
    @PostMapping(value = "/deleteFriendlink")
    public SysResult deleteFriendlink(HttpServletRequest request, @RequestBody Set<Integer> friendlinkIds){
        for(Integer friendlinkId : friendlinkIds){
            cmsFriendlinkService.deleteById(friendlinkId);
        }
        return SysResultGenerator.genSuccessResult("友链删除成功");
    }

    @SysLog("编辑友情链接列表")
    @ApiOperation("编辑友情链接列表")
    @PostMapping(value = "/updateFriendlink")
    public SysResult updateFriendlink(HttpServletRequest request, @RequestBody CmsFriendlink cmsFriendlink){
        //编辑时，传过来的cmsFriendlink有friendlinkId
        if (ObjectUtil.isNull(cmsFriendlink.getFriendlinkId())) {
            return SysResultGenerator.genFailResult("该friendlinkId不存在");
        }
        //更新数据库
        if (Boolean.TRUE.equals(cmsFriendlinkService.updateFriendlink(cmsFriendlink))) {
            return SysResultGenerator.genSuccessResult("友链修改成功");
        }
        return SysResultGenerator.genFailResult("友链修改失败~~");
    }


}
