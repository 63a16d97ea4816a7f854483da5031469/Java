package dao;
/**
 * Code writer:Caolei
 * The Dao is for UserRegist
 * The Dao is for connect the DB and save the user information to the right DB
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jdbc.DBManager;

public class PublicDeleteDao {
	/**
	 * 
	 * @param user
	 * @throws SQLException 
	 */
	public void del(String sql) throws SQLException{//The function to deal with the register
		DBManager db_manager = new DBManager();//New a DBManage and init to connect the DB
		ResultSet result = null; //New a ResultSet to save the result

			db_manager.connect("rcms");//Connect the DB
			db_manager.executeUpdate(sql);//Execute the sql

			System.err.print("æ•°æ�®åº“æ“�ä½œå¤±è´¥ï¼�");
	
	}
 
	public List findAll(){
		return null;
	}

}
