package cn.xuxinkai.modules.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SysUsersJobs)实体类
 *
 * @author makejava
 * @since 2021-03-03 15:51:09
 */
@Data
public class SysUsersJobs implements Serializable {
    private static final long serialVersionUID = -72339550946851874L;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 岗位ID
    */
    private Long jobId;

}