package cn.xuxinkai.modules.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色部门关联(SysRolesDepts)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Data
public class SysRolesDepts implements Serializable {
    private static final long serialVersionUID = -98798103032395152L;
    
    private Long roleId;
    
    private Long deptId;
}