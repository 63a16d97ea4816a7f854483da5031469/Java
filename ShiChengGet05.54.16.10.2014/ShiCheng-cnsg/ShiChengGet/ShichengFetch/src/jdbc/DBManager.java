package jdbc;

import java.io.*;
import java.sql.*;
import java.sql.Statement;
import java.util.*;
import java.util.Date;
import java.beans.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author 
 * @version 1.0
 */

/**
 * DBManager类是对DBConnectionManager类的封装，支持对一个或多个由属性文件定义的数据库连接
 * 池的访问，并可以支持客户程序传送SQL语句，返回操作结果.可以调用初始化函数得到本类的一个实例.
 */
public class DBManager implements Serializable {
	private DBConnectionManager connectionManager_;

	private Connection con_;

	private ResultSet result_set = null;

	private Statement statement = null;

	private String poolName_;

	/**
	 * 从连接池中得到一个连接，赋给con_
	 * 
	 * @param poolName
	 *            连接池名称
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public void connect(String poolName) throws SQLException {
		poolName_ = poolName;
		con_ = connectionManager_.getConnection(poolName_);
		if (con_ == null)
			throw new SQLException("不能和数据库建立连接！");
		if (!con_.getAutoCommit())
			con_.setAutoCommit(true);
	}

	/**
	 * 从连接池中得到数据库的连接，赋给con_
	 * 
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public void connect() throws SQLException {
		connect("database");
	}

	/**
	 * 取连接
	 * 
	 */
	public Connection getConnect() throws SQLException {
		return con_;
	}

	/**
	 * 断开连接，关闭所有连接
	 */
	public void disconnect() throws SQLException {

		try {
			if (result_set != null)
				result_set.close();
			if (statement != null)
				statement.close();
			if (con_ != null)
				connectionManager_.freeConnection(poolName_, con_);
		} catch (SQLException ex1) {
			ex1.printStackTrace();
			throw new SQLException("数据库连接关闭失败！");
		}
	}

	/**
	 * 撤销驱动程序的注册
	 */
	public void release() {
		connectionManager_.release();
	}

	/**
	 * 开始一个事务
	 * 
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public void beginTransaction() throws SQLException {
		if (con_ == null)
			throw new SQLException("不能和数据库建立连接！");
		con_.setAutoCommit(false);
	}

	/**
	 * 提交一个事务
	 * 
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public void commitTransaction() throws SQLException {
		con_.commit();
		con_.setAutoCommit(true);
	}

	/**
	 * 回滚一个事务
	 * 
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public void rollbackTransaction() throws SQLException {
		con_.rollback();
		con_.setAutoCommit(true);
	}

	/**
	 * 对数据库进行查询
	 * 
	 * @param statementString
	 *            执行的SQL语句
	 * @return db_result 执行的结果
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public ResultSet executeQuery(String statementString) throws SQLException {
		// ResultSet result_set = null;
		// java.sql.Statement statement = null;
		// DBResult db_result = new DBResult();
		if (con_ == null)
			throw new SQLException("不能和数据库建立连接！");
		try {
			statement = con_.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result_set = statement.executeQuery(statementString);
			// db_result.create(result_set);
			// statement.close();
		} catch (SQLException x) {
			x.printStackTrace();
			throw new SQLException("数据库操作失败！");
		}
		return result_set;
	}

	/**
	 * 在指定的时间里对数据库进行查询
	 * 
	 * @param statementString
	 *            执行的SQL语句
	 * @param time
	 *            以秒计的等待时间
	 * @return db_result 执行的结果
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public ResultSet executeQuery(String statementString, int time)
			throws SQLException {
		// ResultSet result_set = null;
		// DBResult db_result = new DBResult();
		if (con_ == null)
			throw new SQLException("不能和数据库建立连接！");
		try {
			statement = con_.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result_set = statement.executeQuery(statementString);
			// db_result.create(result_set);
			// statement.close();
		} catch (SQLException x) {
			x.printStackTrace();
			throw new SQLException("数据库操作失败！");
		}
		return result_set;
	}

	/**
	 * 对数据库进行更新，包括insert，delete，update操作
	 * 
	 * @param statementString
	 *            执行的SQL语句
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public int executeUpdate(String statementString) throws SQLException {
		int rows = 0;
		if (con_ == null)
			throw new SQLException("不能和数据库建立连接！");
		try {
			statement = con_.createStatement();
			rows = statement.executeUpdate(statementString);
			// statement.close();
		} catch (SQLException x) {
			x.printStackTrace();
			throw new SQLException("数据库操作失败！");
		}
		return rows;
	}

	/**
	 * 在指定的时间里对数据库进行更新,包括insert，delete，update操作
	 * 
	 * @param statementString
	 *            执行的SQL语句
	 * @param time
	 *            以秒计的等待时间
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public int executeUpdate(String statementString, int time)
			throws SQLException {
		int rows = 0;
		if (con_ == null)
			throw new SQLException("不能和数据库建立连接！");
		try {
			statement = con_.createStatement();
			statement.setQueryTimeout(time);
			rows = statement.executeUpdate(statementString);
			// statement.close();
		} catch (SQLException x) {
			x.printStackTrace();
			throw new SQLException("数据库操作失败！");
		}
		return rows;
	}

	/**
	 * 对数据库进行建立表和删除表操作
	 * 
	 * @param statementString
	 *            执行的SQL语句
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public void execute(String statementString) throws SQLException {
		if (con_ == null)
			throw new SQLException("不能和数据库建立连接！");
		try {
			statement = con_.createStatement();
			statement.execute(statementString);
			// statement.close();
		} catch (SQLException x) {
			x.printStackTrace();
			throw new SQLException("数据库操作失败！");
		}
	}

	/**
	 * 在指定的时间里对数据库建立表和删除表操作
	 * 
	 * @param statementString
	 *            执行的SQL语句
	 * @param time
	 *            以秒计的等待时间
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public void execute(String statementString, int time) throws SQLException {
		if (con_ == null)
			throw new SQLException("不能和数据库建立连接！");
		try {
			statement = con_.createStatement();
			statement.setQueryTimeout(time);
			statement.execute(statementString);
			// statement.close();
		} catch (SQLException x) {
			x.printStackTrace();
			throw new SQLException("数据库操作失败！");
		}
	}

	/**
	 * 构造函数得到DBConnectionManager类的唯一实例
	 */
	public DBManager() {
		connectionManager_ = DBConnectionManager.getInstance();
	}

}
