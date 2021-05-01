package cn.xuxinkai.cms.controller.common;

import cn.xuxinkai.cms.entity.CmsCategory;
import cn.xuxinkai.cms.entity.CmsFriendlink;
import cn.xuxinkai.cms.entity.CmsTag;
import cn.xuxinkai.cms.service.CmsCategoryService;
import cn.xuxinkai.cms.service.CmsFriendlinkService;
import cn.xuxinkai.cms.service.CmsTagService;
import cn.xuxinkai.modules.common.util.SysSpringContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author xuxinkai
 */
@Slf4j
public class CmsInitMethod {

    private CmsFriendlinkService cmsFriendlinkService = SysSpringContextHolder.getBean(CmsFriendlinkService.class);

    private CmsTagService cmsTagService = SysSpringContextHolder.getBean(CmsTagService.class);

    private CmsCategoryService cmsCategoryService = SysSpringContextHolder.getBean(CmsCategoryService.class);


    /**
     *获取友情链接
     *
     * @return {@link List<CmsFriendlink>}
     */
    public List<CmsFriendlink> getFriendLink(){
        log.info("获取前台友情链接");
         return cmsFriendlinkService.getFriendLink();
   }

    /**
     * 获取标签云
     *
     * @return {@link Object}
     */
    public List<CmsTag> getTagCloud() {
        log.info("获取标签云");
        return cmsTagService.getTagCloud();
    }

    /**
     * 获取栏目列表
     *
     * @return {@link List<CmsCategory>}
     */
    public List<CmsCategory> getCategoryList() {
        log.info("获取栏目列表");
        return cmsCategoryService.getCategoryList();
    }
}

