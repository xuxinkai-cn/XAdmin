package cn.xuxinkai.modules.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author xuxinkai
 */
@Getter
@Setter
public class MenuDto implements Serializable {
    private static final long serialVersionUID = -6096026107177009388L;

    /**
     * ID
     */
    private Long menuId;

    private List<MenuDto> children;

    /**
     * 上级菜单ID
     */
    private Long pid;
    /**
     * 子菜单数目
     */
    private Integer subCount;

    /**
     * 菜单类型
     */
    private Integer type;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 组件名称
     */
    private String name;
    /**
     * 组件
     */
    private String component;
    /**
     * 排序
     */
    private Integer menuSort;
    /**
     * 图标
     */
    private String icon;
    /**
     * 链接地址
     */
    private String path;
    /**
     * 是否外链
     */
    private Boolean iframe;
    /**
     * 缓存
     */
    private Boolean cache;
    /**
     * 隐藏
     */
    private Boolean hidden;
    /**
     * 权限
     */
    private String permission;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }

    public String getLabel() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuDto menuDto = (MenuDto) o;
        return Objects.equals(menuId, menuDto.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
