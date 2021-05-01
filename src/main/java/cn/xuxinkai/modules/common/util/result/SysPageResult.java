package cn.xuxinkai.modules.common.util.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysPageResult<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8297224397945376979L;
    /**
     * 第几页
     */
    private int pageNum;
    /**
     * 总页数
     */
    private Long totalPage;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 总数
     */
    private long total;

    /**
     * 数据集合
     */
    private transient List<T> rows;

    public SysPageResult(List<T> rows, long recordCount, int pageSize, int pageNum) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = recordCount;
        this.rows = rows;
        //计算总页数
        this.totalPage = (recordCount + pageSize-1 ) / pageSize;
    }
}

