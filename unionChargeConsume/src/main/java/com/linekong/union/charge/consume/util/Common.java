package com.linekong.union.charge.consume.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;

import com.linekong.union.charge.consume.rabbitmq.pojo.PushOrderWithMQInfoPOJO;
import com.linekong.union.charge.consume.util.cache.CacheUtil;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.web.jsonbean.resbean.AppleResVSevenBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.AppleResVSixBean;
import com.linekong.union.charge.rpc.pojo.AppstoreChargePOJO;
import com.linekong.union.charge.rpc.pojo.ChargingPOJO;
import com.linekong.union.charge.rpc.pojo.LogActivityMQPushPOJO;

public class Common {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * url encode 处理
	 * @param String result	 	需要encode处理的值  
	 * @param String charset	字符集
	 * @return 编码后数据
	 */
	public static String urlEncode(String result,String charset){
		try {
			return URLEncoder.encode(result, charset);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.error(Common.class, e.getMessage());
		}
		return null;
	}
	/**
	 * url decode 处理
	 * @param String result		需要decode处理的值
	 * @param String charset	字符集
	 * @return 解码后数据
	 */
	public static String urlDecode(String result,String charset){
		try {
			return URLDecoder.decode(result, charset);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.error(Common.class, e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * */
	public static long ChargeDetailIdGenerator() {
		Random r = new Random();
		long timeNow = System.currentTimeMillis();
		return  (timeNow*100 + (long)r.nextInt(99));
	}
	/**
	 * base64解密
	 * @param String str 
	 * 				base64加密值
	 * @return base64解密以后值
	 */
	public static String decodeBase64(String str){
		return new String(Base64.decodeBase64(str));
	}
	/**
	 * 获取真实IP
	 * @param request
	 * @return
	 */
	public static String getTrueIP(HttpServletRequest request){
		//因原方式获取到的是nginx转发的服务器Ip，所以将ip设置为nginx配置在Header中的实际用户的值
		String ip="";
		String xRealIp = request.getHeader("X-Real-IP") ;
		String XForwardedIp = request.getHeader("X-Forwarded-For") ;
		if(XForwardedIp != null && !"".equals(XForwardedIp)){
			ip = XForwardedIp.split(",")[0];
		}else if(xRealIp != null && !"".equals(xRealIp)){
			ip = xRealIp;
		}else{
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	//IP 和int的互转
	public static String iplong2Str(long ip)
	{
		StringBuilder sb = new StringBuilder();
		//直接右移24位
		sb.append(ip >> 24);
		sb.append(".");
		//将高8位置0，然后右移16
		sb.append((ip & 0x00FFFFFF) >> 16);
		sb.append(".");
		//将高16位置0，然后右移8位
		sb.append((ip & 0x0000FFFF) >> 8);
		sb.append(".");
		//将高24位置0
		sb.append((ip & 0x000000FF));
		return sb.toString();
	}

	public static long ipStr2long(String strIp) throws UnknownHostException {
		long[] ip = new long[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		//进行左移位处理
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 获取本机IP
	 */
	public static String getLocalIp() {
		String localIp = "127.0.0.1";
		try {
			InetAddress address = InetAddress.getLocalHost();// 获取的是本地的IP地址
			localIp = address.getHostAddress();// 192.168.0.121

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return localIp;
	}

	/**
	 * 检查是否为苹果沙盒
	 * @param form
	 * @param gameId
	 * @return
	 * @throws Exception
	 */
	public static boolean isAppleTest(String gatewayId,String userName,Integer gameId,Integer cpId) throws Exception{
		//获取沙河区服白名单
		String gatewayIDsTest = CacheUtil.getKey(gameId,cpId, Constant.CONFIG_APPLE_GATEWAYID_TEST).trim();
		//如果该网关为沙盒白名单
		if( gatewayIDsTest != null && !"0".equals(gatewayIDsTest) ){
			if("*".equals(gatewayIDsTest) || gatewayIDsTest.contains(gatewayId+";") ){
				return true;
			}
		}
		//获取沙盒用户白名单
		String userNames = CacheUtil.getKey(gameId,cpId, Constant.CONFIG_APPLE_USERNAME_TEST).trim();
		//如果该用户为沙盒白名单
		if( userNames != null && !"0".equals(userNames) ){
			if("*".equals(userNames) || userNames.contains(userName+";") ){
				return true;
			}
		}
		//都不是沙盒用户，返回false
		return false;
	}
	/**
	 * v6.0获取一个AppstoreChargePOJO(充值日志)
	 * @return
	 */
	public static AppstoreChargePOJO getAppstoreChargePOJO(AppleResVSixBean appRes, ChargingPOJO chargingPojo){
		AppstoreChargePOJO appPojo = new AppstoreChargePOJO();
		appPojo.setAllChargeAmount(chargingPojo.getChargeAmount());
		appPojo.setAllProductPrice(chargingPojo.getChargeMoney());
		appPojo.setAppItemId(appRes.getReceipt().getApp_item_id());
		appPojo.setBid(appRes.getReceipt().getBid());
		appPojo.setBvrs(appRes.getReceipt().getBvrs());
		appPojo.setChargeAmount(chargingPojo.getChargeAmount());
		appPojo.setChargeDetailId(String.valueOf(chargingPojo.getChargeDetailId()));
		appPojo.setGameId(chargingPojo.getGameId());
		appPojo.setGatewayId(chargingPojo.getGatewayId());
		appPojo.setOperateTime(new Date());
		appPojo.setOriginalPurchaseDate(appRes.getReceipt().getOriginal_purchase_date());
		appPojo.setOriginalPurchaseDateMs(appRes.getReceipt().getOriginal_purchase_date_ms());
		appPojo.setOriginalPurchaseDatePst(appRes.getReceipt().getOriginal_purchase_date_pst());
		appPojo.setOriginalTransactionId(appRes.getReceipt().getOriginal_transaction_id());
		appPojo.setProductId(appRes.getReceipt().getProduct_id());
		appPojo.setProductPrice(chargingPojo.getChargeMoney());
		appPojo.setPurchaseDate(appRes.getReceipt().getPurchase_date());
		appPojo.setPurchaseDateMs(appRes.getReceipt().getPurchase_date_ms());
		appPojo.setPurchaseDatePst(appRes.getReceipt().getPurchase_date_pst());
		appPojo.setQuantity(Integer.valueOf(appRes.getReceipt().getQuantity()));
		appPojo.setTransactionId(appRes.getReceipt().getTransaction_id());
		appPojo.setUniqueIdentifier(appRes.getReceipt().getUnique_identifier());
		appPojo.setUserName(chargingPojo.getUserName());
		return appPojo;
	}
	/**
	 * v7.0获取一个AppstoreChargePOJO
	 * @return
	 */
	public static AppstoreChargePOJO getAppstoreChargePOJO(AppleResVSevenBean appRes, ChargingPOJO chargingPojo, int order){
		AppstoreChargePOJO appPojo = new AppstoreChargePOJO();
		appPojo.setAllChargeAmount(chargingPojo.getChargeAmount());
		appPojo.setAllProductPrice(chargingPojo.getChargeMoney());
		appPojo.setAppItemId(				appRes.getReceipt().getApp_item_id());
		appPojo.setBid(						appRes.getReceipt().getBundle_id());  					//??
		appPojo.setBvrs(					appRes.getReceipt().getApplication_version());		  	//??
		appPojo.setChargeAmount(chargingPojo.getChargeAmount());
		appPojo.setChargeDetailId(String.valueOf(chargingPojo.getChargeDetailId()));
		appPojo.setGameId(chargingPojo.getGameId());
		appPojo.setGatewayId(chargingPojo.getGatewayId());
		appPojo.setOperateTime(new Date());
		appPojo.setOriginalPurchaseDate(    appRes.getReceipt().getIn_app().get(order).getOriginal_purchase_date());
		appPojo.setOriginalPurchaseDateMs(  appRes.getReceipt().getIn_app().get(order).getOriginal_purchase_date_ms());
		appPojo.setOriginalPurchaseDatePst( appRes.getReceipt().getIn_app().get(order).getOriginal_purchase_date_pst());
		appPojo.setOriginalTransactionId(   appRes.getReceipt().getIn_app().get(order).getOriginal_transaction_id());
		appPojo.setProductId(			    appRes.getReceipt().getIn_app().get(order).getProduct_id());
		appPojo.setProductPrice(chargingPojo.getChargeMoney());
		appPojo.setPurchaseDate(		    appRes.getReceipt().getIn_app().get(order).getPurchase_date());
		appPojo.setPurchaseDateMs(		    appRes.getReceipt().getIn_app().get(order).getPurchase_date_ms());
		appPojo.setPurchaseDatePst(		    appRes.getReceipt().getIn_app().get(order).getPurchase_date_pst());
		appPojo.setQuantity(Integer.valueOf(appRes.getReceipt().getIn_app().get(order).getQuantity()));
		appPojo.setTransactionId(			appRes.getReceipt().getIn_app().get(order).getTransaction_id());
		appPojo.setUniqueIdentifier("v7.0-NULL");															//设备唯一标识v7.0中没有
		appPojo.setUserName(chargingPojo.getUserName());
		return appPojo;
	}
	public static LogActivityMQPushPOJO getLogActivityMQPushPOJO(PushOrderWithMQInfoPOJO pojo){
		LogActivityMQPushPOJO log = new LogActivityMQPushPOJO();
		log.setAttachCode(pojo.getAttachCode());
		log.setChargeAmount(pojo.getChargeAmount());
		log.setChargeChannelId(pojo.getChargeChannelId());
		log.setChargeDetailId(pojo.getChargeDetailId());
		log.setChargeMoney(pojo.getChargeMoney());
		log.setChargeOrderCode(pojo.getChargeOrderCode());
		log.setChargeSubjectId(pojo.getChargeSubjectId());
		log.setChargeTime(pojo.getChargeTime());
		log.setChargeType(pojo.getChargeType());
		log.setClientIp(pojo.getClientIp());
		log.setDiscount(pojo.getDiscount());
		log.setGameId(pojo.getGameId());
		log.setGatewayId(pojo.getGatewayId());
		log.setMoneyType(pojo.getMoneyType());
		log.setRoleId(pojo.getRoleId());
		log.setServerIp(pojo.getServerIp());
		log.setUserId(pojo.getUserId());
		log.setUserName(pojo.getUserName());
		return log;
	}
	/**
	 * 生成预支付订单(yyyyMMddHHmmss + 四位随机数)
	 * @return
	 */
	public static String createPaymentId(){
		
		String time = sdf.format(new Date());
		Random r = new Random();
		String randomNumber = "" +r.nextInt(9) +r.nextInt(9) +r.nextInt(9) +r.nextInt(9);
		return time + randomNumber;
	}
	/**
	 * 检验字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmptyString(String str){
		return str == null || "".equals(str) ? true : false;
	}

	/**
	 * 此方法用于三星预支付去渠道下单后，对结果进行解析，并提取出渠道流水号，本方法只提取成功的格式
	 * 失败的情况返回格式:
	 *transdata={"code":1002,"errmsg":"请求参数错误"}
	 *成功返回格式:
	 *transdata={"transid":"XXXXXX"}&sign=XXXXX&signtype=RSA
	 * @param resultString
	 * @return
	 */
    public static String getSamsungOrder(String resultString) {
		String[] arr = resultString.split("&");
		JsonParser parser = new JsonParser();
		JsonElement je = parser.parse(arr[0].split("=")[1]);
		JsonObject jobj = je.getAsJsonObject();//从json元素转变成json对象
		String transid = "";
		try {
			transid = jobj.get("transid").getAsString();//从json对象获取指定属性的值
		}catch (Exception e){
			try {
				String code = jobj.get("code").getAsString();
				if(code != null && !"".equals(code)){//说明创建订单是失败
					transid = "";
				}
			}catch(Exception e1){
				//此处如能再报异常，说明渠道返回的格式有问题，同样返回个空串
				transid = "";
			}
		}
		return transid;
	}


	/**返回1或-1472的应该都算成功
	 *@param resultCode
	 * @return
	*/
	public static boolean dealSuccess(int resultCode){
		return resultCode == Constant.SUCCESS || resultCode == Constant.ERROR_ORDER_DUPLICATE;
	}

	public static void main(String[] args) throws UnknownHostException {
//		String str = "transdata={\"code\":1002,\"errmsg\":\"请求参数错误\"}";
		String str = "transdata={\"transid\":\"abccd\"}&sign=XXXXX&signtype=RSA";
		System.out.println(getSamsungOrder(str));
	}
}
