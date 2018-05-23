package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * 蝶恋互娱回调接受参数对象
 */
public class DieLianChargingFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "十六进制字符串形式的应用ID", nullable = false)
    private String app;
    @DataValidate(description = "可以传游戏中的道具ID、游戏中的用户ID等", nullable = false)
    private String cbi;
    @DataValidate(description = "支付完成时间", nullable = false)
    private String ct;
    @DataValidate(description = "金额（分）", nullable = false)
    private String fee;
    @DataValidate(description = "付费时间，订单创建服务器UTC时间戳（毫秒）", nullable = false)
    private String pt;
    @DataValidate(description = "渠道在易接服务器的ID", nullable = false)
    private String sdk;
    @DataValidate(description = "蓝港流水号", nullable = false)
    private String ssid;
    @DataValidate(description = "是否成功标志，1标示成功，其余都表示失败", nullable = false)
    private String st;
    @DataValidate(description = "易接服务器上的订单号", nullable = false)
    private String tcd;
    @DataValidate(description = "付费用户在渠道平台上的唯一标记", nullable = false)
    private String uid;
    @DataValidate(description = "协议版本号", nullable = false)
    private String ver;
    @DataValidate(description = "上述内容的数字签名", nullable = false)
    private String sign;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getCbi() {
        return cbi;
    }

    public void setCbi(String cbi) {
        this.cbi = cbi;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getTcd() {
        return tcd;
    }

    public void setTcd(String tcd) {
        this.tcd = tcd;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("gameName=").append(gameName);
        sb.append(",cpName=").append(cpName);
        sb.append(",app=").append(app);
        sb.append(",cbi=").append(cbi);
        sb.append(",ct=").append(ct);
        sb.append(",fee=").append(fee);
        sb.append(",pt=").append(pt);
        sb.append(",sdk=").append(sdk);
        sb.append(",ssid=").append(ssid);
        sb.append(",st=").append(st);
        sb.append(",tcd=").append(tcd);
        sb.append(",uid=").append(uid);
        sb.append(",ver=").append(ver);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
