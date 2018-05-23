package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * 魅族聚合渠道回调参数实体类
 */
public class MeizuJuHeForBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "渠道订单号", nullable = false)
    private String order_id;
    @DataValidate(description = "预支付订单号", nullable = false)
    private String cp_order_id;
    @DataValidate(description = "游戏区服Id", nullable = false)
    private String game_server_id;
    @DataValidate(description = "游戏id", nullable = false)
    private String app_id;
    @DataValidate(description = "用户Id", nullable = false)
    private String uid;
    @DataValidate(description = "购买数量", nullable = true)
    private String buy_amount;
    @DataValidate(description = "购买商品单价", nullable = true)
    private String product_per_price;
    @DataValidate(description = "充值总金额,单位：元", nullable = false)
    private String total_price;
    @DataValidate(description = "支付状态，1：支付成功", nullable = false)
    private String status;
    @DataValidate(description = "产品名称", nullable = false)
    private String product_name;
    @DataValidate(description = "产品描述", nullable = true)
    private String product_desc;
    @DataValidate(description = "扩展字段", nullable = true)
    private String extend_param;
    @DataValidate(description = "订单创建时间", nullable = false)
    private String cTime;
    @DataValidate(description = "支付成功时间", nullable = false)
    private String success_time;
    @DataValidate(description = "通知时间", nullable = false)
    private String notify_time;
    @DataValidate(description = "签名串", nullable = false)
    private String sign;

    public MeizuJuHeForBean() {
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCp_order_id() {
        return cp_order_id;
    }

    public void setCp_order_id(String cp_order_id) {
        this.cp_order_id = cp_order_id;
    }

    public String getGame_server_id() {
        return game_server_id;
    }

    public void setGame_server_id(String game_server_id) {
        this.game_server_id = game_server_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBuy_amount() {
        return buy_amount;
    }

    public void setBuy_amount(String buy_amount) {
        if(buy_amount == null){
            buy_amount = "";
        }
        this.buy_amount = buy_amount;
    }

    public String getProduct_per_price() {
        return product_per_price;
    }

    public void setProduct_per_price(String product_per_price) {
        if(product_per_price == null){
            product_per_price = "";
        }
        this.product_per_price = product_per_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        if(product_desc == null){
            product_desc = "";
        }
        this.product_desc = product_desc;
    }

    public String getExtend_param() {
        return extend_param;
    }

    public void setExtend_param(String extend_param) {
        if(extend_param == null){
            extend_param = "";
        }
        this.extend_param = extend_param;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getSuccess_time() {
        return success_time;
    }

    public void setSuccess_time(String success_time) {
        this.success_time = success_time;
    }

    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
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
        sb.append(",cp_order_id=").append(cp_order_id);
        sb.append(",game_server_id=").append(game_server_id);
        sb.append(",app_id=").append(app_id);
        sb.append(",uid=").append(uid);
        sb.append(",buy_amount=").append(buy_amount);
        sb.append(",product_per_price=").append(product_per_price);
        sb.append(",total_price=").append(total_price);
        sb.append(",status=").append(status);
        sb.append(",product_name=").append(product_name);
        sb.append(",product_desc=").append(product_desc);
        sb.append(",extend_param=").append(extend_param);
        sb.append(",cTime=").append(cTime);
        sb.append(",success_time=").append(success_time);
        sb.append(",notify_time=").append(notify_time);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
