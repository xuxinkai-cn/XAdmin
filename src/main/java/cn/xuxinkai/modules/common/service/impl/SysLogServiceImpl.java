package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysLogDao;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.entity.SysLog;
import cn.xuxinkai.modules.common.service.SysLogService;
import cn.xuxinkai.modules.common.util.result.SysPageQueryUtils;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统日志(SysLog)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:08
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Resource
    private SysLogDao sysLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param logId 主键
     * @return 实例对象
     */
    @Override
    public SysLog queryById(Long logId) {
        return this.sysLogDao.queryById(logId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysLog> queryAllByLimit(int offset, int limit) {
        return this.sysLogDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysLog 实例对象
     * @return 实例对象
     */
    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void insert(SysLog sysLog) {
        this.sysLogDao.insert(sysLog);
    }

    /**
     * 修改数据
     *
     * @param sysLog 实例对象
     * @return 实例对象
     */
    @Override
    public SysLog update(SysLog sysLog) {
        this.sysLogDao.update(sysLog);
        return this.queryById(sysLog.getLogId());
    }

    /**
     * 通过主键删除数据
     *
     * @param logId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long logId) {
        return this.sysLogDao.deleteById(logId) > 0;
    }


    /**
     * 获取某个用户的操作日志-分页获取
     *
     * @param page 页面
     * @param size 大小
     * @param sort 排序
     * @return {@link SysPageResult<Object>}
     */
    @Override
    public SysPageResult<SysLog> getUserLog(int page, int size, String sort) {
        //获取当前登录的用户
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        Map<String, Object> fieldParams = new HashMap<>(2);
        fieldParams.put("username",userDetailDto.getUser().getUsername());

        Map<String, Object> params = new HashMap<>(16);
        //第几页
        params.put("pageNum", page);
        //每页几条
        params.put("pageSize", size);
        //排序方式
        params.put("orderByClaus", sort.replace(","," "));
        //查询限定条件-此处是限定username
        params.put("field",fieldParams);
        //构建翻页
        SysPageQueryUtils pageUtil = new SysPageQueryUtils(params);
        List<SysLog> sysLogList = sysLogDao.queryAllByPageUtil(pageUtil);
        Long total = sysLogDao.queryTotalByField(fieldParams);
        return new SysPageResult<>(sysLogList, total, pageUtil.getPageSize(), pageUtil.getPageNum());
    }
}