package com.linekong.union.charge.consume.util.cache;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.linekong.union.charge.consume.service.invoke.CacheServerDao;
@Component
public class CacheUtil {
	@Resource
	public CacheServerDao cacheServerDao;
	
	private static CacheUtil cacheUtil;
	
	//游戏配置属性缓存
	private static LoadingCache<String, Object> gameKeyValuCache = CacheBuilder.newBuilder().maximumSize(1000)  
	           .expireAfterWrite(120, TimeUnit. SECONDS) //在120秒没有被写访问或者覆盖则移除缓存
	           .refreshAfterWrite(60, TimeUnit. SECONDS).build(new CacheLoader<String, Object>() {

				@Override
				public Object load(String key) throws Exception {
					String str [] = key.split(",");//key格式 :gameId|cpId|key
					return cacheUtil.cacheServerDao.getGameKeyValue(Integer.parseInt(str[0]), Integer.parseInt(str[1]), str[2]);
				}
			});
	//游戏配置属性缓存
	private static LoadingCache<String, Object> gameKeyValuCacheByCpCode = CacheBuilder.newBuilder().maximumSize(1000)  
	           .expireAfterWrite(120, TimeUnit. SECONDS) //在120秒没有被写访问或者覆盖则移除缓存
	           .refreshAfterWrite(60, TimeUnit. SECONDS).build(new CacheLoader<String, Object>() {

				@Override
				public Object load(String key) throws Exception {
					String str [] = key.split(",");//key格式 :gameId|key
					return cacheUtil.cacheServerDao.getGameKeyValueByCpCode(str[0], str[1]);
				}
			});
	//游戏Code缓存
	private static LoadingCache<String, Object> vaildCpGameByNameCache = CacheBuilder.newBuilder().maximumSize(1000)  
	           .expireAfterWrite(120, TimeUnit. SECONDS)  //在120秒没有被写访问或者覆盖则移除缓存
	           .refreshAfterWrite(60, TimeUnit. SECONDS).build(new CacheLoader<String, Object>() {

				@Override
				public Object load(String key) throws Exception {
					String arr[] = key.split("_");
					return cacheUtil.cacheServerDao.vaildCpGameByName(arr[0], arr[1]);
				}
			});
		//验证游戏和渠道对应关系以及开启状态缓存
		private static LoadingCache<String, Object> vaildCpGameByIdCache = CacheBuilder.newBuilder().maximumSize(1000)  
		           .expireAfterWrite(120, TimeUnit. SECONDS)  //在120秒没有被写访问或者覆盖则移除缓存
		           .refreshAfterWrite(60, TimeUnit. SECONDS).build(new CacheLoader<String, Object>() {

					@Override
					public Object load(String key) throws Exception {
						String arr[] = key.split("_");
						return cacheUtil.cacheServerDao.vaildCpGameById(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
					}
				});
	//CPState缓存
	private static LoadingCache<String, Object> cpStateCache = CacheBuilder.newBuilder().maximumSize(1000)  
	           .expireAfterWrite(120, TimeUnit. SECONDS)  //在120秒没有被写访问或者覆盖则移除缓存
	           .refreshAfterWrite(60, TimeUnit. SECONDS).build(new CacheLoader<String, Object>() {

				@Override
				public Object load(String key) throws Exception {
					String str[] = key.split("_");
					return cacheUtil.cacheServerDao.getCPState(str[0], Integer.parseInt(str[1]));
				}
			});
	//通过产品Name获取CPState缓存
	private static LoadingCache<String, Object> cpStateByCpNameCache = CacheBuilder.newBuilder().maximumSize(1000)  
	           .expireAfterWrite(120, TimeUnit. SECONDS)  //在120秒没有被写访问或者覆盖则移除缓存
	           .refreshAfterWrite(60, TimeUnit. SECONDS).build(new CacheLoader<String, Object>() {

				@Override
				public Object load(String cpName) throws Exception {
					return cacheUtil.cacheServerDao.getCPStateByCpName(cpName);
				}
			});
	
	//rsa密钥信息缓存
	private static LoadingCache<String, Object> rsaCache = CacheBuilder.newBuilder().maximumSize(1000)  
		           .expireAfterWrite(120, TimeUnit. SECONDS)  //在120秒没有被写访问或者覆盖则移除缓存
		           .refreshAfterWrite(60, TimeUnit. SECONDS).build(new CacheLoader<String, Object>() {

					@Override
					public Object load(String key) throws Exception {
						String str[] = key.split("_");//key格式 projectCode_type
						return cacheUtil.cacheServerDao.getRSAKey(str[0], Integer.parseInt(str[1]));
					}
				});
	//获取回调地址
	private static LoadingCache<String, Object> getCallBackURLCache = CacheBuilder.newBuilder().maximumSize(1000)  
		           .expireAfterWrite(120, TimeUnit. SECONDS)  //在120秒没有被写访问或者覆盖则移除缓存
		           .refreshAfterWrite(60, TimeUnit. SECONDS).build(new CacheLoader<String, Object>() {

					@Override
					public Object load(String key) throws Exception {
						String str[] = key.split("_");//key格式 projectCode_type
						return cacheUtil.cacheServerDao.queryCallBackURL(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
					}
				});
	@PostConstruct
	public void init(){
		cacheUtil = this;
		cacheUtil.cacheServerDao = this.cacheServerDao;
	}
	/**
	 * 获取游戏和渠道对应的属性配置节点
	 * @param int 	   gameId    游戏ID
	 * @param String   key       属性节点
	 * @return String value
	 */
	public static String getKey(final int gameId, final int cpId, final String key) throws Exception{
		
		return (String) gameKeyValuCache.get(gameId+","+cpId+","+key);
	}
	/**
	 * 获取游戏和渠道对应的属性配置节点
	 * @param String 	   cpCode    渠道标识
	 * @param String   key       属性节点
	 * @return String value
	 */
	public static String getKeyByCpCode(final String cpCode,final String key) throws Exception{
		
		return (String) gameKeyValuCacheByCpCode.get(cpCode+","+key);
	}
	
	/**
	 * 通过请求地址的url地址游戏名称获取游戏ID
	 * @param String gameName  自主游戏简称
	 * @param String cpName    联运游戏简称
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public static int vaildCpGameByName(final String gameName,final String cpName) throws Exception{
		String key = gameName+"_"+cpName;
		return (Integer) vaildCpGameByNameCache.get(key);
	}
	/**
	 * 验证游戏和渠道对应关系以及开启状态
	 * @param cpGameId
	 * @param cpId
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public static int vaildCpGameById(final int  cpGameId,final int cpId) throws Exception{
		String key = cpGameId+"_"+cpId;
		return (Integer) vaildCpGameByIdCache.get(key);
	}
	/**
	 * 通过合作伙伴编码和ID获取合作伙伴state
	 * @param cpName
	 * @param cpId
	 * @return 如果为空则返回-1
	 */
	public static Integer getCPState(final String cpName,final Integer cpId) throws NumberFormatException, Exception{
		String key = cpName+"_" + cpId;
		return (Integer)cpStateCache.get(key);
	}
	
	/**
	 * 通过合作伙伴编码获取合作伙伴state
	 * @param cpName
	 * @return 如果为空则返回-1
	 */
	public static Integer getCPStateByCpName(final String cpName) throws NumberFormatException, Exception{
		return (Integer)cpStateByCpNameCache.get(cpName);
	}
	
	/**
	 * 通过项目名称 获取rsa密钥
	 * @param String projectCode 项目代码
	 * @param String type 1、公钥 2、私钥
	 * @return String rsa密钥
	 */
	public static String getRSAKey(final String projectCode,final int type) throws NumberFormatException, Exception{
		return (String)rsaCache.get(projectCode+"_"+type);
	}
	/**
	 * 通过游戏名称和cp名称获取对应的游戏ID
	 * @return Integer 游戏ID
	 */
	public static List<Object> getGameId(String gameName, String cpName){
		return cacheUtil.cacheServerDao.getCpGameId(gameName, cpName);
	}
	/**
	 * 通过gameId以及test_state,查询总的充值金额
	 * @param gameId
	 * @param testState
	 * @return
	 */
	public static double getSunMoneyByGameId( int gameId, int testState){
		return cacheUtil.cacheServerDao.getSunMoneyByGameId(gameId, testState);
	}
	/**查询苹果用户充值小额金额
	 * 
	 * @param userName
	 * @param cpId
	 * @param gameId
	 * @param date  当前时间（yyyy-mm-dd）
	 * @return
	 */
	public static int queryAppleChargeTime(String userName, Integer cpId, Integer gameId,String date){
		return cacheUtil.cacheServerDao.queryAppleChargeTime(userName, cpId, gameId, date);
	}
	/**
	 * 获取回调地址
	 * @param cpId
	 * @param cpGameId
	 * @return
	 * @throws ExecutionException 
	 */
	public static String queryCallBackURL(final Integer cpId, final Integer cpGameId) throws ExecutionException{
		return (String) getCallBackURLCache.get(cpId+"_"+cpGameId);
	}
}
