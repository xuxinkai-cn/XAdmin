package cn.xuxinkai.cms.controller;

import cn.xuxinkai.cms.controller.common.CmsInitMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuxinkai
 */
@Controller
public class BaseController {
    /**
     * @Description:
     */
    private Map<String, Object> pageMap = new HashMap<>();

    /**
     * @Description:
     * @Param: @param request
     * @Return: {@link Map<String,Object> }
     */
    @ModelAttribute
    public Map<String, Object> pageInit(HttpServletRequest request) {
        // 初始化执行一些方法
        CmsInitMethod cmsInitMethod = new CmsInitMethod();
        //获取友情链接
        pageMap.put("FriendLink",cmsInitMethod.getFriendLink());
        //获取标签云
        pageMap.put("TagCloud",cmsInitMethod.getTagCloud());
        //获取栏目列表
        pageMap.put("CategoryList",cmsInitMethod.getCategoryList());
        return pageMap;
    }
}
