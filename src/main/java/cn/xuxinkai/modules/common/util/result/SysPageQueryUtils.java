package cn.xuxinkai.modules.common.util.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 分页查询工具类
 *
 * @author xuxinkai
 * @date 2021/03/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPageQueryUtils extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = -8384418742095104150L;
    //当前页码
    private int pageNum;
    //每页条数
    private int pageSize;
    /**
     * 排序方式
     */
    private transient Object orderByClaus;

    /**
     * 查询条件字段
     */
    private transient Object field;

    public SysPageQueryUtils(Map<String, Object> params) {
        this.putAll(params);
        //分页参数
        this.pageNum = Integer.parseInt(params.get("pageNum").toString());
        //页面条数
        this.pageSize = Integer.parseInt(params.get("pageSize").toString());
        //排序字段
        this.orderByClaus = params.get("orderByClaus");
        this.field = params.get("field");
        this.put("start", (pageNum - 1) * pageSize);
        this.put("pageNum", pageNum);
        this.put("pageSize", pageSize);
    }

}
