package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class YouximaoChargingFormBean2 {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "蓝港订单号", nullable = false)
    private String codeNo;
    @DataValidate(description = "游戏猫订单号", nullable = false)
    private String   tradeNo;
    @DataValidate(description = "用户id", nullable = false)
    private String openId;
    @DataValidate(description = "支付金额 单位元", nullable = false)
    private String amount;
    @DataValidate(description = "支付方式", nullable = false)
    private String  payWay;
    @DataValidate(description = "扩展字段", nullable = true)
    private String ext;
    @DataValidate(description = "充值回调url", nullable = false)
    private String notifyUrl;
    @DataValidate(description = "签名", nullable = false)
    private String sign;

    private String data;

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

    public String getCodeNo() {
        return codeNo;
    }

    public void setCodeNo(String codeNo) {
        this.codeNo = codeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("gameName=").append(gameName);
        sb.append(",cpName=").append(cpName);
        sb.append(",codeNo=").append(codeNo);
        sb.append(",tradeNo=").append(tradeNo);
        sb.append(",openId=").append(openId);
        sb.append(",amount=").append(amount);
        sb.append(",payWay=").append(payWay);
        sb.append(",ext=").append(ext);
        sb.append(",notifyUrl=").append(notifyUrl);
        sb.append(",sign=").append(sign);
        sb.append(",data=").append(data);
        return sb.toString();
    }
}
