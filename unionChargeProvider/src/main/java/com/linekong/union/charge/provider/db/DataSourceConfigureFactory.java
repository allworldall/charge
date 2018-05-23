package com.linekong.union.charge.provider.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.linekong.union.charge.provider.util.log.LoggerUtil;

@Component
public class DataSourceConfigureFactory {
	//数据注入
	@Autowired
	private DruidDataSource dataSource;
	//平台管理系统数据源注入
	@Autowired
	private DruidDataSource dataSource_pm;
	//passport数据源注入
	@Autowired
	private DruidDataSource dataSource_passport;

	public DruidDataSource getDataSource_pm() {
		return dataSource_pm;
	}

	public void setDataSource_pm(DruidDataSource dataSource_pm) {
		this.dataSource_pm = dataSource_pm;
	}

	public DruidDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DruidDataSource getDataSource_passport() {
		return dataSource_passport;
	}

	public void setDataSource_passport(DruidDataSource dataSource_passport) {
		this.dataSource_passport = dataSource_passport;
	}

	/**
	 * 数据库连接关闭
	 * @param Connection 			conn
	 * @param CallableStatement		call
	 * @param ResultSet				rs
	 */
	public void close(Connection conn,CallableStatement call, PreparedStatement ps,ResultSet rs){
		try {
			if(conn != null){
				conn.close();
			}
			if(call != null){
				call.close();
			}
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(DataSourceConfigureFactory.class, e.getMessage());
		}
	}
	/**
	 * 数据库回滚操作
	 */
	public void rollback(Connection conn){
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(DataSourceConfigureFactory.class, e.getMessage());
		}
		
	}
	/**
	 * 查询返回单个值方法
	 * @param DataSource dataSource  数据源
	 * @param String	 sql         sql语句
	 * @param Ojbect[]   param       参数数组
	 * @return Object result
	 */
	public Object query (DruidDataSource dataSource,String sql,Object param[]){
		Object result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i+1,param[i] );
			}
			rs = ps.executeQuery();
			while(rs.next()){
				result = rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(DataSourceConfigureFactory.class, e.getMessage());
		}finally{
			this.close(conn, null, ps,rs);
		}
		return result;
	}
	
	/**
	 * 查询返回多个值方法
	 * @param DataSource dataSource  数据源
	 * @param String	 sql         sql语句
	 * @param Ojbect[]   param       参数数组
	 * @return List result
	 */
	public List<Object> queryList (DruidDataSource dataSource,String sql,Object param[]){
		List<Object> list = new ArrayList<Object>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i+1,param[i] );
			}
			rs = ps.executeQuery();
			int i=1;
			while(rs.next()){
				i++;
				if(i==3){
					LoggerUtil.info(DataSourceConfigureFactory.class, "getCpGameId method get two datas,please check the dataBase");
					break;
				}
					
				list.add(rs.getInt(1));
				list.add(rs.getInt(2));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(DataSourceConfigureFactory.class, e.getMessage());
		}finally{
			this.close(conn, null, ps,rs);
		}
		return list;
	}
	/**
	 * 添加数据
	 * @param DataSource dataSource  数据源
	 * @param String	 sql         sql语句
	 * @param Ojbect[]   param       参数数组
	 * @return Integer result
	 */
	public int insert(DruidDataSource dataSource,String sql,Object param[]){
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i+1,param[i] );
			}
			//rs = ps.executeQuery();
			result = ps.executeUpdate();
			if(result > 0){
				conn.commit();
			}else{
				this.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(DataSourceConfigureFactory.class, e.getMessage());
		}finally{
			this.close(conn, null, ps,null);
		}
		return result;
	}
	/**
	 * 调用存储过程Function进行添加数据
	 * @param DataSource   dataSource  数据源
	 * @param String	   funName     存储过程函数名称
	 * @param Object[]     param       调用参数数组
	 * @return Integer
	 */
	public int invokeIntFunction(DruidDataSource dataSource,String funName,Object param[]){
		int result = 0;
		Connection conn = null;
		CallableStatement call = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			call = conn.prepareCall("{?=call "+funName+"}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			for (int i = 0; i < param.length; i++) {
				call.setObject(i+2, param[i]);
			}
			call.execute();
			result = call.getInt(1);
			if(result > 0){
				conn.commit();
			}else{
				this.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), e.toString());
		}finally{
			this.close(conn, call, null, null);
		}
		return result;
	}
}
