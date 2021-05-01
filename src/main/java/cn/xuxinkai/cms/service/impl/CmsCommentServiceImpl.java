package cn.xuxinkai.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.dao.CmsCommentDao;
import cn.xuxinkai.cms.dto.CommentDto;
import cn.xuxinkai.cms.entity.CmsComment;
import cn.xuxinkai.cms.service.CmsArticleService;
import cn.xuxinkai.cms.service.CmsCommentService;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (CmsComment)表服务实现类
 *
 * @author makejava
 * @since 2021-04-20 10:20:02
 */
@Slf4j
@Service("cmsCommentService")
public class CmsCommentServiceImpl implements CmsCommentService {
    @Resource
    private CmsCommentDao cmsCommentDao;

    @Autowired
    private CmsArticleService cmsArticleService;

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    @Override
    public CmsComment queryById(Integer commentId) {
        return this.cmsCommentDao.queryById(commentId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<CmsComment> queryAllByLimit(int offset, int limit) {
        return this.cmsCommentDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param cmsComment 实例对象
     * @return 实例对象
     */
    @Override
    public CmsComment insert(CmsComment cmsComment) {
        this.cmsCommentDao.insert(cmsComment);
        return cmsComment;
    }

    /**
     * 修改数据
     *
     * @param cmsComment 实例对象
     * @return 实例对象
     */
    @Override
    public CmsComment update(CmsComment cmsComment) {
        this.cmsCommentDao.update(cmsComment);
        return this.queryById(cmsComment.getCommentId());
    }

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer commentId) {
        return this.cmsCommentDao.deleteById(commentId) > 0;
    }

    /**
     * 创建评论
     *
     * @param commentDto
     * @return {@link Boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addComment(CommentDto commentDto) {
        if (ObjectUtil.isNull(commentDto.getCommentPid())) {
            //顶级评论的pid = 0
            commentDto.setCommentPid(0);
        }
        CmsComment cmsComment = new CmsComment();
        BeanUtil.copyProperties(commentDto, cmsComment);
        //状态设为待审核
        cmsComment.setStatus(1);
        cmsComment.setCreateTime(new DateTime());
        cmsCommentDao.insert(cmsComment);
        //更新文章评论数
        cmsArticleService.updateArticleCommentNum(commentDto.getArticleId());
        return Boolean.TRUE;
    }

    /**
     * 获取某篇文章下的评论数-包含待审核，已通过，未通过的，包含子评论
     *
     * @param articleId
     * @return {@link Integer}
     */
    @Override
    public Integer countByArticleId(Integer articleId) {
        return cmsCommentDao.countByArticleId(articleId);
    }

    /**
     * 获取文章下的评论树
     *
     * @param articleId
     * @return {@link List<CommentDto>}
     */
    @Override
    public List<CommentDto> getCommentTreeByArticleId(Integer articleId) {
        CmsComment cmsComment = new CmsComment();
        cmsComment.setArticleId(articleId);
        //状态为审核通过
        cmsComment.setStatus(0);
        List<CmsComment> cmsCommentList = cmsCommentDao.queryAll(cmsComment);
        return buildCommentTree(cmsCommentList);
    }

    /**
     * 分页查询评论
     *
     * @param page
     * @param size
     * @param sort
     * @param field
     * @param status
     * @return {@link SysPageResult<CommentDto>}
     */
    @Override
    public SysPageResult<CommentDto> getAllComment(int page, int size, String sort, Map field,String status) {
        Map<String, Object> params = new HashMap<>(16);
        //查询限定条件
        params.put("field", field);
        //第几页
        params.put("pageNum", page);
        //每页几条
        params.put("pageSize", size);
        //排序方式
        params.put("orderByClaus", sort.replace(",", " "));
        //构建翻页
        SysPageQueryUtils pageUtil = new SysPageQueryUtils(params);
        log.info(pageUtil.toString());
        List<CommentDto> commentList = cmsCommentDao.queryAllByPageUtil(pageUtil,status);
        log.info("commentList:{}", commentList);
        Long total = cmsCommentDao.queryTotalByField(field,status);
        return new SysPageResult<>(commentList, total, pageUtil.getPageSize(), pageUtil.getPageNum());
    }

    /**
     * 获取评论数量
     *
     * @param fieldMap
     * @return {@link Long}
     */
    @Override
    public Long countCommentNum(Map<String, Object> fieldMap) {
        return cmsCommentDao.queryTotalByField(fieldMap,null);
    }

    /**
     * 通过文章id删除评论
     *
     * @param articleId
     */
    @Override
    public void deleteByArticleId(Integer articleId) {
        cmsCommentDao.deleteByArticleId(articleId);
    }


    /**
     * 构建评论tree
     *
     * @param cmsCommentList
     * @return {@link List<CommentDto>}
     */
    private List<CommentDto> buildCommentTree(List<CmsComment> cmsCommentList) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(cmsCommentList)) {
            //获取顶级节点
            for (CmsComment cmsComment : cmsCommentList) {
                if (cmsComment.getCommentPid().equals(0)) {
                    CommentDto commentDto = new CommentDto();
                    BeanUtil.copyProperties(cmsComment, commentDto);
                    commentDtoList.add(commentDto);
                }
            }
            //获取父节点的所有子节点
            if (ObjectUtil.isNotEmpty(commentDtoList)) {
                for (CommentDto commentDto : commentDtoList) {
                    findCommentChildren(commentDto, cmsCommentList);
                }
            }
        }
        return commentDtoList;
    }

    /**
     * 递归获取所有子节点
     *
     * @param commentDto  父级
     * @param cmsCommentList
     */
    private void findCommentChildren(CommentDto commentDto, List<CmsComment> cmsCommentList) {
        for (CmsComment comment : cmsCommentList) {
            if (commentDto.getCommentId().equals(comment.getCommentPid())) {
                CommentDto item = new CommentDto();
                BeanUtil.copyProperties(comment, item);
                item.setPnickname(commentDto.getNickname());
                if (ObjectUtil.isNull(commentDto.getCommentChildren())) {
                    commentDto.setCommentChildren(new ArrayList<>());
                }
                commentDto.getCommentChildren().add(item);
            }
        }

        if (ObjectUtil.isNotNull(commentDto.getCommentChildren())) {
            for (CommentDto item : commentDto.getCommentChildren()) {
                findCommentChildren(item, cmsCommentList);
            }
        }
    }

}