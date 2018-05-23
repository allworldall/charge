package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * 松鼠充值回调接受实体类
 */
public class SongshuFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "预支付订单ID", nullable = false)
    private String cpOrderID;
    @DataValidate(description = "渠道订单ID", nullable = false)
    private String orderID;
    @DataValidate(description = "用户ID", nullable = false)
    private String userID;
    @DataValidate(description = "游戏ID", nullable = false)
    private String appID;
    @DataValidate(description = "游戏所属服ID", nullable = false)
    private String serverID;
    @DataValidate(description = "金额", nullable = false)
    private String money;
    @DataValidate(description = "RMB", nullable = false)
    private String currency;
    @DataValidate(description = "扩展数据", nullable = true)
    private String extension;
    @DataValidate(description = "1:成功,0:失败", nullable = false)
    private String state;
    @DataValidate(description = "md5加密", nullable = false)
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

    public String getCpOrderID() {
        return cpOrderID;
    }

    public void setCpOrderID(String cpOrderID) {
        this.cpOrderID = cpOrderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public SongshuFormBean() {
        super();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("cpOrderID=").append(cpOrderID);
        sb.append(",orderID=").append(orderID);
        sb.append(",userID=").append(userID);
        sb.append(",appID=").append(appID);
        sb.append(",serverID=").append(serverID);
        sb.append(",money=").append(money);
        sb.append(",currency=").append(currency);
        sb.append(",extension=").append(extension);
        sb.append(",state=").append(state);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }


}
