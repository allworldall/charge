package com.linekong.union.charge.consume.util.log;

import java.lang.reflect.Method;

import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeResBean;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import com.linekong.union.charge.consume.util.ThreadPoolUtil;
import com.linekong.union.charge.consume.util.monitor.MonitorInfoUpload;

@Component
@Aspect
public class LoggerAOPUtils {
	
	private final Logger log = Logger.getLogger(this.getClass());
	
	private long beginTime = 0;
	@Before("execution(* *com.linekong.union.charge.consume.service.business..*.*(..))")
	public void doBeforeMethod(){
		beginTime = System.currentTimeMillis();
	}
	/**
	 * 触发调用方法
	 */
	@Around("execution(* *com.linekong.union.charge.consume.service.business..*.*(..))")
	public Object doCallMethod(ProceedingJoinPoint pjp){
		String method = pjp.getSignature().getName();
		Object args [] = pjp.getArgs();
		StringBuffer buffer = new StringBuffer();
		buffer.append(pjp.getSignature().getDeclaringTypeName()).append(":method=").append(method).append(",param:");
		Method [] mt = null;
		try {
			mt = Class.forName(pjp.getSignature().getDeclaringTypeName()).getDeclaredMethods();
			for(int i = 0;i<mt.length;i++){
				if(mt[i].getName().equals(method)){
					mt[0] = mt[i];
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String [] params = new LocalVariableTableParameterNameDiscoverer().getParameterNames(mt[0]);
		if(params != null){
			if(method.equals("appleCharging")){
				for (int i = 0; i < params.length; i++) {
					if(i == params.length -1){
						buffer.append(String.valueOf(args[i]).toString());
					}else{
						buffer.append(params[i]).append("=").append(replaceTransactionReceipt(String.valueOf(args[i]))).append(",");
					}
				}
			}else{
				for (int i = 0; i < params.length; i++) {
					if(i == params.length -1){
						buffer.append(String.valueOf(args[i]).toString());
					}else{
						buffer.append(params[i]).append("=").append(String.valueOf(args[i])).append(",");
					}
				}
			}
		}
		//方法返回值
		Object result = "";
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		long time = System.currentTimeMillis()-beginTime;
		buffer.append(",time=").append(time).append(",");
		buffer.append("result=").append(result);
		log.info(buffer.toString());
		return result;
	}
	/**
	 * 过滤苹果充值的 transactionReceipt
	 */
	public static String replaceTransactionReceipt(String str){
		String params [] = str.split(",");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < params.length; i++) {
			if(params[i].indexOf("transactionReceipt") >= 0){
				String param [] = params[i].split("=");
				buffer.append(param[0]).append("=").append("").append(",");
			}else{
				buffer.append(params[i]).append(",");
			}
		}
		return buffer.toString();
	}
}
