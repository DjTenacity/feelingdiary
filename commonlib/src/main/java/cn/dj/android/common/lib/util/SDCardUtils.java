package cn.dj.android.common.lib.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * SDCard工具类
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/5/17
 */
public class SDCardUtils {

    /**
     * 剩余内存限制
     */
    private static final long LOW_STORAGE_THRESHOLD = 1024 * 1024 * 10;

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 获得应用默认存储路径
     *
     * @return
     * @author : gaodianjie / Sep 19, 2014
     */
    public static String getDefaultAppPath() {
        return getAbsoluteSDCardPath() + AppUtils.getApplicationName();
    }

    /**
     * 获得sdcard路径
     *
     * @return
     * @author : gaodianjie / Sep 19, 2014
     */
    public static String getAbsoluteSDCardPath() {
        if (isSDCardEnable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        }
        return null;
    }

    /**
     * 获取SD卡路径
     * <p>先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) return null;
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        return strArray[1].replace("/.android_secure", "") + File.separator;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOCloseUtils.closeIO(bufferedReader);
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取可用的存储
     *
     * @return
     * @author : gaodianjie / Sep 19, 2014
     */
    @SuppressWarnings("deprecation")
    public static long getAvailableStorage() {

        String storageDirectory = null;
        storageDirectory = Environment.getExternalStorageDirectory().toString();

        try {
            StatFs stat = new StatFs(storageDirectory);
            long avaliableSize = ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
            return avaliableSize;
        } catch (RuntimeException ex) {
            return 0;
        }
    }

    /**
     * 检查内存是否充足
     *
     * @return
     * @author : gaodianjie / Sep 19, 2014
     */
    public static boolean checkAvailableStorage() {

        if (getAvailableStorage() < LOW_STORAGE_THRESHOLD) {
            return false;
        }

        return true;
    }

    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getSDCardInfo() {
        if (!isSDCardEnable()) return null;
        SDCardInfo sd = new SDCardInfo();
        sd.isExist = true;
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        sd.totalBlocks = sf.getBlockCountLong();
        sd.blockByteSize = sf.getBlockSizeLong();
        sd.availableBlocks = sf.getAvailableBlocksLong();
        sd.availableBytes = sf.getAvailableBytes();
        sd.freeBlocks = sf.getFreeBlocksLong();
        sd.freeBytes = sf.getFreeBytes();
        sd.totalBytes = sf.getTotalBytes();
        return sd.toString();
    }

    public static class SDCardInfo {
        boolean isExist;
        long    totalBlocks;
        long    freeBlocks;
        long    availableBlocks;
        long    blockByteSize;
        long    totalBytes;
        long    freeBytes;
        long    availableBytes;

        @Override
        public String toString() {
            return "isExist=" + isExist +
                    "\ntotalBlocks=" + totalBlocks +
                    "\nfreeBlocks=" + freeBlocks +
                    "\navailableBlocks=" + availableBlocks +
                    "\nblockByteSize=" + blockByteSize +
                    "\ntotalBytes=" + totalBytes +
                    "\nfreeBytes=" + freeBytes +
                    "\navailableBytes=" + availableBytes;
        }
    }

}
