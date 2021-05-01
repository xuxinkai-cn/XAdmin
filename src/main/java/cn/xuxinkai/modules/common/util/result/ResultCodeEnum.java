package cn.xuxinkai.modules.common.util.result;

/**
 * 全局返回结果错误码
 *
 * @author 人间不值得<xuxinkai.cn>
 * @date 2020/10/15
 */
public enum ResultCodeEnum {
    /**
     * 默认成功
     */
    GL0(0, "SUCCESS"),
    /**
     * 默认失败
     */
    GL1(1, "FAIL"),
    /**
     * 参数异常
     */
    GL001(1001, "参数异常"),
    /**
     * 微服务不在线,或者网络超时
     */
    GL1002(1002, "微服务不在线,或者网络超时"),
    /**
     * 没有数据
     */
    GL1003(1003, "没有数据"),
    /**
     * 演示账号，无写权限
     */
    GL1004(1004, "演示账号，无写权限"),
    /**
     * 数据库插入异常
     */
    GL1005(1005, "数据库插入异常"),
    /**
     * 文件后辍不允许
     */
    GL1006(1006, "文件后辍不允许"),
    /**
     * 未登录，无访问权限
     */
    GL100(100, "未登录，无访问权限"),
    /**
     * Token 已过期
     */
    GL101(101, "Token 已过期"),
    /**
     * 请输入正确的验证码
     */
    GL102(102, "请输入正确的验证码"),
    /**
     * 请输入正确的验证码
     */
    GL103(103, "用户名或者密码错误"),
    /**
     * 无访问权限
     */
    GL403(403, "无访问权限"),
    /**
     * 未知异常
     */
    GL500(500, "未知异常"),
    /**
     * 找不到指定资源
     */
    GL404(404, "找不到指定资源");


    private ResultCodeEnum(int code, String message) {
        this.message = message;
        this.code = code;
    }

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 错误码
     */
    private final int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}

