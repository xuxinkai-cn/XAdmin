package cn.xuxinkai.modules.common.util;


import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.xuxinkai.modules.common.entity.ToolEmailConfig;
import cn.xuxinkai.modules.system.rest.vo.SampleEmailVo;
import lombok.extern.slf4j.Slf4j;

/**
 * 邮件发送工具类
 *
 * @author xuxinkai
 * @date 2021/03/18
 */
@Slf4j
public class SysEmailUtils {
    private SysEmailUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 发送纯文本或html邮件
     *
     * @param toolEmailConfig 邮件配置工具
     * @param sampleEmailVo   sampleEmailVo
     */
    public static void sendSampleEmail(ToolEmailConfig toolEmailConfig, SampleEmailVo sampleEmailVo){
        MailAccount account = new MailAccount();
        account.setHost(toolEmailConfig.getHost());
        account.setPort(Integer.parseInt(toolEmailConfig.getPort()));
        account.setFrom(toolEmailConfig.getFromUser());
        account.setUser(toolEmailConfig.getUser());
        account.setPass(toolEmailConfig.getPass());
        account.setAuth(true);
        account.setSslEnable(true);
        try {
            String  result = MailUtil.send(account, sampleEmailVo.getTos(), sampleEmailVo.getSubject(), sampleEmailVo.getContent(), true);
            log.info("{},邮件发送结果为：{}",sampleEmailVo.getTos().toString(),result);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("{},邮件发送结果为：{}",sampleEmailVo.getTos().toString(),e);
        }

    }
}
