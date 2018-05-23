package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.KuPaiReqBean;

public class KuPaiChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "交易类型:0–支付交易；s1–支付冲正（暂未启用）；", nullable = false)
	private Integer transtype;
	@DataValidate(description = "商户订单号", nullable = false)
    private String cporderid;
	@DataValidate(description = "计费支付平台的交易流水号", nullable = false)
    private String transid;
	@DataValidate(description = "用户在商户应用的唯一标识", nullable = false)
    private String appuserid;
	@DataValidate(description = "游戏id, 平台为商户应用分配的唯一代码", nullable = false)
    private String appid;
	@DataValidate(description = "平台为应用内需计费商品分配的编码", nullable = false)
    private Integer waresid;
	@DataValidate(description = "计费方式，具体定义见附录", nullable = false)
    private Integer feetype;
	@DataValidate(description = "本次交易的金额", nullable = false)
    private Float money;
	@DataValidate(description = "货币类型以及单位：RMB – 人民币（单位：元）", nullable = false)
    private String currency;
	@DataValidate(description = "交易结果：0–交易成功 1–交易失败", nullable = false)
    private Integer result;
	@DataValidate(description = "交易时间格式：yyyy-mm-dd hh24:mi:ss", nullable = false)
    private String transtime;
	@DataValidate(description = "商户私有信息", nullable = false)
    private String cpprivate;
	@DataValidate(description = "支付方式，具体定义见附录", nullable = false)
    private Integer paytype;
	@DataValidate(description = "RSA签名，RSA私钥格式为pkcs8", nullable = true)
    private String  sign;
	@DataValidate(description = "签名加密格式", nullable = true)
    private String signtype;
	
	public KuPaiChargingFormBean(String gameName,String cpName,KuPaiReqBean bean,String sign,String signtype){	
		this.gameName = gameName;
		this.cpName = cpName;
		this.transtype = bean.getTranstype();
		this.cporderid = bean.getCporderid();
		this.transid = bean.getTransid();
		this.appuserid = bean.getAppuserid();
		this.appid = bean.getAppid();
		this.waresid = bean.getWaresid();
		this.feetype = bean.getFeetype();
		this.money = bean.getMoney();
		this.currency = bean.getCurrency();
		this.result = bean.getResult();
		this.transtime = bean.getTranstime();
		this.cpprivate = bean.getCpprivate();
		this.paytype = bean.getPaytype();
		this.sign = sign;
		this.signtype = signtype;
	}
	
	public String getGameName() {
		return gameName;
	}
	public String getCpName() {
		return cpName;
	}
	public Integer getTranstype() {
		return transtype;
	}
	public String getCporderid() {
		return cporderid;
	}
	public String getTransid() {
		return transid;
	}
	public String getAppuserid() {
		return appuserid;
	}
	public String getAppid() {
		return appid;
	}
	public Integer getWaresid() {
		return waresid;
	}
	public Integer getFeetype() {
		return feetype;
	}
	public Float getMoney() {
		return money;
	}
	public String getCurrency() {
		return currency;
	}
	public Integer getResult() {
		return result;
	}
	public String getTranstime() {
		return transtime;
	}
	public String getCpprivate() {
		return cpprivate;
	}
	public Integer getPaytype() {
		return paytype;
	}
	public String getSign() {
		return sign;
	}
	public String getSigntype() {
		return signtype;
	}

	@Override
	public String toString() {
		return "KuPaiChargingFormBean [gameName=" + gameName + ", cpName="
				+ cpName + ", transtype=" + transtype + ", cporderid="
				+ cporderid + ", transid=" + transid + ", appuserid="
				+ appuserid + ", appid=" + appid + ", waresid=" + waresid
				+ ", feetype=" + feetype + ", money=" + money + ", currency="
				+ currency + ", result=" + result + ", transtime=" + transtime
				+ ", cpprivate=" + cpprivate + ", paytype=" + paytype
				+ ", sign=" + sign + ", signtype=" + signtype + "]";
	}
}
