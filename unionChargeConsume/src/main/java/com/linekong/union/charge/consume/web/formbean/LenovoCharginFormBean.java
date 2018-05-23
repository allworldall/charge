package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.LenovoReqBean;

public class LenovoCharginFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "外部订单号, 商户生成的订单号", nullable = false)
	private String exorderno;
	@DataValidate(description = "计费支付平台的交易流水号", nullable = false)
	private String transid;
	@DataValidate(description = "游戏 id, Open AppID", nullable = false)
	private String appid;
	@DataValidate(description = "商品编码", nullable = false)
	private String waresid;
	@DataValidate(description = "计费方式：0、按次；1、开放价格；", nullable = false)
	private String feetype;
	@DataValidate(description = "本次交易的金额，单位：分", nullable = false)
	private String money;
	@DataValidate(description = "本次购买的商品数量", nullable = false)
	private String count;
	@DataValidate(description = "交易结果：0–交易成功；1–交易失败", nullable = false)
	private String result;
	@DataValidate(description = "交易类型：0 –交易；1–冲正", nullable = false)
	private String transtype;
	@DataValidate(description = "交易时间格式：yyyy-mm-dd hh24:mi:ss", nullable = false)
	private String transtime;
	@DataValidate(description = "商户私有信息", nullable = false)
	private String cpprivate;
	@DataValidate(description = "支付方式（该字段值后续可能会增加）", nullable = false)
	private String paytype;
	@DataValidate(description = "MD5加密，Base64编码", nullable = false)
	private String sign;

	public LenovoCharginFormBean(String gameName,String cpName,LenovoReqBean bean,String sign){
		this.gameName = gameName;
		this.cpName =cpName;
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
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	
	public String getGameName() {
		return gameName;
	}

	public String getCpName() {
		return cpName;
	}
	
	public String getExorderno() {
		return exorderno;
	}

	public String getTransid() {
		return transid;
	}

	public String getAppid() {
		return appid;
	}

	public String getWaresid() {
		return waresid;
	}

	public String getFeetype() {
		return feetype;
	}

	public String getMoney() {
		return money;
	}
	
	public String getCount() {
		return count;
	}

	public String getResult() {
		return result;
	}

	public String getTranstype() {
		return transtype;
	}

	public String getTranstime() {
		return transtime;
	}

	public String getCpprivate() {
		return cpprivate;
	}

	public String getPaytype() {
		return paytype;
	}

	public String getSign() {
		return sign;
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
