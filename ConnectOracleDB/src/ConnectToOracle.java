import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectToOracle {
	public static final String DBid = "";
	public static final String DBport = "";
	public static final String DBhost = "";
	public static final String USER = "";
	public static final String PWD = "";

	public static String username = "";
	public static String password = "";

	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@" + DBhost + ":" + DBport + ":" + DBid;

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public Connection getCon() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(URL, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;
	}

	// public static void main(String args[]){
	// ConnectToOracle mycon=new ConnectToOracle();
	// mycon.getCon();
	//
	// ResultSet rs=mycon.query("select * from dba_users where username='RGT_Work'");
	// try {
	// while(rs.next()){
	// System.out.println(rs.getString(1));
	// System.out.println(rs.getString(2));
	// System.out.println(rs.getString(3));
	// System.out.println();
	// }
	//
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public static void main(String args[]) {
		ConnectToOracle connObj = new ConnectToOracle();
		connObj.getCon();
		String type = connObj.getUserType("sgt_maint".toUpperCase());
		System.out.println(type);
		connObj.closeAll();
	}

	public String getUserType(String username) {

		String sql = "SELECT TYPE# FROM SYS.USER$ WHERE NAME='" + username + "'";
		ResultSet r = query(sql);
		String type = "NULL";
		try {
			if (r.next()) {
				type = r.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return type;
	}

	public void closeAll() {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	public boolean checkTheAccount(String username, String password) {

		username = username.trim();
		password = password.trim();

		Connection myconn = this.getCon();
		if (myconn == null)
			return false;
		else
			return true;

	}

	public ResultSet query(String sql) {
		con = getCon();
		try {
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

}
