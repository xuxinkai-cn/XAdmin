package cn.xuxinkai.modules.common.enumerate;

public enum SysUsermetaKeyEnum {
    /**
     * 用户身份字段
     */
   PM_CAPABILITIES("pm_capabilities"),


    /**
     * 用户头像
     */
    CUSTOM_AVATAR("custom_avatar"),

    /**
     * 用户QQ
     */
    QQ("qq"),

    GITHUB("github"),

    /**
     * 用户描述
     */
    DESCRIPTION("description");

    /**
     *
     */
    private String metaKey;

    /**
     * @param metaKey
     */
    SysUsermetaKeyEnum(String metaKey) {
        this.metaKey = metaKey;
    }

    /**
     * 获取字段key
     *
     * @return {@link String}
     */
    public String getMetaKey() {
        return metaKey;
    }
}
