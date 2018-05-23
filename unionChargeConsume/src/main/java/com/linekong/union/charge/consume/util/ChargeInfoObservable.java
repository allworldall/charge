package com.linekong.union.charge.consume.util;

import java.util.Observable;

public class ChargeInfoObservable extends Observable{

	public void setOrderInfor(Object orderInfor) {
		setChanged();
		notifyObservers(orderInfor);
	}
	
}
