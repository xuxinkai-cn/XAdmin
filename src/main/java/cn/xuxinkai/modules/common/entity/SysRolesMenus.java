package cn.xuxinkai.modules.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单关联(SysRolesMenus)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Data
public class SysRolesMenus implements Serializable {
    private static final long serialVersionUID = -32514843754424783L;
    /**
    * 菜单ID
    */
    private Long menuId;
    /**
    * 角色ID
    */
    private Long roleId;

}