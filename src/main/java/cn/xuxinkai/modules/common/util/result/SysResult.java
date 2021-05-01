package cn.xuxinkai.modules.common.util.result;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 我的结果
 * 响应body的实体类
 *
 * @author 人间不值得<xuxinkai.cn>
 * @date 2020/10/13
 */
public class SysResult implements Serializable {

    private static final long serialVersionUID = -2368446516546812379L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private transient Object data;

    /**
     * 日期
     */
    private String date;


    /**
     * 无参构造函数
     */
    public SysResult() {
        // get the current date and time
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = dateTime.format(formatter);
    }


    /**
     * 我的结果
     * Result构造函数
     *
     * @param code    代码
     * @param data    数据
     * @param message 消息
     */
    public SysResult(Integer code, Object data, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
        // get the current date and time
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = dateTime.format(formatter);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


