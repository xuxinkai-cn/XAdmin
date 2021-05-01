package cn.xuxinkai.cms.dto;

import cn.xuxinkai.cms.entity.CmsArticle;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuxinkai
 */
@Data
public class ArchiveDto implements Serializable {
    private static final long serialVersionUID = -3304188671645802632L;

    private String year;

    private String month;

    private List<CmsArticle> cmsArticleList;
}
