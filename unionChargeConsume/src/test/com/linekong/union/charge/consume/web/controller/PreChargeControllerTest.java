package com.linekong.union.charge.consume.web.controller;

import com.lenovo.pay.sign.Base64;
import com.linekong.union.charge.consume.util.Common;
import com.linekong.union.charge.consume.util.HttpUtils;
import com.linekong.union.charge.consume.util.JsonUtil;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.util.rsa.RSAEncrypt;
import com.linekong.union.charge.consume.util.sign.MD5SignatureChecker;
import com.linekong.union.charge.consume.web.formbean.*;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.JinliExpandInfo;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.PreChargeMeizuReqBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeResBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeResBeanKuaikan;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

import static oracle.net.aso.C09.v;

/** 
* PreChargeController Tester. 
* 
* 此类用于测试充值的预支付和回调，每一个@Test方法里既包括预支付，也包括回调
 * 后续如果有渠道添加，也需要在此类中添加相应的预支付和回调
*/ 
public class PreChargeControllerTest {

    private String preUrl;
    private String prePath = "http://192.168.84.2:8084/unionChargeConsume/";
    private String callbackUrl;
    Map<String,String> params;
    //苹果凭证
    private static final String RECEIPT = "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";

    private String sign;
    private String userName;
    private int cpId;
    private int gameId;

    private int gatewayId;
    private double chargeMoney;
    private int chargeAmount;
    private Long role;
    private String payMentTime;
    private String attachCode;
    private String platformName;
    private String expandInfo;
    private String productId;
    private String productName;
    private String productDesc;
    private String cpSignType;
    private String var;

@Before
public void before() throws Exception {
    PreChargeFormBean bean = new PreChargeFormBean();
    userName = "panpan";
    role = 1215L;
    gatewayId = 10010;
    chargeMoney = 1.00;
    chargeAmount = 10;
    payMentTime = "12345618";
    attachCode = "MV81MjQyOTZfMTUyMjcyMDY4NzA0MF4xXueSh+mbgeS6pg==";
    platformName = "platformName";
    expandInfo = "expandInfo";
    productId = "productId";
    productName = "蓝钻";
    productDesc = "10蓝钻";

    cpSignType = "MD5";
    var="1.00";

} 

public void initMap (){
    params = new HashMap<String,String>();
    params.put("sign",sign);
    params.put("userName",userName);
    params.put("role",role+"");
    params.put("cpId",cpId+"");
    params.put("gameId",gameId+"");
    params.put("gatewayId",gatewayId+"");
    params.put("chargeMoney",chargeMoney+"");
    params.put("chargeAmount",chargeAmount+"");
    params.put("payMentTime",payMentTime);
    params.put("attachCode",attachCode);
    params.put("platformName",platformName);
    params.put("expandInfo",expandInfo);
    params.put("productId",productId);
    params.put("productName",productName);
    params.put("productDesc",productDesc);
    params.put("cpSignType",cpSignType);
    params.put("var",var);
}

@After
public void after() throws Exception {
    params = null;
    System.out.println("end junit");
}

@Test
public void testPreCharge() throws Exception {

}

//测试苹果回调需要更换凭证，从线上复制一份productId=MY006的凭证即可
@Test
public void testPreChargeApple() throws Exception{
    productId = "com.lk.swglxh.apple.p2";
    String prejson = preCharge(51, 114, "preCharge/apple");
    System.out.println(prejson);
    PreChargeResBean prebean = JsonUtil.convertJsonToBean(prejson, PreChargeResBean.class);
    params = null;
    if(!Common.isEmptyString(prebean.getPayMentId())) {
        Map appleParam = new HashMap();
        appleParam.put("productId","com.lk.swglxh.apple.p2");
        appleParam.put("cpGameId",114+"");
        appleParam.put("cpId",51+"");
        appleParam.put("gatewayID","10010");
//        appleParam.put("payMentId",prebean.getPayMentId());
        appleParam.put("transactionReceipt",RECEIPT);
        appleParam.put("userName","panpan");
        appleParam.put("version","v7.0");
        callbackUrl = "http://192.168.84.2:8084/unionChargeConsume/appleCharge/wzzj/apple";
        System.out.println(HttpUtils.httpPost(callbackUrl, appleParam)+"\n");
    }else
        System.out.println("apple preChagre fail ------------------");


}

    @Test
public void testPreChargeEagleHaodong() throws Exception {
    String prejson = preCharge(95, 595, "preChargeEagleHaodong/eagleHaodong");
    System.out.println(prejson);
    //回调
    String secretKey;
    PreChargeResBean prebean = JsonUtil.convertJsonToBean(prejson, PreChargeResBean.class);
    params = null;
    EagleHaodongFormBean form;
    if(!Common.isEmptyString(prebean.getPayMentId())) {
        secretKey = "qwer";
        form = createEagleHaodongForm(prebean.getPayMentId(), secretKey);
        String json = JsonUtil.convertBeanToJson(form);
        params= JsonUtil.convertJsonToBean(json,new HashMap<String, String>().getClass());
        params.remove("gameName");
        params.remove("cpName");
        callbackUrl = "http://localhost:8087/unionChargeConsume//eagleHaodongCharge/wzzj/eagleHaodong";
        System.out.println(HttpUtils.httpPost(callbackUrl, params));
    }else
        System.out.println("eagleHaodong preChagre fail ------------------");

}

@Test
public void testPreChargeKuaikan() throws Exception {
    productId = "12";
    String prejson = preCharge(58, 2307, "preChargeKuaikan/kuaikan");
    System.out.println(prejson);
    //回调
    String secretKey;
    PreChargeResBeanKuaikan prebean = JsonUtil.convertJsonToBean(prejson, PreChargeResBeanKuaikan.class);
    HashMap hashMap = JsonUtil.convertJsonToBean(prebean.getTransData(), HashMap.class);
    String paymentId = (String) hashMap.get("out_order_id");
    System.out.println(paymentId);
    params = null;
    KuaikanChargingFormBean form;
    if(!Common.isEmptyString(paymentId)) {
        secretKey = "kY8b3l0AqW08iFFI7Xq80OIWXeIlYL2z";
        form = createKuaikanForm(paymentId, secretKey);
        String json = JsonUtil.convertBeanToJson(form);
        HashMap map = JsonUtil.convertJsonToBean(json,new HashMap<String, String>().getClass());
        map.remove("gameName");
        map.remove("cpName");
        map.remove("sign");
        String json1 = JsonUtil.convertBeanToJson(map);
        params = new HashMap<String,String>();
        params.put("sign",form.getSign());
        params.put("trans_data",json1);
        callbackUrl = "http://192.168.84.2:8084/unionChargeConsume/kuaikanCharge/wzzj/kuaikan";
        System.out.println(HttpUtils.httpPost(callbackUrl, params));
    }else
        System.out.println("kuaikan preChagre fail ------------------");
} 


@Test
public void testPreChargeUC() throws Exception {
    String prejson = preCharge(4, 504, "preChargeUC/uc");
    System.out.println(prejson);
    //回调
    String secretKey;
    PreChargeResBean prebean = JsonUtil.convertJsonToBean(prejson, PreChargeResBean.class);
    params = null;
    UCChargingFormBean form;
    if(!Common.isEmptyString(prebean.getPayMentId())) {
        secretKey = "qwer";
        form = createUcForm(prebean.getPayMentId(), secretKey);
        String json = JsonUtil.convertBeanToJson(form);
        HashMap map = JsonUtil.convertJsonToBean(json,new HashMap<String, String>().getClass());
        map.remove("gameName");
        map.remove("cpName");
        map.remove("sign");
        map.remove("ver");
        String json1 = JsonUtil.convertBeanToJson(map);
        params = new HashMap<String,String>();
        params.put("ver",form.getVer());
        params.put("data",json1);
        params.put("sign",form.getSign());
        String httpJson = JsonUtil.convertBeanToJson(params);
        callbackUrl = "http://192.168.84.2:8084/unionChargeConsume/ucCharge/wzzj/uc";
        System.out.println(httpPost1(callbackUrl, httpJson,"test UC"));
    }else
        System.out.println("UC preChagre fail ------------------");
} 

/** 
* 
* Method= preChargeMeizu(@PathVariable String cpName, PreChargeFormBean form, HttpServletResponse response) 
* 
*/ 
@Test
public void testPreChargeMeizu() throws Exception {
    PreChargeMeizuReqBean expand = new PreChargeMeizuReqBean();
    expand.setProduct_per_price("1.0");
    expand.setProduct_subject("元宝");
    expand.setPay_type("0");
    expandInfo = JsonUtil.convertBeanToJson(expand);
    String prejson = preCharge(27, 527, "preChargeMeizu/meizu");
    System.out.println(prejson);
    expandInfo = null;
    //回调
    String secretKey;
    PreChargeResBean prebean = JsonUtil.convertJsonToBean(prejson, PreChargeResBean.class);
    params = null;
    MeiZuChargingFormBean form;
    if(!Common.isEmptyString(prebean.getPayMentId())) {
        secretKey = "qwer";
        form = createMeizuForm(prebean.getPayMentId(), secretKey);
        String json = JsonUtil.convertBeanToJson(form);
        HashMap map = JsonUtil.convertJsonToBean(json,new HashMap<String, String>().getClass());
        map.remove("gameName");
        map.remove("cpName");
        callbackUrl = "http://192.168.84.2:8084/unionChargeConsume/meizuCharge/wzzj/meizu";
        System.out.println(HttpUtils.httpPost(callbackUrl, map));
    }else
        System.out.println("meizu preChagre fail ------------------");
} 

//金立回调未写，rsa加密
@Test
public void testPreChargeJinli() throws Exception {
    JinliExpandInfo je = new JinliExpandInfo();
    je.setJl_uid("B111CEBD222542B6A89D29752B6E09C5");
    je.setPayType("1");
    expandInfo = JsonUtil.convertBeanToJson(je);
    String prejson = preCharge(17, 517, "preChargeJinli/jinli");
    expandInfo = null;
    System.out.println(prejson);
} 


@Test
public void testPreChargeVivo() throws Exception {
    userName = "8436126deb480aff";
    String prejson = preCharge(30, 530, "preChargeVivo/vivo");
    System.out.println(prejson);
    //回调
    String secretKey;
    PreChargeResBean prebean = JsonUtil.convertJsonToBean(prejson, PreChargeResBean.class);
    params = null;
    VivoChargingFormBean form;
    if(!Common.isEmptyString(prebean.getPayMentId())) {
        secretKey = "qwer";
        form = createVivoForm(prebean.getPayMentId(), secretKey);
        String json = JsonUtil.convertBeanToJson(form);
        HashMap map = JsonUtil.convertJsonToBean(json,new HashMap<String, String>().getClass());
        map.remove("gameName");
        map.remove("cpName");
        callbackUrl = "http://192.168.84.2:8084/unionChargeConsume/vivoCharge/wzzj/vivo";
        System.out.println(HttpUtils.httpPost(callbackUrl, map));
    }else
        System.out.println("vivo preChagre fail ------------------");
} 


@Test
public void testPreChargeNubia() throws Exception {
    String prejson = preCharge(98, 598, "preChargeNubia/nubia");
    System.out.println(prejson);
    //回调
    String secretKey;
    PreChargeResBean prebean = JsonUtil.convertJsonToBean(prejson, PreChargeResBean.class);
    params = null;
    NubiaFormBean form;
    if(!Common.isEmptyString(prebean.getPayMentId())) {
        secretKey = "qwer";
        form = createNubiaForm(prebean.getPayMentId(), secretKey);
        String json = JsonUtil.convertBeanToJson(form);
        HashMap map = JsonUtil.convertJsonToBean(json,new HashMap<String, String>().getClass());
        map.remove("gameName");
        map.remove("cpName");
        callbackUrl = "http://192.168.84.2:8084/unionChargeConsume/nubiaCharge/wzzj/nubia";
        System.out.println(HttpUtils.httpPost(callbackUrl, map));
    }else
        System.out.println("nubia preChagre fail ------------------");
}

    @Test
    public void testPreChargeSamsung() throws Exception {
        productId = "1";
        String prejson = preCharge(54, 554, "preChargeSamsung/samsung");
        System.out.println(prejson);
        //回调
    }

@Test
public void testPreChargeHuawei() throws Exception {
    String prejson = preCharge(11, 511, "preChargeHuawei/huawei");
    System.out.println(prejson);
    //回调
}
    // 华为
//	//回测--成功
    @Test
    public void callBackChargeHuaweiTest() {
        String url = "http://192.168.84.2:8084/unionChargeConsume/huaweiCharge/wzzj/huawei";
        String sign = "";
        try {
            sign = URLEncoder.encode(
                    "eaKWJPUL8Hr5J+vhszkNdETmi0fpwDH2CqU2mp7x0NA7fUiQO8B1bykupAJIfzZwBQrQPYplQ2ECFNeeeyarlQ==",
                    "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String params = "amount=1.00"
                + "&notifyTime=1514293606139"
                + "&result=0"
                + "&tradeTime=2017-12-26 20:59:05"
                + "&requestId=201712262058501303"
                + "&extReserved=594011"
                + "&userName=900086000020125018"
                + "&payType=17"
                + "&orderTime=2017-12-26 20:58:58"
                + "&productName=1元宝"
                + "&accessMode=0"
                + "&spending=0.00"
                + "&sysReserved=on2k0wSQ2b33hLHao2uYKIbz9qrE"
                + "&signType=RSA256"
                + "&orderId=WX17100108725A201712262058501303&sign="
                + sign;
        HttpUtils.httpPost1(url, params,"HuaWei");
    }

    //百度
    @Test
    public void testBaidu() throws Exception {
    userName = "ascvdasascvdasascvdasascvdasascvdasascvda";
        String prejson = preCharge(1, 501, "preCharge/baidu");
        System.out.println(prejson);
        //回调
        String secretKey;
        PreChargeResBean prebean = JsonUtil.convertJsonToBean(prejson, PreChargeResBean.class);
        params = null;
        BaiduChargingFormBean form;
        if(!Common.isEmptyString(prebean.getPayMentId())) {
            secretKey = "qwer";
            params = createBaiduForm(prebean.getPayMentId(), secretKey);
            callbackUrl = "http://192.168.84.2:8084/unionChargeConsume/baiduCharge/wzzj/nubia";
            System.out.println(HttpUtils.httpPost(callbackUrl, params));
        }else
            System.out.println("baidu preChagre fail ------------------");
    }


    public String preCharge(int cpId,int gameId,String uri) throws Exception {
    this.cpId = cpId;
    this.gameId = gameId;
    sign = RSAEncrypt.testEncrypt(userName+cpId+gameId);
//    sign = "WWxeDbZ4RaVnp+oCXyb5+djgjP8UqF3VM/790zR5YAe0YwkFc63b/mxoPGmJbv92N9Z2C1REe6efOXSYEgIzfe82F78zDeSPJMwghh78281derXlCXn+dWYLXg1zQFUax5+ylWwjcDPsi2tYNaR3BFrnnm+b4iNXjwSW4/CHf38=";
    initMap();
    preUrl = prePath+uri;
    return HttpUtils.httpPost(preUrl, params);
}


public AppleChargingFormBean createAppleForm(String paymentId) {
    AppleChargingFormBean bean = new AppleChargingFormBean();

    return bean;
}


//初始化EagleHaodong回调参数
public EagleHaodongFormBean createEagleHaodongForm(String paymentId, String secretKey) throws UnsupportedEncodingException {
    EagleHaodongFormBean form = new EagleHaodongFormBean();
    form.setUserID("userId");
    form.setOrderID("unionOrderId"+new Random().nextInt(100));
    form.setChannelID("channelId");
    form.setGameID("gameId");
    form.setServerID("serverId");
    form.setRoleID("roleId");
    form.setProductID("productId");
    form.setMoney((int)chargeMoney*100 +"");
    form.setCurrency("RMB");
    form.setExtension(paymentId);
    String s = JsonUtil.convertBeanToJson(form);
    Map map = JsonUtil.convertJsonToBean(s,HashMap.class);
    TreeMap tm = new TreeMap(map);
    String str = "channelID="+form.getChannelID()+"&currency="+form.getCurrency()+"&extension="+form.getExtension()+"&gameID="+form.getGameID()+
            "&money="+form.getMoney()+"&orderID="+form.getOrderID()+"&productID="+form.getProductID()+"&serverID="+form.getServerID()+
            "&userID="+form.getUserID()+"&"+secretKey;
//    System.out.println(str);
    form.setSign(MD5SignatureChecker.doMD5HexEncrypt(str, "utf-8").toLowerCase());
    return form;
}
    //初始化Kuaikan回调参数
    public KuaikanChargingFormBean createKuaikanForm(String paymentId, String secretKey) throws Exception {
        KuaikanChargingFormBean bean = new KuaikanChargingFormBean();
        bean.setApp_id("app_id");
        bean.setOrder_id("kuaikanOrder"+new Random().nextInt(99));
        bean.setOut_order_id(paymentId);
        bean.setOpen_uid("open_uid");
        bean.setWares_id("wares_id");
        bean.setTrans_id("trans_id");
        bean.setTrans_money("1.00");
        bean.setCurrency("RMB");
        bean.setPay_type("pay_type");
        bean.setPay_status("2");
        bean.setTrans_time("414513355");
        bean.setTrans_result("2454");
        String str = "app_id="+bean.getApp_id()+"&currency="+bean.getCurrency()+"&open_uid="+bean.getOpen_uid()+"&order_id="
                +bean.getOrder_id()+"&out_order_id="+bean.getOut_order_id()+"&pay_status="+bean.getPay_status()+"&pay_type="+bean.getPay_type()
                +"&trans_id="+bean.getTrans_id()+"&trans_money="+ bean.getTrans_money()+"&trans_result="+bean.getTrans_result()+
                "&trans_time="+bean.getTrans_time()+"&wares_id="+bean.getWares_id()+"&key="+secretKey;
//        System.out.println(str);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] b = md5.digest(str.getBytes("utf-8"));
        String str1 = net.iharder.Base64.encodeBytes(b);
        bean.setSign(str1);
        return bean;
    }
    //初始化UC回调参数
    public UCChargingFormBean createUcForm(String paymentId, String secretKey) throws Exception {
        UCChargingFormBean bean = new UCChargingFormBean();
        bean.setVer("2.0");
        bean.setOrderId("ucOrder"+new Random().nextInt(99));
        bean.setGameId("gameId");
        bean.setAccountId("accountId");
        bean.setCreator("JY");
        bean.setPayWay("payWay");
        bean.setAmount("1.0");
        bean.setCallbackInfo("callbackinfo");
        bean.setOrderStatus("S");
        bean.setFailedDesc("failedDesc");
        bean.setCpOrderId(paymentId);
        String str = "accountId="+bean.getAccountId()+"amount="+bean.getAmount()+"callbackInfo="+bean.getCallbackInfo()+"cpOrderId="
            +bean.getCpOrderId()+"creator="+bean.getCreator()+"failedDesc="+bean.getFailedDesc()+"gameId="+bean.getGameId()+"orderId="+bean.getOrderId()+
                "orderStatus="+bean.getOrderStatus()+"payWay="+bean.getPayWay()+secretKey;
        bean.setSign(MD5SignatureChecker.doMD5HexEncrypt(str, "utf-8").toLowerCase());
        return bean ;
    }

    //初始化MeiZu回调参数
    public MeiZuChargingFormBean createMeizuForm(String paymentId, String secretKey) throws Exception{
        MeiZuChargingFormBean bean = new MeiZuChargingFormBean();
            bean.setNotify_time("notify_time");
            bean.setNotify_id("notify_id");
            bean.setOrder_id("meizuOrder"+new Random().nextInt(99));
            bean.setApp_id("app_id");
            bean.setUid("uid");
            bean.setPartner_id("partner_id");
            bean.setCp_order_id(paymentId);
            bean.setProduct_id("product_id");
            bean.setProduct_unit("product_unit");
            bean.setBuy_amount("10");
            bean.setProduct_per_price("product_per_price");
            bean.setTotal_price("1.0");
            bean.setTrade_status("3");
            bean.setCreate_time("create_time");
            bean.setPay_time("pay_time");
            bean.setPay_type("pay_type");
            bean.setUser_info("user_info");
            bean.setSign_type("md5");
            String str = "app_id="+bean.getApp_id()+"&buy_amount="+bean.getBuy_amount()+"&cp_order_id="+bean.getCp_order_id()+"&create_time="+bean.getCreate_time()
                        +"&notify_id="+bean.getNotify_id()+"&notify_time="+bean.getNotify_time()+"&order_id="+bean.getOrder_id()+"&partner_id="+bean.getPartner_id()+
                        "&pay_time="+bean.getPay_time()+"&pay_type="+bean.getPay_type()+"&product_id="+bean.getProduct_id()+"&product_per_price="+bean.getProduct_per_price()
                        +"&product_unit="+bean.getProduct_unit()+"&total_price="+bean.getTotal_price()+"&trade_status="+bean.getTrade_status()+"&uid="+bean.getUid()+
                        "&user_info="+bean.getUser_info()+":"+secretKey;
//        System.out.println(str);
            bean.setSign(MD5SignatureChecker.doMD5HexEncrypt(str, "utf-8"));
        return bean;
    }

    public VivoChargingFormBean createVivoForm(String paymentId, String secretKey) throws UnsupportedEncodingException {
        VivoChargingFormBean bean = new VivoChargingFormBean();
        bean.setRespCode("200");
        bean.setRespMsg("rRespMsg");
        bean.setSignMethod("signMethod");
        bean.setSignature("signature");
        bean.setTradeType("tradeType");
        bean.setTradeStatus("0000");
        bean.setCpId("cpId");
        bean.setAppId("appId");
        bean.setUid("uid");
        bean.setCpOrderNumber(paymentId);
        bean.setOrderNumber("vivoOrder"+new Random().nextInt(99));
        bean.setOrderAmount("100");
        bean.setExtInfo("extInfo");
        bean.setPayTime("payTime");
        String str = "appId="+bean.getAppId()+"&cpId="+bean.getCpId()+"&cpOrderNumber="+bean.getCpOrderNumber()+"&extInfo="+bean.getExtInfo()+
                "&orderAmount="+bean.getOrderAmount()+"&orderNumber="+bean.getOrderNumber()+"&payTime="+bean.getPayTime()+"&respCode="+
                bean.getRespCode()+"&respMsg="+bean.getRespMsg()+"&tradeStatus="+bean.getTradeStatus()+"&tradeType="+bean.getTradeType()+
                "&uid="+bean.getUid()+"&"+MD5SignatureChecker.doMD5HexEncrypt(secretKey, "utf-8").toLowerCase();
        bean.setSignature(MD5SignatureChecker.doMD5HexEncrypt(str, "utf-8"));
        return bean;
    }

    public NubiaFormBean createNubiaForm(String paymentId, String secretKey) throws UnsupportedEncodingException {
        NubiaFormBean bean = new NubiaFormBean();
        bean.setOrder_no(paymentId);
        bean.setData_timestamp("data_timestamp");
        bean.setPay_success("1");
        bean.setApp_id("app_id");
        bean.setUid("uid");
        bean.setAmount("1.0");
        bean.setOrder_serial("nubiaOrder"+new Random().nextInt(99));
        bean.setProduct_des("product_des");
        bean.setProduct_name("product_name");
        bean.setNumber("number");
        bean.setSign("sign");
        String str = "amount="+bean.getAmount()+"&app_id="+bean.getApp_id()+"&data_timestamp="+bean.getData_timestamp()+
                "&number="+bean.getNumber()+"&order_no="+bean.getOrder_no()+"&pay_success="+bean.getPay_success()+"&product_des="
                +bean.getProduct_des()+"&product_name="+bean.getProduct_name()+"&uid="+bean.getUid()+":"+bean.getApp_id()+":"+
                secretKey;
        bean.setOrder_sign(MD5SignatureChecker.doMD5HexEncrypt(str, "utf-8"));
        return bean;
    }

    private Map<String,String> createBaiduForm(String payMentId, String secretKey) throws UnsupportedEncodingException {
        BaiduChargingFormBean bean = new BaiduChargingFormBean();
        Map<String,String> map = new HashMap<String, String>();
        bean.setAppID(123456);
        bean.setBankDateTime("BankDateTime");
        bean.setCooperatorOrderSerial(payMentId);
        bean.setExtInfo("ExtInfo");
        bean.setMerchandiseName("10元宝");
        bean.setOrderMoney(1.00);
        bean.setOrderSerial("huaweiOrder" + new Random().nextInt(100));
        bean.setOrderStatus(1);
        bean.setStartDateTime("StartDateTime");
        bean.setStatusMsg("成功");
        bean.setUID(123456L);
        bean.setVoucherMoney(1);
        StringBuffer sb = new StringBuffer();
        String content = "{\"BankDateTime\":\"BankDateTime\",\"ExtInfo\":\"ExtInfo\",\"MerchandiseName\":\"10元宝\",\"OrderMoney\":1.00," +
                "\"OrderStatus\":1,\"StartDateTime\":\"StartDateTime\",\"StatusMsg\":\"成功\",\"UID\":13456,\"VoucherMoney\":0}";
        sb.append(bean.getAppID()).append(bean.getOrderSerial()).append(bean.getCooperatorOrderSerial())
                .append(Base64.encode(content.getBytes())).append(secretKey);

        String sign = MD5SignatureChecker.doMD5HexEncrypt(sb.toString(), "utf-8");
        bean.setSign(sign);
        map.put("AppID", String.valueOf(bean.getAppID()));
        map.put("OrderSerial", bean.getOrderSerial());
        map.put("CooperatorOrderSerial", bean.getCooperatorOrderSerial());
        map.put("Sign", "f570c34b81c27c85b357e4f69384e98d");
        map.put("Content",Base64.encode(content.getBytes()));
        return map;
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println( MD5SignatureChecker.doMD5HexEncrypt("{\"BankDateTime\":\"2018-01-26 13:03:43\",\"ExtInfo\":\"594004\",\"MerchandiseName\":\"10元宝\",\"OrderMoney\":\"1.00\",\"OrderStatus\":1,\"StartDateTime\":\"2018-01-26 13:03:43\",\"StatusMsg\":\"成功\",\"UID\":\"2999797427\",\"VoucherMoney\":6}","utf-8"));
//    }
    public static String httpPost1(String url, String body,String channelName) {
        URL u = null;
        HttpURLConnection conn = null;
        StringBuilder sb = new StringBuilder();
        sb.append("POST_"+channelName+":");
        sb.append(",url:"+url);
        sb.append(",param:"+body);
        StringBuffer buffer = new StringBuffer();
        // 发送数据，给苹果
        try {
            u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type",
                    "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(
                    conn.getOutputStream(), "UTF-8");
            osw.write(body);
            osw.flush();
            osw.close();
            // 读取数据
            sb.append(",result:");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
                buffer.append(temp);
            }
            LoggerUtil.info(HttpUtils.class, sb.toString());

        } catch (Exception e) {
            LoggerUtil.error(HttpUtils.class, "POST,url:"+url+",error:"+e.toString()+",param:"+sb.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return buffer.toString();
    }

}
