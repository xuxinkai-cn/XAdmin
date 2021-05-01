package cn.xuxinkai.cms.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.dao.CmsFriendlinkDao;
import cn.xuxinkai.cms.entity.CmsFriendlink;
import cn.xuxinkai.cms.service.CmsFriendlinkService;
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
 * (CmsFriendlink)表服务实现类
 *
 * @author makejava
 * @since 2021-04-04 14:49:49
 */
@Slf4j
@Service("cmsFriendlinkService")
public class CmsFriendlinkServiceImpl implements CmsFriendlinkService {
    @Resource
    private CmsFriendlinkDao cmsFriendlinkDao;

    /**
     * 通过ID查询单条数据
     *
     * @param friendlinkId 主键
     * @return 实例对象
     */
    @Override
    public CmsFriendlink queryById(Integer friendlinkId) {
        return this.cmsFriendlinkDao.queryById(friendlinkId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<CmsFriendlink> queryAllByLimit(int offset, int limit) {
        return this.cmsFriendlinkDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param cmsFriendlink 实例对象
     * @return 实例对象
     */
    @Override
    public CmsFriendlink insert(CmsFriendlink cmsFriendlink) {
        this.cmsFriendlinkDao.insert(cmsFriendlink);
        return cmsFriendlink;
    }

    /**
     * 修改数据
     *
     * @param cmsFriendlink 实例对象
     * @return 实例对象
     */
    @Override
    public CmsFriendlink update(CmsFriendlink cmsFriendlink) {
        this.cmsFriendlinkDao.update(cmsFriendlink);
        return this.queryById(cmsFriendlink.getFriendlinkId());
    }

    /**
     * 通过主键删除数据
     *
     * @param friendlinkId 主键
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Integer friendlinkId) {
        return this.cmsFriendlinkDao.deleteById(friendlinkId) > 0;
    }

    /**
     * 获取已审核通过的友情链接
     *
     * @return {@link List<CmsFriendlink>}
     */
    @Override
    public List<CmsFriendlink> getFriendLink() {
        CmsFriendlink cmsFriendlink = new CmsFriendlink();
        //status = 0 审核通过
        cmsFriendlink.setStatus(0);
        return cmsFriendlinkDao.queryAll(cmsFriendlink);
    }

    /**
     * 添加友情链接
     *
     * @param cmsFriendlink dto的朋友联系
     * @return {@link Boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addFriendlink(CmsFriendlink cmsFriendlink) {
        log.info("需要新增的友链是:{}",cmsFriendlink);
        // 需要校验：友链名称唯一、url唯一并且有前缀
        CmsFriendlink friendlink1 = new CmsFriendlink();
        friendlink1.setFriendlinkName(cmsFriendlink.getFriendlinkName());
        List<CmsFriendlink> cmsFriendlinkList = cmsFriendlinkDao.queryAll(friendlink1);
        if(ObjectUtil.isNotEmpty(cmsFriendlinkList)){
            for(CmsFriendlink friendlink : cmsFriendlinkList){
                if(Objects.equals(friendlink.getFriendlinkName(), cmsFriendlink.getFriendlinkName())){
                    throw new BadRequestException("友链名称已存在，请勿重复~~");
                }
            }
        }
        String http = "http://";
        String https = "https://";
        if (!(cmsFriendlink.getFriendlinkUrl().toLowerCase().startsWith(http)||cmsFriendlink.getFriendlinkUrl().toLowerCase().startsWith(https))) {
            throw new BadRequestException("URL必须以http://或者https://开头");
        }
        friendlink1.setFriendlinkName(null);
        friendlink1.setFriendlinkUrl(cmsFriendlink.getFriendlinkUrl());
        cmsFriendlinkList = cmsFriendlinkDao.queryAll(friendlink1);
        if(ObjectUtil.isNotEmpty(cmsFriendlinkList)){
            for(CmsFriendlink friendlink : cmsFriendlinkList){
                if(Objects.equals(friendlink.getFriendlinkUrl(), cmsFriendlink.getFriendlinkUrl())){
                    throw new BadRequestException("URL已存在，请勿重复~~");
                }
            }
        }
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //构建需要写入的cmsFriendLink
        cmsFriendlink.setUpdateBy(userDetailDto.getUsername());
        cmsFriendlink.setCreateBy(userDetailDto.getUsername());
        cmsFriendlink.setCreateTime(new DateTime());
        cmsFriendlink.setUpdateTime(new DateTime());
        //todo : 可能需要清理缓存，待完善
        return cmsFriendlinkDao.insert(cmsFriendlink) > 0;
    }

    /**
     * 分页获取所有的友情链接
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult <CmsFriendlink>}
     */
    @Override
    public SysPageResult<CmsFriendlink> getAllFriendlinK(int page, int size, String sort) {
        Map<String, Object> params = new HashMap<>(16);
        //查询限定条件-此处是限定pid = 0
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
        List<CmsFriendlink> cmsFriendlinkList = cmsFriendlinkDao.queryAllByPageUtil(pageUtil);
        log.info("cmsFriendlinkList为:{}", cmsFriendlinkList);
        Long total = cmsFriendlinkDao.queryTotalByField(null);
        return new SysPageResult<>(cmsFriendlinkList, total, pageUtil.getPageSize(), pageUtil.getPageNum());
    }

    /**
     * 更新友链
     *
     * @param cmsFriendlink cms链接
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateFriendlink(CmsFriendlink cmsFriendlink) {
        log.info("需要修改的友链是:{}",cmsFriendlink);
        // 需要校验：修改后友链名称唯一、url唯一并且有前缀
        CmsFriendlink friendlink1 = new CmsFriendlink();
        friendlink1.setFriendlinkName(cmsFriendlink.getFriendlinkName());
        List<CmsFriendlink> cmsFriendlinkList = cmsFriendlinkDao.queryAll(friendlink1);
        if(ObjectUtil.isNotEmpty(cmsFriendlinkList)){
            for(CmsFriendlink friendlink : cmsFriendlinkList){
                if(Objects.equals(friendlink.getFriendlinkName(), cmsFriendlink.getFriendlinkName()) && !friendlink.getFriendlinkId().equals(cmsFriendlink.getFriendlinkId())){
                    throw new BadRequestException("友链名称已存在，请勿重复~~");
                }
            }
        }
        String http = "http://";
        String https = "https://";
        if (!(cmsFriendlink.getFriendlinkUrl().toLowerCase().startsWith(http)||cmsFriendlink.getFriendlinkUrl().toLowerCase().startsWith(https))) {
            throw new BadRequestException("URL必须以http://或者https://开头");
        }
        friendlink1.setFriendlinkName(null);
        friendlink1.setFriendlinkUrl(cmsFriendlink.getFriendlinkUrl());
        cmsFriendlinkList = cmsFriendlinkDao.queryAll(friendlink1);
        if(ObjectUtil.isNotEmpty(cmsFriendlinkList)){
            for(CmsFriendlink friendlink : cmsFriendlinkList){
                if(Objects.equals(friendlink.getFriendlinkUrl(), cmsFriendlink.getFriendlinkUrl()) &&  !friendlink.getFriendlinkId().equals(cmsFriendlink.getFriendlinkId())){
                    throw new BadRequestException("URL已存在，请勿重复~~");
                }
            }
        }
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //构建需要写入的cmsFriendLink
        cmsFriendlink.setUpdateBy(userDetailDto.getUsername());
        cmsFriendlink.setUpdateTime(new DateTime());
        //todo : 可能需要清理缓存，待完善
        return cmsFriendlinkDao.update(cmsFriendlink) > 0;
    }
}