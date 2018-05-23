package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class JisuFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "渠道服务器订单号" ,nullable = false)
    private String order;
    @DataValidate(description = "渠道服务器本地时间戳",nullable = true)
    private String time;
    @DataValidate(description = "订单金额，单位：分", nullable = false)
    private String amount;
    @DataValidate(description = "用户标识",nullable = false)
    private String uid;
    @DataValidate(description = "蓝港订单号", nullable = false)
    private String cp_order;
    @DataValidate(description = "预留透传字段", nullable = true)
    private String cp_info;
    @DataValidate(description = "预留透传字段", nullable = false)
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCp_order() {
        return cp_order;
    }

    public void setCp_order(String cp_order) {
        this.cp_order = cp_order;
    }

    public String getCp_info() {
        return cp_info;
    }

    public void setCp_info(String cp_info) {
        this.cp_info = cp_info;
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
        sb.append(",order=").append(order);
        sb.append(",time=").append(time);
        sb.append(",amount=").append(amount);
        sb.append(",uid=").append(uid);
        sb.append(",cp_order=").append(cp_order);
        sb.append(",cp_info=").append(cp_info);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
