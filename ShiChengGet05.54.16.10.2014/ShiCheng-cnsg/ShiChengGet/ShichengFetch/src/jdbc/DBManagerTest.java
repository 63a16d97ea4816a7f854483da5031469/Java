package jdbc;

import java.sql.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 
 * @version 1.0
 */

/**
 * DBManager示例程序
 */
public class DBManagerTest {

	public DBManagerTest() {
	}

	public static void main(String[] args) {
		DBManagerTest DBManagerTest1 = new DBManagerTest();
		DBManager db_manager = new DBManager();
		ResultSet result = null; // 数据库查询结果
		try {

			db_manager.connect("rcms");
			// 建表
			db_manager
					.execute("create table table2 (c1 varchar(32) not null,c2 varchar(21))");

		} catch (SQLException x) {
			x.printStackTrace();
			try {
				db_manager.disconnect();
			} catch (SQLException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
			System.err.print("数据库操作失败！");
		}
		try {

			// 表更新操作，包括insert,update,delete
			db_manager
					.executeUpdate("insert into table2 (c1,c2) values('workflow1','engine1')");
			db_manager
					.executeUpdate("insert into table2 (c1,c2) values('workflow2','engine2')");
			db_manager
					.executeUpdate("insert into table2 (c1,c2) values('workflow3','engine3')");
			db_manager
					.executeUpdate("insert into table2 (c1,c2) values('workflow4','engine4')");
			db_manager.beginTransaction();
//			for(int i=1500;i<2000;i++){
////			db_manager.executeUpdate("insert into T_USER (USER_ID, USER_NAME, ORG_ID, PASSWD, OLD_PASSWD, STATION, CREAT_DATE, CREAT_TIME, ALTER_DATE, ALTER_TIME, DEL_DATE, TEL, EMAIL, STATUS, SEX, ACADEMIC, CERTI_TYPE, CERTI, FAX, ADRESS, POSTCODE, BAK1, BAK2, BAK3, BAK4, BAK5)values ('test00"+i+"', 'test00"+i+"', '0001      ', 'FF5E61835C355E755EEF9321        ', 'A43B59E342F86CEE5EEF9321        ', '0     ', '20071201', '101010', null, null, null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null)");
//			db_manager.executeUpdate("insert into T_USER_ROLE values('test00"+i+"','sys_admin','')");
//			if(i%300==0)db_manager.commitTransaction();
//			}
			System.exit(0);
		} catch (SQLException x) {
			x.printStackTrace();
			try {
				db_manager.disconnect();
			} catch (SQLException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
			System.err.print("数据库操作失败！");
		}
		try {
			// 表查询操作,返回结果集存在DBResult类中，DBResult中的数据库查询结果已与数据库断开连接了，
			// 不能动态更新，注意在并发操作中应重新执行表查询操作
			result = db_manager.executeQuery("select * from table2");
		} catch (SQLException x) {
			x.printStackTrace();
			try {
				db_manager.disconnect();
			} catch (SQLException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
			System.err.print("数据库操作失败！");
		}
		// int rows = result.getRows(); //返回的记录数
		try {
			while (result.next()) {

				String s = result.getString("c1");// 取第4条记录c1的字段
				System.out.print(s);
				s = result.getString("c2");
				System.out.print(s);

			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		try {
			// 删表
			db_manager.execute("drop table table2");
		} catch (SQLException x) {
			x.printStackTrace();
			try {
				db_manager.disconnect();
			} catch (SQLException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
			System.err.print("数据库操作失败！");
		}

		try {
			db_manager.disconnect();
			db_manager.release();
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
