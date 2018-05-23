package com.linekong.union.charge.consume.util;

public class Constant {
	//************************************                       MQ错误转态码                                                                                         ******************************//
	
	public static final int 				ERROR_MQ_IOEXCEPTION			= -101;		//MQ IOException
	
	public static final int					ERROR_MQ_EXCEPTION				= -102;		//MQ 其他错误
	
	public static final int					ERROR_MQ_ERATING_RETURN			= -103;		//MQ 信息返回错误
	
	public static final int					ERROR_MQ_UNION_ORDER_EXIST		= -104;		//MQ 联运订单号不存在
	
	//************************************                       错误转态码                                                                                               ******************************//
	public static final int					SUCCESS							= 1;		 //执行成功状态吗
	
	public static final int					ERROR_SYSTEM					= -200;		 //系统异常
	
	public static final int					HTTP_EXCEPION					= -201;		 //http请求异常
	
	public static final int					ERROR_ORDER_DUPLICATE			= -1472;	 //订单号重复
	
	public static final int					ERROR_ORDER_EXIST				= -1490;	 //订单号不存在
	
	public static final int					ERROR_SIGN						= -21000;	 //签名验证异常
	
	public static final int					ERROR_REQUEST_CPGAME			= -21001;	 //渠道游戏配置不存在
	
	public static final int					ERROR_REQUEST_CP				= -21002;	 //渠道不存在
	
	public static final int					ERROR_CHARGE_STATUS				= -21003;	 //合作伙伴没有开通充值请联系管理员开通
	
	public static final int					ERROR_CHARGE_RATIO				= -21004;	 //合作伙伴没有配置对应的充值比例(预支付时，校验)
	
	public static final int					ERROR_CHARGE_SIGN				= -21005;	 //获取CpKey值异常
	
	public static final int					ERROR_PARAM_VALIDATE			= -21006;	 //参数校验异常
	
	public static final int					ERROR_PUSH_ORDER_EXIST  		= -21007;	 //需要推送的订单不存在-暂时没有用到
	
	public static final int					ERROR_PUSH_COUNT				= -21008;	 //推送失败次数超过限制-暂时没有用到
	
	public static final int					ERROR_CONFIG_SIGN				= -21009;	 //未获取到加密sign值-暂时没有用到
	
	public static final int					ERROR_CHARGE_MONEY				= -21010;	 //预支付金额和回调金额不符
	
	public static final int 				ERROR_FAIL_ORDER				= -21011;	 //此订单不是成功订单
	
	public static final int 				ERROR_CHARGE_SERVER_IP			= -21012;	 //没有配置合作伙伴ServerIP白名单
	
	public static final int 				ERROR_SERVER_IP					= -21013;	 //与配置合作伙伴ServerIP白名单，不匹配
	
	public static final int 				ERROR_ROLE						= -21014;	 //角色信息错误
	
	public static final int 				ERROR_CALL_BACK_URL				= -21015;	 //参与签名的回调URL未配置
	
	public static final int					ERROR_REQUEST_GAME_CONNECTION	= -21016;	 //预支付中请求地址和请求参数不匹配(gameName和gameID不匹配)
	
	public static final int 				ERROR_TEST_MAX_MONEY			= -21017;	 //配置测试状态下该游戏的最大充值金额 有误
	
	public static final int 				ERROR_EXCEED_TEST_MAX_MONEY		= -21018;	//超过了 配置测试状态下该游戏的最大充值金额
	
	public static final int					ERROR_APPLE_VERIFY_URL			= -21019;	//苹果验证凭证的URL未配置
	
	public static final int 				ERROR_APPLE_VERIFY				= -21020;	//苹果验证凭证有误
	
	public static final int     			ERROR_PRODUCT_INFO				= -21021;	//产品信息不存在
	
	public static final int					ERROR_REQUEST_CP_STATE			= -21022;	//渠道已经关闭
	
	public static final int 				ERROR_REQUEST_CPGAME_STATE		= -21023;	//渠道游戏配置未生效
	
	public static final int 				ERROR_APPLE_VERIFY_URL_TEST		= -21024;	//苹果验证凭证的URL未配置_沙盒测试
	
	public static final int 				ERROR_APPLE_CHARGE_TIME			= -21025;	//苹果充值小额笔数，错误
	
	public static final int 				ERROR_APPLE_OVER_CHARGE_TIME	= -21026;	//超过苹果充值小额笔数
	
	public static final int 				ERROR_APPLE_VERSION				= -21027;	//苹果接口版本错误
	
	public static final int					ERROR_APPLE_ORDER				= -21028;	//返回的苹果订单信息中inapp数组为0
	
	public static final int					ERROR_RATIO						= -21029;	//money与amount的比例与配置的不符
	
	public static final int					ERROR_CP_PRE_CHARGE_URL			= -21030;	//预支付访问第三方地址没有配置
	
	public static final int					ERROR_CP_PRE_CHARGE_RES_INFO	= -21031;	//预支付访问第三方,返回信息有误
	
	public static final int					ERROR_CP_PRE_CHARGE				= -21032;	//预支付访问第三方,返回状态为失败
	
	public static final int					ERROR_CP_ID						= -21033;	//渠道方提供的CpID没有配置
	
	public static final int					ERROR_APP_ID					= -21034;	//渠道方提供的appId没有配置
	
	public static final int					ERROR_CP_PRE_CHARGE_SIGN		= -21035;	//渠道预支付返回，签名验证异常
	
	public static final int 				ERROR_ORDER_NO_PAY				= -21036;	//订单没有支付
	
	public static final int                 ERROR_PRODUCTID                 = -21037;   //商品ID不合法
	
	public static final int 				ERROR_MATCH_PAYMENT				= -21047;	//没有匹配到对应的预支付信息
	
	public static final int                 ERROR_UID                       = -21048;   //UID预支付和回调不一致
	
	public static final int                 ERROR_ExtInfo                   = -21049;   //透传字段值不一致
	
	public static final int                 ERROR_MerchandiseName           = -21050;   //productName不一致
	
	public static final int                 ERROR_ORDER                     = -21051;   //360返回为支付订单
	
	public static final int                 ERROR_APPKEY_CONFIG             = -21052;   //APPKEY未配置
	
	public static final int                 ERROR_CALLBACK_APPKEY           = -21053;   //回调的APPKEY和配置的APPKEY不一致
	
	public static final int                 ERROR_appext1_CONFIG            = -21054;   //透传字段1不能为空

	public static final int                 ERROR_UPDATE_PRE_USERNAME       = -21055;   //预支付表试玩账号改成正式账号失败

	public static final int                 ERROR_PAY_ID                    = -21056;   //支付ID未配置

	public static final int                 ERROR_PAY_PRE_KEY               = -21057;   //支付私钥未配置

	public static final int                 ERROR_PRE_SAMSUNG_CHARGE        = -21058;   //预支付去三星下单时返回结果数据有误
	
	public static final int                 ERROR_CALLBACK_APPID            = -21059;   //回调的APPID和配置的APPID不一致

	public static final int                 ERROR_CALLBACK_URL            	= -21060;   //回调的URL和配置的URL不一致
	
	public static final int                 ERROR_CALL_CHECK            	= -21061;   //验证订单异常

	public static final int                 ERROR_REDIRECT_URL              = -21062;   //类似爱贝渠道那样的网页支付，未配置支付或取消支付的跳转路径

	public static final int                 ERROR_PLACE_ORDER               = -21062;   //下单失败
	//************************************                       错误转态码 对应描述                                                                                              ******************************//
	public static final String 				DESC_SUCCESS					= "SUCCESS";
	
	public static final String 				DESC_ERROR_SYSTEM				= "ERROR_SYSTEM";
	
	public static final String 				DESC_ERROR_ORDER_DUPLICATE		= "ORDER_REPEAT";
	
	public static final String 				DESC_ERROR_ORDER_EXIST			= "ORDER_NOT_EXIST";
	
	public static final String 				DESC_ERROR_SIGN					= "ERROR_SIGN";
	
	public static final String 				DESC_ERROR_REQUEST_CPGAME		= "CPGAME_NOT_EXIST";
	
	public static final String 				DESC_ERROR_REQUEST_CP			= "CP_NOT_EXIST";
	
	public static final String 				DESC_ERROR_CHARGE_STATUS		= "ERROR_CHARGE_STATUS";
	
	public static final String 				DESC_ERROR_CHARGE_SIGN			= "SECRETKEY_NOT_EXIST";
	
	public static final String 				DESC_ERROR_CHARGE_MONEY			= "ERROR_CHARGE_MONEY";
	
	public static final String 				DESC_ERROR_CHARGE_SERVER_IP		= "ERROR_SERVER_IP_CONFIG";
	
	public static final String 				DESC_ERROR_SERVER_IP			= "ERROR_SERVER_IP";
	
	public static final String 				DESC_ERROR_PARAM_VALIDATE		= "ERROR_PARAM_VALIDATE";
	
	public static final String 				DESC_ERROR_FAIL_ORDER			= "FAIL_ORDER";
	
	public static final String 				DESC_ERROR_CALL_BACK_URL		= "ERROR_CALL_BACK_URL_CONFIG";
	
	public static final String 				DESC_ERROR_TEST_MAX_MONEY		= "ERROR_TEST_MAX_MONEY";
	
	public static final String 				DESC_ERROR_EXCEED_TEST_MAX_MONEY= "EXCEED_TEST_MAX_MONEY";
	
	public static final String				DESC_ERROR_REQUEST_CP_STATE		= "CP_NOT_OPEN";
	
	public static final String				DESC_ERROR_REQUEST_CPGAME_STATE = "CPGAME_NOT_OPEN";
	
	
	//************************************                          配置对应KEY                            ******************************//
	public static final String				CONFIG_PROJECT_CODE			=  "unionCharge";					//项目编码
	
	public static final String				CONFIG_IS_OPEN_CHARGE		=  "config_charge_status"; 			//获取充值状态
	
	public static final String				CONFIG_CHARGE_RATIO			=  "config_charge_ratio";			//获取充值比例
	
	public static final String 				CONFIG_CP_SERVER_IP			=  "config_cp_server_ip";			//合作伙伴服务器IP白名单
	
	public static final String 				CONFIG_IS_CHECK_ROLE		=  "config_is_check_role";			//是否验证角色信息
	
	public static final String				CONFIG_IS_NEW_ACT			=	"config_is_new_act";			//是否使用新的活动程序
	
	public static final String 				CONFIG_CALL_BACK_URL		=	"config_call_back_url";			//参与签名的回调URL
	
	public static final String 				CONFIG_TEST_STATE			=	"config_test_state";			//测试状态
	
	public static final String 				CONFIG_TEST_MAX_MONEY		=	"config_test_max_money";		//测试状态下该游戏的最大充值金额（共计）
	
	public static final String 				CONFIG_PRE_CHARGE_URL		=	"config_pre_charge_url";		//渠道预支付地址
	
	public static final String 				CONFIG_APPLE_VERIFY_URL		=	"config_apple_verify_url";		//苹果apple验证凭证路径
	
	public static final String 				CONFIG_APPLE_VERIFY_URL_TEST=	"config_apple_verify_url_test"; //苹果apple验证凭证路径_沙盒测试
	
	public static final String 				CONFIG_APPLE_USERNAME_TEST  =   "config_apple_username_test";	//苹果apple用户名白名单_沙盒测试
	
	public static final String				CONFIG_APPLE_GATEWAYID_TEST =   "config_apple_gatewayid_test";	//苹果apple网关白名单_沙盒测试
	
	public static final String				CONFIG_APPLE_CHARGE_TIME	=	"config_apple_charge_time";		//苹果小额充值次数

	public static final String				CONFIG_ENCRYPTION_CHECKORDERURL	=  "check_order_url";			//验证订单URL
	
	public static final String				CONFIG_ENCRYPTION_RSA		=  "RSA";
	
	public static final String				CONFIG_ENCRYPTION_RSA_PRIVATE=  "RSA-PRIVATE";
	
	public static final String				CONFIG_ENCRYPTION_MD5		=  "MD5";
	
	public static final String				CONFIG_ENCRYPTION_DES3		=  "DES3";
	
	public static final String				CONFIG_ENCRYPTION_DES		=  "DES";
	
	public static final String				CONFIG_ENCRYPTION_SHA1		=  "SHA1";
	
	public static final String				CONFIG_ENCRYPTION_HMACSHA1  =  "HMACSHA1";
	
	public static final String              CONFIG_ENCRYPTION_HMACMD5   =  "HMACMD5";
	
	public static final String 				CONFIG_ENCRYPTION_MD5WITHRSA=	"MD5WITHRSA";
	
	public static final String				CONFIG_ENCRYPTION_CPID      = 	"CPID";

	public static final String				CONFIG_ENCRYPTION_APPID     = 	"APPID";

	public static final String              CONFIG_ENCRYPTION_PAYID     =  "PAYID";

	public static final String              CONFIG_ENCRYPTION_PRE_KEY 	=  "PAYPREKEY";

	public static final String				CONFIG_ENCRYPTION_APIKEY	=  "APIKEY";
	
	public static final String				CONFIG_ENCRYPTION_APPKEY	=  "APPKEY";
	
	public static final String              CONFIG_STAND_ALONE          =  "STANDALONE";  //是否单机

	public static final String              SUCCESS_REDIRECT_URL        =  "SUCCESSURL";//"http://www.8864.com/";支付成功跳转地址

	public static final String              FAIL_REDIRECT_URL           =  "FAILURL";//"http://new.linekong.com/";支付成功跳转地址

//************************************                          对应状态                         			   ******************************//
	
	public static final int 				STATE_NOT_EFFECTIVE			=   2;		//未生效
	
	public static final int 				STATE_COOPERATE				= 	1;		//合作
	
	public static final int 				STATE_STOP_COOPERATE		=	0;		//停止合作
	
	public static final int 				STATE_NULL					=  -1;		//配置为空
	
	public static final int 				CHARGE_SUCCESS				=	1;		//支付成功
	
	public static final int 				DB_PUSH						=	1;		//数据库存储过程推送
	
	public static final int					MQ_PUSH						=	2;		//MQ推送方式（充值解耦方式）
	
	public static final int					DEFAULT_DISCOUNT			=	1;		//默认充值渠道折扣
	
	public static final int                 APPLE_SANDBOX_STATE         =   9;      //苹果沙盒订单

	public static final int                 KAFKAFAILCOUNT              =   50;     //记录发送kafka失败的map最大保存50条
}
