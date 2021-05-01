package cn.xuxinkai.cms.service;

import cn.xuxinkai.cms.entity.CmsFriendlink;
import cn.xuxinkai.modules.common.util.result.SysPageResult;

import java.util.List;

/**
 * (CmsFriendlink)表服务接口
 *
 * @author makejava
 * @since 2021-04-04 14:49:49
 */
public interface CmsFriendlinkService {

    /**
     * 通过ID查询单条数据
     *
     * @param friendlinkId 主键
     * @return 实例对象
     */
    CmsFriendlink queryById(Integer friendlinkId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CmsFriendlink> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param cmsFriendlink 实例对象
     * @return 实例对象
     */
    CmsFriendlink insert(CmsFriendlink cmsFriendlink);

    /**
     * 修改数据
     *
     * @param cmsFriendlink 实例对象
     * @return 实例对象
     */
    CmsFriendlink update(CmsFriendlink cmsFriendlink);

    /**
     * 通过主键删除数据
     *
     * @param friendlinkId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer friendlinkId);

    /**
     * 获取已审核通过的友情链接
     *
     * @return {@link List<CmsFriendlink>}
     */
    List<CmsFriendlink> getFriendLink();

    /**
     * 添加友情链接
     *
     * @param cmsFriendlink
     * @return {@link Boolean}
     */
    Boolean addFriendlink(CmsFriendlink cmsFriendlink);

    /**
     * 分页获取所有的友情链接
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult<CmsFriendlink>}
     */
    SysPageResult<CmsFriendlink> getAllFriendlinK(int page, int size, String sort);

    /**
     * 更新友链
     *
     * @param cmsFriendlink cms链接
     * @return {@link Boolean}
     */
    Boolean updateFriendlink(CmsFriendlink cmsFriendlink);
}