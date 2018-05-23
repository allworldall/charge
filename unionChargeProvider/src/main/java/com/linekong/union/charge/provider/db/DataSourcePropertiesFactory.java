package com.linekong.union.charge.provider.db;

import java.util.Properties;

import com.linekong.union.charge.provider.util.log.LoggerUtil;
import org.apache.log4j.Logger;

import com.linekong.union.charge.provider.db.util.DBSecurityUtils;
import com.linekong.union.charge.provider.util.Constant;

/**
 * 数据库连接池密文密码解密
 */
public class DataSourcePropertiesFactory {
	
	//private static final Logger log = Logger.getLogger(DataSourcePropertiesFactory.class);
	
	/**
	 * 对密文密码进行解密
	 */
	public static Properties getProperties(String username,String password){
		Properties p = new Properties();
		try {
			p.setProperty(Constant.PRO_USERNAME, username);
			p.setProperty(Constant.PRO_PASSWORD, DBSecurityUtils.decode(password));
		} catch (Exception e) {
			LoggerUtil.error(DataSourcePropertiesFactory.class,"start service error by connetcion db error:" + e.getMessage());
			System.exit(-1);
		}
		return p;
	}
	
}
