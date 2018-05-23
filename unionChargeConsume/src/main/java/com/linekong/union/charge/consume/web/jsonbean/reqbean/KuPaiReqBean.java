package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class KuPaiReqBean {
    private Integer transtype;
    private String cporderid;
    private String transid;
    private String appuserid;
    private String appid;
    private Integer waresid;
    private Integer feetype;
    private Float money;
    private String currency;
    private Integer result;
    private String transtime;
    private String cpprivate;
    private Integer paytype;
	public Integer getTranstype() {
		return transtype;
	}
	public void setTranstype(Integer transtype) {
		this.transtype = transtype;
	}
	public String getCporderid() {
		return cporderid;
	}
	public void setCporderid(String cporderid) {
		this.cporderid = cporderid;
	}
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	public String getAppuserid() {
		return appuserid;
	}
	public void setAppuserid(String appuserid) {
		this.appuserid = appuserid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public Integer getWaresid() {
		return waresid;
	}
	public void setWaresid(Integer waresid) {
		this.waresid = waresid;
	}
	public Integer getFeetype() {
		return feetype;
	}
	public void setFeetype(Integer feetype) {
		this.feetype = feetype;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getTranstime() {
		return transtime;
	}
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	public String getCpprivate() {
		return cpprivate;
	}
	public void setCpprivate(String cpprivate) {
		this.cpprivate = cpprivate;
	}
	public Integer getPaytype() {
		return paytype;
	}
	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("transtype=").append(transtype);
		sb.append(",cporderid=").append(cporderid);
		sb.append(",transid=").append(transid);
		sb.append(",appuserid=").append(appuserid);
		sb.append(",appid=").append(appid);
		sb.append(",waresid=").append(waresid);
		sb.append(",feetype=").append(feetype);
		sb.append(",money=").append(money);
		sb.append(",currency=").append(currency);
		sb.append(",result=").append(result);
		sb.append(",transtime=").append(transtime);
		sb.append(",cpprivate=").append(cpprivate);
		sb.append(",paytype=").append(paytype);
		return sb.toString();
	}
}
