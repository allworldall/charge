package com.linekong.union.charge.consume.util.annotation.support;

import java.lang.reflect.Field;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.util.annotation.RegexType;
import com.linekong.union.charge.rpc.util.RegexUtils;
import com.linekong.union.charge.rpc.util.StringUtils;

/**
 * 注解解析
 */
public class ValidateService {
	
	
	
	public ValidateService(){
		super();
	}
	//解析入口
	public static void valid(Object object) throws Exception{
		//获取object类型
		Class<? extends Object> clazz = object.getClass();
		//获取该类型声明的成员
		Field [] fields = clazz.getDeclaredFields();
		//遍历属性
		for (Field field:fields) {
			//对于private私有化的成员变量，通过setAccessible来修改器访问权限
			field.setAccessible(true);
			validate(field,object);
			//重新设置会私有权限
			field.setAccessible(false);
		}
	}
	//验证服务
	public static void validate(Field field,Object object) throws Exception{
		
		String description;
		Object value;
		
		//获取对象的成员的注解信息
		DataValidate dataValidate = field.getAnnotation(DataValidate.class);
		value		 = field.get(object);
		
		if(dataValidate == null){
			return;
		}
		description = dataValidate.description().equals("") ? field.getName() : dataValidate.description();
		
		/********************解析注解********************/
		if(!dataValidate.nullable()){
			if(value == null || StringUtils.isBlank(value.toString())){
				throw new Exception(description + field.getName() + " not null");
			}
		}else{
			return;
		}
		
		if(value.toString().length() > dataValidate.maxLength() && dataValidate.maxLength() != 0){
			throw new Exception(description + field.getName() + " The length can not exceed " + dataValidate.maxLength());
		}
		
		if(value.toString().length() < dataValidate.minLength() && dataValidate.minLength() != 0){
			throw new Exception(description + field.getName() + " The length can not be less than" + dataValidate.minLength());
		}
		
		if(dataValidate.regexType() != RegexType.NONE){
			switch (dataValidate.regexType()) {
			case NONE:
				break;
			case SPECIALCHAR:
				if(!RegexUtils.hasSpecialChar(value.toString())){
					throw new Exception(description + field.getName() + " No special characters can be contained");
				}
				break;
			case CHINESE:
				if(!RegexUtils.isChinese2(value.toString())){
					throw new Exception(description + field.getName() + " Cannot contain Chinese characters");
				}
				break;
			case EMAIL:
				if(!RegexUtils.isEmail(value.toString())){
					throw new Exception(description + field.getName() + " Incorrectly formatting");
				}
				break;
			case IP:
				if(!RegexUtils.isIp(value.toString())){
					throw new Exception(description + field.getName() + " Incorrectly formatting");
				}
				break;
			case NUMBER:
				if(!RegexUtils.isNumber(value.toString())){
					throw new Exception(description + field.getName() + " Incorrectly formatting");
				}
				break;
			case NUMBER1:
				if(!RegexUtils.isNumber(value.toString())){
					throw new Exception(description + field.getName() + " Incorrectly formatting");
				}
				break;
			case PHONENUMBER:
				if(!RegexUtils.isPhoneNumber(value.toString())){
					throw new Exception(description + field.getName() + " Incorrectly formatting");
				}
				break;
			default:
				break;
			}
		}
		
		if(!dataValidate.regexExpression().equals("")){
			if(value.toString().matches(dataValidate.regexExpression())){
				throw new Exception(description + field.getName() + " Incorrectly formatting");
			}
		}
	}
}
