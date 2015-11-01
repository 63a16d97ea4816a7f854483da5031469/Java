package dao;

/**
 * Code writer:Caolei
 * The Dao is for UserRegist
 * The Dao is for connect the DB and save the user information to the right DB
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import jdbc.DBManager;

public class PublicUpdateDao {

	private DBManager db_manager = new DBManager();// New a DBManage and init to
	// connect the DB
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	private String insertPicSql = "insert into images(piclink,localpath,detailpagelink,type,content) values(?,?,?,?,?)";
 
	public PublicUpdateDao() {
		// Begin-for log
		Logger log = Logger.getLogger(PublicUpdateDao.class);
		PropertyConfigurator.configure("log4j.properties");

		try {
			db_manager.connect("rcms"); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// Connect the DB
		// End-for log
	}

	public int update(String sql) throws SQLException {// The function to deal
		// with the register
 
		int exeresult = db_manager.executeUpdate(sql);// Execute the sql

		return exeresult;
	}
	
	public static void main(String args[]){
		PublicUpdateDao dao=new PublicUpdateDao();
		int result=0;
		while(result!=-1){
		result=dao.updateLastNum(1);
		}
	}
 

	public int updateLastNum(int num) {
		int result = -1;

		try {
			String sql = "update lastRecord set lastNum=" + num ;
			System.out.println(sql);
			result = update(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}

}
