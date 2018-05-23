package com.linekong.union.charge.consume.web.jsonbean.reqbean;

/**
 * 此类用于封装三星预支付接口中，去渠道下单的请求参数
 */
public class SamsungReqBean {
    private String appid;        //平台分配的应用编号
    private int waresid;         //应用中的商品编号
    private String waresname;    //商品名称，可选
    private String cporderid;    //预支付订单号
    private double  price;        //价钱， 可选
    private String currency="RMB";     //币种
    private String appuserid;     //username
    private String cpprivateinfo; //商户私有信息
    private String notifyurl;    //回调地址

    public SamsungReqBean() {
    }

    public SamsungReqBean(String appId, int waresid, double price, String payMentId, String userName, String callBackURL) {
        this.appid = appId;
        this.waresid = waresid;
        this.price = price;
        this.cporderid = payMentId;
        this.appuserid = userName;
        this.cpprivateinfo = payMentId;
        this.notifyurl = callBackURL;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public int getWaresid() {
        return waresid;
    }

    public void setWaresid(int waresid) {
        this.waresid = waresid;
    }

    public String getWaresname() {
        return waresname;
    }

    public void setWaresname(String waresname) {
        this.waresname = waresname;
    }

    public String getCporderid() {
        return cporderid;
    }

    public void setCporderid(String cporderid) {
        this.cporderid = cporderid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAppuserid() {
        return appuserid;
    }

    public void setAppuserid(String appuserid) {
        this.appuserid = appuserid;
    }

    public String getCpprivateinfo() {
        return cpprivateinfo;
    }

    public void setCpprivateinfo(String cpprivateinfo) {
        this.cpprivateinfo = cpprivateinfo;
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("appid=").append(appid);
        sb.append(",waresid=").append(waresid);
        sb.append(",waresname=").append(waresname);
        sb.append(",cporderid=").append(cporderid);
        sb.append(",price=").append(price);
        sb.append(",currency=").append(currency);
        sb.append(",appuserid=").append(appuserid);
        sb.append(",cpprivateinfo=").append(cpprivateinfo);
        sb.append(",notifyurl=").append(notifyurl);
        return sb.toString();
    }
}
