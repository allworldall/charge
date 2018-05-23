package com.linekong.union.charge.rpc;

import java.util.Date;

import com.linekong.union.charge.rpc.pojo.ChargingPOJO;

public class ChargeTest {

	public static void main(String[] args) {
		
		ChargingPOJO pojo = new ChargingPOJO();
		pojo.setUnionCode("12");
		pojo.setGameId(1);
		pojo.setOrderId("11122");
		pojo.setUserName("sssss");
		pojo.setChargeTime(new Date());
		pojo.setChargeMoney(1);
		//pojo.setGatewayId(1001);
		try {
			//ValidateService.valid(pojo);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e.getMessage());
		}
	}

}
