package cn.xuxinkai.modules.common.service;

import cn.xuxinkai.modules.common.entity.ToolLocalStorage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 本地存储(ToolLocalStorage)表服务接口
 *
 * @author makejava
 * @since 2021-04-07 22:10:42
 */
public interface ToolLocalStorageService {

    /**
     * 通过ID查询单条数据
     *
     * @param storageId 主键
     * @return 实例对象
     */
    ToolLocalStorage queryById(Long storageId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ToolLocalStorage> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param toolLocalStorage 实例对象
     * @return 实例对象
     */
    ToolLocalStorage insert(ToolLocalStorage toolLocalStorage);

    /**
     * 修改数据
     *
     * @param toolLocalStorage 实例对象
     * @return 实例对象
     */
    ToolLocalStorage update(ToolLocalStorage toolLocalStorage);

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
     * @return {@link ToolLocalStorage}
     */
    ToolLocalStorage create(String name, MultipartFile multipartFile);
}