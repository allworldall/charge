package com.linekong.union.charge.consume.web.formbean;


import com.alibaba.fastjson.JSONObject;
import com.linekong.union.charge.consume.util.StringUtils;
import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class HuaShengChargingFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "1表示成功，其它均失败", nullable = false)
    private String state;
    @DataValidate(description = "商品ID", nullable = false)
    private String productID;
    @DataValidate(description = "订单号", nullable = false)
    private String orderID;
    @DataValidate(description = "用户ID", nullable = false)
    private String userID;
    @DataValidate(description = "渠道ID", nullable = false)
    private String channelID;
    @DataValidate(description = "游戏ID", nullable = false)
    private String gameID;
    @DataValidate(description = "游戏服务器ID", nullable = false)
    private String serverID;
    @DataValidate(description = "充值金额,单位分", nullable = false)
    private String money;
    @DataValidate(description = "货币类型，默认RMB")
    private String currency;
    @DataValidate(description = "预支付订单号", nullable = false)
    private String extension;
    @DataValidate(description = "加密类型", nullable = false)
    private String signType;
    @DataValidate(description = "签名值")
    private String sign;

    /**
     * 解析渠道回调的json串
     * @param jsonString
     */
    public boolean parseRequestData(String jsonString) {
        if(StringUtils.isEmpty(jsonString)) {
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        if(jsonObject.containsKey("state") && jsonObject.getInteger("state") == 1) {
            state = "1";
            JSONObject data = jsonObject.getJSONObject("data");
            productID = data.getString("productID");
            orderID = data.getString("orderID");
            userID = data.getString("userID");
            channelID = data.getString("channelID");
            gameID = data.getString("gameID");
            serverID = data.getString("serverID");
            money = data.getString("money");
            currency = data.getString("currency");
            extension = data.getString("extension");
            signType = data.getString("signType");
            sign = data.getString("sign");
        }else {
            state = String.valueOf(jsonObject.getInteger("state"));
            return false;
        }

        return true;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
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
        sb.append(",state=").append(state);
        sb.append(",productID=").append(productID);
        sb.append(",orderID=").append(orderID);
        sb.append(",userID=").append(userID);
        sb.append(",channelID=").append(channelID);
        sb.append(",gameID=").append(gameID);
        sb.append(",serverID=").append(serverID);
        sb.append(",money=").append(money);
        sb.append(",currency=").append(currency);
        sb.append(",extension=").append(extension);
        sb.append(",signType=").append(signType);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }

}