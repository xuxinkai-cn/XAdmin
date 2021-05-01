package cn.xuxinkai.cms.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.dao.CmsTagDao;
import cn.xuxinkai.cms.entity.CmsTag;
import cn.xuxinkai.cms.service.CmsTagService;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.exception.BadRequestException;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * (CmsTag)表服务实现类
 *
 * @author makejava
 * @since 2021-04-05 19:45:45
 */
@Slf4j
@Service("cmsTagService")
public class CmsTagServiceImpl implements CmsTagService {
    @Resource
    private CmsTagDao cmsTagDao;

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    @Override
    public CmsTag queryById(Integer tagId) {
        return this.cmsTagDao.queryById(tagId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<CmsTag> queryAllByLimit(int offset, int limit) {
        return this.cmsTagDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param cmsTag 实例对象
     * @return 实例对象
     */
    @Override
    public CmsTag insert(CmsTag cmsTag) {
        this.cmsTagDao.insert(cmsTag);
        return cmsTag;
    }

    /**
     * 修改数据
     *
     * @param cmsTag 实例对象
     * @return 实例对象
     */
    @Override
    public CmsTag update(CmsTag cmsTag) {
        this.cmsTagDao.update(cmsTag);
        return this.queryById(cmsTag.getTagId());
    }

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer tagId) {
        return this.cmsTagDao.deleteById(tagId) > 0;
    }

    /**
     * 添加标签
     *
     * @param cmsTag cms标签
     * @return {@link Boolean}
     */
    @Override
    public Boolean addTag(CmsTag cmsTag) {
         //新增时，标签名称、别名需要唯一存在
        CmsTag cmsTagQuery = new CmsTag();
        cmsTagQuery.setTagName(cmsTag.getTagName());
        List<CmsTag> cmsTagList = cmsTagDao.queryAll(cmsTagQuery);
        //todo ： 所有的这种校验需要重构，只要查出的列表不为空就throw exception
        //todo : 查询时可以使用或查询进行重构，没必要把两个条件分开写
        if(ObjectUtil.isNotEmpty(cmsTagList)){
            for(CmsTag tag : cmsTagList){
                if(tag.getTagName().equals(cmsTag.getTagName())){
                    throw new BadRequestException("该名称已存在，请勿重复");
                }
            }
        }
        cmsTagQuery.setTagName(null);
        cmsTagQuery.setTagSlug(cmsTag.getTagSlug());
        cmsTagList = cmsTagDao.queryAll(cmsTagQuery);
        if(ObjectUtil.isNotEmpty(cmsTagList)){
            for(CmsTag tag : cmsTagList){
                if(tag.getTagSlug().equals(cmsTag.getTagSlug())){
                    throw new BadRequestException("该别名已存在，请勿重复");
                }
            }
        }
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //新增的栏目下文章数为0
        cmsTag.setArticleNum(0);
        cmsTag.setCreateBy(userDetailDto.getUsername());
        cmsTag.setUpdateBy(userDetailDto.getUsername());
        cmsTag.setCreateTime(new DateTime());
        cmsTag.setUpdateTime(new DateTime());
        return cmsTagDao.insert(cmsTag) > 0;
    }

    /**
     * 分页获取标签
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult <CmsTag>}
     */
    @Override
    public SysPageResult<CmsTag> getAllTag(int page, int size, String sort) {
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
        List<CmsTag> cmsTagList = cmsTagDao.queryAllByPageUtil(pageUtil);
        log.info("cmsTagList为:{}", cmsTagList);
        Long total = cmsTagDao.queryTotalByField(null);
        return new SysPageResult<>(cmsTagList, total, pageUtil.getPageSize(), pageUtil.getPageNum());
    }

    /**
     * 更新标签
     *
     * @param cmsTag cms标签
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateTag(CmsTag cmsTag) {
        log.info("需要修改的标签是:{}",cmsTag);
        // 需要校验：修改后名称唯一、别名唯一
        CmsTag cmsTagQuery = new CmsTag();
        cmsTagQuery.setTagName(cmsTag.getTagName());
        List<CmsTag> cmsTagList = cmsTagDao.queryAll(cmsTagQuery);
        if(ObjectUtil.isNotEmpty(cmsTagList)){
            for(CmsTag tag : cmsTagList){
                if(Objects.equals(tag.getTagName(), cmsTag.getTagName()) && !tag.getTagId().equals(cmsTag.getTagId())){
                    throw new BadRequestException("名称已存在，请勿重复~~");
                }
            }
        }

        cmsTagQuery.setTagName(null);
        cmsTagQuery.setTagSlug(cmsTag.getTagSlug());
        cmsTagList = cmsTagDao.queryAll(cmsTagQuery);
        if(ObjectUtil.isNotEmpty(cmsTagList)){
            for(CmsTag tag : cmsTagList){
                if(Objects.equals(tag.getTagSlug(), cmsTag.getTagSlug()) &&  !tag.getTagId().equals(cmsTag.getTagId())){
                    throw new BadRequestException("别名已存在，请勿重复~~");
                }
            }
        }
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //构建需要写入的cmsFriendLink
        cmsTag.setUpdateBy(userDetailDto.getUsername());
        cmsTag.setUpdateTime(new DateTime());
        //todo : 可能需要清理缓存，待完善
        return cmsTagDao.update(cmsTag) > 0;
    }

    /**
     * 获取所有标签列表
     *
     * @return {@link List<CmsTag>}
     */
    @Override
    public List<CmsTag> getTags() {
        CmsTag cmsTag = new CmsTag();
        return cmsTagDao.queryAll(cmsTag);
    }

    /**
     * 获取标签云
     *
     * @return {@link List<CmsTag>}
     */
    @Override
    public List<CmsTag> getTagCloud() {
        //需要获取文章数>0的标签列表
        return cmsTagDao.queryTagCloud();
    }

    /**
     * @param cmsTagQuery
     * @return {@link List<CmsTag>}
     */
    @Override
    public List<CmsTag> query(CmsTag cmsTagQuery) {
        return cmsTagDao.queryAll(cmsTagQuery);
    }
}