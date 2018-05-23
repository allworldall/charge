package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class EagleHaodongFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "用户id", nullable = false)
    private String userID;
    @DataValidate(description = "订单号", nullable = false)
    private String orderID;
    @DataValidate(description = "渠道号", nullable = false)
    private String channelID;
    @DataValidate(description = "游戏ID", nullable = false)
    private String gameID;
    @DataValidate(description = "游戏服ID", nullable = false)
    private String serverID;
    @DataValidate(description = "角色ID", nullable = false)
    private String roleID;
    @DataValidate(description = "商品ID", nullable = false)
    private String productID;
    @DataValidate(description = "充值金额，单位分", nullable = false)
    private String money;
    @DataValidate(description = "货币类型，默认RMB", nullable = false)
    private String currency;
    @DataValidate(description = "自定义参数", nullable = false)
    private String extension;
    @DataValidate(description = "签名MD5", nullable = false)
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    
    public String toString(){
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("gameName=").append(gameName).append(",")
    		  .append("cpName=").append(cpName).append(",")
    		  .append("userID=").append(userID).append(",")
    		  .append("orderID=").append(orderID).append(",")
    		  .append("channelID=").append(channelID).append(",")
    		  .append("gameID=").append(gameID).append(",")
    		  .append("serverID=").append(serverID).append(",")
    		  .append("roleID=").append(roleID).append(",")
    		  .append("productID=").append(productID).append(",")
    		  .append("money=").append(money).append(",")
    		  .append("currency=").append(currency).append(",")
    		  .append("extension=").append(extension).append(",")
    		  .append("sign=").append(sign);
    	return buffer.toString();
    }
    
}
