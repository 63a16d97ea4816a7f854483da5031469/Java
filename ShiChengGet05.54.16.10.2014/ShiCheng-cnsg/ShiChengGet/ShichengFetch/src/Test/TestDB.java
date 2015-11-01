package Test;
/**
 * Code writer:Caolei
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.PublicSearchDao;

public class TestDB {
	public static void main(String args[]){
		PublicSearchDao dao=new PublicSearchDao();
		try {
		ResultSet result=dao.SelectResult("select * from customer");
		while(result.next()){
			System.out.println(result.getString(2));
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
