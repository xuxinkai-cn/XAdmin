package cn.xuxinkai.modules.system.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xuxinkai
 * @date 2021/03/12
 */
@Data
@AllArgsConstructor
public class MenuMetaVo implements Serializable {

    private static final long serialVersionUID = 1869859860039196388L;
    private String title;

    private String icon;

    private Boolean noCache;
}