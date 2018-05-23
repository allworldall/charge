package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * UC单机版，回调接收参数实体类
 */
public class UCStandAloneFormBean {
    @DataValidate(description = "请求地址中的gameName", nullable = false)
    private String gameName;
    @DataValidate(description = "请求地址中的cpName", nullable = false)
    private String cpName;
    @DataValidate(description = "版本号", nullable = false)
    private String ver;
    @DataValidate(description = "签名", nullable = false)
    private String sign;

    private Data data;

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

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("gameName=").append(gameName);
        sb.append(",cpName=").append(cpName);
        sb.append(",ver=").append(ver);
        sb.append(",sign=").append(sign);
        sb.append(",data=").append(data);
        return sb.toString();
    }

    public class Data {
        @DataValidate(description = "UC交易流水号", nullable = false)
        private String tradeId;
        @DataValidate(description = "交易时间", nullable = false)
        private String tradeTime;
        @DataValidate(description = "CP充值订单号", nullable = false)
        private String orderId;
        @DataValidate(description = "游戏编号", nullable = false)
        private String gameId;
        @DataValidate(description = "支付金额", nullable = false)
        private String amount;
        @DataValidate(description = "支付方式代码", nullable = false)
        private String payType;
        @DataValidate(description = "游戏合作商自定义参数", nullable = true)
        private String attachInfo;
        @DataValidate(description = "订单状态", nullable = false)
        private String orderStatus;
        @DataValidate(description = "订单失败原因详细描述", nullable = true)
        private String failedDesc;

        public String getTradeId() {
            return tradeId;
        }

        public void setTradeId(String tradeId) {
            this.tradeId = tradeId;
        }

        public String getTradeTime() {
            return tradeTime;
        }

        public void setTradeTime(String tradeTime) {
            this.tradeTime = tradeTime;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getAttachInfo() {
            return attachInfo;
        }

        public void setAttachInfo(String attachInfo) {
            this.attachInfo = attachInfo;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getFailedDesc() {
            return failedDesc;
        }

        public void setFailedDesc(String failedDesc) {
            this.failedDesc = failedDesc;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("tradeId=").append(tradeId);
            sb.append(",tradeTime=").append(tradeTime);
            sb.append(",orderId=").append(orderId);
            sb.append(",gameId=").append(gameId);
            sb.append(",amount=").append(amount);
            sb.append(",payType=").append(payType);
            sb.append(",attachInfo=").append(attachInfo);
            sb.append(",orderStatus=").append(orderStatus);
            sb.append(",failedDesc=").append(failedDesc);
            return sb.toString();
        }
    }
}
