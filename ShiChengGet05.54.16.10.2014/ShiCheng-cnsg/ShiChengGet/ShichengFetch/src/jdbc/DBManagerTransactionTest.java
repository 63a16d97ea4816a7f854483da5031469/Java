package jdbc;

import java.sql.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */

public class DBManagerTransactionTest {

	public DBManagerTransactionTest() {
	}

	public static void main(String[] args) {
		DBManagerTransactionTest DBManagerTransactionTest1 = new DBManagerTransactionTest();
		DBManager db_manager = new DBManager();
		int i = 1;
		try {
			db_manager.connect("mydb");
			db_manager.beginTransaction();
			db_manager
					.executeUpdate("insert  TABLE1 (c1,c2,c3) values('workflow1','engine1','domain1')");
			db_manager
					.executeUpdate("insert  TABLE1 (c1,c2,c3) values('workflow2','engine2','domain2')");
			db_manager
					.executeUpdate("insert  TABLE1 (c1,c2,c3) values('workflow3','engine3','domain3')");
			db_manager
					.executeUpdate("insert  TABLE1 (c1,c2,c3) values('workflow4','engine4','domain4')");
			db_manager
					.executeUpdate("insert  TABLE1 (c1,c2,c3) values('workflow5','engine5','domain5')");
			db_manager
					.execute("update  TABLE1 set c1='workflow19' where c2 = 'engine1'");
			if (i != 1) {
				db_manager
						.execute("update  TABLE1 set c1='workflow20' where c2 = 'engine1'");
				db_manager.commitTransaction();
			} else {
				db_manager
						.execute("update  TABLE1 set c1='workflow21' where c2 = 'engine1'");
				db_manager.rollbackTransaction();
			}
		} catch (SQLException x) {
			System.out.println("数据库操作失败！！！");
		} finally {
			try {
				db_manager.disconnect();
			} catch (SQLException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}
	}
}
