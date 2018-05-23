package com.linekong.union.charge.consume.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.linekong.union.charge.consume.util.log.LoggerUtil;

public class BaseController {
	
	/**
	 * 所有Controller 继承此类返回结果
	 * @param HttpServletResponse response
	 * @param String			  result
	 */
	protected void response(HttpServletResponse response,String result){
		try {
			response.getWriter().print(result);
		} catch (IOException e) {
			LoggerUtil.error(BaseController.class, e.getMessage());
		}
		
	}
}
