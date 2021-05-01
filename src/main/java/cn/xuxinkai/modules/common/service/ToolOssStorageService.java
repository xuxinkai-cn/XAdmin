package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.ToolOssStorage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * OSS文件存储(ToolOssStorage)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 17:05:32
 */
public interface ToolOssStorageService {

    /**
     * 通过ID查询单条数据
     *
     * @param storageId 主键
     * @return 实例对象
     */
    ToolOssStorage queryById(Long storageId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ToolOssStorage> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param toolOssStorage 实例对象
     * @return 实例对象
     */
    ToolOssStorage insert(ToolOssStorage toolOssStorage);

    /**
     * 修改数据
     *
     * @param toolOssStorage 实例对象
     * @return 实例对象
     */
    ToolOssStorage update(ToolOssStorage toolOssStorage);

    /**
     * 通过主键删除数据
     *
     * @param storageId 主键
     * @return 是否成功
     */
    boolean deleteById(Long storageId);

    /**
     * 上传文件
     *
     * @param name
     * @param multipartFile
     * @return {@link ToolOssStorage}
     */
    ToolOssStorage create(String name, MultipartFile multipartFile);
}