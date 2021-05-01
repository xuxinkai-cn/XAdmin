package cn.xuxinkai.cms.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.dao.CmsCategoryDao;
import cn.xuxinkai.cms.entity.CmsCategory;
import cn.xuxinkai.cms.service.CmsCategoryService;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.exception.BadRequestException;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * (CmsCategory)表服务实现类
 *
 * @author makejava
 * @since 2021-04-05 16:38:40
 */
@Slf4j
@Service("cmsCategoryService")
public class CmsCategoryServiceImpl implements CmsCategoryService {
    @Resource
    private CmsCategoryDao cmsCategoryDao;


    /**
     * 通过ID查询单条数据
     *
     * @param categoryId 主键
     * @return 实例对象
     */
    @Override
    public CmsCategory queryById(Integer categoryId) {
        return this.cmsCategoryDao.queryById(categoryId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<CmsCategory> queryAllByLimit(int offset, int limit) {
        return this.cmsCategoryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param cmsCategory 实例对象
     * @return 实例对象
     */
    @Override
    public CmsCategory insert(CmsCategory cmsCategory) {
        this.cmsCategoryDao.insert(cmsCategory);
        return cmsCategory;
    }

    /**
     * 修改数据
     *
     * @param cmsCategory 实例对象
     * @return 实例对象
     */
    @Override
    public CmsCategory update(CmsCategory cmsCategory) {
        this.cmsCategoryDao.update(cmsCategory);
        return this.queryById(cmsCategory.getCategoryId());
    }

    /**
     * 通过主键删除数据
     *
     * @param categoryId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer categoryId) {
        return this.cmsCategoryDao.deleteById(categoryId) > 0;
    }

    /**
     * 新增栏目
     *
     * @param cmsCategory cms的类别
     * @return {@link Boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addCategory(CmsCategory cmsCategory) {
        //新增的栏目需要校验：名称唯一、别名唯一
        //校验标题唯一
        CmsCategory cmsCategoryQuery = new CmsCategory();
        cmsCategoryQuery.setCategoryName(cmsCategory.getCategoryName());
        List<CmsCategory> cmsCategoryList = cmsCategoryDao.queryAll(cmsCategoryQuery);
        if(ObjectUtil.isNotEmpty(cmsCategoryList)){
            for(CmsCategory category : cmsCategoryList){
                if(Objects.equals(category.getCategoryName(), cmsCategory.getCategoryName())){
                    throw new BadRequestException("栏目名称已存在，请勿重复");
                }
            }
        }
        //校验别名唯一
        cmsCategoryQuery.setCategoryName(null);
        cmsCategoryQuery.setCategorySlug(cmsCategory.getCategorySlug());
        cmsCategoryList = cmsCategoryDao.queryAll(cmsCategoryQuery);
        if(ObjectUtil.isNotEmpty(cmsCategoryList)){
            for(CmsCategory category: cmsCategoryList){
                if(Objects.equals(category.getCategorySlug(),cmsCategory.getCategorySlug())){
                    throw new BadRequestException("栏目别名已存在，请勿重复");
                }
            }
        }
        //补充完整cmsCategory
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //新增的栏目下文章数为0
        cmsCategory.setArticleNum(0);
        cmsCategory.setCreateBy(userDetailDto.getUsername());
        cmsCategory.setUpdateBy(userDetailDto.getUsername());
        cmsCategory.setCreateTime(new DateTime());
        cmsCategory.setUpdateTime(new DateTime());
        //写入数据库
        return cmsCategoryDao.insert(cmsCategory) > 0;
    }

    /**
     * 分页获取所有的栏目
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult <CmsCategory>}
     */
    @Override
    public SysPageResult<CmsCategory> getAllCategory(int page, int size, String sort) {
        Map<String, Object> params = new HashMap<>(16);
        //查询限定条件
        params.put("field", null);
        //第几页
        params.put("pageNum", page);
        //每页几条
        params.put("pageSize", size);
        //排序方式
        params.put("orderByClaus", sort.replace(",", " "));
        //构建翻页
        SysPageQueryUtils pageUtil = new SysPageQueryUtils(params);
        log.info(pageUtil.toString());
        List<CmsCategory> cmsCategoryList = cmsCategoryDao.queryAllByPageUtil(pageUtil);
        log.info("cmsCategoryList为:{}", cmsCategoryList);
        Long total = cmsCategoryDao.queryTotalByField(null);
        return new SysPageResult<>(cmsCategoryList, total, pageUtil.getPageSize(), pageUtil.getPageNum());
    }

    /**
     * 更新栏目信息
     *
     * @param cmsCategory cms的类别
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateCategory(CmsCategory cmsCategory) {
        log.info("需要修改的栏目是:{}",cmsCategory);
        // 需要校验：修改后名称唯一、别名唯一
        CmsCategory cmsCategoryQuery = new CmsCategory();
        cmsCategoryQuery.setCategoryName(cmsCategory.getCategoryName());
        List<CmsCategory> cmsCategoryList = cmsCategoryDao.queryAll(cmsCategoryQuery);
        if(ObjectUtil.isNotEmpty(cmsCategoryList)){
            for(CmsCategory category : cmsCategoryList){
                if(Objects.equals(category.getCategoryName(), cmsCategory.getCategoryName()) && !category.getCategoryId().equals(cmsCategory.getCategoryId())){
                    throw new BadRequestException("名称已存在，请勿重复~~");
                }
            }
        }

        cmsCategoryQuery.setCategoryName(null);
        cmsCategoryQuery.setCategorySlug(cmsCategory.getCategorySlug());
        cmsCategoryList = cmsCategoryDao.queryAll(cmsCategoryQuery);
        if(ObjectUtil.isNotEmpty(cmsCategoryList)){
            for(CmsCategory category : cmsCategoryList){
                if(Objects.equals(category.getCategorySlug(), cmsCategory.getCategorySlug()) &&  !category.getCategoryId().equals(cmsCategory.getCategoryId())){
                    throw new BadRequestException("别名已存在，请勿重复~~");
                }
            }
        }
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //构建需要写入的cmsFriendLink
        cmsCategory.setUpdateBy(userDetailDto.getUsername());
        cmsCategory.setUpdateTime(new DateTime());
        //todo : 可能需要清理缓存，待完善
        return cmsCategoryDao.update(cmsCategory) > 0;
    }

    /**
     * 获取栏目列表
     *
     * @return {@link List<CmsCategory>}
     */
    @Override
    public List<CmsCategory> getCategoryList() {
        return cmsCategoryDao.queryCategoryList();
    }

    /**
     * @param cmsCategoryQuery
     * @return {@link List<CmsCategory>}
     */
    @Override
    public List<CmsCategory> query(CmsCategory cmsCategoryQuery) {
        return cmsCategoryDao.queryAll(cmsCategoryQuery);
    }

}