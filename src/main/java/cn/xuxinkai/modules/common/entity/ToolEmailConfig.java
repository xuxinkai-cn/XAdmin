package cn.xuxinkai.modules.common.entity;

import java.io.Serializable;

/**
 * 邮箱配置(ToolEmailConfig)实体类
 *
 * @author makejava
 * @since 2021-03-18 14:05:37
 */
public class ToolEmailConfig implements Serializable {
    private static final long serialVersionUID = -77597014807270568L;
    /**
    * ID
    */
    private Long configId;
    /**
    * 邮件服务器SMTP地址
    */
    private String host;
    /**
    * SMTP端口
    */
    private String port;
    /**
    * 发件人邮箱
    */
    private String fromUser;
    /**
    * 密码
    */
    private String pass;
    /**
    * 发件人用户名
    */
    private String user;


    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}