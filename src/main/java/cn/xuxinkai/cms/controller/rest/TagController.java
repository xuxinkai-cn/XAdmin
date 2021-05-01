package cn.xuxinkai.cms.controller.rest;

import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.entity.CmsTag;
import cn.xuxinkai.cms.service.CmsTagArticleService;
import cn.xuxinkai.cms.service.CmsTagService;
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
import java.util.List;
import java.util.Set;

/**
 * @author xuxinkai
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tag")
@RequiredArgsConstructor
@Api(tags = "CMS：标签管理")
public class TagController {

    @Autowired
    private CmsTagService cmsTagService;

    @Autowired
    private CmsTagArticleService cmsTagArticleService;

    @SysLog("新增标签")
    @ApiOperation("新增标签")
    @PostMapping(value = "/addTag")
    public SysResult addTag(HttpServletRequest request, @RequestBody CmsTag cmsTag) {
        //TODO : 需要保证某些字段必须存在，此处校验后续补充
        //新增时，传过来的cmsTag没有tagId
        if (ObjectUtil.isNotNull(cmsTag.getTagId())) {
            return SysResultGenerator.genFailResult("该tagId已存在");
        }
        //写入数据库
        if (Boolean.TRUE.equals(cmsTagService.addTag(cmsTag))) {
            return SysResultGenerator.genSuccessResult("标签新增成功");
        }
        return SysResultGenerator.genFailResult("标签新增失败~~");
    }

    @SysLog("获取标签列表")
    @ApiOperation("获取标签列表")
    @GetMapping(value = "/getTag")
    public SysResult getTag(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort){
        SysPageResult<CmsTag> cmsTagSysPageResult = cmsTagService.getAllTag(page,size,sort);
        return SysResultGenerator.genSuccessResult(cmsTagSysPageResult);
    }

    @SysLog("获取所有标签列表")
    @ApiOperation("获取所有标签列表")
    @GetMapping(value = "/getTags")
    public SysResult getTags(){
        List<CmsTag> cmsTagList = cmsTagService.getTags();
        return SysResultGenerator.genSuccessResult(cmsTagList);
    }

    @SysLog("编辑标签列表")
    @ApiOperation("编辑标签列表")
    @PostMapping(value = "/updateTag")
    public SysResult updateTag(HttpServletRequest request,  @RequestBody CmsTag cmsTag){
        //编辑时，传过来的cmsTag有tagId
        if (ObjectUtil.isNull(cmsTag.getTagId())) {
            return SysResultGenerator.genFailResult("标签的tagId不存在");
        }
        //更新数据库
        if (Boolean.TRUE.equals(cmsTagService.updateTag(cmsTag))) {
            return SysResultGenerator.genSuccessResult("标签修改成功");
        }
        return SysResultGenerator.genFailResult("标签修改失败~~");
    }

    @SysLog("删除标签")
    @ApiOperation("删除标签")
    @PostMapping(value = "/deleteTag")
    public SysResult deleteTag(HttpServletRequest request, @RequestBody Set<Integer> tagIds){
        for(Integer tagId : tagIds){
            //删除标签
            cmsTagService.deleteById(tagId);
            //清除该标签下文章的绑定关系
            cmsTagArticleService.unbindTagWithArticle(tagId,null);
        }
        return SysResultGenerator.genSuccessResult("友链删除成功");
    }
}
