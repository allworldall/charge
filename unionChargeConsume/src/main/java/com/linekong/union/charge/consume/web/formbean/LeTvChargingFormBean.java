package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.LeTvReqBean;

public class LeTvChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "应用所属 AppId", nullable = false)
	// 乐视平台分配的应用参数
	private String appKey;
	@DataValidate(description = "货币种类", nullable = false) 
	private String currencyCode;
	@DataValidate(description = "CP 自定义参数", nullable = false)
	// 此值为客户端开发者传入, 在回调开发者服务器时, encode 之后返回给开发者服务器
	private String params;
	@DataValidate(description = "实际付费金额", nullable = false)
	// 订单总价格
	private String price;
	@DataValidate(description = "商品订单 id", nullable = true)
	private String externalProductId;
	@DataValidate(description = "购买商品数量", nullable = false)
	private String quantity;
	@DataValidate(description = "支付系统产品标识", nullable = false)
	private String sku;
	@DataValidate(description = "商品单价，为 0", nullable = false)
	private String total;
	// 支付请求在乐视支付系统的唯一标识.
	@DataValidate(description = "支付流水号", nullable = false)
	private String pxNumber;
	// 开发者请求时传入的 UserId 参数(ssoUid).
	@DataValidate(description = "用户名", nullable = false)
	private String userName;
	// 做签名对比，具体可参考下方签名规则
	@DataValidate(description = "签名", nullable = false)
	private String sign;

	public LeTvChargingFormBean(LeTvReqBean bean, String gameName, String cpName, String appKey, String currencyCode,
			String params, String price, String pxNumber, String userName, String sign) {
		this.gameName = gameName;
		this.cpName = cpName;
		this.appKey = appKey;
		this.currencyCode = currencyCode;
		this.params = params;
		this.price = price;
		this.externalProductId = bean.getExternalProductId();
		this.quantity = bean.getQuantity();
		this.sku = bean.getSku();
		this.total = bean.getTotal();
		this.pxNumber = pxNumber;
		this.userName = userName;
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}

	public String getGameName() {
		return gameName;
	}

	public String getCpName() {
		return cpName;
	}

	public String getAppKey() {
		return appKey;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getParams() {
		return params;
	}

	public String getPrice() {
		return price;
	}

	public String getExternalProductId() {
		return externalProductId;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getSku() {
		return sku;
	}

	public String getTotal() {
		return total;
	}

	public String getPxNumber() {
		return pxNumber;
	}

	public String getUserName() {
		return userName;
	}

	public String getSign() {
		return sign;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",appKey=" + appKey + ",currencyCode="
				+ currencyCode + ",params=" + params + ",price=" + price
				+ ",externalProductId=" + externalProductId + ",quantity="
				+ quantity + ",sku=" + sku + ",total=" + total
				+ ",pxNumber=" + pxNumber + ",userName=" + userName
				+ ",sign=" + sign;
	}
}
