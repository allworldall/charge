package com.linekong.union.charge.consume.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.linekong.union.charge.consume.rabbitmq.pojo.PushOrderWithMQInfoPOJO;
import com.linekong.union.charge.consume.service.business.PHPChargingService;
import com.linekong.union.charge.consume.util.Constant;
import com.linekong.union.charge.consume.util.JsonUtil;
import com.linekong.union.charge.consume.util.annotation.support.ValidateService;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PHPReturnResBean;

/**
 * PHP充值项目与渠道项目通信
 * @author Administrator
 *
 */
@Controller
public class PHPChargingController extends BaseController {

	@Resource
	private PHPChargingService phpChargingService;
	/**
	 * 腾讯渠道充值，进行新版活动计算
	 */
	@RequestMapping(value = "tencentChargingForAct")
	public void tencentChargingForAct(PushOrderWithMQInfoPOJO pojo, String sign
			,HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		try {
			//验证参数
			ValidateService.valid(pojo);
			if(sign == null || "".equals(sign.trim())){
				throw new Throwable("sign不能为空！！");
			}
			//调用活动MQ，推送信息（先验证pojo数据合法性）
			this.response(response, JsonUtil.convertBeanToJson(phpChargingService.pushActMQ(pojo,sign)));
		} catch (Throwable e) {
			LoggerUtil.error(PHPChargingController.class, "receive tencentChargingForAct request param error:"+pojo+"sign"+sign+"error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new PHPReturnResBean(Constant.ERROR_PARAM_VALIDATE, e.toString())));
			return;
		}
	}
	
}
