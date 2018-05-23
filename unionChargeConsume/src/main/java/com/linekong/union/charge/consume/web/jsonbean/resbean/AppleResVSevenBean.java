package com.linekong.union.charge.consume.web.jsonbean.resbean;

import java.util.ArrayList;

public class AppleResVSevenBean {
	//订单信息
	private Receipt receipt;
	//返回结果
	private int status;
	
	private String environment;
	
	public class Receipt{
		
		private String receipt_type;
		
		private String adam_id;
		
		private String app_item_id;
		
		private String bundle_id;
		
		private String application_version;
		
		private String download_id;
		
		private String version_external_identifier;
		
		private String receipt_creation_date;
		
		private String receipt_creation_date_ms;
		
		private String receipt_creation_date_pst;
		
		private String request_date;
		
		private String request_date_ms;
		
		private String request_date_pst;

		private String original_purchase_date;
		
		private String original_purchase_date_ms;
		
		private String original_purchase_date_pst;
		
		private String original_application_version;
		
		private ArrayList<InApp> in_app;
		
		public class InApp{
			private String quantity;
			
			private String product_id;
			
			private String transaction_id;
			
			private String original_transaction_id;
			
			private String purchase_date;
			
			private String purchase_date_ms;
			
			private String purchase_date_pst;
			
			private String original_purchase_date;
			
			private String original_purchase_date_ms;
			
			private String original_purchase_date_pst;
			
			private String is_trial_period;

			public String getQuantity() {
				return quantity;
			}

			public void setQuantity(String quantity) {
				this.quantity = quantity;
			}

			public String getProduct_id() {
				return product_id;
			}

			public void setProduct_id(String product_id) {
				this.product_id = product_id;
			}

			public String getTransaction_id() {
				return transaction_id;
			}

			public void setTransaction_id(String transaction_id) {
				this.transaction_id = transaction_id;
			}

			public String getOriginal_transaction_id() {
				return original_transaction_id;
			}

			public void setOriginal_transaction_id(String original_transaction_id) {
				this.original_transaction_id = original_transaction_id;
			}

			public String getPurchase_date() {
				return purchase_date;
			}

			public void setPurchase_date(String purchase_date) {
				this.purchase_date = purchase_date;
			}

			public String getPurchase_date_ms() {
				return purchase_date_ms;
			}

			public void setPurchase_date_ms(String purchase_date_ms) {
				this.purchase_date_ms = purchase_date_ms;
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

			public String getOriginal_purchase_date_pst() {
				return original_purchase_date_pst;
			}

			public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
				this.original_purchase_date_pst = original_purchase_date_pst;
			}

			public String getIs_trial_period() {
				return is_trial_period;
			}

			public void setIs_trial_period(String is_trial_period) {
				this.is_trial_period = is_trial_period;
			}

			@Override
			public String toString() {
				final StringBuilder sb = new StringBuilder();
				sb.append("quantity=").append(quantity);
				sb.append(",product_id=").append(product_id);
				sb.append(",transaction_id=").append(transaction_id);
				sb.append(",original_transaction_id=").append(original_transaction_id);
				sb.append(",purchase_date=").append(purchase_date);
				sb.append(",purchase_date_ms=").append(purchase_date_ms);
				sb.append(",purchase_date_pst=").append(purchase_date_pst);
				sb.append(",original_purchase_date=").append(original_purchase_date);
				sb.append(",original_purchase_date_ms=").append(original_purchase_date_ms);
				sb.append(",original_purchase_date_pst=").append(original_purchase_date_pst);
				sb.append(",is_trial_period=").append(is_trial_period);
				return sb.toString();
			}
		}

		public String getReceipt_type() {
			return receipt_type;
		}

		public void setReceipt_type(String receipt_type) {
			this.receipt_type = receipt_type;
		}

		public String getAdam_id() {
			return adam_id;
		}

		public void setAdam_id(String adam_id) {
			this.adam_id = adam_id;
		}

		public String getApp_item_id() {
			return app_item_id;
		}

		public void setApp_item_id(String app_item_id) {
			this.app_item_id = app_item_id;
		}

		public String getBundle_id() {
			return bundle_id;
		}

		public void setBundle_id(String bundle_id) {
			this.bundle_id = bundle_id;
		}

		public String getApplication_version() {
			return application_version;
		}

		public void setApplication_version(String application_version) {
			this.application_version = application_version;
		}

		public String getDownload_id() {
			return download_id;
		}

		public void setDownload_id(String download_id) {
			this.download_id = download_id;
		}

		public String getVersion_external_identifier() {
			return version_external_identifier;
		}

		public void setVersion_external_identifier(String version_external_identifier) {
			this.version_external_identifier = version_external_identifier;
		}

		public String getReceipt_creation_date() {
			return receipt_creation_date;
		}

		public void setReceipt_creation_date(String receipt_creation_date) {
			this.receipt_creation_date = receipt_creation_date;
		}

		public String getReceipt_creation_date_ms() {
			return receipt_creation_date_ms;
		}

		public void setReceipt_creation_date_ms(String receipt_creation_date_ms) {
			this.receipt_creation_date_ms = receipt_creation_date_ms;
		}

		public String getReceipt_creation_date_pst() {
			return receipt_creation_date_pst;
		}

		public void setReceipt_creation_date_pst(String receipt_creation_date_pst) {
			this.receipt_creation_date_pst = receipt_creation_date_pst;
		}

		public String getRequest_date() {
			return request_date;
		}

		public void setRequest_date(String request_date) {
			this.request_date = request_date;
		}

		public String getRequest_date_ms() {
			return request_date_ms;
		}

		public void setRequest_date_ms(String request_date_ms) {
			this.request_date_ms = request_date_ms;
		}

		public String getRequest_date_pst() {
			return request_date_pst;
		}

		public void setRequest_date_pst(String request_date_pst) {
			this.request_date_pst = request_date_pst;
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

		public String getOriginal_purchase_date_pst() {
			return original_purchase_date_pst;
		}

		public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
			this.original_purchase_date_pst = original_purchase_date_pst;
		}

		public String getOriginal_application_version() {
			return original_application_version;
		}

		public void setOriginal_application_version(String original_application_version) {
			this.original_application_version = original_application_version;
		}
		
		public ArrayList<InApp> getIn_app() {
			return in_app;
		}

		public void setIn_app(ArrayList<InApp> in_app) {
			this.in_app = in_app;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("receipt_type=").append(receipt_type);
			sb.append(",adam_id=").append(adam_id);
			sb.append(",app_item_id=").append(app_item_id);
			sb.append(",bundle_id=").append(bundle_id);
			sb.append(",application_version=").append(application_version);
			sb.append(",download_id=").append(download_id);
			sb.append(",version_external_identifier=").append(version_external_identifier);
			sb.append(",receipt_creation_date=").append(receipt_creation_date);
			sb.append(",receipt_creation_date_ms=").append(receipt_creation_date_ms);
			sb.append(",receipt_creation_date_pst=").append(receipt_creation_date_pst);
			sb.append(",request_date=").append(request_date);
			sb.append(",request_date_ms=").append(request_date_ms);
			sb.append(",request_date_pst=").append(request_date_pst);
			sb.append(",original_purchase_date=").append(original_purchase_date);
			sb.append(",original_purchase_date_ms=").append(original_purchase_date_ms);
			sb.append(",original_purchase_date_pst=").append(original_purchase_date_pst);
			sb.append(",original_application_version=").append(original_application_version);
			sb.append(",in_app=").append(in_app);
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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("receipt=").append(receipt);
		sb.append(",status=").append(status);
		sb.append(",environment=").append(environment);
		return sb.toString();
	}
}
