package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class AiqiyiJuheFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "预支付订单号", nullable = false)
    private String cp_order_no;
    @DataValidate(description = "商品ID", nullable = false)
    private String product_id;
    @DataValidate(description = "服务器ID", nullable = false)
    private String server_id;
    @DataValidate(description = "用户在游戏中的角色ID", nullable = false)
    private String role_id;
    @DataValidate(description = "支付金额，单位：分", nullable = false)
    private String money;
    @DataValidate(description = "透传数据", nullable = true)
    private String extension;
    @DataValidate(description = "渠道订单号", nullable = false)
    private String union_order_id;
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

    public String getCp_order_no() {
        return cp_order_no;
    }

    public void setCp_order_no(String cp_order_no) {
        this.cp_order_no = cp_order_no;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getUnion_order_id() {
        return union_order_id;
    }

    public void setUnion_order_id(String union_order_id) {
        this.union_order_id = union_order_id;
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
        sb.append(",cp_order_no=").append(cp_order_no);
        sb.append(",product_id=").append(product_id);
        sb.append(",server_id=").append(server_id);
        sb.append(",role_id=").append(role_id);
        sb.append(",money=").append(money);
        sb.append(",extension=").append(extension);
        sb.append(",union_order_id=").append(union_order_id);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
