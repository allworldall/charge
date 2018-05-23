package com.linekong.union.charge.provider.util.log;

import java.lang.reflect.Method;

import com.linekong.union.charge.provider.util.ThreadPoolUtil;
import com.linekong.union.charge.provider.util.monitor.MonitorInfoUpload;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAOPUtils {
	
	private final Logger log = Logger.getLogger(this.getClass());
	
	private long beginTime = 0;
	@Before("execution(* *com.linekong.union.charge.provider.service..*.*(..))")
	public void doBeforeMethod(){
		beginTime = System.currentTimeMillis();
	}
	/**
	 * 触发调用方法
	 */
	@Around("execution(* *com.linekong.union.charge.provider.service..*.*(..))")
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
			for (int i = 0; i < params.length; i++) {
				if(i == params.length -1){
					buffer.append((args[i]).toString());
				}else{
					buffer.append(params[i]).append("=").append(String.valueOf(args[i])).append(",");
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
		buffer.append(",result=").append(result).append(",");
		long aopTime = System.currentTimeMillis() - beginTime;
		buffer.append("time=").append(aopTime);
		log.info(buffer.toString());
		//异步上传信息
//		result = result == null ? "" : result;
//		ThreadPoolUtil.pool.execute(new MonitorInfoUpload(result.toString(), buffer.toString(), aopTime));
		return result;
	}
}
