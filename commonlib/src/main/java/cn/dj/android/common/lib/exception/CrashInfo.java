package cn.dj.android.common.lib.exception;

/**
 * 异常信息实体
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016/5/17
 */
public class CrashInfo {

    /**
     * 项目版本
     **/
    private String versionName;
    /**
     * 项目版本 代码
     **/
    private String versionCode;
    /**
     * 手机imei号
     **/
    private String imei;
    /**
     * 手机mac号
     **/
    private String mac;
    /**
     * 手机型号
     **/
    private String buildModel;
    /**
     * 系统版本
     **/
    private String buildVersionRelease;
    /**
     * 网络状态
     **/
    private boolean netState;
    /**
     * 网络类型
     **/
    private String netType;


    /**
     *
     */
    public CrashInfo() {
        super();
    }


    /**
     * @param versionName
     * @param versionCode
     * @param imei
     * @param mac
     * @param buildModel
     * @param buildVersionRelease
     * @param netState
     * @param netType
     */
    public CrashInfo(String versionName, String versionCode, String imei,
                     String mac, String buildModel, String buildVersionRelease,
                     boolean netState, String netType) {
        super();
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.imei = imei;
        this.mac = mac;
        this.buildModel = buildModel;
        this.buildVersionRelease = buildVersionRelease;
        this.netState = netState;
        this.netType = netType;
    }


    /**
     * @return the versionName
     */
    public String getVersionName() {
        return versionName;
    }


    /**
     * @param versionName the versionName to set
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }


    /**
     * @return the versionCode
     */
    public String getVersionCode() {
        return versionCode;
    }


    /**
     * @param versionCode the versionCode to set
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }


    /**
     * @return the imei
     */
    public String getImei() {
        return imei;
    }


    /**
     * @param imei the imei to set
     */
    public void setImei(String imei) {
        this.imei = imei;
    }


    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }


    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }


    /**
     * @return the buildModel
     */
    public String getBuildModel() {
        return buildModel;
    }


    /**
     * @param buildModel the buildModel to set
     */
    public void setBuildModel(String buildModel) {
        this.buildModel = buildModel;
    }


    /**
     * @return the buildVersionRelease
     */
    public String getBuildVersionRelease() {
        return buildVersionRelease;
    }


    /**
     * @param buildVersionRelease the buildVersionRelease to set
     */
    public void setBuildVersionRelease(String buildVersionRelease) {
        this.buildVersionRelease = buildVersionRelease;
    }


    /**
     * @return the netState
     */
    public boolean isNetState() {
        return netState;
    }


    /**
     * @param netState the netState to set
     */
    public void setNetState(boolean netState) {
        this.netState = netState;
    }


    /**
     * @return the netType
     */
    public String getNetType() {
        return netType;
    }


    /**
     * @param netType the netType to set
     */
    public void setNetType(String netType) {
        this.netType = netType;
    }


    @Override
    public String toString() {
        return "VersionName=" + versionName + "\n VersionCode = " + versionCode
                + "\n imei=" + imei + "\n mac=" + mac + "\n buildModel="
                + buildModel + "\n buildVersionRelease=" + buildVersionRelease
                + "\n netState=" + netState + "\n netType="
                + netType + "\n";
    }


}
