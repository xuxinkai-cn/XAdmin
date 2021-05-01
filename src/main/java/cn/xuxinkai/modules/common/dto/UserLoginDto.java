package cn.xuxinkai.modules.common.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录传递过来的参数实体 DTO
 *
 * @author xuxinkai
 */
@Getter
@Setter
public class UserLoginDto {
    @NotBlank(message = "请输入用户名")
    private String username;

    @NotBlank(message = "请输入密码")
    private String password;

    @NotBlank(message = "请输入验证码")
    private String code;

    @NotBlank(message = "参数错误")
    private String uuid = "";
}

