package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.BaiduReqBean;

/**
 * 百度FormBean
 * JsonBean 到 FormBean转换
 * 
 * */
public class BaiduChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "应用 ID", nullable = false)
	private int AppID;
	@DataValidate(description = "SDK 系统内部订单号", nullable = false)
	private String OrderSerial;
	@DataValidate(description = "CP 订单号", nullable = false)
	private String CooperatorOrderSerial;
	@DataValidate(description = "签名", nullable = false)
	private String Sign;
	@DataValidate(description = "用户 ID同客户端 SDK 中 API返回的用户 ID", nullable = false)
	private Long UID;
	@DataValidate(description = "商品名称", nullable = false)
	private String MerchandiseName;
	@DataValidate(description = "订单金额，保留两位小数。单位：元", nullable = false)
	private Double OrderMoney;
	@DataValidate(description = "订单创建时间格 式 ：yyyy-MM-ddHH:mm:ss", nullable = false)
	private String StartDateTime;
	@DataValidate(description = "银行到帐时间格 式 ：yyyy-MM-ddHH:mm:ss", nullable = false)
	private String BankDateTime;
	@DataValidate(description = "订单状态0:失败1:成功", nullable = false)
	private int OrderStatus;
	@DataValidate(description = "订单状态描述", nullable = false)
	private String StatusMsg;
	@DataValidate(description = "CP 扩展信息，客户端SDK 传入，发货通知原样返回", nullable = true)
	private String ExtInfo;
	@DataValidate(description = "代金券金额", nullable = false)
	private int VoucherMoney;
	
	public BaiduChargingFormBean(String gameName,String cpName,BaiduReqBean bean,int AppID,String OrderSerial,String CooperatorOrderSerial,String Sign){
		this.gameName = gameName;
		this.cpName = cpName;
		this.AppID  = AppID;
		this.OrderSerial = OrderSerial;
		this.CooperatorOrderSerial = CooperatorOrderSerial;
		this.Sign = Sign.replace(" ", "+").replace("\\", "");
		this.BankDateTime = bean.getBankDateTime();
		this.ExtInfo = bean.getExtInfo();
		this.MerchandiseName = bean.getMerchandiseName();
		this.OrderStatus = bean.getOrderStatus();
		this.OrderMoney = Double.parseDouble(bean.getOrderMoney());
		this.StartDateTime=bean.getStartDateTime();
		this.StatusMsg = bean.getStatusMsg();
		this.UID = Long.parseLong(bean.getUID());
		this.VoucherMoney = bean.getVoucherMoney();
	}

	public BaiduChargingFormBean() {

	}

	public String getGameName() {
		return gameName;
	}

	public String getCpName() {
		return cpName;
	}

	public int getAppID() {
		return AppID;
	}

	public String getOrderSerial() {
		return OrderSerial;
	}

	public String getCooperatorOrderSerial() {
		return CooperatorOrderSerial;
	}

	public String getSign() {
		return Sign;
	}

	public Long getUID() {
		return UID;
	}

	public String getMerchandiseName() {
		return MerchandiseName;
	}

	public Double getOrderMoney() {
		return OrderMoney;
	}

	public String getStartDateTime() {
		return StartDateTime;
	}

	public String getBankDateTime() {
		return BankDateTime;
	}

	public int getOrderStatus() {
		return OrderStatus;
	}

	public String getStatusMsg() {
		return StatusMsg;
	}

	public String getExtInfo() {
		return ExtInfo;
	}

	public int getVoucherMoney() {
		return VoucherMoney;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public void setAppID(int appID) {
		AppID = appID;
	}

	public void setOrderSerial(String orderSerial) {
		OrderSerial = orderSerial;
	}

	public void setCooperatorOrderSerial(String cooperatorOrderSerial) {
		CooperatorOrderSerial = cooperatorOrderSerial;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public void setUID(Long UID) {
		this.UID = UID;
	}

	public void setMerchandiseName(String merchandiseName) {
		MerchandiseName = merchandiseName;
	}

	public void setOrderMoney(Double orderMoney) {
		OrderMoney = orderMoney;
	}

	public void setStartDateTime(String startDateTime) {
		StartDateTime = startDateTime;
	}

	public void setBankDateTime(String bankDateTime) {
		BankDateTime = bankDateTime;
	}

	public void setOrderStatus(int orderStatus) {
		OrderStatus = orderStatus;
	}

	public void setStatusMsg(String statusMsg) {
		StatusMsg = statusMsg;
	}

	public void setExtInfo(String extInfo) {
		ExtInfo = extInfo;
	}

	public void setVoucherMoney(int voucherMoney) {
		VoucherMoney = voucherMoney;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",AppID=" + AppID + ",OrderSerial=" + OrderSerial
				+ ",CooperatorOrderSerial=" + CooperatorOrderSerial
				+ ",Sign=" + Sign + ",UID=" + UID + ",MerchandiseName="
				+ MerchandiseName + ",OrderMoney=" + OrderMoney
				+ ",StartDateTime=" + StartDateTime + ",BankDateTime="
				+ BankDateTime + ",OrderStatus=" + OrderStatus
				+ ",StatusMsg=" + StatusMsg + ",ExtInfo=" + ExtInfo
				+ ",VoucherMoney=" + VoucherMoney;
	}
	
}
