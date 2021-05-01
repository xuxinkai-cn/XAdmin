package cn.xuxinkai.modules.common.util.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 使用ServletResponse直接响应返回结果，建议与ResultGenerate搭配使用
 *
 * @author 人间不值得<xuxinkai.cn>
 * @date 2020/10/16
 */
public class SysResponseUtils {
    /**
     * 使用response输出JSON
     */
    public static void out(ServletResponse response, SysResult sysResult) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(sysResult));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 响应内容
     */
    public static void getResponse(HttpServletResponse httpServletResponse, String message) {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = httpServletResponse.getWriter()) {
            writer.print(JSONObject.toJSONString(SysResultGenerator.genSuccessResult(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

