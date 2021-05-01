package cn.xuxinkai.modules.common.dto;

import cn.xuxinkai.modules.common.entity.SysDept;
import cn.xuxinkai.modules.common.entity.SysJob;
import cn.xuxinkai.modules.common.entity.SysRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 用户dto
 *
 * @author xuxinkai
 * @date 2021/03/04
 */
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -7055550004615563060L;
    /**
     * ID
     */
    private Long userId;
    /**
     * 部门名称
     */
    private Long deptId;
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户角色
     */
    private Set<SysRole> roles;

    /**
     * 用户岗位
     */
    private Set<SysJob> jobs;

    /**
     * 用户所属部门
     */
    private SysDept dept;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像地址
     */
    private String avatarName;
    /**
     * 头像真实路径
     */
    private String avatarPath;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 是否为admin账号
     */
    @JsonIgnore
    private Boolean isAdmin=false;
    /**
     * 状态：1启用、0禁用
     */
    @JsonIgnore
    private Long enabled;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新着
     */
    private String updateBy;
    /**
     * 修改密码的时间
     */
    @JsonIgnore
    private Date pwdResetTime;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 关联的qq号
     */
    private String qqid;

}
