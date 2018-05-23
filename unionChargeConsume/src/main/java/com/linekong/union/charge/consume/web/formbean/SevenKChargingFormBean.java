package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class SevenKChargingFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "预支付订单号",nullable = false)
    private String Payorderid;
    @DataValidate(description = "渠道订单号",nullable = false)
    private String Paycid;
    @DataValidate(description = "Uid", nullable = false)
    private String Account;
    @DataValidate(description = "区服id", nullable = false)
    private String Sid;
    @DataValidate(description = "角色ID", nullable =false)
    private String Roleid;
    @DataValidate(description = "道具id", nullable = false)
    private String Goodsid;
    @DataValidate(description = "人民币 单位元", nullable = false)
    private String Money;
    @DataValidate(description = "游戏币", nullable = false)
    private String Coins;
    @DataValidate(description = "充值时间 当前的时间戳", nullable = false)
    private String Time;
    @DataValidate(description = "自定义项 可做透传参数", nullable = false)
    private String Custominfo;
    @DataValidate(description = "签名", nullable = false)
    private String Sign;

    public SevenKChargingFormBean() {
    }

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

    public String getPayorderid() {
        return Payorderid;
    }

    public void setPayorderid(String payorderid) {
        Payorderid = payorderid;
    }

    public String getPaycid() {
        return Paycid;
    }

    public void setPaycid(String paycid) {
        Paycid = paycid;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getRoleid() {
        return Roleid;
    }

    public void setRoleid(String roleid) {
        Roleid = roleid;
    }

    public String getGoodsid() {
        return Goodsid;
    }

    public void setGoodsid(String goodsid) {
        Goodsid = goodsid;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getCoins() {
        return Coins;
    }

    public void setCoins(String coins) {
        Coins = coins;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCustominfo() {
        return Custominfo;
    }

    public void setCustominfo(String custominfo) {
        Custominfo = custominfo;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("gameName=").append(gameName);
        sb.append(",cpName=").append(cpName);
        sb.append(",Payorderid=").append(Payorderid);
        sb.append(",Paycid=").append(Paycid);
        sb.append(",Account=").append(Account);
        sb.append(",Sid=").append(Sid);
        sb.append(",Roleid=").append(Roleid);
        sb.append(",Goodsid=").append(Goodsid);
        sb.append(",Money=").append(Money);
        sb.append(",Coins=").append(Coins);
        sb.append(",Time=").append(Time);
        sb.append(",Custominfo=").append(Custominfo);
        sb.append(",Sign=").append(Sign);
        return sb.toString();
    }
}
