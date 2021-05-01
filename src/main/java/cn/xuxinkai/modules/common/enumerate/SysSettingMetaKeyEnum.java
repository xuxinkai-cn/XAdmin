package cn.xuxinkai.modules.common.enumerate;

public enum SysSettingMetaKeyEnum {
    //角色初始化标识符
    INIT_ROLE_FLAG("init_role_flag")
    ;
    private String metaKey;

    SysSettingMetaKeyEnum(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }
}
