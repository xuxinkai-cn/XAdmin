package cn.xuxinkai.cms.controller.rest;

import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.dto.ArticleDto;
import cn.xuxinkai.cms.dto.ArticleWithCategoryDto;
import cn.xuxinkai.cms.entity.CmsCategoryArticle;
import cn.xuxinkai.cms.entity.CmsTagArticle;
import cn.xuxinkai.cms.service.CmsArticleService;
import cn.xuxinkai.cms.service.CmsCategoryArticleService;
import cn.xuxinkai.cms.service.CmsCommentService;
import cn.xuxinkai.cms.service.CmsTagArticleService;
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
 * @date 2021/04/12
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
@Api(tags = "CMS：文章管理")
public class ArticleController {

    @Autowired
    private CmsArticleService cmsArticleService;

    @Autowired
    private CmsCategoryArticleService cmsCategoryArticleService;

    @Autowired
    private CmsTagArticleService cmsTagArticleService;

    @Autowired
    private CmsCommentService cmsCommentService;

    @SysLog("保存文章")
    @ApiOperation("保存文章")
    @PostMapping(value = "/saveArticle")
    public SysResult saveArticle(HttpServletRequest request, @RequestBody ArticleDto articleDto) {
       if(Boolean.TRUE.equals( cmsArticleService.saveArticle(articleDto))){
           return SysResultGenerator.genSuccessResult("文章保存成功~~");
       }
        return SysResultGenerator.genFailResult("文章保存失败~~");
    }

    @SysLog("获取文章列表")
    @ApiOperation("获取文章列表")
    @GetMapping(value = "/getArticle")
    public SysResult getArticle(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort){
        SysPageResult<ArticleWithCategoryDto> articleDtoSysPageResult = cmsArticleService.getAllArticle(page,size,sort,null);
        return SysResultGenerator.genSuccessResult(articleDtoSysPageResult);
    }


    @SysLog("获取文章信息")
    @ApiOperation("获取文章信息")
    @GetMapping(value = "/getArticleInfo")
    public SysResult getArticleInfo(HttpServletRequest request, @RequestParam("articleId") Integer articleId){
        if(ObjectUtil.isNull(articleId)){
            return SysResultGenerator.genFailResult("文章Id不存在~~");
        }
        return SysResultGenerator.genSuccessResult(cmsArticleService.getArticleInfo(articleId));
    }

    @SysLog("删除文章")
    @ApiOperation("删除文章")
    @PostMapping(value = "/deleteArticle")
    public SysResult deleteCategory(HttpServletRequest request, @RequestBody Set<Integer> articleIds){
        for(Integer articleId : articleIds){
            //删除文章
            cmsArticleService.deleteById(articleId);
            //清除栏目-文章绑定关系
            CmsCategoryArticle cmsCategoryArticle = cmsCategoryArticleService.queryByArticleId(articleId);
            cmsCategoryArticleService.unbindCategoryWithArticle(null,articleId);
            // 更新栏目下文章数量
            cmsArticleService.updateCategoryArticleNum(cmsCategoryArticle.getCategoryId());
            //清除标签-文章绑定关系
            List<CmsTagArticle> cmsTagArticleList = cmsTagArticleService.queryByArticleId(articleId);
            cmsTagArticleService.unbindTagWithArticle(null,articleId);
            //更新标签下文章数量
            for(CmsTagArticle cmsTagArticle : cmsTagArticleList){
                cmsArticleService.updateTagArticleNum(cmsTagArticle.getTagId());
            }
            // 清除该文章下评论
            cmsCommentService.deleteByArticleId(articleId);
        }
        return SysResultGenerator.genSuccessResult("友链删除成功");
    }




}
