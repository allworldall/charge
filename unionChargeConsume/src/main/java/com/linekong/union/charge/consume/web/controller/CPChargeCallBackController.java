package com.linekong.union.charge.consume.web.controller;

import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linekong.union.charge.consume.util.log.LoggerAOPUtils;
import com.linekong.union.charge.consume.web.formbean.*;
import com.linekong.union.charge.consume.web.jsonbean.resbean.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonObject;
import com.linekong.union.charge.consume.service.business.CPChargeCallBackService;
import com.linekong.union.charge.consume.util.Common;
import com.linekong.union.charge.consume.util.Constant;
import com.linekong.union.charge.consume.util.JsonUtil;
import com.linekong.union.charge.consume.util.UrlToObject;
import com.linekong.union.charge.consume.util.annotation.support.ValidateService;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.util.sign.DESUtil;
import com.linekong.union.charge.consume.util.sign.Des3Util;
import com.linekong.union.charge.consume.util.sign.MD5SignatureChecker;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.AnzhiReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.BaiduReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.HaodongAndroidReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.JianGuoReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.KuPaiNewReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.LeTvReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.LenovoReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.PywBTReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.PywReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.TTReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.UCReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.WDJReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.YYHReqBean;
import com.linekong.union.charge.consume.web.xmlbean.resbean.JifengResBean;
/**
 * 接收联运伙伴充值成功数据回调
 */
@Controller
public class CPChargeCallBackController extends BaseController {

	private static Logger log = Logger.getLogger("payInfo");

	@Autowired
	private CPChargeCallBackService cpChargeCallBackService;

	public CPChargeCallBackService getCpChargeCallBackService() {
		return cpChargeCallBackService;
	}

	public void setCpChargeCallBackService(CPChargeCallBackService cpChargeCallBackService) {
		this.cpChargeCallBackService = cpChargeCallBackService;
	}

	/**
	 * UC 单机版充值回调
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "ucStandAloneCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void ucStandAloneCharge(@PathVariable String gameName, @PathVariable String cpName,
								   @RequestBody UCStandAloneFormBean form, HttpServletRequest request, HttpServletResponse response){
		String result = "SUCCESS";
		try {//注解解析，通过注解的要求来判断传过来的属性值是否合法
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			ValidateService.valid(form.getData());
			int resultCode = cpChargeCallBackService.ucStandAloneCharging(form, Common.getTrueIP(request));
			if(!Common.dealSuccess(resultCode)){
				result = "FAILURE";
			}
		}catch (Exception e){
			LoggerUtil.error(CPChargeCallBackController.class, "receive ucStandAloneCharge request param error:"+form+",error info:"+e.toString());
			result = "FAILURE";
		}finally {
			this.response(response, result);
		}

	}

	/**
	 * 快看充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param trans_data
	 * @param sign
	 */
	@RequestMapping(value = "kuaikanCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void kuaikanCharge(@PathVariable String gameName, @PathVariable String cpName,HttpServletRequest request,
			HttpServletResponse response,@RequestParam("trans_data") String trans_data,@RequestParam("sign") String sign){
		KuaikanChargingFormBean form =null;
		//返回渠道的字符串
		String result = null;
		try{
			//json串转换成对象，并赋值
			form = JsonUtil.convertJsonToBean(trans_data, KuaikanChargingFormBean.class);
			if(form == null){
				LoggerUtil.error(CPChargeCallBackController.class, "receive kuaikanCharge request param error:"+trans_data);
				this.response(response, "FAIL");
				return;
			}
			form.setGameName(gameName);
			form.setCpName(cpName);
			form.setSign(sign);
			////注解解析，通过注解的要求来判断传过来的属性值是否合法
			ValidateService.valid(form);
		}catch(Exception e){
			LoggerUtil.error(CPChargeCallBackController.class, "receive kuaikanCharge request param error:"+trans_data+",error info:"+e.getMessage());
			this.response(response, "FAIL");
			return;
		}
		int resultCode = cpChargeCallBackService.kuaikanCharging(form,Common.getTrueIP(request));
		result = Common.dealSuccess(resultCode) ? "SUCCESS" :  "FAIL";
		this.response(response, result);
	}
	/**
	 * OPPO 充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletResponse
	 *            response
	 * @param HttpServletRequest
	 *            request
	 * @param OppoChargingFormBean
	 *            form
	 *
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm RSA
	 * 
	 * @return void
	 */
	@RequestMapping(value = "oppoCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void oppoCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,OppoChargingFormBean form) {
		try {
			StringBuilder sb = new StringBuilder();
			form.setCpName(cpName);
			form.setGameName(gameName);
			//注解解析，通过注解的要求来判断传过来的属性值是否合法
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.oppoCharging(form, Common.getTrueIP(request));
			sb = Common.dealSuccess(resultCode) ? sb.append("result=OK") : sb.append("result=FAIL");
			sb.append("&resultMsg=").append(resultCode);
			//将结果输出到前台
			this.response(response, sb.toString());
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class, "receive oppoCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "result=FAIL&resultMsg=-1");
			return;
		}
	}

	/**
	 * VIVO 充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param VivoChargingFormBean
	 *            form
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 * @return void
	 */
	@RequestMapping(value = "vivoCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void vivoCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, VivoChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.vivoCharging(form, Common.getTrueIP(request));
			String result = Common.dealSuccess(resultCode) ? "success" : "fail";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class, "receive vivoCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
			return;
		}
	}

	/**
	 * 当乐 充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param DangLeChargingFormBean
	 *            form
	 * 
	 * @method HTTP GET
	 * @SignEncryptedAlgorithm MD5
	 *
	 * @return void
	 */
	@RequestMapping(value = "dangleCharge/{gameName}/{cpName}",method = RequestMethod.GET)
	public void dangleCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, DangLeChargingFormBean form) {
		//因为是get请求，故有可能会乱码
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.dangleCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "success" : "failure";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class, "receive dangleCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "failure");
			return;
		}
	}

	/**
	 * 华为 充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param HuaWeiChargingFormBean
	 *            form
	 * 
	 * @method HTTP GET
	 * @SignEncryptedAlgorithm RSA
	 * 
	 * @return void 操作结果： 0 表示成功， 1:验签失败, 2:超时, 3: 业务信息错误，比如订单不存在, 94: 系统错误, 95:
	 *         IO 错误, 96: 错误的 url, 97: 错误的响应, 98: 参数错误, 99: 其他错误
	 *
	 *特别声明：此接口回调之前严格按照文档要求，对那几个字符转进行URLEncode()
	 */

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "huaweiCharge/{gameName}/{cpName}", method = RequestMethod.POST, headers = {
			"Content-Type=application/x-www-form-urlencoded" })
	public void huaweiCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		HuaWeiChargingFormBean form = new HuaWeiChargingFormBean();
		
		String postContent = "";
		try {
			InputStream stream=request.getInputStream();
			InputStreamReader isr=new InputStreamReader(stream);
			BufferedReader br=new BufferedReader(isr);
			String temp = "";
			while((temp = br.readLine()) != null){
				postContent += temp;
			}
			form = UrlToObject.huaweiUrlToObject(postContent);
			form.setSign(URLDecoder.decode(form.getSign(),"utf-8").replace(" ", "+").replace("\\", ""));
			
			form.setGameName(gameName);
			form.setCpName(cpName);
			form.setNull(); 
			postContent = URLDecoder.decode(postContent);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.huaweiCharging(form, Common.getTrueIP(request), postContent);
			switch(resultCode){
				case  Constant.SUCCESS : 			   	resultCode =  0; break;	//执行成功状态*****1
				case  Constant.ERROR_ORDER_DUPLICATE : 	resultCode =  0; break;	//订单号重复*****-1472
				case  Constant.ERROR_PARAM_VALIDATE : 	resultCode = 98; break;	//参数校验异常****-21006
				case  Constant.ERROR_SYSTEM : 			resultCode = 94; break;	//系统异常****-200
				case  Constant.ERROR_ORDER_EXIST : 		resultCode =  3; break;	//订单号不存在****-1490
				case  Constant.ERROR_SIGN : 			resultCode =  1; break;	//签名验证异常****-21000
				case  Constant.ERROR_REQUEST_CPGAME : 	resultCode = 96; break;	//渠道游戏配置不存在****-21001
				case  Constant.ERROR_REQUEST_CPGAME_STATE:	resultCode = 96; break;//渠道游戏配置未生效
				case  Constant.ERROR_REQUEST_CP_STATE:	resultCode = 96; break;//渠道配置未开放
				case  Constant.ERROR_REQUEST_CP : 		resultCode = 96; break;	//渠道配置不存在****-21002
				case  Constant.ERROR_CHARGE_STATUS : 	resultCode = 99; break;	//合作伙伴没有开通充值请联系管理员开通****-21003
				case  Constant.ERROR_CHARGE_SIGN : 		resultCode = 94; break;	//获取CpKey值异常****-21005
				case  Constant.ERROR_CHARGE_MONEY : 	resultCode =  3; break;	//预支付金额和回调金额不符****-21010
				case  Constant.ERROR_FAIL_ORDER : 		resultCode =  3; break;	//此订单为失败订单****-21011
				case  Constant.ERROR_CHARGE_SERVER_IP : resultCode = 99; break;	//没有配置合作伙伴ServerIP白名单****-21012
				case  Constant.ERROR_SERVER_IP : 		resultCode = 99; break;	//与配置合作伙伴ServerIP白名单，不匹配****-21013
				default : resultCode = 94;
			}
			HuaWeiResBean bean = new HuaWeiResBean(resultCode);
			this.response(response, JsonUtil.convertBeanToJson(bean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive huaweiCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new HuaWeiResBean(99)));
			return;
		}
	}

	
	/**
	 * 益玩充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param YiwanCharginFormBean
	 *            bean
	 * 
	 * @method HTTP GET
	 * @SignEncryptedAlgorithm MD5
	 * 
	 * @return void
	 * 
	 *         1 成功 100 签名错误 101 金额错误 102 OpenId错误 103 其他未知错误
	 */

	@RequestMapping(value = "yiwanCharge/{gameName}/{cpName}", method = RequestMethod.GET)
	public void yiwanCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, YiwanCharginFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.yiwanCharging(form, Common.getTrueIP(request));
			switch(resultCode){
				case  Constant.SUCCESS : 			   		resultCode =   1; break;	//执行成功状态*****1
				case  Constant.ERROR_ORDER_DUPLICATE : 		resultCode = 1; break;	//订单号重复*****-1472
				case  Constant.ERROR_PARAM_VALIDATE : 		resultCode = 103; break;	//参数校验异常****-21006
				case  Constant.ERROR_SYSTEM : 				resultCode = 103; break;	//系统异常****-200
				case  Constant.ERROR_ORDER_EXIST : 			resultCode = 103; break;	//订单号不存在****-1490
				case  Constant.ERROR_SIGN : 				resultCode = 100; break;	//签名验证异常****-21000
				case  Constant.ERROR_REQUEST_CPGAME : 		resultCode = 103; break;	//预支付中请求地址和请求参数不匹配(gameName和gameId不匹配)****-21001
				case  Constant.ERROR_REQUEST_CP : 			resultCode = 103; break;	//预支付中请求地址和请求参数不匹配(cpName和cpId不匹配)****-21002
				case  Constant.ERROR_REQUEST_CPGAME_STATE:	resultCode = 103; break;	//渠道游戏配置未生效
				case  Constant.ERROR_REQUEST_CP_STATE:		resultCode = 103; break; 	//渠道配置未开放
				case  Constant.ERROR_CHARGE_STATUS : 		resultCode = 103; break;	//合作伙伴没有开通充值请联系管理员开通****-21003
				case  Constant.ERROR_CHARGE_SIGN : 			resultCode = 103; break;	//获取CpKey值异常****-21005
				case  Constant.ERROR_CHARGE_MONEY : 		resultCode = 101; break;	//预支付金额和回调金额不符****-21010
				case  Constant.ERROR_FAIL_ORDER : 			resultCode = 103; break;	//此订单为失败订单****-21011
				case  Constant.ERROR_CHARGE_SERVER_IP : 	resultCode = 103; break;	//没有配置合作伙伴ServerIP白名单****-21012
				case  Constant.ERROR_SERVER_IP : 			resultCode = 103; break;	//与配置合作伙伴ServerIP白名单，不匹配****-21013
				default : resultCode = 103;
			}
			this.response(response, String.valueOf(resultCode));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive yiwanCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, String.valueOf(103));
			return;
		}
	}

	/**
	 * UC充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP body is JSON
	 * 
	 * @return void
	 */

	@RequestMapping(value = "ucCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void ucCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		UCReqBean jsonbean = null;
		UCChargingFormBean form = null;
		try {
			byte[] content = new byte[request.getContentLength()];
			request.getInputStream().read(content);
			jsonbean = JsonUtil.convertJsonToBean(new String(content, "UTF-8"), UCReqBean.class);
			form = new UCChargingFormBean(jsonbean, gameName, cpName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.ucCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "SUCCESS" : "FAILURE";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive ucCharge request param error:"+form.toString()+"error info:"+e.getMessage());
			this.response(response, "FAILURE");
			return;
		}
	}
	
	/**
	 * 百度充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * 
	 * @method HTTP POST/GET
	 * @SignEncryptedAlgorithm MD5
	 *                         HTTP Parameter is JSON
	 *                         1 业务正确 2 AppID无效 3 Sign无效 4 参数错误
	 * @return void
	 */

	@RequestMapping(value = "baiduCharge/{gameName}/{cpName}", method = { RequestMethod.POST, RequestMethod.GET })
	public void baiduCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		BaiduChargingFormBean form = null;
		String AppID = null;
		try {
			AppID = request.getParameter("AppID");
			String OrderSerial = request.getParameter("OrderSerial");
			String CooperatorOrderSerial = request.getParameter("CooperatorOrderSerial");
			String Sign = request.getParameter("Sign");
			String Content = Common.decodeBase64(request.getParameter("Content"));
			BaiduReqBean jsonbean = JsonUtil.convertJsonToBean(Content, BaiduReqBean.class);
			if (jsonbean == null) {
				throw new Exception("Content param error");
			}
			form = new BaiduChargingFormBean(gameName, cpName, jsonbean, Integer.parseInt(AppID),
					OrderSerial, CooperatorOrderSerial, Sign);
			ValidateService.valid(form);
			ResultBean result = cpChargeCallBackService.baiduCharging(form, Content, Common.getTrueIP(request));
			BaiduResBean resBean = new BaiduResBean(form.getAppID(),result.getResultCode(),"");
			if(resBean.getResultCode() != Constant.ERROR_CHARGE_SIGN){
				resBean.setSign(MD5SignatureChecker.baiduSignGenerator(resBean, result.getCpKey()));
			}
			this.response(response, JsonUtil.convertBeanToJson(resBean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive baiduCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			BaiduResBean resBean = new BaiduResBean(Integer.parseInt(AppID),4,"");
			resBean.setResultMsg(e.toString());
			this.response(response, JsonUtil.convertBeanToJson(resBean));
			return;
		}
	}

	/**
	 * 果盘安卓充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param GuoPanChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP body is form
	 * 
	 * @return void
	 */

	@RequestMapping(value = "guopanCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void guopanCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, GuoPanChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.guopanCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "success" : "fail";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive guopanCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fails");
			return;
		}
	}

	/**
	 * 魅族充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param MeiZuChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP body is form
	 * 
	 * @return void
	 * 
	 *         200 成功发货 120013 尚未发货 120014 发货失败 900000 未知异常
	 * 
	 */

	@RequestMapping(value = "meizuCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void meizuCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, MeiZuChargingFormBean form) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.meizuCharging(form, Common.getTrueIP(request));
			MeiZuResBean jsonbean = new MeiZuResBean(resultCode,null,null);
			this.response(response, JsonUtil.convertBeanToJson(jsonbean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive meizuCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			MeiZuResBean jsonbean = new MeiZuResBean(Constant.ERROR_SYSTEM,null,null);
			jsonbean.setMessage(e.toString());
			this.response(response, JsonUtil.convertBeanToJson(jsonbean));
		}
	}

	/**
	 * TT游戏充值回调信息认证
	 * 备注： 没有回测，因为无法还原线上数据，
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param MeiZuChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP body is JSON
	 * 
	 * @return void
	 */

	@RequestMapping(value = "ttCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void ttCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		TTChargingFormBean form = null;
		try {
			String sign = request.getHeader("sign");
			byte[] arrays = new byte[request.getContentLength()];
			request.getInputStream().read(arrays);
			String content = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			TTReqBean jsonbean = JsonUtil.convertJsonToBean(content, TTReqBean.class);
			form = new TTChargingFormBean(gameName, cpName, jsonbean, sign);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.ttCharging(form, Common.getTrueIP(request), content);
			TTData data = new TTData();
			if (Common.dealSuccess(resultCode)) {
				data.setResult(0);
				data.setMessage("通知成功");
			} else {
				data.setResult(1);
				data.setMessage("通知失败");
			}
			TTResBean bean = new TTResBean();
			bean.setHead(data);
			this.response(response, JsonUtil.convertBeanToJson(bean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive ttCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			TTData data = new TTData();
			data.setResult(1);
			data.setMessage("未知异常");
			TTResBean bean = new TTResBean();
			bean.setHead(data);
			this.response(response, JsonUtil.convertBeanToJson(bean));
			return;
		}
	}

	/**
	 * 金立充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param JinLiChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         Data is in the HTTP Parameter
	 * 
	 * @return void
	 * 
	 * 
	 */
	
	@RequestMapping(value = "jinliCharge/{gameName}/{cpName}", method = { RequestMethod.POST }, headers = {
			"Content-Type=application/x-www-form-urlencoded" })
	public void jinliCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, JinLiChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.jinliCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "success" : "fail";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive jinliCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
			return;
		}
	}

	/**
	 * PPS充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param PPSChargingFormBean
	 *            bean
	 * 
	 * @method HTTP GET
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         Data is in the HTTP Parameter
	 * 
	 * @return void
	 * 
	 *         ---------resultCode---------- 0：success -1：Sign error
	 *         -2：Parameters error -3：User not exists -4：Order repeat -5：No
	 *         server -6：Other errors ----------resultCode---------
	 */
	@RequestMapping(value = "ppsCharge/{gameName}/{cpName}")
	public void ppsCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, PPSChargingFormBean form) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setUserData(URLDecoder.decode(form.getUserData(), "UTF-8"));
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.ppsCharging(form, Common.getTrueIP(request));
			PPSResBean resbean = new PPSResBean(resultCode);
			this.response(response, JsonUtil.convertBeanToJson(resbean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive ppsCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			PPSResBean resbean = new PPSResBean(Constant.ERROR_SYSTEM);
			resbean.setMessage(e.toString());
			this.response(response, JsonUtil.convertBeanToJson(resbean));
			return;
		}
	}

	/**
	 * PPTV充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param PPTVChargingFormBean
	 *            bean
	 * 
	 * @method HTTP GET
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         Data is in the HTTP Parameter
	 * 
	 * @return void
	 * 
	 *         ------resultCode----------- 1 支付成功 2 支付失败 3 订单已存在 4 验证失败 5 用户不存在
	 *         6 充值金额错误 ------resultCode----------
	 * 
	 */

	@RequestMapping(value = "pptvCharge/{gameName}/{cpName}", method = { RequestMethod.GET })
	public void pptvCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, PPTVChargingFormBean form) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setExtra(URLDecoder.decode(form.getExtra(), "UTF-8"));
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.pptvCharging(form, Common.getTrueIP(request));
			PPTVResBean bean = new PPTVResBean(resultCode);
			this.response(response, JsonUtil.convertBeanToJson(bean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive pptvCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			PPTVResBean bean = new PPTVResBean(Constant.ERROR_SYSTEM);
			bean.setMessage(e.toString());
			this.response(response, JsonUtil.convertBeanToJson(bean));
			return;
		}
	}

	/**
	 * 酷派充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm RSA
	 * 
	 *                         Data is in the JSON
	 * 
	 * @return void
	 */

	@RequestMapping(value = "kupaiCharge/{gameName}/{cpName}", method = { RequestMethod.POST }, headers = {
			"Content-Type=application/x-www-form-urlencoded " })
	public void kupaiCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		String sign = null;
		String json = null;
		KuPaiChargingNewFormBean form = null;
		try {
			byte[] arrays = new byte[request.getContentLength()];
			request.getInputStream().read(arrays);
			String content = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			String[] keyAndValues = content.split("&");
			for (String keyAndValue : keyAndValues) {
				String[] paramChain = keyAndValue.split("=",2);
				switch (paramChain[0]) {
				case "transdata":
					json = paramChain[1];
					break;
				case "sign":
					sign = paramChain[1];
					break;
				
				default:
				}
			}
			KuPaiNewReqBean jsonbean = JsonUtil.convertJsonToBean(json, KuPaiNewReqBean.class);
			form = new KuPaiChargingNewFormBean(gameName, cpName, jsonbean, sign);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.kupaiCharging(form, Common.getTrueIP(request), json);
			String result =  Common.dealSuccess(resultCode) ? "SUCCESS" : "FAILURE";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive kupaiCharge request param error:"+form.toString()+"error info:"+e.getMessage());
			this.response(response, "FAILURE");
			return;
		}
	}

	/**
	 * 今日头条充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param JRTTChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm RSA
	 * 
	 *                         Data is in the HTTP Form
	 * 
	 * @return void
	 */

	@RequestMapping(value = "jrttCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void jrttCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, JRTTChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.jrttCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "success" : "fail";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive jrttCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
			return;
		}
	}

	/**
	 * 联想充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm RSA
	 * 
	 *                         HTTP Body is Form
	 * 
	 * @return void
	 */

	@RequestMapping(value = "levonoCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void levonoCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		String sign = null;
		String transdata = null;
		LenovoCharginFormBean bean = null;
		try {
			byte[] arrays = new byte[request.getContentLength()];
			request.getInputStream().read(arrays);
			String content = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			String[] keyAndValues = content.split("&");
			for (String keyAndValue : keyAndValues) {
				String[] paramChain = keyAndValue.split("=",2);
				if (paramChain[0].equals("transdata")) {
					transdata = paramChain[1];
				}
				if (paramChain[0].equals("sign")) {
					sign = paramChain[1];
				}
			}
			LenovoReqBean jsonbean = JsonUtil.convertJsonToBean(transdata, LenovoReqBean.class);
			bean = new LenovoCharginFormBean(gameName, cpName, jsonbean, sign);
			ValidateService.valid(bean);
			int resultCode = cpChargeCallBackService.levonoCharging(bean, Common.getTrueIP(request), transdata);
			String result =  Common.dealSuccess(resultCode) ? "SUCCESS" : "FAILURE";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive levonoCharge request param error:"+bean.toString()+",error info:"+e.getMessage());
			this.response(response, "FAILURE");
			return;
		}
	}

	/**
	 * 啪啪游戏厅充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param PaPaCharginFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP Body is Form
	 * 
	 * @return void
	 */

	@RequestMapping(value = "papaCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void papaCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, PaPaCharginFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = this.cpChargeCallBackService.papaCharging(form, Common.getTrueIP(request));
			this.response(response, "ok");
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive papaCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "ok");
			return;
		}
	}

	/**
	 * 安智充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm DES3
	 * 
	 *                         HTTP Body is JSON
	 * 
	 * @return void
	 */

	@RequestMapping(value = "anzhiCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void anzhiCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		AnzhiChargingFormBean form = null;
		try {
			byte[] arrays = new byte[request.getContentLength()];
			request.getInputStream().read(arrays);
			String content = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			String encodedContent = content.split("=",2)[1].replace(" ", "+").replace("\\", "");
			String publicKey = cpChargeCallBackService.getAnZhiDes3Key(gameName, cpName);
			if (publicKey == null) {
				LoggerUtil.error(CPChargeCallBackController.class,"receive anzhi request param error:"+encodedContent);
				this.response(response, "fail");
				return;
			}
			String decodedContent = Des3Util.decrypt(encodedContent, publicKey);
			AnzhiReqBean jsonbean = JsonUtil.convertJsonToBean(decodedContent, AnzhiReqBean.class);
			form = new AnzhiChargingFormBean(gameName, cpName, jsonbean);
			ValidateService.valid(form);
			switch (cpChargeCallBackService.anzhiCharging(form, Common.getTrueIP(request))) {
			case 1:
			case -1472:
				this.response(response, "success");
				break;
			case -21010:
				this.response(response, "money_error");
				break;
			default:
				this.response(response, "fail");
				break;
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive anzhiCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
			return;
		}

	}

	/**
	 * 斗鱼充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param DouyuChargingFormBean
	 *            bean
	 * 
	 * 
	 * @method HTTP GET/POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP Body is Form Or Not
	 * 
	 * @return void
	 */

	@RequestMapping(value = "douyuCharge/{gameName}/{cpName}", method = { RequestMethod.POST, RequestMethod.GET })
	public void douyuCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, DouyuChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.douyuCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "success" : "failure";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive douyuCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "failure");
		}
	}

	/**
	 * 4399充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param FTNNChargingFormBean
	 *            bean
	 * 
	 * @method HTTP GET
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP Body is Form
	 * 
	 * @return void
	 */

	@RequestMapping(value = "ftnnCharge/{gameName}/{cpName}" , method = { RequestMethod.GET })
	public void ftnnCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, FTNNChargingFormBean form) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			Integer resultCode = cpChargeCallBackService.ftnnCharging(form, Common.getTrueIP(request));
			FTNNResBean resbean = new FTNNResBean(resultCode, form.getMoney(), form.getGamemoney());
			this.response(response, JsonUtil.convertBeanToJson(resbean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive ftnnCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			FTNNResBean resbean = new FTNNResBean(Constant.ERROR_SYSTEM, form.getMoney(), form.getGamemoney());
			resbean.setMsg(e.toString());
			this.response(response, JsonUtil.convertBeanToJson(resbean));
		}
	}

	/**
	 * 乐视视频充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param LeVideoChargingFormBean
	 *            bean
	 * 
	 * 
	 * @method HTTP GET
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP Body is Form
	 * 
	 * @return void
	 */

	@RequestMapping(value = "leVideoCharge/{gameName}/{cpName}", method = { RequestMethod.GET })
	public void leVideoCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, LeVideoChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.leVideoCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "success" : "fail";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive leVideoCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
		}
	}

	/**
	 * 朋友玩充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP Body is JSON
	 * 
	 * @return void
	 *
	 *         ACK 数值参考HTTP CODE
	 */

	@RequestMapping(value = "pywCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void pywCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		PywChargingFormBean form = null;
		byte[] arrays = new byte[request.getContentLength()];
		try {
			request.getInputStream().read(arrays);
			String content = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			PywReqBean jsonbean = JsonUtil.convertJsonToBean(content, PywReqBean.class);
			form = new PywChargingFormBean(gameName, cpName, jsonbean);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.pywCharging(form, Common.getTrueIP(request));
			PywResBean resbean = new PywResBean();
			if (Common.dealSuccess(result)) {
				resbean.setAck(200);
				resbean.setMsg("Ok");
			} else {
				resbean.setAck(400);
				resbean.setMsg("Error");
			}
			this.response(response, JsonUtil.convertBeanToJson(resbean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive pywCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			PywResBean resbean = new PywResBean();
			resbean.setAck(400);
			resbean.setMsg(e.toString());
			this.response(response, JsonUtil.convertBeanToJson(resbean));
		}
	}
	/**
	 * 朋友玩变态版充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm MD5
	 * 
	 *                         HTTP Body is JSON
	 * 
	 * @return void
	 *
	 *         ACK 数值参考HTTP CODE
	 */

	@RequestMapping(value = "pywBTCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void pywBTCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		PywBTChargingFormBean form = null;
		byte[] arrays = new byte[request.getContentLength()];
		try {
			request.getInputStream().read(arrays);
			String content = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			PywBTReqBean jsonbean = JsonUtil.convertJsonToBean(content, PywBTReqBean.class);
			form = new PywBTChargingFormBean(gameName, cpName, jsonbean);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.pywBTCharging(form, Common.getTrueIP(request));
			PywBTResBean resbean = new PywBTResBean();
			if (Common.dealSuccess(result)) {
				resbean.setAck(200);
				resbean.setMsg("Ok");
			} else {
				resbean.setAck(400);
				resbean.setMsg("Error");
			}
			this.response(response, JsonUtil.convertBeanToJson(resbean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive pywBTCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			PywBTResBean resbean = new PywBTResBean();
			resbean.setAck(400);
			resbean.setMsg(e.toString());
			this.response(response, JsonUtil.convertBeanToJson(resbean));
		}
	}
	/**
	 * 小米充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param XiaoMiChargingFormBean
	 *            bean
	 * 
	 * @method HTTP GET
	 * 
	 * @SignEncryptedAlgorithm HmacSHA1
	 * 
	 *                         HTTP Body is Form
	 * 
	 * @return void
	 * 
	 * --- errcode --- 
	 * 1506  cpOrderId错误
	 * 1515  appId错误 
	 * 1516  uid错误
	 * 1525  signature错误
	 * --- errMsg --- 
	 * 错误信息 （optional）
	 */

	@RequestMapping(value = "xiaomiCharge/{gameName}/{cpName}")
	public void xiaomiCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, XiaoMiChargingFormBean form) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int errcode = cpChargeCallBackService.xiaomiCharging(form, Common.getTrueIP(request));
			XiaoMiResBean bean = new XiaoMiResBean(errcode);
			this.response(response, JsonUtil.convertBeanToJson(bean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive xiaomiCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			XiaoMiResBean bean = new XiaoMiResBean(Constant.ERROR_SYSTEM);
			bean.setErrMsg(e.toString());
			this.response(response,JsonUtil.convertBeanToJson(bean));
		}
	}
	
	/**
	 * 金山云充值回调充值回调信息认证(与小米公用文档)
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param XiaoMiChargingFormBean
	 *            bean
	 * 
	 * @method HTTP GET
	 * 
	 * @SignEncryptedAlgorithm HmacSHA1
	 * 
	 *                         HTTP Body is Form
	 * 
	 * @return void
	 * 
	 * --- errcode --- 
	 * 1506  cpOrderId错误
	 * 1515  appId错误 
	 * 1516  uid错误
	 * 1525  signature错误
	 * --- errMsg --- 
	 * 错误信息 （optional）
	 */

	@RequestMapping(value = "ksyunCharge/{gameName}/{cpName}")
	public void ksyunCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, XiaoMiChargingFormBean form) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setCpUserInfo(URLDecoder.decode(form.getCpUserInfo(), "UTF-8"));
			form.setProductName(URLDecoder.decode(form.getProductName(), "UTF-8"));
			form.setOrderStatus(URLDecoder.decode(form.getOrderStatus(), "UTF-8"));
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int errcode = cpChargeCallBackService.xiaomiCharging(form, Common.getTrueIP(request));
			XiaoMiResBean bean = new XiaoMiResBean(errcode);
			this.response(response, JsonUtil.convertBeanToJson(bean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive ksyunCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			XiaoMiResBean bean = new XiaoMiResBean(Constant.ERROR_SYSTEM);
			bean.setErrMsg(e.toString());
			this.response(response,JsonUtil.convertBeanToJson(bean));
		}
	}
	
	/**
	 * 拇指玩充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param MuzhiwanChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * 
	 * @SignEncryptedAlgorithm md5
	 * 
	 * HTTP Body is Form
	 * 
	 * @return void
	 */
	@RequestMapping(value = "muzhiwanCharge/{gameName}/{cpName}", method = { RequestMethod.GET })
	public void muzhiwanCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, MuzhiwanChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.muzhiwanCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "SUCCESS" : "FAIL";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive muzhiwanCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAIL");
		}
	}

	/**
	 * 豌豆荚充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * @method HTTP POST
	 * 
	 * @SignEncryptedAlgorithm RSA
	 * 
	 * HTTPBody is Form
	 * 
	 * @return void
	 */
	@RequestMapping(value = "wandoujiaCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void wandoujiaCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		String content = null;
		String signType = null;
		String sign = null;
		WandoujiaChargingFormBean form = null;
		try {
			byte[] arrays = new byte[request.getContentLength()];
			request.getInputStream().read(arrays);
			String formBody = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			String[] formBodyArray = formBody.split("&");
			for (String parameter : formBodyArray) {
				String[] keyAndValue = parameter.split("=",2);
				switch (keyAndValue[0]) {
				case "content":
					content = keyAndValue[1];break;
				case "signType":
					signType = keyAndValue[1];break;
				case "sign":
					sign = keyAndValue[1];break;
				default:
				}
			}
			WDJReqBean reqbean = JsonUtil.convertJsonToBean(content, WDJReqBean.class);
			form = new WandoujiaChargingFormBean(reqbean, gameName, cpName, signType, sign);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.wandoujiaCharging(form, Common.getTrueIP(request),content);
			String result =  Common.dealSuccess(resultCode) ? "success" : "fail";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive wandoujiaCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
			return;
		}
	}

	/**
	 * 新浪充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param SinaChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * @SignEncryptedAlgorithm SHA1
	 * 
	 *                         HTTP Body is Form
	 * 
	 * @return void
	 */

	@RequestMapping(value = "sinaCharge/{gameName}/{cpName}")
	public void sinaCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, SinaChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.sinaCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "OK" : "REJECT";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive sinaCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "REJECT");
			return;
		}
	}

	/**
	 * 优酷充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param SinaChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * 
	 * @SignEncryptedAlgorithm SHA1
	 * 
	 * HTTP Body is Form
	 * 
	 * @return void
	 */

	@RequestMapping(value = "youkuCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void youkuCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, YoukuChargingFormBean form) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			Integer resultCode = cpChargeCallBackService.youkuCharging(form, Common.getTrueIP(request));
			YouKuResBean bean = new YouKuResBean(resultCode);
			this.response(response, JsonUtil.convertBeanToJson(bean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive youkuCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			YouKuResBean bean = new YouKuResBean(Constant.ERROR_SYSTEM);
			bean.setDesc(e.toString());
			this.response(response, JsonUtil.convertBeanToJson(bean));
			return;
		}
	}

	/**
	 * 应用汇充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * @method HTTP POST
	 * 
	 * @SignEncryptedAlgorithm RSA
	 * 
	 *                         HTTP Body is Form
	 * 
	 * @return void
	 */

	@RequestMapping(value = "yingyonghuiCharge/{gameName}/{cpName}", method = { RequestMethod.POST }, headers = {
			"Content-Type=application/x-www-form-urlencoded " })
	public void yingyonghuiCharge(@PathVariable String gameName, @PathVariable String cpName,
			HttpServletRequest request, HttpServletResponse response) {
		String transdata = null;
		String signtype = null;
		String sign = null;
		YYHChargingFormBean form = null;
		try {
			byte[] arrays = new byte[request.getContentLength()];
			request.getInputStream().read(arrays);
			String formBody = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			String[] formBodyArray = formBody.split("&");
			for (String parameter : formBodyArray) {
				String[] keyAndValue = parameter.split("=",2);
				switch (keyAndValue[0]) {
					case "transdata":
						transdata = keyAndValue[1];break;
					case "signtype":
						signtype = keyAndValue[1];break;
					case "sign":
						sign = keyAndValue[1];break;
					default:
				}
			}
			YYHReqBean reqbean = JsonUtil.convertJsonToBean(transdata, YYHReqBean.class);
			form = new YYHChargingFormBean(reqbean, gameName, cpName, sign, signtype);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.yingyonghuiCharging(form, Common.getTrueIP(request),transdata);
			String result = Common.dealSuccess(resultCode) ? "SUCCESS" : "FAIL";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive yingyonghuiCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAIL");
		}
	}

	/**
	 * 乐视手机充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param LePhoneChargingFormBean
	 *            bean
	 * 
	 * @method HTTP GET
	 * 
	 * @SignEncryptedAlgorithm MD5
	 * 
	 * @return void
	 */

	@RequestMapping(value = "lePhoneCharge/{gameName}/{cpName}", method = { RequestMethod.GET })
	public void lePhoneCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, LePhoneChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.lePhoneCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "success" : "fail";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive lePhoneCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
			return;
		}
	}

	/**
	 * 乐视TV充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * 
	 * @method HTTP GET
	 * 
	 * @SignEncryptedAlgorithm MD5
	 * 
	 * @return void
	 */

	@RequestMapping(value = "leTvCharge/{gameName}/{cpName}", method = { RequestMethod.GET })
	public void leTvCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		LeTvChargingFormBean form = null;
		try {
			String appKey = request.getParameter("appKey");
			String currencyCode = request.getParameter("currencyCode");
			String params = request.getParameter("params") == null ? null
					: URLDecoder.decode(request.getParameter("params"), "UTF-8");
			String price = request.getParameter("price");
			String products = URLDecoder.decode(request.getParameter("products"), "UTF-8");
			String pxNumber = request.getParameter("pxNumber");
			String userName = URLDecoder.decode(request.getParameter("userName"), "UTF-8");
			String sign = request.getParameter("sign");
			LeTvReqBean bean = JsonUtil.convertJsonToBean(products.substring(1, products.length()-1), LeTvReqBean.class);
			if (bean == null) {
				throw new Exception("Params are empty");
			}
			form = new LeTvChargingFormBean(bean, gameName, cpName, appKey, currencyCode, params,
					price, pxNumber, userName, sign);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.leTvCharging(form, Common.getTrueIP(request), products);
			String result =  Common.dealSuccess(resultCode) ? "SUCCESS" : "FAIL";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive leTvCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAIL");
			return;
		}
	}
	
	/**
	 * 机锋充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param sign time为key、value形式
	 * 		  订单信息为
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "jifengCharge/{gameName}/{cpName}")
	public void jifengCharge(@PathVariable String gameName, @PathVariable String cpName, String sign, String time,
			HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		Document document = DocumentHelper.createDocument();// 创建根节点  
		Element root = document.addElement("response");  
		Element ed = root.addElement("ErrorDesc");  
		Element ec = root.addElement("ErrorCode");  
		JifengChargingFormBean form = null;
		try {
			byte[] arrays = new byte[request.getContentLength()];
			request.getInputStream().read(arrays);
			String params = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			form = new JifengChargingFormBean(gameName,cpName,sign,time);
			ValidateService.valid(form);
			Integer resultCode = cpChargeCallBackService.jifengCharging(params, form, Common.getTrueIP(request));
			JifengResBean resultBean = new JifengResBean(resultCode);
			ec.setText(resultBean.getErrorCode()+"");  
	        ed.setText(resultBean.getErrorDesc());  
	        this.response(response, document.asXML().split("\n")[1]);
			return;
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive jifengCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			ec.setText("0");  
	        ed.setText(e.toString());  
	        this.response(response, document.asXML().split("\n")[1]);
			return;
		}
	}
	/**
	 * 8868
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "juGameCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void juGameCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		JuGameChargingFormBean form = null;
		byte[] arrays = new byte[request.getContentLength()];
		try {
			request.getInputStream().read(arrays);
			String content = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			form = JsonUtil.convertJsonToBean(content, JuGameChargingFormBean.class);
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			ValidateService.valid(form.getData());
			int result = cpChargeCallBackService.juGameCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else {
				this.response(response, "FAILURE");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive juGameCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAILURE");
		}
	}
	/**
	 * 乐游
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "leyouCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void leyouCharge(@PathVariable String gameName, @PathVariable String cpName, LeyouChargingFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.leyouCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "success");
			} else if(result == Constant.ERROR_SIGN){
				this.response(response, "errorSign");
			}else{
				this.response(response, "error");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive leyouCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "error");
		}
	}
	/**
	 * 猎宝
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "liebaoCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void liebaCharge(@PathVariable String gameName, @PathVariable String cpName, LiebaoChargingFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.liebaoCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "success");
			} else if(result == Constant.ERROR_SIGN){
				this.response(response, "errorSign");
			}else{
				this.response(response, "error");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive liebaCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "error");
		}
	}
	/**
	 * 迅雷
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * 1	成功
		2	订单重复
		-1	提交参数不全
		-2	验证失败
		-3	用户不存在
		-4	请求超时
		-5	服务器编号错误
		-6	Ip限制
	 */
	@RequestMapping(value = "xunleiCharge/{gameName}/{cpName}")
	public void xunleiCharge(@PathVariable String gameName, @PathVariable String cpName, XunleiChargingFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			Integer result = cpChargeCallBackService.xunleiCharging(form, Common.getTrueIP(request));
			switch(result){
				case Constant.SUCCESS : result = 1; break;
				case Constant.ERROR_ORDER_DUPLICATE : result = 2; break;
				case Constant.ERROR_PARAM_VALIDATE : result = -2; break;
				case Constant.ERROR_SYSTEM : result = -5; break;
				case Constant.ERROR_ORDER_EXIST : result = -2; break;
				case Constant.ERROR_SIGN : result = -2; break;
				case Constant.ERROR_REQUEST_CPGAME : result = -2; break;
				case  Constant.ERROR_REQUEST_CPGAME_STATE:	result = -2 ; break;//渠道游戏配置未生效
				case  Constant.ERROR_REQUEST_CP_STATE: result = -2 ; break;//渠道配置未开放
				case Constant.ERROR_REQUEST_CP :result = -2; break;
				case Constant.ERROR_CHARGE_STATUS : result = -2; break;
				case Constant.ERROR_CHARGE_SIGN : result = -5; break;
				case Constant.ERROR_CHARGE_MONEY : result = -5; break;
				case Constant.ERROR_FAIL_ORDER : result = -5; break;
				case Constant.ERROR_CHARGE_SERVER_IP : result = -5; break;
				case Constant.ERROR_SERVER_IP : result = -6; break;
			}
			this.response(response, result.toString());
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive xunleiCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "-5");
		}
	}
	/**
	 * 虫虫
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "ccCharge/{gameName}/{cpName}")
	public void ccCharge(@PathVariable String gameName, @PathVariable String cpName, CCChargingFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			Integer result = cpChargeCallBackService.ccCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "success");
			} else{
				this.response(response, "fail");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive ccCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
		}
	}
	
	/**
	 *ICC
	 *备注：sszj接的，但没有上线，所以先不用开发
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "iccCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void iccCharge(@PathVariable String gameName, @PathVariable String cpName,ICCChargingFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ICCChargingFormBean.content data = JsonUtil.convertJsonToBean(form.getContent(), ICCChargingFormBean.content.class);
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			ValidateService.valid(data);
			Integer result = cpChargeCallBackService.iccCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "success");
			} else{
				this.response(response, "error");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive iccCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "error");
		}
	}
	
	/**
	 *快用
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "kyCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void kyCharge(@PathVariable String gameName, @PathVariable String cpName,KYChargingFormBean form ,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			Integer result = cpChargeCallBackService.kyCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "success");
			} else{
				this.response(response, "error");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive kyCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "error");
		}
	}
	/**
	 *内涵段子
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "neihanCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void neihanCharge(@PathVariable String gameName, @PathVariable String cpName,NeihanChargingFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			Integer result = cpChargeCallBackService.neihanCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "success");
			} else{
				this.response(response, "error");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive neihanCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "error");
		}
	}
	/**
	 *三星
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "samsungCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void samsungCharge(@PathVariable String gameName, @PathVariable String cpName,SamsungChargingFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			form.setTransdata(Common.urlDecode(form.getTransdata(),"UTF-8"));
			form.setSign(Common.urlDecode(form.getSign(),"UTF-8").replace(" ", "+").replace("\\", ""));
			SamsungChargingFormBean.OrderInfo orderInfoBean = JsonUtil.convertJsonToBean(form.getTransdata(), SamsungChargingFormBean.OrderInfo.class);
			ValidateService.valid(form);
			Integer result = cpChargeCallBackService.samsungCharging(form, orderInfoBean, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else{
				this.response(response, "FAILURE");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive samsungCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAILURE");
		}
	}

	/**
	 * 爱贝回调,因为爱贝的回调和三星一样，估直接采用三星的回调代码
	 */
	@RequestMapping(value = "aibeiCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void aibeiCharge(SamsungChargingFormBean form, HttpServletRequest request, HttpServletResponse response) {
		try{
			form.setTransdata(Common.urlDecode(form.getTransdata(),"UTF-8"));
			form.setSign(Common.urlDecode(form.getSign(),"UTF-8").replace(" ", "+").replace("\\", ""));
			SamsungChargingFormBean.OrderInfo orderInfoBean = JsonUtil.convertJsonToBean(form.getTransdata(), SamsungChargingFormBean.OrderInfo.class);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.samsungCharging(form, orderInfoBean, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else{
				this.response(response, "FAILURE");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class, "receive aibeiCharge request param error:" + form.toString() + ",error info:" + e.toString());
			this.response(response, "FAILURE");
		}
	}
	/**
	 * 靠谱
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "kaopuCharge/{gameName}/{cpName}", method = { RequestMethod.GET })
	public void kaopuCharge(@PathVariable String gameName, @PathVariable String cpName,KaopuChargingFormBean form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		KaopuResBean resultBean;
		ResultBean result = new ResultBean();
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			result = cpChargeCallBackService.kaopuCharging(form, Common.getTrueIP(request));
			resultBean = new KaopuResBean(result.getResultCode());
			resultBean.setSign(MD5SignatureChecker.kaopuResutMD5(resultBean, result.getCpKey()));
			this.response(response, JsonUtil.convertBeanToJson(resultBean));
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive kaopuCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			resultBean = new KaopuResBean("1004",e.toString());
			try {
				resultBean.setSign(MD5SignatureChecker.kaopuResutMD5(resultBean, result.getCpKey()));
			} catch (UnsupportedEncodingException e1) {
				LoggerUtil.error(CPChargeCallBackController.class,"receive kaopuCharge request param error:"+form.toString()+",error info:"+e1.getMessage());
			}
			this.response(response, JsonUtil.convertBeanToJson(resultBean));
		
			
		}
	}
	/**
	 * 搜狗充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param SouGouChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * 
	 * @SignEncryptedAlgorithm MD5
	 * 
	 * @return void
	 * 
	 *  OK 成功
     *  ERR_100 参数不符合规则
     *  ERR_200 验证失败
     *  ERR_300 账号不存在
     *  ERR_400 非法ip访问
     *  ERR_500 其他错误
	 */

	@RequestMapping(value = "souGouCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void souGouCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, SouGouChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive souGouCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "ERR_100");
			return;
		}
		try {
			Integer result = cpChargeCallBackService.souGouCharging(form, Common.getTrueIP(request));
			String message = "";
			switch(result){
				case Constant.SUCCESS :
				case Constant.ERROR_ORDER_DUPLICATE : message = "OK"; break;
				case Constant.ERROR_SIGN : message = "ERR_200"; break;
				case Constant.ERROR_SERVER_IP : message = "ERR_400"; break;
				default : message = "ERR_500";break;
			}		
			this.response(response, message);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive souGouCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "ERR_500");
			return;
		}
		
	}
	/**
	 * 37充值回调充值回调信息认证
	 * 
	 * @param String
	 *            gameName
	 * @param String
	 *            cpName
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @param TSChargingFormBean
	 *            bean
	 * 
	 * @method HTTP POST
	 * 
	 * @SignEncryptedAlgorithm MD5
	 * 
	 * @return void
	 *  
	 * 
	 */

	@RequestMapping(value = "stCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void tsCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, TSChargingFormBean form) {
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.stCharging(form, Common.getTrueIP(request));
			String result =  Common.dealSuccess(resultCode) ? "1": "0";
			this.response(response, result);
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive tsCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "0");
			return;
		}
	}
	/**
	 * 坚果充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * 
	 *  @SignEncryptedAlgorithm MD5
	 *  
	 *  @method HTTP POST
	 */
	@RequestMapping(value = "jianGuoCharge/{gameName}/{cpName}")
	public void jianGuoCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		byte[] arrays = new byte[request.getContentLength()];
		JianGuoReqBean form = new JianGuoReqBean();
		try {
			request.getInputStream().read(arrays);
			String content = URLDecoder.decode(new String(arrays, "UTF-8"), "UTF-8");
			form = JsonUtil.convertJsonToBean(content, JianGuoReqBean.class);
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.jianGuoCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else {
				this.response(response, "FAILURE");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive jianGuoCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAILURE");
		}
	}
	/**
	 * 57K
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "57KCharge/{gameName}/{cpName}")
	public void fiveSevenKCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,FiveSevenKChargingFormBean form) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.fiveSevenKCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else {
				this.response(response, "FAILURE");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive fiveSevenKCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAILURE");
		}
	}
	/**
	 * 泡椒IOS充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "paojiaoIOSCharge/{gameName}/{cpName}")
	public void paojiaoIosCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,PaoJiaoIOSFormBean form){
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.paojiaoIosCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else {
				this.response(response, "FAIL");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive paojiaoIosCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAIL");
		}
	}
	/**
	 * 泡椒Android充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "paojiaoAndroidCharge/{gameName}/{cpName}")
	public void paojiaoAndroidCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,PaoJiaoAndroidFormBean form){
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.paojiaoAndroidCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else {
				this.response(response, "FAIL");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive paojiaoAndroidCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAIL");
		}
	}
	/**
	 * 浩动IOS充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "haodongIOSCharge/{gameName}/{cpName}")
	public void haodongIOSCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,HaodongIOSFormBean form){
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.haodongIosCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else {
				this.response(response, "FAIL");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive haodongIOSCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAIL");
		}
	}
	/**
	 * 浩动Android青冥双剑-充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "haodongAndroidQMSJCharge/{gameName}/{cpName}")
	public void haodongAndroidQMSJCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,HaodongAndroidQMSJFormBean form){
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.haodongAndroidQMSJCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else {
				this.response(response, "FAIL");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive haodongAndroidQMSJCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAIL");
		}
	}
	/**
	 * 浩动Android-蜀山剑雨充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "haodongAndroidSSJYCharge/{gameName}/{cpName}")
	public void haodongAndroidSSJYCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,HaodongAndroidSSJYFormBean form){
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.haodongAndroidSSJYCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			} else {
				this.response(response, "FAIL");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive haodongAndroidSSJYCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAIL");
		}
	}
	/**
	 * 浩动Android(游戏猫)充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param data
	 */
	@RequestMapping(value = "haodongAndroidCharge/{gameName}/{cpName}")
	public void haodongAndroidCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,String data){
		response.setContentType("text/html;charset=UTF-8");
		HaodongAndroidReqBean haodongAndroidReqBean = new HaodongAndroidReqBean();
		data = data.replace(" ", "+");
		try {
			if(data == null || "".equals(data)){
				LoggerUtil.error(CPChargeCallBackController.class,"receive haodongAndroidCharge request param error:"+data+"error info:data param is null");
				this.response(response, JsonUtil.convertBeanToJson(new HaodongAndroidResbean("-21006", "参数data不能为空", "")));
				return;
			}
			//获取DES加密key
			String DESKey = cpChargeCallBackService.getHaodongAndroidDESKey(gameName, cpName);
			if (DESKey == null) {
				LoggerUtil.error(CPChargeCallBackController.class,"receive haodongAndroidCharge request param error:"+data+"error info:get desc key error");
				this.response(response, JsonUtil.convertBeanToJson(new HaodongAndroidResbean("-21005 ", "FAIL", "")));
				return;
			}
			//解密data
			String dataStr = DESUtil.decryptBasedDes(data,DESKey);
			
			haodongAndroidReqBean = JsonUtil.convertJsonToBean(dataStr, HaodongAndroidReqBean.class);
			HaodongAndroidFormBean form = new HaodongAndroidFormBean(haodongAndroidReqBean, gameName, cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.haodongAndroidCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, JsonUtil.convertBeanToJson(new HaodongAndroidResbean("000", "success", "")));
				return;
			} else {
				this.response(response, JsonUtil.convertBeanToJson(new HaodongAndroidResbean("-200", "FAIL", "")));
				return;
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive haodongAndroidCharge request param error:"+data+"error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new HaodongAndroidResbean("-21006", e.toString(), "")));
		}
	}


	/**
	 * eagleHaodongCharge渠道回调
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping("/eagleHaodongCharge/{gameName}/{cpName}")
	public void eagleHaodongCharge(HttpServletRequest request,HttpServletResponse response
										,EagleHaodongFormBean form) {
		try{
			ValidateService.valid(form);
			int result = cpChargeCallBackService.eagleHaodongCharging(form, Common.getTrueIP(request));
			if(Common.dealSuccess(result))
				this.response(response, "SUCCESS");
			else
				this.response(response, "FAIL");
		}catch(Exception e){
			LoggerUtil.error(CPChargeCallBackController.class,"receive eagleHaodongCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "FAIL");
		}
	}

	/**
	 * CGame充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "cgameCharge/{gameName}/{cpName}")
	public void cgameCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,CGameFormBean form){
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.cgameCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "success");
			} else {
				this.response(response, "fail");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive cgameCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
		}
	}
	
	/**
	 * play800充值回调handle
	 * @param gameName
	 * @param cpName
	 * @param requset
	 * @param response
	 * @param Play800ChargingFormBean
	 */
	@RequestMapping(value="playCharge/{gameName}/{cpName}",method=RequestMethod.POST)
	public void playCharge(@PathVariable String gameName,@PathVariable String cpName,HttpServletRequest request,
			HttpServletResponse response,PlayChargingFormBean form){
		try {
			//注入gameName和cpName
			StringBuilder sbr =  new StringBuilder();
			form.setGameName(gameName);
			form.setCpName(cpName);
			//注解解析
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.playCharging(form, Common.getTrueIP(request));
			sbr = Common.dealSuccess(resultCode) ? sbr.append("SUCCESS") : sbr.append("FAIL");
			//将结果输出到前台
			this.response(response, sbr.toString());
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive playCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "result=FAIL");
			return;
		}
	}
	
	/**
	 * 360充值回调handle,post/get方法都支持
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value="qihuCharge/{gameName}/{cpName}")
	public void qihuCharge(HttpServletRequest request,HttpServletResponse response,QihuChargingFormBean form){
		//注解解析
		QihuResbean resultbean = null;
		try{
			ValidateService.valid(form);
			int result = cpChargeCallBackService.qihuCharging(form, Common.getTrueIP(request));
			switch(result){
			case Constant.SUCCESS :
			case Constant.ERROR_ORDER_DUPLICATE : resultbean = new QihuResbean("ok","success","");break;
			case Constant.ERROR_ORDER     : resultbean = new QihuResbean("ok","other","用户支付未成功");break;
			case Constant.ERROR_UID       : resultbean = new QihuResbean("ok","mismatch","360账户不一致");break;
			case Constant.ERROR_ROLE      : resultbean = new QihuResbean("ok","mismatch","app_uid不一致");break;
			case Constant.ERROR_CALLBACK_APPKEY : resultbean = new QihuResbean("ok","other","app_key不一致");break;
			case Constant.ERROR_PRODUCTID : resultbean = new QihuResbean("ok","other","product_id不一致");break;
			case Constant.ERROR_CHARGE_MONEY    : resultbean = new QihuResbean("error","other","金额不一致");break;
			case Constant.ERROR_ExtInfo   : resultbean = new QihuResbean("ok","other","扩展信息1不一致");break;
			default : resultbean = new QihuResbean("error","error","此类错误需要重复通知");break;
			}
		}catch(Exception e){
			LoggerUtil.error(CPChargeCallBackController.class,"receive qihuCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new QihuResbean("error", "other", "system error")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(resultbean));
	}
	/**
	 * nubia充值回调handle
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value="nubiaCharge/{gameName}/{cpName}",method=RequestMethod.POST)
	public void nubiaCharge(HttpServletRequest request,HttpServletResponse response,NubiaFormBean form){
		//注解解析
		NubiaResBean resultbean = null;
		try{
			ValidateService.valid(form);
			//如果渠道没有提供订单号，自己生成一个
			Random ran = new Random();
			if(form.getOrder_serial() == null || "".equals(form.getOrder_serial())){
				form.setOrder_serial("unionOrderId_null_"+new Date().getTime()+"_"+ran.nextInt(10000));
			}
			int result = cpChargeCallBackService.nubiaCharging(form, Common.getTrueIP(request));
			switch(result){
			case Constant.SUCCESS        			 :
			case Constant.ERROR_ORDER_DUPLICATE      :	resultbean = new NubiaResBean(0,new JsonObject(),"成功");break;
			case Constant.ERROR_SIGN    			 : resultbean = new NubiaResBean(10000,new JsonObject(),"签名异常，请确认签名秘钥AppSecret，或验签方式，努比亚更改，而没有告知");break;
			case Constant.ERROR_REQUEST_CPGAME       : resultbean = new NubiaResBean(10000,new JsonObject(),"渠道游戏配置不存在，请检查蓝港相应平台的充值配置");break;
			case Constant.ERROR_REQUEST_CP      	 : resultbean = new NubiaResBean(10000,new JsonObject(),"渠道不存在，请检查蓝港相应平台的充值配置");break;
			case Constant.ERROR_CHARGE_STATUS		 : resultbean = new NubiaResBean(10000,new JsonObject(),"努比亚渠道没有开通充值，请联系平台配置充值管理员");break;
			case Constant.ERROR_SERVER_IP   		 : resultbean = new NubiaResBean(10000,new JsonObject(),"ip白名单校验异常，"+Common.getTrueIP(request)+",该IP未在蓝港的努比亚的IP白名单中，请与努比亚确认，是否更新了充值的IP，而没有告知，并且在起确认好后，将ip白名单更新");break;
		
			default : resultbean = new NubiaResBean(90000,new JsonObject(),"错误——"+result);break;
			}
		}catch(Exception e){
			LoggerUtil.error(CPChargeCallBackController.class,"receive nubiaCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new NubiaResBean(10000, new JsonObject(), "参数异常")));
			return;
		}
		this.response(response, JsonUtil.convertBeanToJson(resultbean));
	}

	/**
	 * 松鼠渠道回调
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "songshuCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void songshuCharge(HttpServletRequest request,HttpServletResponse response,SongshuFormBean form) {
		String result = null;
		try{
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.songshuCharging(form, Common.getTrueIP(request));
			if(Common.dealSuccess(resultCode)){
				result = "SUCCESS";
			}else{
				result = "FAIL";
			}
		}catch (Exception e){
			LoggerUtil.error(CPChargeCallBackController.class,"receive songshuCharge request param error:"+form.toString()+",error info:"+e.toString());
			result = "FAIL";
		}finally {
			this.response(response, result);
		}
	}
	/**
	 * 此方法为临时方法，因乱码导致多笔清源订单需要补单，所以临时加了此补单方法
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "qingyuanIOSRepairCharge/{gameName}/{cpName}",method=RequestMethod.GET)
	public void qingyuanIOSRepairCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,QingYuanIOSChargingFormBean form){
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.qingyuanIOSRepairCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			}else {
				this.response(response, "FAIL");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive qingyuanIOSRepairCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
		}
	}
	
	
	
	/**
	 * 清源IOS充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "qingyuanIOSCharge/{gameName}/{cpName}")
	public void qingyuanIOSCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response,QingYuanIOSChargingFormBean form){
		response.setContentType("text/html;charset=UTF-8");
		try {
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			int result = cpChargeCallBackService.qingyuanIOSCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, "SUCCESS");
			}else {
				this.response(response, "FAIL");
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive qingyuanIOSCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, "fail");
		}
	}
	/**
	 * apple充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "appleCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void appleCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response, AppleChargingFormBean form){
		try {
			form.setCpName(cpName);
			form.setGameName(gameName);
			ValidateService.valid(form);
			Integer result = cpChargeCallBackService.appleCharging(form, Common.getTrueIP(request));
			if(Common.dealSuccess(result)) {
				this.response(response, "1");
			}else {
				this.response(response, result.toString());
			}

		} catch (Exception e) {
			String info = LoggerAOPUtils.replaceTransactionReceipt(form.toString());
			LoggerUtil.error(CPChargeCallBackController.class,"receive appleCharge request param error:"+info+",error info:"+e.getMessage());
			log.error("receive appleCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, String.valueOf(Constant.ERROR_PARAM_VALIDATE));
			return;
		}
	}

	/**
	 * caohua渠道充值回调接口
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "caohuaCharge/{gameName}/{cpName}", method = RequestMethod.GET)
	public void caohuaCharge(HttpServletRequest request,
			HttpServletResponse response, CaoHuaChargingFormBean form){
			String requestIp = "";
		    int resCode = 0;
			try{
				ValidateService.valid(form);
				requestIp = Common.getTrueIP(request);
				resCode = cpChargeCallBackService.caohuaCharging(form, requestIp);
			}catch (Exception e){
				LoggerUtil.error(CPChargeCallBackController.class,"receive caohuaCharge request param error:"+form.toString()+",error info:"+e.getMessage());
				resCode = Constant.ERROR_PARAM_VALIDATE;
			}finally {
				this.response(response, JsonUtil.convertBeanToJson(new CaohuaResBean(resCode)));
			}

	}

	/**
	 * 7K渠道充值回调接口
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "7KCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void sevenKCharge(HttpServletRequest request,
							 HttpServletResponse response, SevenKChargingFormBean form){
		String requestIp = "";
		int resCode = 0;
		try{
			ValidateService.valid(form);
			requestIp = Common.getTrueIP(request);
			resCode = cpChargeCallBackService.sevenKCharging(form, requestIp);
		}catch (Exception e){
			LoggerUtil.error(CPChargeCallBackController.class,"receive sevenKCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			resCode = Constant.ERROR_PARAM_VALIDATE;
		}finally {
			this.response(response, JsonUtil.convertBeanToJson(new SevenKResBean().buildBean(resCode)));
		}
	}
	/**
	 * 游戏猫充值回调充值回调信息认证
	 * @param gameName
	 * @param cpName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "youximaoCharge/{gameName}/{cpName}", method = { RequestMethod.POST })
	public void youximaoCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request,
			HttpServletResponse response){
		YouximaoChargingFormBean form = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			String cryptData = request.getParameter("data");
			if(cryptData == null || "".equals(cryptData)){
				LoggerUtil.error(CPChargeCallBackController.class,"receive youximaoCharge request param error:"+cryptData+"error info: param data not null");
				this.response(response, JsonUtil.convertBeanToJson(new YouximaoResbean(String.valueOf(Constant.ERROR_PARAM_VALIDATE), "参数data不能为空", "")));
				return;
			}
			String desKey = cpChargeCallBackService.getDESKey(gameName, cpName);
			if (desKey == null) {
				LoggerUtil.error(CPChargeCallBackController.class,"receive youximaoCharge request param error:"+cryptData+",error info: param data not null");
				this.response(response, JsonUtil.convertBeanToJson(new YouximaoResbean(String.valueOf(Constant.ERROR_CHARGE_SIGN), "FAIL", "")));
				return;
			}
			String decryptData = DESUtil.decryptBasedDes(cryptData, desKey);
            
			form = JsonUtil.convertJsonToBean(decryptData, YouximaoChargingFormBean.class);
			form.setGameName(gameName);
			form.setCpName(cpName);
			ValidateService.valid(form);
			
			Integer result = cpChargeCallBackService.youximaoCharging(form, Common.getTrueIP(request));
			if (Common.dealSuccess(result)) {
				this.response(response, JsonUtil.convertBeanToJson(new YouximaoResbean("000", "success", "")));
				return;
			} else {
				this.response(response, JsonUtil.convertBeanToJson(new YouximaoResbean("-200", "FAIL", "")));
				return;
			}
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive youximaoCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			this.response(response, JsonUtil.convertBeanToJson(new YouximaoResbean("-21006", e.toString(), "")));
		}
	}

	/**
	 * 新版本游戏猫充值回调
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "youximao2Charge/{gameName}/{cpName}", method = RequestMethod.POST )
	public void youximao2Charge(HttpServletRequest request, HttpServletResponse response, YouximaoChargingFormBean2 form){
		int resCode = 0;
		try{
			ValidateService.valid(form);
			resCode = cpChargeCallBackService.youximao2Charging(form, Common.getTrueIP(request));
		}catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive youximao2Charge request param error:"+form.toString()+",error info:"+e.toString());
			resCode = Constant.ERROR_PARAM_VALIDATE;
		}finally {
			this.response(response, JsonUtil.convertBeanToJson(new YouximaoResbean().buildRespBean(resCode)));
		}
	}
	/**
	 * 一点渠道充值回调接口
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping(value = "yiDianCharge/{gameName}/{cpName}", method = RequestMethod.GET)
	public void yiDianCharge(HttpServletRequest request,
							 HttpServletResponse response, YiDianChargingFormBean form){
		String requestIp = "";
		int resCode = Constant.ERROR_SYSTEM;
		try{
			ValidateService.valid(form);
			//验证参数是否为int
			if(!StringUtils.isNumeric(form.getApp_id())){
				throw new Exception("app_id is not number");
			}
			if(!StringUtils.isNumeric(form.getCode())){
				throw new Exception("code is not number");
			}
			if(!StringUtils.isNumeric(form.getPay_way())){
				throw new Exception("pay_way is not number");
			}
			if(!StringUtils.isNumeric(form.getMoney())){
				throw new Exception("money is not number");
			}
			requestIp = Common.getTrueIP(request);
			if(Common.dealSuccess(cpChargeCallBackService.yidianCharging(form, requestIp))) {
				resCode = Constant.SUCCESS;
			}
		}catch (Exception e){
			LoggerUtil.error(CPChargeCallBackController.class,"receive yiDianCharge request param error:"+form.toString()+",error info:"+e.getMessage());
			resCode = Constant.ERROR_PARAM_VALIDATE;
		}finally {
			this.response(response, String.valueOf(resCode));
		}
	}

	/**
	 * 魅族聚合回调
	 * 成功返回{"code":200}
	 */
	@RequestMapping(value = "meizuJuheCharge/{gameName}/{cpName}")
	public void meizuJuheCharge(HttpServletRequest request, HttpServletResponse response, MeizuJuHeForBean form){
		int result = 0;
		try{
			result = cpChargeCallBackService.meizuJuheCharging(form, Common.getTrueIP(request));
			if(result== Constant.CHARGE_SUCCESS || result == Constant.ERROR_ORDER_DUPLICATE){
				result = 200;
			}
		}catch(Exception e){
			LoggerUtil.error(this.getClass(), "receive meizuJuheCharge request param error:" + form.toString() +"error info:" + e.toString());
			result = Constant.ERROR_PARAM_VALIDATE;
		}finally {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("code", Integer.valueOf(result));
			response(response, JsonUtil.convertBeanToJson(map));
		}
	}

	/**
	 * 爱奇艺聚合回调
	 */
	@RequestMapping(value = "aiqiyiJuheCharge/{gameName}/{cpName}")
	public void aiqiyiJuheCharge(AiqiyiJuheFormBean form, HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		try{
			ValidateService.valid(form);
			result = cpChargeCallBackService.aiqiyiJuheCharging(form, Common.getTrueIP(request));
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "receive aiqiyiJuheCharge request param error:" + form.toString() +"error info:" + e.toString());
			result = Constant.ERROR_PARAM_VALIDATE;
		}finally {
			if(Common.dealSuccess(result)){
				response(response, "SUCCESS");
			}else {
				response(response, "FAILURE");
			}
		}
	}

	/**
	 *9377渠道回调
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "ntssCharge/{gameName}/{cpName}", method = RequestMethod.GET)
	public void ntssCharge(NTSSChargingFormBean form, HttpServletRequest request, HttpServletResponse response){
		NTSSResBean resBean =  new NTSSResBean();
		int result = 0;
		try{
			form.set_server(request.getParameter("_server"));
			ValidateService.valid(form);
			result = cpChargeCallBackService.ntssCharging(form, Common.getTrueIP(request));
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "receive ntssCharge request param error:" + form.toString() +"error info:" + e.toString());
			result = Constant.ERROR_PARAM_VALIDATE;
		}finally {
			response(response, JsonUtil.convertBeanToJson(resBean.createRespJson(result)));
		}
	}

	/**
	 * 蝶恋渠道回调
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "dielianCharge/{gameName}/{cpName}", method = RequestMethod.GET)
	public void dielianCharge(DieLianChargingFormBean form, HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		try{
			ValidateService.valid(form);
			result = cpChargeCallBackService.dielianCharging(form, Common.getTrueIP(request));
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "receive dielianCharge request param error:" + form.toString() +"error info:" + e.toString());
			result = Constant.ERROR_PARAM_VALIDATE;
		}finally {
			if(Common.dealSuccess(result)) {
				response(response, "SUCCESS");
			}else {
				response(response, String.valueOf(result));
			}
		}
	}

	/**
	 * 花生互娱回调
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "huaShengCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void huaShengCharge(@PathVariable String gameName, @PathVariable String cpName, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		HuaShengChargingFormBean form = new HuaShengChargingFormBean();
		try{
			form.setCpName(cpName);
			form.setGameName(gameName);
			BufferedReader reader = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
			if(form.parseRequestData(sb.toString())) {
				ValidateService.valid(form);
				int resultCode = cpChargeCallBackService.huaShengCharging(form, Common.getTrueIP(request));
				if(Common.dealSuccess(resultCode)) {
					result = "SUCCESS";
				}else {
					result = "FAIL";
				}
			}else {
				LoggerUtil.info(this.getClass(),"receive huaShengCharge the order state error"+form.toString());
				result = "FAIL";
			}
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "receive huaShengCharge request param error:" + form.toString() +"error info:" + e.toString());
			result = "FAIL";
		}finally {
			response(response, result);
		}
	}

	/**
	 * 极速渠道回调,除了返回success，其它都视作失败
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "jisuCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void jisuChargre(JisuFormBean form, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try{
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.jisuCharging(form,  Common.getTrueIP(request));
			if(Common.dealSuccess(resultCode)) {
				result = "success";
			}else {
				result = String.valueOf(resultCode);
			}
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "receive jisuChargre request param error:" + form.toString() +"error info:" + e.toString());
			result = String.valueOf(Constant.ERROR_PARAM_VALIDATE);
		}finally {
			response(response, result);
		}
	}

	/**
	 * 一点自己的渠道，充值回调
	 * @param form
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "yidianSelfCharge/{gameName}/{cpName}", method = RequestMethod.POST)
	public void yidianCharge(YidianselfFormBean form, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try{
			ValidateService.valid(form);
			int resultCode = cpChargeCallBackService.yidianSelfCharging(form, Common.getTrueIP(request));
			if(Common.dealSuccess(resultCode)) {
				result = "SUCCESS";
			}else{
				result = "FAILURE";
			}
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "receive yidianSelfCharge request param error:" + form.toString() +"error info:" + e.toString());
			result = "FAILURE";
		}finally {
			response(response, result);
		}
	}

	@RequestMapping(value = "fillOrder/{unionOrderId}")
	public void fillOrder(@PathVariable String unionOrderId, HttpServletRequest request,HttpServletResponse response){
		try {
			Integer result = cpChargeCallBackService.fillOrder(unionOrderId);
			this.response(response, "fillOrder:"+result.toString());
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive fillOrder request param error:"+unionOrderId+",error info:"+e.getMessage());
			this.response(response, String.valueOf(Constant.ERROR_PARAM_VALIDATE));
			return;
		}
	}	
	
	@RequestMapping(value = "fillOrder2", method = { RequestMethod.POST })
	public void fillOrder2(HttpServletRequest request,HttpServletResponse response){
		try {
			String chargeDetailId = request.getParameter("chargeDetailId");
			String unionOrderId = request.getParameter("unionOrderId");
			String pwd = request.getParameter("pwd");
			int pushType = Integer.valueOf(request.getParameter("pushType"));
			if (pwd.equals("line@17720") == false){
				this.response(response, "fillOrder:-8");
				return;
			}
			Integer result = cpChargeCallBackService.fillOrder2(chargeDetailId, unionOrderId, pushType);
			this.response(response, "fillOrder:"+result.toString());
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackController.class,"receive fillOrder2 request param error: error info:"+e.getMessage());
			this.response(response, String.valueOf(Constant.ERROR_PARAM_VALIDATE));
			return;
		}
	}	
	
	//新增苹果补单接口,pushType=1?"DB" : "MQ"
	@RequestMapping(value = "repair/{paymentId}/{unionId}/{pushType}")
	public void repairrepairApple(@PathVariable String paymentId, @PathVariable String unionId, @PathVariable int pushType , HttpServletResponse response){
		int result = 0;
		try{

			result = cpChargeCallBackService.repairApple(paymentId, unionId ,pushType);
			if(result>0)
				this.response(response, "成功,result="+result);
			else
				this.response(response, "失败,result=" +result);
			//2、
		}catch(Exception e){
			LoggerUtil.error(CPChargeCallBackController.class,"receive repairrepairApple request param error: error info:"+e.getMessage());
			this.response(response, "程序出错");
			return;
		}
	}

}
