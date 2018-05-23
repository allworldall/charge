package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * 9377回调参数对象
 */
public class NTSSChargingFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "渠道订单号", nullable = false)
    private String order_id;
    @DataValidate(description = "用户名", nullable = false)
    private String username;
    @DataValidate(description = "游戏服务序号", nullable = false)
    private String server_id;
    @DataValidate(description = "开发商服务ID", nullable = true)
    private String _server;
    @DataValidate(description = "充值金额,单位元", nullable = false)
    private String money;
    @DataValidate(description = "透传参数,预支付订单号", nullable = false)
    private String extra_info;
    @DataValidate(description = "整型时间戳", nullable = false)
    private String time;
    @DataValidate(description = "签名", nullable = false)
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String get_server() {
        return _server;
    }

    public void set_server(String _server) {
        this._server = _server;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        sb.append(",order_id=").append(order_id);
        sb.append(",username=").append(username);
        sb.append(",server_id=").append(server_id);
        sb.append(",_server=").append(_server);
        sb.append(",money=").append(money);
        sb.append(",extra_info=").append(extra_info);
        sb.append(",time=").append(time);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
