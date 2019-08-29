package cn.dj.android.common.lib.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5工具类
 *
 * @author : gaodianjie / mwgao@gzulmu.com
 * @version : 1.0
 * @date : 2016-07-10
 */
public class MD5 {

    /**
     * 全局数组
     */
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5","6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 返回形式为数字跟字符串
     *
     * @param bByte the b byte
     * @return the string
     * @author : gaodianjie / 2016-07-10
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * 返回形式只为数字
     *
     * @param bByte the b byte
     * @return the string
     * @author : gaodianjie / 2016-07-10
     */
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param bByte the b byte
     * @return the string
     * @author : gaodianjie / 2016-07-10
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * Get md 5 code string.
     *
     * @param strObj the str obj
     * @return the string
     * @author : gaodianjie / 2016-07-10
     */
    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

}
