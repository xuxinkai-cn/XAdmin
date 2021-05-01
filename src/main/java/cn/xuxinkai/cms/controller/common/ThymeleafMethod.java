package cn.xuxinkai.cms.controller.common;


import cn.hutool.core.util.ObjectUtil;
import cn.xuxinkai.cms.entity.CmsTag;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 在thymeleaf中自定义函数
 *
 * @author xuxinkai
 * @date 2021/03/26
 */
@Component("ThymeleafMethod")
public class ThymeleafMethod {

    public static String tagListToString(List<CmsTag> cmsTagList){
        if(ObjectUtil.isNull(cmsTagList)){
            return " ";
        }
        String tagStr = "";
        for(CmsTag cms : cmsTagList){
            tagStr = tagStr.concat(cms.getTagName()).concat(",");
        }
        return tagStr;
    }
}
