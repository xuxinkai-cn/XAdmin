package cn.xuxinkai.modules.system.rest;

import cn.hutool.core.date.DateTime;
import cn.xuxinkai.modules.common.entity.SysUser;
import cn.xuxinkai.modules.common.entity.ToolLocalStorage;
import cn.xuxinkai.modules.common.entity.ToolOssStorage;
import cn.xuxinkai.modules.common.exception.BadRequestException;
import cn.xuxinkai.modules.common.service.SysUserService;
import cn.xuxinkai.modules.common.service.ToolLocalStorageService;
import cn.xuxinkai.modules.common.service.ToolOssStorageService;
import cn.xuxinkai.modules.common.util.file.SysFileUtils;
import cn.xuxinkai.modules.common.util.result.SysResult;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import cn.xuxinkai.modules.logger.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuxinkai
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
@Api(tags = "SYSTEM：文件管理")
public class FileController {

    @Autowired
    private ToolLocalStorageService toolLocalStorageService;

    @Autowired
    private ToolOssStorageService toolOssStorageService;

    @Autowired
    private SysUserService sysUserService;

    @SysLog("上传文件到本地存储")
    @ApiOperation("上传文件到本地存储")
    @PostMapping(value = "/uploadFile")
    public SysResult uploadFile(HttpServletRequest request,@RequestParam String name, @RequestParam("file") MultipartFile file){
        ToolLocalStorage toolLocalStorage = toolLocalStorageService.create(name, file);
        return SysResultGenerator.genSuccessResult(toolLocalStorage);
    }

    @SysLog("上传文件到OSS存储")
    @ApiOperation("上传文件到OSS存储")
    @PostMapping(value = "/uploadOssFile")
    public SysResult uploadOssFile(HttpServletRequest request,@RequestParam String name, @RequestParam("file") MultipartFile file){
        ToolOssStorage toolOssStorage = toolOssStorageService.create(name,file);
        //todo: 此处暂时把静态资源访问链接写死，后面要做成系统配置项
        toolOssStorage.setPath("https://minio.xuxinkai.cn".concat(toolOssStorage.getPath()));
        return SysResultGenerator.genSuccessResult(toolOssStorage);
    }

    @SysLog("上传图片")
    @ApiOperation("上传图片")
    @PostMapping(value = "/uploadImage")
    public SysResult uploadImage(HttpServletRequest request, @RequestParam("file") MultipartFile file){
        log.info("上传图片");
        // 判断文件是否为图片
        String suffix = SysFileUtils.getExtensionName(file.getOriginalFilename());
        if(!SysFileUtils.IMAGE.equals(SysFileUtils.getFileType(suffix))){
            throw new BadRequestException("只能上传图片");
        }
        return this.uploadOssFile(request,null,file);
    }

    @SysLog("修改头像")
    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public SysResult updateAvatar(HttpServletRequest request, @RequestParam("avatar") MultipartFile file){
        log.info("修改头像");
        // 判断文件是否为图片
        String suffix = SysFileUtils.getExtensionName(file.getOriginalFilename());
        if(!SysFileUtils.IMAGE.equals(SysFileUtils.getFileType(suffix))){
            throw new BadRequestException("只能上传图片");
        }
        ToolOssStorage toolOssStorage = toolOssStorageService.create(null,file);
        SysUser sysUser = new SysUser();
        sysUser.setUsername(toolOssStorage.getUpdateBy());
        sysUser.setAvatarPath(toolOssStorage.getPath());
        sysUser.setAvatarName(toolOssStorage.getRealName());
        sysUser.setUpdateBy(toolOssStorage.getUpdateBy());
        sysUser.setUpdateTime(new DateTime());
        sysUserService.update(sysUser);
        return SysResultGenerator.genSuccessResult("头像修改成功~~");
    }

}
