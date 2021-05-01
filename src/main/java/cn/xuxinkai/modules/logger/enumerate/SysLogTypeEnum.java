package cn.xuxinkai.modules.logger.enumerate;

/**
 * 日志类型的枚举
 *
 * @author xuxinkai
 * @date 2020/12/26
 */
public enum SysLogTypeEnum {
    /**
     * 错误
     */
    LOG_ERROR("ERROR"),
    /**
     * 警告
     */
    LOG_WARN("WARN"),
    /**
     * 信息
     */
    LOG_INFO("INFO");

    /**
     * meta关键
     */
    private String myLogType;

    /**
     * @param myLogType
     */
    SysLogTypeEnum(String myLogType) {
        this.myLogType = myLogType;
    }

    /**
     * @return {@link String}
     */
    public String getMyLogType() {
        return myLogType;
    }

    /**
     * @param myLogType
     */
    public void setMyLogType(String myLogType) {
        this.myLogType = myLogType;
    }

}


