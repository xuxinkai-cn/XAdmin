package cn.xuxinkai.modules.common.util;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

/**
 * @author xuxinkai
 * @date 2021/04/20
 */
@Slf4j
public class HtmlTemplateUtils {

    private HtmlTemplateUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static TemplateEngine engine = new TemplateEngine();

    /**
     * 使用Thymeleaf渲染html模板
     *
     * @param template
     * @param params
     * @return {@link String}
     */
    public static String renderString(String template, Map<String,Object> params){
        Context context = new Context();
        context.setVariables(params);
        String temp = engine.process(template, context);
        log.info(temp);
        return temp;
    }

    public static String renderTemplate(String templatePath,String templateName, Map<String,Object> params){
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        //设置模板所在目录，相对于classpath
        resolver.setPrefix(templatePath);
        //设置模板文件后缀
        resolver.setSuffix(".html");
        Context context = new Context();
        context.setVariables(params);
        engine.setTemplateResolver(resolver);
        return engine.process(templateName, context);
    }

}
