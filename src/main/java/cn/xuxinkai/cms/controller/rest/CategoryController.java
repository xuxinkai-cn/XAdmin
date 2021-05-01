package cn.xuxinkai.cms.controller.rest;

import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.entity.CmsCategory;
import cn.xuxinkai.cms.service.CmsCategoryArticleService;
import cn.xuxinkai.cms.service.CmsCategoryService;
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
 * @author xuxinkai
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Api(tags = "CMS：栏目管理")
public class CategoryController {

    @Autowired
    private CmsCategoryService cmsCategoryService;

    @Autowired
    private CmsCategoryArticleService cmsCategoryArticleService;

    @SysLog("新增栏目")
    @ApiOperation("新增栏目")
    @PostMapping(value = "/addCategory")
    public SysResult addCategory(HttpServletRequest request, @RequestBody CmsCategory cmsCategory) {
        //TODO : 需要保证某些字段必须存在，此处校验后续补充
        //新增时，传过来的cmsCategory没有categoryId
        if (ObjectUtil.isNotNull(cmsCategory.getCategoryId())) {
            return SysResultGenerator.genFailResult("该categoryId已存在");
        }
        //写入数据库
        if (Boolean.TRUE.equals(cmsCategoryService.addCategory(cmsCategory))) {
            return SysResultGenerator.genSuccessResult("栏目新增成功");
        }
        return SysResultGenerator.genFailResult("栏目新增失败~~");
    }

    @SysLog("获取栏目列表")
    @ApiOperation("获取栏目列表")
    @GetMapping(value = "/getCategory")
    public SysResult getCategory(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort){
        SysPageResult<CmsCategory> cmsCategorySysPageResult = cmsCategoryService.getAllCategory(page,size,sort);
        return SysResultGenerator.genSuccessResult(cmsCategorySysPageResult);
    }

    @SysLog("获取所有栏目列表-不分页")
    @ApiOperation("获取所有栏目列表-不分页")
    @GetMapping(value = "/getAllCategory")
    public SysResult getAllCategory(){
        return SysResultGenerator.genSuccessResult(cmsCategoryService.queryAllByLimit(0,200));
    }

    @SysLog("编辑栏目列表")
    @ApiOperation("编辑栏目列表")
    @PostMapping(value = "/updateCategory")
    public SysResult updateCategory(HttpServletRequest request,  @RequestBody CmsCategory cmsCategory){
        //编辑时，传过来的cmsCategory有categoryId
        if (ObjectUtil.isNull(cmsCategory.getCategoryId())) {
            return SysResultGenerator.genFailResult("栏目categoryId不存在");
        }
        //更新数据库
        if (Boolean.TRUE.equals(cmsCategoryService.updateCategory(cmsCategory))) {
            return SysResultGenerator.genSuccessResult("栏目修改成功");
        }
        return SysResultGenerator.genFailResult("栏目修改失败~~");
    }


    @SysLog("删除栏目")
    @ApiOperation("删除栏目")
    @PostMapping(value = "/deleteCategory")
    public SysResult deleteCategory(HttpServletRequest request, @RequestBody Set<Integer> categoryIds){
        for(Integer categoryId : categoryIds){
            //删除栏目
            cmsCategoryService.deleteById(categoryId);
            //清除该栏目下文章的绑定关系，没有绑定栏目的文章归属到未分类
            cmsCategoryArticleService.unbindCategoryWithArticle(categoryId,null);
        }
        return SysResultGenerator.genSuccessResult("友链删除成功");
    }

}
