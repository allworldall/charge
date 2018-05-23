package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.YYHReqBean;

public class YYHChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "交易类型", nullable = false)
	private Integer transtype;
	@DataValidate(description = "商户订单号", maxLength = 64, nullable = false)
	private String cporderid;
	@DataValidate(description = "计费支付平台的交易流水号", maxLength = 32, nullable = false)
	private String transid;
	@DataValidate(description = "用户在商户应用的唯一标识", maxLength = 32, nullable = false)
	private String appuserid;
	@DataValidate(description = "游戏 id，平台为商户应用分配的唯一代码", maxLength = 20, nullable = false)
	private String appid;
	@DataValidate(description = "平台为应用内需计费商品分配的编码", nullable = false)
	private Integer waresid;
	@DataValidate(description = "计费方式，具体定义见简介及注意事", nullable = false)
	private Integer feetype;
	@DataValidate(description = "本次交易的金额， 请务必严格校验商品金额与交易的金额是否一致", nullable = false)
	private Float money;
	@DataValidate(description = "货币类型以及单位RMB – 人民币（ 单位： 元）", maxLength = 32, nullable = false)
	private String currency;
	@DataValidate(description = "交易结果", nullable = false)
	private Integer result;
	@DataValidate(description = "交易完成时间：yyyy-mm-dd hh24:mi:ss", maxLength = 20, nullable = false)
	private String transtime;
	@DataValidate(description = "商户私有信息", maxLength = 64, nullable = true)
	private String cpprivate;
	@DataValidate(description = "支付方式，具体定义见简介及注意事项", nullable = true)
	private Integer paytype;
	@DataValidate(description = "对 transdata 的签名数据", nullable = false)
	private String sign;
	@DataValidate(description = "签名算法类型，目前仅支持RSA", nullable = false)
	private String signtype;

	public YYHChargingFormBean(YYHReqBean bean, String gameName, String cpName, String sign,
			String signtype) {
		this.gameName = gameName;
		this.cpName = cpName;
		this.sign = sign.replace(" ", "+").replace("\\", "");
		this.signtype = signtype;
		this.appid = bean.getAppid();
		this.appuserid = bean.getAppuserid();
	    this.cporderid = bean.getCporderid();
		this.cpprivate = bean.getCpprivate();
		this.currency = bean.getCurrency();
		this.feetype = bean.getFeetype();
		this.money = bean.getMoney();
		this.paytype = bean.getPaytype();
		this.result = bean.getResult();
		this.transid = bean.getTransid();
		this.transtime = bean.getTranstime();
		this.transtype = bean.getTranstype();
		this.waresid = bean.getWaresid();
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
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",transtype=" + transtype + ",cporderid="
				+ cporderid + ",transid=" + transid + ",appuserid="
				+ appuserid + ",appid=" + appid + ",waresid=" + waresid
				+ ",feetype=" + feetype + ",money=" + money + ",currency="
				+ currency + ",result=" + result + ",transtime=" + transtime
				+ ",cpprivate=" + cpprivate + ",paytype=" + paytype
				+ ",sign=" + sign + ",signtype=" + signtype;
	}

}
