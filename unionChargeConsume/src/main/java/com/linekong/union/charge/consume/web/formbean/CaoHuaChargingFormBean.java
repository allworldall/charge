package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class CaoHuaChargingFormBean {
    @DataValidate(description = "gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "草花唯一订单号", nullable = false, maxLength = 32)
    private String orderno;
    @DataValidate(description = "预支付订单号", nullable = false, maxLength = 64)
    private String orderno_cp;
    @DataValidate(description = "草花唯一用户ID", nullable = false, maxLength = 10)
    private String userid;
    @DataValidate(description = "订单金额（单位：分,整型）", nullable = false, maxLength = 10)
    private String order_amt;
    @DataValidate(description = "充值金额（单位：分,整型）", nullable = false, maxLength = 10)
    private String pay_amt;
    @DataValidate(description = "充值完成时间戳", nullable = false, maxLength = 10)
    private String pay_time;
    @DataValidate(description = "游戏透传字段", nullable = false, maxLength = 64)
    private String extra;
    @DataValidate(description = "签名", nullable = false, maxLength = 32 )
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

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderno_cp() {
        return orderno_cp;
    }

    public void setOrderno_cp(String orderno_cp) {
        this.orderno_cp = orderno_cp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrder_amt() {
        return order_amt;
    }

    public void setOrder_amt(String order_amt) {
        this.order_amt = order_amt;
    }

    public String getPay_amt() {
        return pay_amt;
    }

    public void setPay_amt(String pay_amt) {
        this.pay_amt = pay_amt;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public CaoHuaChargingFormBean() { super(); }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("gameName=").append(gameName);
        sb.append(",cpName=").append(cpName);
        sb.append(",orderno=").append(orderno);
        sb.append(",orderno_cp=").append(orderno_cp);
        sb.append(",userid=").append(userid);
        sb.append(",order_amt=").append(order_amt);
        sb.append(",pay_amt=").append(pay_amt);
        sb.append(",pay_time=").append(pay_time);
        sb.append(",extra=").append(extra);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
