package cn.xuxinkai.modules.common.util;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 密码处理及校验工具类
 *
 * @author xuxinkai
 * @date 2021/01/04
 */
public class SysPasswordUtils {

    /**
     * 采用 wordpress 的用户密码加密算法来生成 password
     *
     * @param password 密码
     * @return {@link String}
     */
    public static String useWordpressEncrypt(String password) {
        SysPasswordUtils sysPasswordUtils = new SysPasswordUtils();
        return sysPasswordUtils.wordpressEncrypt(password, sysPasswordUtils.genSalt(8));
    }

    /**
     * wordpress 密码校验方法
     *
     * @param myPassword     我的密码
     * @param hashedPassword 散列密码
     * @return boolean
     */
    public static boolean isValidWordpressPassword(String myPassword, String hashedPassword) {
        SysPasswordUtils sysPasswordUtils = new SysPasswordUtils();
        //对未处理的密码加密处理
        String delMyPassword = sysPasswordUtils.wordpressEncrypt(myPassword, sysPasswordUtils.getSaltFromPass(hashedPassword));
        return hashedPassword.equalsIgnoreCase(delMyPassword);
    }

    /**
     * wordpress 密码加密算法
     * （1）wordpress密码加密后的密文格式：
     * $P$B12345678huiyw4r7qhfuhs8yjmd6ef
     * $P$912345678huiyw4r7qhfuhs8yjmd6ef
     * 第一段：$P$格式固定
     * 第二段：只有一个字符。若php版本大于5.0则为B，否则为9
     * 第三段：8位salt
     * 第四段：22位，真正加密后的密码
     *
     * @param str  str
     * @param salt 盐
     * @return {@link String}
     */
    public String wordpressEncrypt(String str, String salt) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest((salt + str).getBytes());
            byte[] palin = str.getBytes();
            for (int i = 0; i < 8192; i++) {
                byte[] newplain = new byte[hash.length + palin.length];
                System.arraycopy(hash, 0, newplain, 0, hash.length);
                System.arraycopy(palin, 0, newplain, hash.length, palin.length);
                //MD5加密
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                hash = md5.digest(newplain);
            }
            int[] x = new int[hash.length];
            for (int i = 0; i < hash.length; i++) {
                x[i] = hash[i] & 0xff;
            }
            return "$P$B" + salt + encode64(x, 16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 随机生成salt
     *
     * @param n n
     * @return {@link String}
     */
    public String genSalt(int n) {
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "1234567890!@#$%^&*()_+").toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            //Random().nextInt()返回值为[0,n)
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    /**
     * 从加密后的密码中获取salt
     *
     * @param hashPass hashPass
     * @return {@link String}
     */
    public String getSaltFromPass(String hashPass) {
        //加密后的密码中第4到第11位位salt
        return StringUtils.substring(hashPass, 4, 12);
    }


    /**
     * encode64
     *
     * @param input  输入
     * @param number 长度
     * @return {@link String}
     */
    private String encode64(int[] input, int number) {
        String hash = "";
        int output = 0;
        int[] input_2 = new int[number];
        for (int i = 0; i < number; i++) {
            input_2[i] = input[i];
            //text_2.Text += "'" + input_2[i] + "'" ;
        }
        String itoa64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int output_2 = 0;
        int len_2 = 0;
        int value_2 = 0;
        for (int i = 0; i < number; i++) {
            int value = input_2[i];
            output = input_2[i];
            hash += itoa64.substring((value % 64 + 64) % 64, (value % 64 + 64) % 64 + 1);
            if (i + 1 <= number) {
                if (i + 1 < number) {
                    value = input_2[++i];
                    //左移8位
                    output_2 = (value << 8);
                    output = output + output_2;
                }

                value_2 = output;
                int len = Integer.toBinaryString(output).length();
                if (len - 6 > 0) {
                    //右移6位
                    output = (output >> 6);
                } else {
                    output = 0;
                }
                value = output;
                hash += itoa64.substring((value % 64 + 64) % 64, (value % 64 + 64) % 64 + 1);
            } else {
                break;
            }

            if (i + 1 < number) {
                value = input_2[++i];
                //左移16位
                output_2 = (value << 16);
                output = value_2 + output_2;
                value_2 = output;
                len_2 = Integer.toBinaryString(output).length();
                output_2 = output;
                //右移12位
                output = (output >> 12);
                value = output;
                hash += itoa64.substring((value % 64 + 64) % 64, (value % 64 + 64) % 64 + 1);
            } else {
                break;
            }
            if (i + 1 < number) {
                len_2 = Integer.toBinaryString(output_2).length();
                //右移18位
                output = (output_2 >> 18);
                value = output;
                hash += itoa64.substring((value % 64 + 64) % 64, (value % 64 + 64) % 64 + 1);
            }
        }
        return hash;
    }

}
