package cn.xuxinkai.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.dao.CmsArticleDao;
import cn.xuxinkai.cms.dto.ArchiveDto;
import cn.xuxinkai.cms.dto.ArticleDto;
import cn.xuxinkai.cms.dto.ArticleWithCategoryDto;
import cn.xuxinkai.cms.entity.*;
import cn.xuxinkai.cms.service.*;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.service.SysUserService;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (CmsArticle)表服务实现类
 *
 * @author makejava
 * @since 2021-03-26 18:02:59
 */
@Slf4j
@Service("cmsArticleService")
public class CmsArticleServiceImpl implements CmsArticleService {
    @Resource
    private CmsArticleDao cmsArticleDao;

    @Autowired
    private CmsCategoryArticleService cmsCategoryArticleService;

    @Autowired
    private CmsCategoryService cmsCategoryService;

    @Autowired
    private CmsTagService cmsTagService;

    @Autowired
    private CmsTagArticleService cmsTagArticleService;


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CmsCommentService cmsCommentService;

    /**
     * 通过ID查询单条数据
     *
     * @param articleId 主键
     * @return 实例对象
     */
    @Override
    public CmsArticle queryById(Integer articleId) {
        return this.cmsArticleDao.queryById(articleId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<CmsArticle> queryAllByLimit(int offset, int limit) {
        return this.cmsArticleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param cmsArticle 实例对象
     * @return 实例对象
     */
    @Override
    public CmsArticle insert(CmsArticle cmsArticle) {
        this.cmsArticleDao.insert(cmsArticle);
        return cmsArticle;
    }

    /**
     * 修改数据
     *
     * @param cmsArticle 实例对象
     * @return 实例对象
     */
    @Override
    public CmsArticle update(CmsArticle cmsArticle) {
        this.cmsArticleDao.update(cmsArticle);
        return this.queryById(cmsArticle.getArticleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param articleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer articleId) {
        return this.cmsArticleDao.deleteById(articleId) > 0;
    }

    /**
     * 保存文章
     *
     * @param articleDto
     * @return {@link Boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveArticle(ArticleDto articleDto) {
        CmsArticle cmsArticle = new CmsArticle();
        BeanUtil.copyProperties(articleDto, cmsArticle);
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        cmsArticle.setUpdateBy(userDetailDto.getUsername());
        cmsArticle.setUpdateTime(new DateTime());
        //新增时，文章id为null
        if (ObjectUtil.isNull(articleDto.getArticleId())) {
            //新增文章
            cmsArticle.setCommentNum(0);
            cmsArticle.setViewNum(0);
            cmsArticle.setCreateBy(userDetailDto.getUsername());
            cmsArticle.setCreateTime(new DateTime());
            cmsArticleDao.insert(cmsArticle);
        } else {
            //更新文章
            cmsArticleDao.update(cmsArticle);
            //解除现有文章-栏目关联
            cmsCategoryArticleService.unbindCategoryWithArticle(null, cmsArticle.getArticleId());
            //解除现有文章-标签关联
            cmsTagArticleService.unbindTagWithArticle(null, cmsArticle.getArticleId());
        }
        //存入栏目与文章的关联，并更新栏目表下栏目数量
        if(ObjectUtil.isNotNull(articleDto.getCategory())){
            saveCategoryArticle(cmsArticle.getArticleId(), articleDto.getCategory().getCategoryId());
        }
        //存入栏目与标签的关联，并更新标签表下栏目数量
        saveTagArticle(cmsArticle.getArticleId(), articleDto.getTagIds());
        return Boolean.TRUE;
    }

    /**
     * 存入栏目与标签的关联，并更新标签表下栏目数量
     *
     * @param articleId
     * @param tagIds
     */
    private void saveTagArticle(Integer articleId, List<Integer> tagIds) {
        //todo : 后续要重构-改成批量插入和批量更新
        for (Integer tagId : tagIds) {
            //写入关联关系
            CmsTagArticle cmsTagArticle = new CmsTagArticle(articleId, tagId);
            cmsTagArticleService.insert(cmsTagArticle);
            //更新该标签下文章数量
            updateTagArticleNum(tagId);
        }
    }

    /**
     * 更新标签下文章数量
     *
     * @param tagId
     */
    @Override
    public void updateTagArticleNum(Integer tagId){
        int articleNum = cmsTagArticleService.queryCount(tagId);
        CmsTag cmsTagUpdate = new CmsTag(tagId, articleNum);
        cmsTagService.update(cmsTagUpdate);
    }


    /**
     * 存入栏目与文章的关联，并更新栏目表下栏目数量
     *
     * @param articleId
     * @param categoryId
     */
    private void saveCategoryArticle(Integer articleId, Integer categoryId) {
        // 写入关联关系
        CmsCategoryArticle categoryArticle = new CmsCategoryArticle(articleId, categoryId);
        cmsCategoryArticleService.insert(categoryArticle);
        // 更新栏目下文章数量
        updateCategoryArticleNum(categoryId);
    }

    @Override
    public void updateCategoryArticleNum(Integer categoryId){
        int articleNum = cmsCategoryArticleService.queryCount(categoryId);
        CmsCategory cmsCategory = new CmsCategory(categoryId, articleNum);
        cmsCategoryService.update(cmsCategory);
    }

    /**
     * 获取当前文章的上一篇文章
     *
     * @param articleId
     * @return {@link CmsArticle}
     */
    @Override
    public CmsArticle getBeforeArticle(Integer articleId) {
        return cmsArticleDao.queryBeforeArticle(articleId);
    }

    /**
     * 获取当前文章的下一篇文章
     *
     * @param articleId
     * @return {@link CmsArticle}
     */
    @Override
    public CmsArticle getAfterArticle(Integer articleId) {
        return cmsArticleDao.queryAfterArticle(articleId);
    }

    /**
     * 文章阅读量+1
     *
     * @param articleId
     * @param newViewNum
     */
    @Override
    public void updateArticleViewNum(Integer articleId,Integer newViewNum) {
        //todo ： 高并发情况下，可以将阅读量写入redis，然后起一个定时任务去读取redis更新文章阅读量
        CmsArticle cmsArticle = new CmsArticle(articleId,newViewNum);
        cmsArticleDao.update(cmsArticle);
    }

    /**
     * 更新文章下评论数
     *
     * @param articleId
     */
    @Override
    public void updateArticleCommentNum(Integer articleId) {
        Integer commentNum = cmsCommentService.countByArticleId(articleId);
        CmsArticle cmsArticle = new CmsArticle();
        cmsArticle.setArticleId(articleId);
        cmsArticle.setCommentNum(commentNum);
        cmsArticleDao.update(cmsArticle);
    }

    /**
     * 获取文章数量
     *
     * @param fieldMap
     * @return {@link Long}
     */
    @Override
    public Long countArticleNum(Map<String, Object> fieldMap) {
        return cmsArticleDao.queryTotalByField(fieldMap);
    }

    /**
     * 归档
     *
     * @return {@link Object}
     */
    @Override
    public List<ArchiveDto> getArchive() {
        //首先获取所有year month
        List<ArchiveDto> archiveDtoList = cmsArticleDao.queryYearAndMonth();
        archiveDtoList.forEach(archiveDto-> archiveDto.setCmsArticleList(cmsArticleDao.queryArticleByYearAndMonth(archiveDto.getYear(),archiveDto.getMonth())));
        return archiveDtoList;
    }

    /**
     * 分页查询指定tag下的文章列表
     *
     * @param page
     * @param size
     * @param sort
     * @param field
     * @return {@link SysPageResult<ArticleWithCategoryDto>}
     */
    @Override
    public SysPageResult<ArticleWithCategoryDto> getAllArticleByTag(int page, int size, String sort, Map<String, Object> field) {
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
        List<ArticleWithCategoryDto> articleWithCategoryDtoList = cmsArticleDao.queryAllByPageUtilAndTag(pageUtil);
        log.info("articleWithCategoryDtoList为:{}", articleWithCategoryDtoList);
        Long total = cmsArticleDao.queryTotalByFieldAndTag(field);
        return new SysPageResult<>(articleWithCategoryDtoList, total, pageUtil.getPageSize(), pageUtil.getPageNum());
    }


    /**
     * 获取文章列表
     *
     * @param page 第几页
     * @param size 梳理
     * @param sort 排序
     * @param field 限定字段
     * @return {@link SysPageResult<ArticleWithCategoryDto>}
     */
    @Override
    public SysPageResult<ArticleWithCategoryDto> getAllArticle(int page, int size, String sort,Map field) {
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
        List<ArticleWithCategoryDto> articleWithCategoryDtoList = cmsArticleDao.queryAllByPageUtil(pageUtil);
        log.info("articleWithCategoryDtoList为:{}", articleWithCategoryDtoList);
        Long total = cmsArticleDao.queryTotalByField(field);
        return new SysPageResult<>(articleWithCategoryDtoList, total, pageUtil.getPageSize(), pageUtil.getPageNum());
    }

    /**
     * 获取文章详情
     *
     * @param articleId
     * @return {@link ArticleDto}
     */
    @Override
    public ArticleDto getArticleInfo(Integer articleId) {
        CmsArticle cmsArticle = cmsArticleDao.queryById(articleId);
        if(ObjectUtil.isNull(cmsArticle)){
            return null;
        }
        ArticleDto articleDto = new ArticleDto();
        BeanUtil.copyProperties(cmsArticle, articleDto);
        //获取栏目信息
        CmsCategoryArticle cmsCategoryArticle = cmsCategoryArticleService.queryByArticleId(articleId);
        if(ObjectUtil.isNotNull(cmsCategoryArticle)){
            CmsCategory cmsCategory = cmsCategoryService.queryById(cmsCategoryArticle.getCategoryId());
            articleDto.setCategory(cmsCategory);
        }
        //获取标签信息
        List<CmsTagArticle> cmsTagArticleList = cmsTagArticleService.queryByArticleId(articleId);
        List<Integer> tagIds = new ArrayList<>();
        List<CmsTag> cmsTagList = new ArrayList<>();
        cmsTagArticleList.forEach(item -> {
            tagIds.add(item.getTagId());
            cmsTagList.add(cmsTagService.queryById(item.getTagId()));
        });
        articleDto.setCmsTagList(cmsTagList);
        articleDto.setTagIds(tagIds);
        articleDto.setAuthor(sysUserService.getUserDtoByUsername(cmsArticle.getCreateBy()));
        return articleDto;
    }
}