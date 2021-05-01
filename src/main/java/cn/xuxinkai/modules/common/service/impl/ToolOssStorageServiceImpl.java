package cn.xuxinkai.modules.common.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.modules.common.config.SysFileProperties;
import cn.xuxinkai.modules.common.dao.ToolOssStorageDao;
import cn.xuxinkai.modules.common.dto.UserDetailDto;
import cn.xuxinkai.modules.common.entity.ToolOssStorage;
import cn.xuxinkai.modules.common.exception.BadRequestException;
import cn.xuxinkai.modules.common.service.ToolOssStorageService;
import cn.xuxinkai.modules.common.util.file.SysFileUtils;
import cn.xuxinkai.modules.common.util.file.oss.SysMinioUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OSS文件存储(ToolOssStorage)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 17:05:32
 */
@Service("toolOssStorageService")
public class ToolOssStorageServiceImpl implements ToolOssStorageService {
    @Resource
    private ToolOssStorageDao toolOssStorageDao;

    @Resource
    private SysFileProperties properties;

    /**
     * 通过ID查询单条数据
     *
     * @param storageId 主键
     * @return 实例对象
     */
    @Override
    public ToolOssStorage queryById(Long storageId) {
        return this.toolOssStorageDao.queryById(storageId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ToolOssStorage> queryAllByLimit(int offset, int limit) {
        return this.toolOssStorageDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param toolOssStorage 实例对象
     * @return 实例对象
     */
    @Override
    public ToolOssStorage insert(ToolOssStorage toolOssStorage) {
        this.toolOssStorageDao.insert(toolOssStorage);
        return toolOssStorage;
    }

    /**
     * 修改数据
     *
     * @param toolOssStorage 实例对象
     * @return 实例对象
     */
    @Override
    public ToolOssStorage update(ToolOssStorage toolOssStorage) {
        this.toolOssStorageDao.update(toolOssStorage);
        return this.queryById(toolOssStorage.getStorageId());
    }

    /**
     * 通过主键删除数据
     *
     * @param storageId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long storageId) {
        return this.toolOssStorageDao.deleteById(storageId) > 0;
    }

    /**
     * 上传文件
     *
     * @param name
     * @param multipartFile
     * @return {@link ToolOssStorage}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ToolOssStorage create(String name, MultipartFile multipartFile) {
        //校验文件大小
        long size = multipartFile.getSize();
        SysFileUtils.checkSize(properties.getMaxSize(), size);
        //上传过来的文件名字
        name = StringUtils.isBlank(name) ? SysFileUtils.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
        //获取文件后缀
        String suffix = SysFileUtils.getExtensionName(multipartFile.getOriginalFilename());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssS");
        String nowStr = "-" + format.format(date);
        //构建文件存储在oss里面的真实名字
        String realName = name + nowStr + "." + suffix;
        //上传文件到OSS
        SysMinioUtils sysMinioUtils = new SysMinioUtils();
        ToolOssStorage toolOssStorage = sysMinioUtils.upload(multipartFile, realName);
        if (ObjectUtil.isNull(toolOssStorage)) {
            throw new BadRequestException("上传失败");
        }
        //获取当前登录的用户信息
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();
        //构建toolOssStorage
        toolOssStorage.setRealName(realName);
        toolOssStorage.setName(name);
        toolOssStorage.setSuffix(suffix);
        toolOssStorage.setSize(String.valueOf(size));
        toolOssStorage.setCreateBy(userDetailDto.getUsername());
        toolOssStorage.setCreateTime(new DateTime());
        toolOssStorage.setUpdateBy(userDetailDto.getUsername());
        toolOssStorage.setUpdateTime(new DateTime());
        toolOssStorageDao.insert(toolOssStorage);
        return toolOssStorage;
    }
}