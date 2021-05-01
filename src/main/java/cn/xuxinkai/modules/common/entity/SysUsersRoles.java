package cn.xuxinkai.modules.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色关联(SysUsersRoles)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Data
public class SysUsersRoles implements Serializable {
    private static final long serialVersionUID = 376426868720789746L;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 角色ID
    */
    private Long roleId;

}