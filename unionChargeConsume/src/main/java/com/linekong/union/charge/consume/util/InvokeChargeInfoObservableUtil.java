package com.linekong.union.charge.consume.util;

import java.util.Observer;


public class InvokeChargeInfoObservableUtil {

	//充值订单信息被观察者
	private static ChargeInfoObservable chargeInfoObservable = null;

	private static boolean isLoadFinish = false;
	//添加活动观察者
	public static void addActivityWatcher(Observer observer) {
		if (chargeInfoObservable == null) {
			chargeInfoObservable = new ChargeInfoObservable();
		}
		chargeInfoObservable.addObserver(observer);
	}

	//添加充值成功订单信息
	public static void setChargeInfo(Object object){
		chargeInfoObservable.setOrderInfor(object);
	}

	public static boolean isLoadFinish() {
		return isLoadFinish;
	}

	public static void setLoadFinish(boolean isLoadFinish) {
		InvokeChargeInfoObservableUtil.isLoadFinish = isLoadFinish;
	}

}
