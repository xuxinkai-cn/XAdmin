package cn.xuxinkai.modules.common.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.modules.common.config.SysFileProperties;
import cn.xuxinkai.modules.common.dao.ToolLocalStorageDao;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.entity.ToolLocalStorage;
import cn.xuxinkai.modules.common.exception.BadRequestException;
import cn.xuxinkai.modules.common.service.ToolLocalStorageService;
import cn.xuxinkai.modules.common.util.file.SysFileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 本地存储(ToolLocalStorage)表服务实现类
 *
 * @author makejava
 * @since 2021-04-07 22:10:42
 */
@Service("toolLocalStorageService")
public class ToolLocalStorageServiceImpl implements ToolLocalStorageService {
    @Resource
    private ToolLocalStorageDao toolLocalStorageDao;

    @Resource
    private SysFileProperties properties;

    /**
     * 通过ID查询单条数据
     *
     * @param storageId 主键
     * @return 实例对象
     */
    @Override
    public ToolLocalStorage queryById(Long storageId) {
        return this.toolLocalStorageDao.queryById(storageId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ToolLocalStorage> queryAllByLimit(int offset, int limit) {
        return this.toolLocalStorageDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param toolLocalStorage 实例对象
     * @return 实例对象
     */
    @Override
    public ToolLocalStorage insert(ToolLocalStorage toolLocalStorage) {
        this.toolLocalStorageDao.insert(toolLocalStorage);
        return toolLocalStorage;
    }

    /**
     * 修改数据
     *
     * @param toolLocalStorage 实例对象
     * @return 实例对象
     */
    @Override
    public ToolLocalStorage update(ToolLocalStorage toolLocalStorage) {
        this.toolLocalStorageDao.update(toolLocalStorage);
        return this.queryById(toolLocalStorage.getStorageId());
    }

    /**
     * 通过主键删除数据
     *
     * @param storageId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long storageId) {
        return this.toolLocalStorageDao.deleteById(storageId) > 0;
    }

    /**
     * 上传文件
     *
     * @param name
     * @param multipartFile
     * @return {@link ToolLocalStorage}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ToolLocalStorage create(String name, MultipartFile multipartFile) {
        SysFileUtils.checkSize(properties.getMaxSize(), multipartFile.getSize());
        String suffix = SysFileUtils.getExtensionName(multipartFile.getOriginalFilename());
        String type = SysFileUtils.getFileType(suffix);
        //上传本地文件
        File file = SysFileUtils.upload(multipartFile, properties.getPath().getPath() + type + File.separator);
        if (ObjectUtil.isNull(file)) {
            throw new BadRequestException("上传失败");
        }
        try {
            name = StringUtils.isBlank(name) ? SysFileUtils.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
            ToolLocalStorage toolLocalStorage = new ToolLocalStorage(
                    file.getName(),
                    name,
                    suffix,
                    file.getPath(),
                    type,
                    SysFileUtils.getSize(multipartFile.getSize())
            );
            //获取当前登录的用户信息
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
            toolLocalStorage.setCreateBy(userDetailDto.getUsername());
            toolLocalStorage.setCreateTime(new DateTime());
            toolLocalStorage.setUpdateBy(userDetailDto.getUsername());
            toolLocalStorage.setUpdateTime(new DateTime());
            toolLocalStorageDao.insert(toolLocalStorage);
            return toolLocalStorage;
        } catch (Exception e) {
            FileUtil.del(file);
            throw e;
        }
    }
}