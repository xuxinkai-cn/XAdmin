package cn.xuxinkai.cms.controller.rest;


import cn.xuxinkai.cms.dto.CommentDto;
import cn.xuxinkai.cms.entity.CmsComment;
import cn.xuxinkai.cms.service.CmsCommentService;
import cn.xuxinkai.modules.common.annotation.rest.AnonymousGetMapping;
import cn.xuxinkai.modules.common.annotation.rest.AnonymousPostMapping;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import cn.xuxinkai.modules.common.util.result.SysResult;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import cn.xuxinkai.modules.logger.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuxinkai
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Api(tags = "CMS：评论管理")
public class CommentController {

    @Autowired
    private CmsCommentService cmsCommentService;

    @AnonymousPostMapping(value = {"/publish"})
    public SysResult publish(HttpServletRequest request,@Validated CommentDto commentDto) {
        log.info("commentDto为：{}",commentDto.toString());
        cmsCommentService.addComment(commentDto);
        return SysResultGenerator.genSuccessResult("评论成功~");
    }

    @AnonymousGetMapping(value = {"/getComment"})
    public SysResult getComment(HttpServletRequest request, @RequestParam("articleId") int articleId) {
        log.info("articleId为：{}",articleId);
        return SysResultGenerator.genSuccessResult(cmsCommentService.getCommentTreeByArticleId(articleId));
    }

    @SysLog("获取评论列表")
    @ApiOperation("获取评论列表")
    @GetMapping(value = "/getCommentPage")
    public SysResult getCommentPage(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort,@RequestParam("status") String status){
        SysPageResult<CommentDto> commentDtoSysPageResult = cmsCommentService.getAllComment(page,size,sort,null,status);
        return SysResultGenerator.genSuccessResult(commentDtoSysPageResult);
    }

    @SysLog("评论审核")
    @ApiOperation("评论审核")
    @GetMapping(value = "/commentDeal")
    public SysResult commentDeal(HttpServletRequest request, @RequestParam("commentId") int commentId, @RequestParam("status") int status){
        CmsComment cmsComment = new CmsComment();
        cmsComment.setCommentId(commentId);
        cmsComment.setStatus(status);
        cmsCommentService.update(cmsComment);
        return SysResultGenerator.genSuccessResult("操作成功");
    }



}
