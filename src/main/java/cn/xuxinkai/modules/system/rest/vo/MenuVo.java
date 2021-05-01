package cn.xuxinkai.modules.system.rest.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 传给前端的路由实体
 *
 * @author xuxinkai
 * @date 2021/03/12
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo implements Serializable {

    private static final long serialVersionUID = 9049023801798263725L;
    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private MenuMetaVo meta;

    private List<MenuVo> children;
}

