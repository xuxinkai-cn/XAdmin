package cn.xuxinkai.modules.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author xuxinkai
 * @date 2021/01/11
 */
public class SysThrowableUtils {
    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
