package com.linekong.union.charge.consume.web.jsonbean.resbean;
//平台接收后验证凭证时，再接收苹果的请求对应的bean（Receipt）
public class AppleResVSixBean {
	//订单信息
	private Receipt receipt;
	//返回结果
	private int status;
	
	public class Receipt{
		
		private	String original_purchase_date_pst;
		
		private String unique_identifier;
		
		private String original_transaction_id;
		
		private String bvrs;
		
		private String app_item_id;
		
		private String transaction_id;
		
		private String quantity;
		
		private String unique_vendor_identifier;
		
		private String product_id;

		private String item_id;

		private String version_external_identifier;

		private String bid;
		
		private String purchase_date_ms;

		private String purchase_date;

		private String purchase_date_pst;

		private String original_purchase_date;
		
		private String original_purchase_date_ms;

		public String getOriginal_purchase_date_pst() {
			return original_purchase_date_pst;
		}

		public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
			this.original_purchase_date_pst = original_purchase_date_pst;
		}

		public String getUnique_identifier() {
			return unique_identifier;
		}

		public void setUnique_identifier(String unique_identifier) {
			this.unique_identifier = unique_identifier;
		}

		public String getOriginal_transaction_id() {
			return original_transaction_id;
		}

		public void setOriginal_transaction_id(String original_transaction_id) {
			this.original_transaction_id = original_transaction_id;
		}

		public String getBvrs() {
			return bvrs;
		}

		public void setBvrs(String bvrs) {
			this.bvrs = bvrs;
		}

		public String getApp_item_id() {
			return app_item_id;
		}

		public void setApp_item_id(String app_item_id) {
			this.app_item_id = app_item_id;
		}

		public String getTransaction_id() {
			return transaction_id;
		}

		public void setTransaction_id(String transaction_id) {
			this.transaction_id = transaction_id;
		}

		public String getQuantity() {
			return quantity;
		}

		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}

		public String getUnique_vendor_identifier() {
			return unique_vendor_identifier;
		}

		public void setUnique_vendor_identifier(String unique_vendor_identifier) {
			this.unique_vendor_identifier = unique_vendor_identifier;
		}

		public String getProduct_id() {
			return product_id;
		}

		public void setProduct_id(String product_id) {
			this.product_id = product_id;
		}

		public String getItem_id() {
			return item_id;
		}

		public void setItem_id(String item_id) {
			this.item_id = item_id;
		}

		public String getVersion_external_identifier() {
			return version_external_identifier;
		}

		public void setVersion_external_identifier(String version_external_identifier) {
			this.version_external_identifier = version_external_identifier;
		}

		public String getBid() {
			return bid;
		}

		public void setBid(String bid) {
			this.bid = bid;
		}

		public String getPurchase_date_ms() {
			return purchase_date_ms;
		}

		public void setPurchase_date_ms(String purchase_date_ms) {
			this.purchase_date_ms = purchase_date_ms;
		}

		public String getPurchase_date() {
			return purchase_date;
		}

		public void setPurchase_date(String purchase_date) {
			this.purchase_date = purchase_date;
		}

		public String getPurchase_date_pst() {
			return purchase_date_pst;
		}

		public void setPurchase_date_pst(String purchase_date_pst) {
			this.purchase_date_pst = purchase_date_pst;
		}

		public String getOriginal_purchase_date() {
			return original_purchase_date;
		}

		public void setOriginal_purchase_date(String original_purchase_date) {
			this.original_purchase_date = original_purchase_date;
		}

		public String getOriginal_purchase_date_ms() {
			return original_purchase_date_ms;
		}

		public void setOriginal_purchase_date_ms(String original_purchase_date_ms) {
			this.original_purchase_date_ms = original_purchase_date_ms;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("original_purchase_date_pst=").append(original_purchase_date_pst);
			sb.append(",unique_identifier=").append(unique_identifier);
			sb.append(",original_transaction_id=").append(original_transaction_id);
			sb.append(",bvrs=").append(bvrs);
			sb.append(",app_item_id=").append(app_item_id);
			sb.append(",transaction_id=").append(transaction_id);
			sb.append(",quantity=").append(quantity);
			sb.append(",unique_vendor_identifier=").append(unique_vendor_identifier);
			sb.append(",product_id=").append(product_id);
			sb.append(",item_id=").append(item_id);
			sb.append(",version_external_identifier=").append(version_external_identifier);
			sb.append(",bid=").append(bid);
			sb.append(",purchase_date_ms=").append(purchase_date_ms);
			sb.append(",purchase_date=").append(purchase_date);
			sb.append(",purchase_date_pst=").append(purchase_date_pst);
			sb.append(",original_purchase_date=").append(original_purchase_date);
			sb.append(",original_purchase_date_ms=").append(original_purchase_date_ms);
			return sb.toString();
		}
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("receipt=").append(receipt);
		sb.append(",status=").append(status);
		return sb.toString();
	}

	public AppleResVSixBean(Receipt receipt, int status) {
		super();
		this.receipt = receipt;
		this.status = status;
	}
	
}
