package cn.xuxinkai.modules.system.rest;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.xuxinkai.modules.common.dto.UserInfoDto;
import cn.xuxinkai.modules.common.service.SysUserService;
import cn.xuxinkai.modules.common.service.ToolEmailConfigService;
import cn.xuxinkai.modules.common.util.SysEmailUtils;
import cn.xuxinkai.modules.common.util.SysRedisUtils;
import cn.xuxinkai.modules.common.util.result.SysResult;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import cn.xuxinkai.modules.logger.annotation.SysLog;
import cn.xuxinkai.modules.system.rest.vo.SampleEmailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author xuxinkai
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Api(tags = "SYSTEM：用户管理")
public class UserController {

    private final SysRedisUtils sysRedisUtils;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ToolEmailConfigService toolEmailConfigService;

    @SysLog(value = "编辑用户资料")
    @ApiOperation("编辑用户资料")
    @PostMapping(value = {"/editUserInfo"})
    public SysResult editUserInfo(@Validated @RequestBody UserInfoDto userInfoDto) {
        log.info("修改的用户资料为:{}", userInfoDto);
        //要先校验手机号不能重复，然后保存入库，最后刷新userDetail
        if (sysUserService.isUniquePhone(userInfoDto.getPhone()) && sysUserService.updateByUserInfo(userInfoDto)) {
            return SysResultGenerator.genSuccessResult("用户资料修改成功");
        }
        return SysResultGenerator.genFailResult("用户资料修改失败");
    }

    @SysLog(value = "修改登录密码")
    @ApiOperation("修改登录密码")
    @PostMapping(value = {"/changePassword"})
    public SysResult changerPassword(@RequestBody HashMap<String, String> paramMap) throws Exception {
        log.info("修改的密码信息为:{}\n{}", paramMap.get("oldPass"), paramMap.get("newPass"));
        if (!paramMap.containsKey("oldPass") || !paramMap.containsKey("newPass")) {
            return SysResultGenerator.genFailResult("参数错误");
        }
        if (sysUserService.changePassword(paramMap.get("oldPass"), paramMap.get("newPass"))) {
            return SysResultGenerator.genSuccessResult("密码修改成功");
        }
        return SysResultGenerator.genFailResult("密码修改失败");
    }

    @SysLog(value = "更换邮箱发送验证码")
    @ApiOperation("更换邮箱发送验证码")
    @GetMapping(value = {"/resetEmail"})
    public SysResult resetEmail(@RequestParam("email") String email) {
        //首先要检验格式
        if (ObjectUtil.isNotNull(email) && Validator.isEmail(email)) {
            //生成6位数字验证码，并且存到Redis，设置五分钟过期，最后发送邮件
            String code = RandomUtil.randomNumbers(6);
            sysRedisUtils.set(email, code, 5L, TimeUnit.MINUTES);
            SampleEmailVo sampleEmailVo = new SampleEmailVo();
            sampleEmailVo.setSubject("您正在申请更换邮箱");
            sampleEmailVo.setTos(ListUtil.toLinkedList(email));
            StrBuilder builder = StrBuilder.create();
            sampleEmailVo.setContent(builder.append("验证码为").append(code).toString());
            SysEmailUtils.sendSampleEmail(toolEmailConfigService.queryById(1L), sampleEmailVo);
            return SysResultGenerator.genSuccessResult("发送成功，验证码有效期5分钟");
        }
        return SysResultGenerator.genFailResult("请输入正确的邮箱~~");
    }


    @SysLog(value = "更换绑定的邮箱")
    @ApiOperation("更换绑定的邮箱")
    @PostMapping(value = {"/changeEmail"})
    public SysResult changeEmail(@RequestBody HashMap<String, String> paramMap) throws Exception{
        if (!paramMap.containsKey("email") || !paramMap.containsKey("code") || !paramMap.containsKey("password")) {
            return SysResultGenerator.genFailResult("参数错误");
        }
        if(sysUserService.changeEmail(paramMap.get("email"),paramMap.get("password"),paramMap.get("code"))){
            return SysResultGenerator.genSuccessResult("修改成功");
        }
        return SysResultGenerator.genFailResult("修改失败，请重试");
    }

}
