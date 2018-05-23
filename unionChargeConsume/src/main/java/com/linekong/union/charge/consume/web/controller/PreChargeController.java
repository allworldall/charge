package com.linekong.union.charge.consume.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.linekong.union.charge.consume.service.business.PreChargeService;
import com.linekong.union.charge.consume.util.Common;
import com.linekong.union.charge.consume.util.Constant;
import com.linekong.union.charge.consume.util.JsonUtil;
import com.linekong.union.charge.consume.util.StringUtils;
import com.linekong.union.charge.consume.util.annotation.support.ValidateService;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.web.formbean.PreChargeFormBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.PreChargeMeizuReqBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeResBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeResBeanKuaikan;


@Controller
public class PreChargeController extends BaseController{
	@Autowired
	private PreChargeService preChargeService;

	public PreChargeService getPreChargeService() {
		return preChargeService;
	}

	public void setPreChargeService(PreChargeService preChargeService) {
		this.preChargeService = preChargeService;
	}
	/**
	 * SDK预支付接口
	 * @param String 			gameName	请求url地址的游戏名称
	 * @param String 			cpName		请求url中合作商名称
	 * @param PreChargeFormBean form 		请求参数
	 * @param HttpServletResponse response
	 */
	@RequestMapping("/preCharge/{cpName}")
	public void preCharge(@PathVariable String cpName,PreChargeFormBean form,
			HttpServletResponse response){
		form.setCpName(cpName);
		try {
			if(form.getRole() == null){
				form.setRole(0L);
			}
			ValidateService.valid(form);
			//如果合作伙伴为苹果，参数中productId不能为空
			if(form.getCpName().toLowerCase().contains("apple")){
				if(form.getProductId() == null || "".equals(form.getProductId())){
					LoggerUtil.info(PreChargeController.class, "receive preCharge request param productId error:"+form);
					this.response(response,  JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PRODUCT_INFO,"")));
					return;
				}
			}
		} catch (Exception e) {
			LoggerUtil.error(PreChargeController.class, "receive preCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		//如果传来的参数没有问题，带着参数调用service的preCharge（form）方法，去验证渠道是否合法、可用
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preCharge(form)));
	}


	/**
	 * SDK华为预支付接口
	 * @param String 			gameName	请求url地址的游戏名称
	 * @param String 			cpName		请求url中合作商名称
	 * @param PreChargeFormBean form 		请求参数
	 * @param HttpServletResponse response
	 */
	@RequestMapping("/preChargeHuawei/{cpName}")
	public void preChargeHuawei(@PathVariable String cpName,PreChargeFormBean form,
						  HttpServletResponse response){
		form.setCpName(cpName);
		try {
			if(form.getRole() == null){
				form.setRole(0L);
			}
			ValidateService.valid(form);

		} catch (Exception e) {
			LoggerUtil.error(PreChargeController.class, "receive preChargeHuawei request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		//如果传来的参数没有问题，带着参数调用service的preCharge（form）方法，去验证渠道是否合法、可用
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeHuawei(form)));
	}

	/**
	 * SDK预支付接口--快看
	 * @param cpName   url中的渠道编码
	 * @param form     请求体封装成的对象
	 * @param response
	 */
	@RequestMapping(value="preChargeKuaikan/{cpName}")
	public void preChargeKuaikan(@PathVariable String cpName, PreChargeFormBean form, HttpServletResponse response) {
		form.setCpName(cpName);
		try {
			ValidateService.valid(form);
			//参数中productId不能为空
			if(form.getProductId() == null || "".equals(form.getProductId())){
				LoggerUtil.info(PreChargeController.class, "receive preChargeKuaikan request param productId error:"+form);
				this.response(response,  JsonUtil.convertBeanToJson(new PreChargeResBeanKuaikan("", "" ,Constant.ERROR_PRODUCT_INFO)));
				return;
			}
		} catch (Exception e) {
			LoggerUtil.error(PreChargeController.class, "receive preChargeKuaikan request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBeanKuaikan("", "" ,Constant.ERROR_PARAM_VALIDATE)));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeKuaikan(form)));
	}
	
	/**
	 * SDK预支付接口--uc
	 * @param String 			cpName		请求url中合作商名称
	 * @param PreChargeFormBean form 		请求参数
	 * @param HttpServletResponse response
	 */
	@RequestMapping(value="/preChargeUC/{cpName}",produces="text/html;charset=UTF-8")
	public void preChargeUC(@PathVariable String cpName,PreChargeFormBean form,
			HttpServletResponse response){
		form.setCpName(cpName);
		try {
			ValidateService.valid(form);
			if(Common.isEmptyString(form.getCpSignType())){
				throw new Exception("cpSignType is empty");
			}
		} catch (Exception e) {
			LoggerUtil.error(PreChargeController.class, "receive preChargeUC request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeUC(form)));
	}
	/**
	 * SDK预支付接口--魅族
	 * @param String 			cpName		请求url中合作商名称
	 * @param PreChargeFormBean form 		请求参数
	 * @param HttpServletResponse response
	 */
	@RequestMapping(value="/preChargeMeizu/{cpName}",produces="text/html;charset=UTF-8")
	public void preChargeMeizu(@PathVariable String cpName,PreChargeFormBean form,
			HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		form.setCpName(cpName);
		PreChargeMeizuReqBean bean = null;
		try {
			ValidateService.valid(form);
			bean =  JsonUtil.convertJsonToBean(new String(form.getExpandInfo().getBytes(), "utf-8"), PreChargeMeizuReqBean.class);
			ValidateService.valid(bean);
			if(bean.getProduct_per_price() == null){
				bean.setProduct_per_price("");
			}
			if(bean.getProduct_subject() == null){
				bean.setProduct_subject("");
			}
			if(form.getProductName() == null){
				form.setPlatformName("");
			}
			if(form.getProductDesc() == null){
				form.setProductDesc("");
			}
			if(form.getProductId() == null){
				throw new Exception("productId is empty");
			}
			if(Common.isEmptyString(form.getCpSignType())){
				throw new Exception("cpSignType is empty");
			}
		} catch (Exception e) {
			LoggerUtil.error(PreChargeController.class, "receive preChargeMeizu request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeMeizu(form,bean)));
	}
	/**
	 * SDK预支付接口--金立
	 * @param String 			cpName		请求url中合作商名称
	 * @param PreChargeFormBean form 		请求参数
	 * @param HttpServletResponse response
	 */
	@RequestMapping(value="/preChargeJinli/{cpName}",produces="text/html;charset=UTF-8")
	public void preChargeJinli(@PathVariable String cpName,PreChargeFormBean form,
			HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		form.setCpName(cpName);
		try {
			ValidateService.valid(form);
			if(form.getProductName() == null){
				throw new Exception("productName is empty");
			}
			if(Common.isEmptyString(form.getExpandInfo())){
				throw new Exception("expandInfo is empty");
			}
		} catch (Exception e) {
			LoggerUtil.error(PreChargeController.class, "receive preChargeJinli request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeJinli(form)));
	}
	/**
	 * SDK预支付接口--Vivo（步步高）
	 * @param String 			cpName		请求url中合作商名称
	 * @param PreChargeFormBean form 		请求参数
	 * @param HttpServletResponse response
	 */
	@RequestMapping(value="/preChargeVivo/{cpName}",produces="text/html;charset=UTF-8")
	public void preChargeVivo(@PathVariable String cpName,PreChargeFormBean form,
			HttpServletResponse response){
		form.setCpName(cpName);
		try {
			ValidateService.valid(form);
			if(Common.isEmptyString(form.getCpSignType())){
				throw new Exception("cpSignType is empty");
			}
			if(form.getProductName() == null){
				form.setProductName("");
			}
			if(form.getProductDesc() == null){
				form.setProductDesc("");
				
			}
		} catch (Exception e) {
			LoggerUtil.error(PreChargeController.class, "receive preChargeVivo request param error:"+form.toString()+",error info:"+e.toString());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeVivo(form)));
	}


	@RequestMapping(value="/preChargeNubia/{cpName}",produces="text/html;charset=UTF-8")
	public void preChargeNubia(@PathVariable String cpName,PreChargeFormBean form,
			HttpServletResponse response){
		form.setCpName(cpName);
		try {
			ValidateService.valid(form);
			if(Common.isEmptyString(form.getExpandInfo())){
				throw new Exception("expandInfo_uid is empty");
			}
			if(Common.isEmptyString(form.getProductName())){
				throw new Exception("productName is empty");
			}
			if(Common.isEmptyString(form.getProductDesc())){
				throw new Exception("productDesc is empty");
				
			}
		} catch (Exception e) {
			LoggerUtil.error(PreChargeController.class, "receive preChargeNubia request param error:"+form.toString()+",error info:"+e.toString());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeNubia(form)));
	}

	/**
	 * eagleHaodong预支付
	 * @param form
	 * @param response
	 */
	@RequestMapping(value="/preChargeEagleHaodong/{cpName}")
	public void preChargeEagleHaodong(PreChargeFormBean form, HttpServletResponse response){
		try{
			ValidateService.valid(form);
		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(PreChargeController.class, "receive preChargeEagleHaodong request param error:"+form.toString()+",error info:"+e.toString());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeEagleHaodong(form)));
	}

	/**
	 * 三星预支付
	 * @param form
	 * @param response
	 */
	@RequestMapping(value="/preChargeSamsung/{cpName}")
	public void preChargeSamsung(PreChargeFormBean form, HttpServletResponse response){
		try{
			if(form.getRole() == null){
				form.setRole(0L);
			}
			ValidateService.valid(form);
			if(form.getProductId() == null || "".equals(form.getProductId())){
				LoggerUtil.info(PreChargeController.class, "receive preChargeSamsung request param productId error:"+form);
				this.response(response,  JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PRODUCT_INFO,"")));
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(PreChargeController.class, "receive preChargeSamsung request param error:"+form.toString()+",error info:"+e.toString());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeSamsung(form)));
	}
	/**
	 * 一点预支付
	 * @param form
	 * @param response
	 */
	@RequestMapping(value="/preChargeYiDian/{cpName}")
	public void preChargeYiDian(PreChargeFormBean form, HttpServletResponse response){
		try{
			ValidateService.valid(form);
			if(StringUtils.isEmpty(form.getExpandInfo())){
				throw new Exception("expandInfo(wap_type) is empty");
			}
			if(form.getRole() == null){
				form.setRole(0L);
			}
		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(PreChargeController.class, "receive preChargeYiDian request param error:"+form.toString()+",error info:"+e.toString());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeYiDian(form)));
	}

	/**
	 * 爱贝预支付
	 * @param form
	 * @param response
	 */
	@RequestMapping(value = "/preChargeAibei/{cpName}")
	public void preChargeAibei(PreChargeFormBean form, HttpServletResponse response) {
		try {
			if(form.getRole() == null){
				form.setRole(0L);
			}
			ValidateService.valid(form);
		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(PreChargeController.class, "receive preChargeAibei request param error:"+form.toString()+",error info:"+e.toString());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeAibei(form)));
	}

	/**
	 * 花生互娱预支付
	 * @param form
	 * @param response
	 */
	@RequestMapping(value = "/preChargeHuaSheng/{cpName}")
	public void preChargeHuaSheng(PreChargeFormBean form, HttpServletResponse response) {
		try {
			if(form.getRole() == null){
				form.setRole(0L);
			}
			ValidateService.valid(form);
		}catch(Exception e){
			LoggerUtil.error(PreChargeController.class, "receive preChargeHuaSheng request param error:"+form.toString()+",error info:"+e.toString());
			this.response(response, JsonUtil.convertBeanToJson(new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(preChargeService.preChargeHuaSheng(form)));
	}






	/**
	 * 此接口用与监控服务器状态，类似心跳检测机制，提供给运维使用
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = "/keepalive", method = RequestMethod.GET)
	public void keepalive (HttpServletRequest req, HttpServletResponse resp){
		this.response(resp, "1");
	}
}
