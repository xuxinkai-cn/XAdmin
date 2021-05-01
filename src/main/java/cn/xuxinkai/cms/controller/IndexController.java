package cn.xuxinkai.cms.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.dto.ArticleDto;
import cn.xuxinkai.cms.dto.ArticleWithCategoryDto;
import cn.xuxinkai.cms.entity.CmsArticle;
import cn.xuxinkai.cms.entity.CmsCategory;
import cn.xuxinkai.cms.entity.CmsTag;
import cn.xuxinkai.cms.service.CmsArticleService;
import cn.xuxinkai.cms.service.CmsCategoryService;
import cn.xuxinkai.cms.service.CmsCommentService;
import cn.xuxinkai.cms.service.CmsTagService;
import cn.xuxinkai.modules.common.annotation.rest.AnonymousGetMapping;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author xuxinkai
 */
@Slf4j
@Controller
public class IndexController extends BaseController {

    private static final String CMS_THEME_NAME = "echo";

    @Autowired
    private CmsArticleService cmsArticleService;

    @Autowired
    private CmsCommentService cmsCommentService;

    @Autowired
    private CmsCategoryService cmsCategoryService;

    @Autowired
    private CmsTagService cmsTagService;

    /**
     * 请求此路径，将请求转发到第一页
     *
     * @param request 请求
     * @param pageMap 页面图
     * @return {@link String}
     */
    @AnonymousGetMapping(value = {"/", "index.html"})
    public String index(HttpServletRequest request, @ModelAttribute Map<String, Object> pageMap) {
        return this.page(request, 1, pageMap);
    }

    /**
     * 翻页
     *
     * @param request 请求
     * @param pageNum 页面num
     * @return {@link String}
     */
    @AnonymousGetMapping(value = {"/page/{pageNum}.html"})
    public String page(HttpServletRequest request, @PathVariable("pageNum") Integer pageNum, @ModelAttribute Map<String, Object> pageMap) {
        request.setAttribute("pageMap", pageMap);
        //获取当前页的 已发布的，按时间倒序的文章
        String sort = "create_time desc";
        Map<String,Object> field = new HashedMap<>(16);
        field.put("status",0);
        SysPageResult<ArticleWithCategoryDto> cmsArticlePageResult = cmsArticleService.getAllArticle(pageNum,10,sort,field);
        //分页数据
        request.setAttribute("cmsArticlePageResult", cmsArticlePageResult);
        log.info(cmsArticlePageResult.toString());
        //页面名称
        request.setAttribute("pageTitle", "首页");
        return "cms/".concat(CMS_THEME_NAME).concat("/index");
    }

    @AnonymousGetMapping(value = {"/article/{articleId}.html"})
    public String article(HttpServletRequest request, @PathVariable("articleId") Integer articleId, @ModelAttribute Map<String, Object> pageMap) {
        request.setAttribute("pageMap", pageMap);
        //获取文章信息
        ArticleDto articleDto = cmsArticleService.getArticleInfo(articleId);
        //需要判断文章状态，如果是为开放的需要返回404，如果文章不存在也要返回404
        if(ObjectUtil.isNull(articleDto) || !articleDto.getStatus().equals(0)){
            return "/error/404";
        }
        log.info(articleDto.toString());
        //获取上一篇文章
        CmsArticle beforeArticle = cmsArticleService.getBeforeArticle(articleId);
        //获取下一篇文章
        CmsArticle afterArticle = cmsArticleService.getAfterArticle(articleId);
        request.setAttribute("beforeArticle",beforeArticle);
        request.setAttribute("afterArticle",afterArticle);
        request.setAttribute("cmsArticle",articleDto);
        //文章阅读量+1
        cmsArticleService.updateArticleViewNum(articleId,articleDto.getViewNum()+1);
        return "cms/".concat(CMS_THEME_NAME).concat("/article");
    }

    /**
     * 文章归档
     *
     * @param request
     * @param pageMap
     * @return {@link String}
     */
    @AnonymousGetMapping(value = {"/archive.html"})
    public String archive(HttpServletRequest request, @ModelAttribute Map<String, Object> pageMap) {
        request.setAttribute("pageMap", pageMap);
        //获取已发布文章和已通过评论总数
        Map<String,Object> fieldMap = new HashedMap<>(8);
        fieldMap.put("status",0);
        request.setAttribute("articleNum",cmsArticleService.countArticleNum(fieldMap));
        request.setAttribute("commentNum",cmsCommentService.countCommentNum(fieldMap));
        request.setAttribute("archive",cmsArticleService.getArchive());
        return "cms/".concat(CMS_THEME_NAME).concat("/archive");
    }


    @AnonymousGetMapping(value = {"/category/{categorySlug}.html"})
    public String category(HttpServletRequest request,@PathVariable("categorySlug") String categorySlug, @ModelAttribute Map<String, Object> pageMap) {
        return this.categoryPage(request, categorySlug,1, pageMap);
    }

    @AnonymousGetMapping(value = {"/category/{categorySlug}/{pageNum}.html"})
    public String categoryPage(HttpServletRequest request, @PathVariable("categorySlug") String categorySlug,@PathVariable("pageNum") Integer pageNum, @ModelAttribute Map<String, Object> pageMap) {
        request.setAttribute("pageMap", pageMap);
        //获取栏目
        CmsCategory cmsCategoryQuery = new CmsCategory();
        cmsCategoryQuery.setCategorySlug(categorySlug);
        List<CmsCategory> cmsCategoryList = cmsCategoryService.query(cmsCategoryQuery);
        if(ObjectUtil.isNull(cmsCategoryList) || ObjectUtil.isEmpty(cmsCategoryList)){
            return "/error/404";
        }
        request.setAttribute("pathName","/category/".concat(categorySlug).concat("/"));
        //页面名称
        request.setAttribute("pageTitle", "分类 ".concat(cmsCategoryList.get(0).getCategoryName()).concat(" 下的文章"));
        //获取当前页的 已发布的，按时间倒序的文章
        String sort = "create_time desc";
        Map<String,Object> field = new HashedMap<>(16);
        field.put("status",0);
        field.put("category_slug",categorySlug);
        SysPageResult<ArticleWithCategoryDto> cmsArticlePageResult = cmsArticleService.getAllArticle(pageNum,10,sort,field);
        //分页数据
        request.setAttribute("cmsArticlePageResult", cmsArticlePageResult);
        log.info(cmsArticlePageResult.toString());
        return "cms/".concat(CMS_THEME_NAME).concat("/index");
    }

    @AnonymousGetMapping(value = {"/tag/{tagSlug}.html"})
    public String tag(HttpServletRequest request,@PathVariable("tagSlug") String tagSlug, @ModelAttribute Map<String, Object> pageMap) {
        return this.tagPage(request, tagSlug,1, pageMap);
    }

    @AnonymousGetMapping(value = {"/tag/{tagSlug}/{pageNum}.html"})
    public String tagPage(HttpServletRequest request, @PathVariable("tagSlug") String tagSlug,@PathVariable("pageNum") Integer pageNum, @ModelAttribute Map<String, Object> pageMap) {
        request.setAttribute("pageMap", pageMap);
        //获取标签
        CmsTag cmsTagQuery = new CmsTag();
        cmsTagQuery.setTagSlug(tagSlug);
        List<CmsTag> cmsTagList = cmsTagService.query(cmsTagQuery);
        if(ObjectUtil.isNull(cmsTagList) || ObjectUtil.isEmpty(cmsTagList)){
            return "/error/404";
        }
        request.setAttribute("pathName","/tag/".concat(tagSlug).concat("/"));
        //页面名称
        request.setAttribute("pageTitle", "标签 ".concat(cmsTagList.get(0).getTagName()).concat(" 下的文章"));
        //获取当前页的 已发布的，按时间倒序的文章
        String sort = "create_time desc";
        Map<String,Object> field = new HashedMap<>(16);
        field.put("status",0);
        field.put("tag_id",cmsTagList.get(0).getTagId());
        SysPageResult<ArticleWithCategoryDto> cmsArticlePageResult = cmsArticleService.getAllArticleByTag(pageNum,10,sort,field);
        //分页数据
        request.setAttribute("cmsArticlePageResult", cmsArticlePageResult);
        log.info(cmsArticlePageResult.toString());
        return "cms/".concat(CMS_THEME_NAME).concat("/index");
    }



}
