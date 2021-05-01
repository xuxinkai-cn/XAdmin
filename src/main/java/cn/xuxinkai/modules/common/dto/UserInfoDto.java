package cn.xuxinkai.modules.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xuxinkai
 */
@Data
public class UserInfoDto {
    @NotBlank(message = "请选择性别")
    private String gender;

    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @NotBlank(message = "手机号不能为空")
    private String phone;
}
