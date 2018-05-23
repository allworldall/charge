package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class BaiduReqBean {
	private String 	UID;				//用户 ID同客户端 SDK 中 API返回的用户 ID
	private String 	MerchandiseName;	//商品名称
	private String 	OrderMoney;			//订单金额，保留两位小数。单位：元
	private String 	StartDateTime;		//订单创建时间格 式 ：yyyy-MM-ddHH:mm:ss
	private String 	BankDateTime;		//银行到帐时间格 式 ：yyyy-MM-ddHH:mm:ss
	private int 	OrderStatus;		//订单状态 0:失败 1:成功
	private String 	StatusMsg;			//订单状态描述
	private String 	ExtInfo;			//CP 扩展信息，客户端SDK 传入，发货通知原样返回
    private int 	VoucherMoney;		//代金券金额
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getMerchandiseName() {
		return MerchandiseName;
	}
	public void setMerchandiseName(String merchandiseName) {
		MerchandiseName = merchandiseName;
	}
	public String getOrderMoney() {
		return OrderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		OrderMoney = orderMoney;
	}
	public String getStartDateTime() {
		return StartDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		StartDateTime = startDateTime;
	}
	public String getBankDateTime() {
		return BankDateTime;
	}
	public void setBankDateTime(String bankDateTime) {
		BankDateTime = bankDateTime;
	}
	public int getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		OrderStatus = orderStatus;
	}
	public String getStatusMsg() {
		return StatusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		StatusMsg = statusMsg;
	}
	public String getExtInfo() {
		return ExtInfo;
	}
	public void setExtInfo(String extInfo) {
		ExtInfo = extInfo;
	}
	public int getVoucherMoney() {
		return VoucherMoney;
	}
	public void setVoucherMoney(int voucherMoney) {
		VoucherMoney = voucherMoney;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("UID=").append(UID);
		sb.append(",MerchandiseName=").append(MerchandiseName);
		sb.append(",OrderMoney=").append(OrderMoney);
		sb.append(",StartDateTime=").append(StartDateTime);
		sb.append(",BankDateTime=").append(BankDateTime);
		sb.append(",OrderStatus=").append(OrderStatus);
		sb.append(",StatusMsg=").append(StatusMsg);
		sb.append(",ExtInfo=").append(ExtInfo);
		sb.append(",VoucherMoney=").append(VoucherMoney);
		return sb.toString();
	}
}
