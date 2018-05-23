package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class YidianselfFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "游戏ID", nullable = false)
    private String app_id;
    @DataValidate(description = "预支付订单号", nullable = false)
    private String cp_order_id;
    @DataValidate(description = "玩家Id", nullable = false)
    private String mem_id;
    @DataValidate(description = "平台订单号", nullable = false)
    private String order_id;
    @DataValidate(description = "平台订单状态 1 未支付 2成功支付 3支付失败", nullable = false)
    private String order_status;
    @DataValidate(description = "订单下单时间 时间戳", nullable = false)
    private String pay_time;
    @DataValidate(description = "商品id", nullable = false)
    private String product_id;
    @DataValidate(description = "商品名称", nullable = false)
    private String product_name;
    @DataValidate(description = "商品价格(元);保留两位小", nullable = false)
    private String product_price;
    @DataValidate(description = "MD5 签名", nullable = false)
    private String sign;
    @DataValidate(description = "透传参数 CP下单时的原样放回", nullable = true)
    private String ext;

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

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getCp_order_id() {
        return cp_order_id;
    }

    public void setCp_order_id(String cp_order_id) {
        this.cp_order_id = cp_order_id;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("gameName=").append(gameName);
        sb.append(",cpName=").append(cpName);
        sb.append(",app_id=").append(app_id);
        sb.append(",cp_order_id=").append(cp_order_id);
        sb.append(",mem_id=").append(mem_id);
        sb.append(",order_id=").append(order_id);
        sb.append(",order_status=").append(order_status);
        sb.append(",pay_time=").append(pay_time);
        sb.append(",product_id=").append(product_id);
        sb.append(",product_name=").append(product_name);
        sb.append(",product_price=").append(product_price);
        sb.append(",sign=").append(sign);
        sb.append(",ext=").append(ext);
        return sb.toString();
    }
}
