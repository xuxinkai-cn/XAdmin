package cn.xuxinkai.modules.common.service.impl;

import cn.xuxinkai.modules.common.dao.SysDictDetailDao;
import cn.xuxinkai.modules.common.entity.SysDictDetail;
import cn.xuxinkai.modules.common.service.SysDictDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典详情(SysDictDetail)表服务实现类
 *
 * @author makejava
 * @since 2021-03-03 15:51:06
 */
@Service("sysDictDetailService")
public class SysDictDetailServiceImpl implements SysDictDetailService {
    @Resource
    private SysDictDetailDao sysDictDetailDao;

    /**
     * 通过ID查询单条数据
     *
     * @param detailId 主键
     * @return 实例对象
     */
    @Override
    public SysDictDetail queryById(Long detailId) {
        return this.sysDictDetailDao.queryById(detailId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysDictDetail> queryAllByLimit(int offset, int limit) {
        return this.sysDictDetailDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysDictDetail 实例对象
     * @return 实例对象
     */
    @Override
    public SysDictDetail insert(SysDictDetail sysDictDetail) {
        this.sysDictDetailDao.insert(sysDictDetail);
        return sysDictDetail;
    }

    /**
     * 修改数据
     *
     * @param sysDictDetail 实例对象
     * @return 实例对象
     */
    @Override
    public SysDictDetail update(SysDictDetail sysDictDetail) {
        this.sysDictDetailDao.update(sysDictDetail);
        return this.queryById(sysDictDetail.getDetailId());
    }

    /**
     * 通过主键删除数据
     *
     * @param detailId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long detailId) {
        return this.sysDictDetailDao.deleteById(detailId) > 0;
    }
}