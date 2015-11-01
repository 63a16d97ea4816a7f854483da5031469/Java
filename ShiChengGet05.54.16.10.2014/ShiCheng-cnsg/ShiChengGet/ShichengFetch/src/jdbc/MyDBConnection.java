package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pojo.PreUnit;

public class MyDBConnection {
	private String user = "root";
	private String password = "123456";
	private String url = "jdbc:mysql://localhost:3306/live?characterEncoding=utf-8";
	private Connection connection = null;
	private Statement stmt = null;

	public MyDBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
			stmt = connection.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		MyDBConnection myown = new MyDBConnection();
		myown.select();

	}

	public int insertIntoUnit(PreUnit unit) {
		int result=-1;
		String sql = "insert into listingunits(title,date,phone,price,link,sourceWeb,sourcePageLink,pageReferenceId) values('"
				+ unit.getTitle().replace("'", " ")
				+ "','"
				+ unit.getDate()
				+ "','"
				+ unit.getPhone()
				+ "','"
				+ unit.getPrice()
				+ "','"
				+ unit.getLink()
				+ "','"
				+ unit.getSourceWeb()
				+ "','"
				+ unit.getSourcePageLink()
				+ "','"
				+ unit.getPageReferenceId()
				+ "')";

		try {

			result=stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void select() {

		String sql = "select * from listingunits";

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString(3));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
