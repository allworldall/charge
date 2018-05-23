package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.KuPaiNewReqBean;

public class KuPaiChargingNewFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "外部订单号", nullable = false)
    private String exorderno;
	@DataValidate(description = "计费支付平台的交易流水号", nullable = false)
    private String transid;
	@DataValidate(description = "游戏id, 平台为商户应用分配的唯一代码", nullable = false)
    private String appid;
	@DataValidate(description = "平台为应用内需计费商品分配的编码", nullable = false)
    private String waresid;
	@DataValidate(description = "计费方式", nullable = false)
	private String feetype;
	@DataValidate(description = "本次交易的金额", nullable = false)
    private String money;
	@DataValidate(description = "购买数量", nullable = false)
	private String count;
	@DataValidate(description = "交易结果：0–交易成功 1–交易失败", nullable = false)
    private String result;
	@DataValidate(description = "交易类型:0–支付交易；s1–支付冲正（暂未启用）；", nullable = false)
	private String transtype;
	@DataValidate(description = "交易时间格式：yyyy-mm-dd hh24:mi:ss", nullable = false)
    private String transtime;
	@DataValidate(description = "商户私有信息", nullable = false)
    private String cpprivate;
	@DataValidate(description = "支付方式，具体定义见附录", nullable = false)
    private String paytype;
	@DataValidate(description = "RSA签名，RSA私钥格式为pkcs8", nullable = false)
    private String  sign;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public KuPaiChargingNewFormBean(String gameName, String cpName,
			KuPaiNewReqBean bean, String sign) {
		super();
		this.gameName = gameName;
		this.cpName = cpName;
		this.exorderno = bean.getExorderno();
		this.transid = bean.getTransid();
		this.appid = bean.getAppid();
		this.waresid = bean.getWaresid();
		this.feetype = bean.getFeetype();
		this.money = bean.getMoney();
		this.count = bean.getCount();
		this.result = bean.getResult();
		this.transtype = bean.getTranstype();
		this.transtime = bean.getTranstime();
		this.cpprivate = bean.getCpprivate();
		this.paytype = bean.getPaytype();
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",exorderno=" + exorderno + ",transid=" + transid
				+ ",appid=" + appid + ",waresid=" + waresid + ",feetype="
				+ feetype + ",money=" + money + ",count=" + count
				+ ",result=" + result + ",transtype=" + transtype
				+ ",transtime=" + transtime + ",cpprivate=" + cpprivate
				+ ",paytype=" + paytype + ",sign=" + sign;
	}
	
}
