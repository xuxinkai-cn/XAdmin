package cn.xuxinkai.modules.common.util.result;

/**
 * Result快速构建方法类
 *
 * @author 人间不值得<xuxinkai.cn>
 * @date 2020/10/14
 */
public class SysResultGenerator {

    private SysResultGenerator(){
        throw new IllegalStateException("SysResultGenerator class");
    }

    /**
     * 成功结果快速生成方法
     *
     * @return {@link SysResult <Object>}
     */
    public static SysResult genSuccessResult() {
        SysResult sysResult = new SysResult();
        sysResult.setCode(ResultCodeEnum.GL0.getCode());
        sysResult.setMessage(ResultCodeEnum.GL0.getMessage());
        return sysResult;
    }

    /**
     * 成功结果生成方法，无data，自定义message
     *
     * @param message 消息
     * @return {@link SysResult}
     */
    public static SysResult genSuccessResult(String message) {
        SysResult sysResult = new SysResult();
        sysResult.setCode(ResultCodeEnum.GL0.getCode());
        sysResult.setMessage(message);
        return sysResult;
    }

    /**
     * 成功结果生成方法，无message，自定义data
     *
     * @param data 数据
     * @return {@link SysResult <Object>}
     */
    public static SysResult genSuccessResult(Object data) {
        SysResult sysResult = new SysResult();
        sysResult.setCode(ResultCodeEnum.GL0.getCode());
        sysResult.setMessage(ResultCodeEnum.GL0.getMessage());
        sysResult.setData(data);
        return sysResult;
    }

    /**
     * 成功结果生成方法，自定义message，自定义data
     *
     * @param data    数据
     * @param message 消息
     * @return {@link SysResult <Object>}
     */
    public static SysResult genSuccessResult(Object data, String message) {
        SysResult sysResult = new SysResult();
        sysResult.setCode(ResultCodeEnum.GL0.getCode());
        sysResult.setMessage(message);
        sysResult.setData(data);
        return sysResult;
    }

    /**
     * 失败结果快速生成方法
     *
     * @return {@link SysResult}
     */
    public static SysResult genFailResult() {
        SysResult sysResult = new SysResult();
        sysResult.setCode(ResultCodeEnum.GL1.getCode());
        sysResult.setMessage(ResultCodeEnum.GL1.getMessage());
        return sysResult;
    }

    /**
     * 失败结果生成方法，自定义message
     *
     * @param message 消息
     * @return {@link SysResult}
     */
    public static SysResult genFailResult(String message) {
        SysResult sysResult = new SysResult();
        sysResult.setCode(ResultCodeEnum.GL1.getCode());
        sysResult.setMessage(message);
        return sysResult;
    }

    /**
     * 失败结果生成方法，ResultCodeEnum快速构建，返回特定code和message
     *
     * @param resultCodeEnum 枚举结果代码
     * @return {@link SysResult}
     */
    public static SysResult genFailResult(ResultCodeEnum resultCodeEnum) {
        SysResult sysResult = new SysResult();
        sysResult.setCode(resultCodeEnum.getCode());
        sysResult.setMessage(resultCodeEnum.getMessage());
        return sysResult;
    }
}

