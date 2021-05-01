package cn.xuxinkai.modules.system.rest;

import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.modules.common.entity.ToolEmailConfig;
import cn.xuxinkai.modules.common.service.ToolEmailConfigService;
import cn.xuxinkai.modules.common.util.SysEmailUtils;
import cn.xuxinkai.modules.common.util.result.SysResult;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import cn.xuxinkai.modules.logger.annotation.SysLog;
import cn.xuxinkai.modules.system.rest.vo.SampleEmailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xuxinkai
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
@Api(tags = "SYSTEM：邮件管理")
public class EmailController {

    @Autowired
    private ToolEmailConfigService toolEmailConfigService;

    @SysLog("获取邮箱配置")
    @ApiOperation("获取邮箱配置")
    @GetMapping(value = "/getEmailConfig")
    public SysResult getEmailConfig() {
        //目前数据库邮箱配置表有且只有一条记录
        Long configId = 1L;
        return SysResultGenerator.genSuccessResult(toolEmailConfigService.queryById(configId));
    }

    @SysLog("修改邮箱配置")
    @ApiOperation("修改邮箱配置")
    @PostMapping(value = "/changeEmailConfig")
    public SysResult changeEmailConfig(@RequestBody ToolEmailConfig toolEmailConfig) {
        //todo: 参数后续需要做校验
        if (ObjectUtil.isNotNull(toolEmailConfigService.update(toolEmailConfig))) {
            return SysResultGenerator.genSuccessResult("修改成功~");
        }
        return SysResultGenerator.genFailResult("修改失败~");
    }

    @SysLog("发送邮件")
    @ApiOperation("发送邮件")
    @PostMapping(value = "/sendSampleEmail")
    public SysResult sendSampleEmail(@RequestBody SampleEmailVo sampleEmailVo) {
        SysEmailUtils.sendSampleEmail(toolEmailConfigService.queryById(1L), sampleEmailVo);
        return SysResultGenerator.genSuccessResult("发送成功~^^~");
    }

}
