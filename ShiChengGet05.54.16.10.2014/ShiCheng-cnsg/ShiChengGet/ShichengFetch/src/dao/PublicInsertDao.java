package dao;

/**
 * Code writer:Caolei
 * The Dao is for UserRegist
 * The Dao is for connect the DB and save the user information to the right DB
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import pojo.DetailInfo;
import pojo.ImageInfo;
import pojo.PageInfo;
import pojo.PreUnit;
import util.ImageUtil;
import util.UtilTool;

import jdbc.DBManager;
import live.GetDataFromListing;

public class PublicInsertDao {
	private Logger log = UtilTool.getLogger("PublicInsertDao.class");
	private DBManager db_manager = new DBManager();// New a DBManage and init to
													// connect the DB
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	private String insertPicSql = "insert into images(piclink,localpath,detailpagelink,type,content) values(?,?,?,?,?)";

	/**
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public PublicInsertDao() {
		try {
			db_manager.connect("rcms");
			connection = db_manager.getConnect();
			preparedStatement = connection.prepareStatement(insertPicSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// Connect the DB
	}

	public int insert(String sql) throws SQLException {// The function to deal
														// with the register

		ResultSet result = null; // New a ResultSet to save the result

		int exeresult = db_manager.executeUpdate(sql);// Execute the sql

		return exeresult;

	}

	public int insertPreUnit(PreUnit unit) {
		int result = -1;
		try {
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
					+ "','" + unit.getPageReferenceId() + "')";
			// System.out.println(sql);
			result = insert(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}

	public int insertUnit(DetailInfo unit) {
		int result = -1;
		try {
			String sql = "insert into detailunits(title,date,phone,price,link,detail,location) values('"
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
					+ unit.getDetail().replace("'", " ") 
					+ "','"
					+ unit.getLocation()
					+ "')";
			// System.out.println(sql);
			result = insert(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}

	public int insertListingPageInfo(PageInfo page) {
		int result = -1;

		try {
			String sql = "insert into listingpages(link,sourcecode,dateTime,sourceWeb,Lengthofcode,dateofcontent) values('"
					+ page.getLink()
					+ "','"
					+ page.getSourcecode().replace("'", "~")
					+ "','"
					+ page.getDateTime()
					+ "','"
					+ page.getSourceWeb()
					+ "','"
					+ page.getLengthofcode()
					+ "','"
					+ page.getDateofcontent()
					+ "')";
			// System.out.println(sql);
			result = insert(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	
	public int insertDetailPageInfo(PageInfo page) {
		int result = -1;

		try {
			String sql = "insert into detailpages(link,sourcecode,dateTime,sourceWeb,Lengthofcode,dateofcontent,listingTableReferenceID) values('"
					+ page.getLink()
					+ "','"
					+ page.getSourcecode().replace("'", "~")
					+ "','"
					+ page.getDateTime()
					+ "','"
					+ page.getSourceWeb()
					+ "','"
					+ page.getLengthofcode()
					+ "','"
					+ page.getDateofcontent()
					+"','"
					+page.getListingTableReferenceID()
					+ "')";
			// System.out.println(sql);
			result = insert(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	 
 
	public static void main(String args[]) {
		PublicInsertDao dao = new PublicInsertDao();
		ImageInfo picobj=new ImageInfo();
		picobj.setPiclink("piclink");
		picobj.setLocalpath("g:\\test\\0\\1.jpg");
		picobj.setPiclink("http://www3.ntu.edu.sg/thinfilms/thinfilms/ThinFilms2014-photos/fullpic/DSC_6294.JPG");
 
		dao.insertImage(picobj);
	}

	public int insertImage(ImageInfo imageObject) {

		URL urlObject = null;
		try {
			String outputPathAndFileNameAndType=imageObject.getLocalpath();
			String outputFolderPath=outputPathAndFileNameAndType.substring(0, outputPathAndFileNameAndType.lastIndexOf("\\"));
//			System.out.println(outputFolderPath);
			urlObject = new URL(imageObject.getPiclink());
			InputStream is = urlObject.openStream();
			File outFile = new File(outputPathAndFileNameAndType);
			//Begin-create the parent and son folder
			File outFileFolder = new File(outputFolderPath);
			if(!outFileFolder.exists()){			
				outFileFolder.mkdirs();
			}
			//End-create the parent and son folder
			OutputStream os = new FileOutputStream(outFile);
 
			byte[] buff = new byte[1024];
			while (true) {
				int readed = is.read(buff);
				if (readed == -1) {
					break;
				}
				byte[] temp = new byte[readed];
				System.arraycopy(buff, 0, temp, 0, readed);
				os.write(temp);
			}
			is.close();
			os.close();
			//sql order:  piclink,localpath,detailpagelink,type,content
			InputStream inputStream = ImageUtil.getImageByte(outputPathAndFileNameAndType);
 
				preparedStatement.setString(1, imageObject.getPiclink());
				preparedStatement.setString(2, imageObject.getLocalpath());
				preparedStatement.setString(3, imageObject.getDetailPageLink());
				preparedStatement.setString(4, imageObject.getType());
				preparedStatement.setBinaryStream(5, inputStream, inputStream
						.available());
				preparedStatement.execute();
				
				System.out.println("OK>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		return 0;
	}

	public List findAll() {
		return null;
	}

}
