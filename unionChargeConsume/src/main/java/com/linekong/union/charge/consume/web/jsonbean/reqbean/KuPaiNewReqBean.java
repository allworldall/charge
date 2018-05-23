package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class KuPaiNewReqBean {
	private String exorderno;
    private String transid;
    private String appid;
    private String waresid;
    private String feetype;
    private String money;
    private String count;
    private String result;
    private String transtype;
    private String transtime;
    private String cpprivate;
    private String paytype;
	public String getExorderno() {
		return exorderno;
	}
	public void setExorderno(String exorderno) {
		this.exorderno = exorderno;
	}
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getWaresid() {
		return waresid;
	}
	public void setWaresid(String waresid) {
		this.waresid = waresid;
	}
	public String getFeetype() {
		return feetype;
	}
	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
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
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("exorderno=").append(exorderno);
		sb.append(",transid=").append(transid);
		sb.append(",appid=").append(appid);
		sb.append(",waresid=").append(waresid);
		sb.append(",feetype=").append(feetype);
		sb.append(",money=").append(money);
		sb.append(",count=").append(count);
		sb.append(",result=").append(result);
		sb.append(",transtype=").append(transtype);
		sb.append(",transtime=").append(transtime);
		sb.append(",cpprivate=").append(cpprivate);
		sb.append(",paytype=").append(paytype);
		return sb.toString();
	}
}
