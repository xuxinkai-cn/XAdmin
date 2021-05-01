package cn.xuxinkai.modules.logger.rest;

import cn.xuxinkai.modules.common.entity.SysLog;
import cn.xuxinkai.modules.common.service.SysLogService;
import cn.xuxinkai.modules.common.util.result.SysPageResult;
import cn.xuxinkai.modules.common.util.result.SysResult;
import cn.xuxinkai.modules.common.util.result.SysResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuxinkai
 */
@RequestMapping(value = "/api/v1/log")
@RestController
@Slf4j
public class LogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping(value = "/getUserLog")
    @ApiOperation("查询某个用户的操作日志")
    public SysResult getUserLog(HttpServletRequest request, @RequestParam("page")int page,@RequestParam("size") int size,@RequestParam("sort") String sort){
        SysPageResult<SysLog> sysLogPageResult = sysLogService.getUserLog(page, size, sort);
        return SysResultGenerator.genSuccessResult(sysLogPageResult);
    }

}
