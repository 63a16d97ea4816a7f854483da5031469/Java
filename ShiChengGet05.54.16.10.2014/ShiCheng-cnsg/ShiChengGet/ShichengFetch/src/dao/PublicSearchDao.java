package dao;

/**
 * Code writer :Caolei
 * The Dao is for searchjob
 * The Dao is for connect the DB and return the List
 */
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pojo.PageInfo;
import pojo.PreUnit;
import util.ImageUtil;
import util.UtilTool;

import jdbc.DBManager;

public class PublicSearchDao {
	private Logger log = UtilTool.getLogger("PublicSearchDao.class");

	public ResultSet SelectResult(String sql) throws SQLException { // The function begin
		DBManager db_manager = new DBManager();// New a DBManage object and init it
		ResultSet result = null;// Create the ResultSet object
		// 数据库查询结果

		System.out.println(sql);
		// 表查询操作,返回结果集存在DBResult类中，DBResult中的数据库查询结果已与数据库断开连接了，
		// 不能动态更新，注意在并发操作中应重新执行表查询操作
		db_manager.connect("rcms");// connect the DB
		result = db_manager.executeQuery(sql);// Execute the sql

		return result;// return list
	}

	public ArrayList<PageInfo> getAllPageSavedFromDB(String beginPageNum) throws SQLException {
		ArrayList<PageInfo> list = new ArrayList<PageInfo>();
		ResultSet rs = null;
		if (beginPageNum == null) {
			rs = SelectResult("select * from listingpages");
		} else {
			rs = SelectResult("select * from listingpages where id>=" + beginPageNum);
			log.info("** From " + beginPageNum + " continue!");
		}
		while (rs.next()) {
			PageInfo tmp = new PageInfo();
			tmp.setId(rs.getInt(1));
			tmp.setLink(rs.getString(2));
			tmp.setSourcecode(rs.getString(3));
			tmp.setDateTime(rs.getString(4));
			tmp.setSourceWeb(rs.getString(5));
			tmp.setLengthofcode(Long.parseLong(rs.getString(6)));
			tmp.setDateofcontent(rs.getString(7));
			list.add(tmp);
		}

		return list;
	}

	public static void main(String args[]) {
		PublicSearchDao dao = new PublicSearchDao();
		try {
			System.out.println(dao.getLastNum());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getLastNum() throws SQLException {
		ResultSet rs = null;
		rs = SelectResult("select * from lastRecord");
		int lastnum = -1;
		while (rs.next()) {
			lastnum = rs.getInt(1);
		}

		return lastnum;
	}

	public ArrayList<PreUnit> getAllListingUnitsSavedFromDB(int fromBeginPageNum) throws SQLException {
		ArrayList<PreUnit> list = new ArrayList<PreUnit>();
		ResultSet rs = null;

		if (fromBeginPageNum == -1) {
			rs = SelectResult("select * from listingunits");
		} else {
			rs = SelectResult("select * from listingunits where id>" + fromBeginPageNum);
			// System.out.println("From "+fromBeginPageNum);
			log.info("** From " + fromBeginPageNum + " continue!");
		}
		// System.out.println("From"+beginPageNum);

		while (rs.next()) {
			PreUnit tmp = new PreUnit();
			tmp.setId(rs.getInt(1) + "");
			tmp.setTitle(rs.getString(2));
			tmp.setDate(rs.getString(3));
			tmp.setPhone(rs.getString(4));
			tmp.setPrice(rs.getString(5));
			tmp.setLink(rs.getString(6));
			tmp.setSourceWeb(rs.getString(7));
			tmp.setSourcePageLink(rs.getString(8));
			tmp.setPageReferenceId(rs.getString(9));
			list.add(tmp);
		}

		return list;
	}

	public boolean getPicFromDB() {
		boolean flag = false;

		DBManager db_manager = new DBManager();// New a DBManage object and init it
		try {
			db_manager.connect("rcms");
			String sql = "select * from images limit 1";
			ResultSet resultSet = db_manager.executeQuery(sql);// Execute the sql
			resultSet.next();
			InputStream inputStream = resultSet.getBinaryStream("content");
			ImageUtil.readBlob(inputStream, "D:\\photo211.JPG");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// connect the DB

		return flag;
	}

}
