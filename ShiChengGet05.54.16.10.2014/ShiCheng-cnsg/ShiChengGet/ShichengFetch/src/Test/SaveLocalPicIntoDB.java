package Test;

/**
 * Code writer:Caolei
 */
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;  

import util.ImageUtil;
 public class SaveLocalPicIntoDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String user = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/sgcn?characterEncoding=utf-8";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        InputStream inputStream = null;
        inputStream = ImageUtil.getImageByte("c:\\mydownloadtmp.jpg");
        try {
        	String sql = "insert into images(piclink,localpath,detailpagelink,type,content) values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,"link");
			preparedStatement.setString(2, "d:\\");
			preparedStatement.setString(3, "1");
			preparedStatement.setString(4, "png");
			preparedStatement.setBinaryStream(5, inputStream, inputStream.available());
            preparedStatement.execute();
            System.out.println("OK");
        }catch(com.mysql.jdbc.MysqlDataTruncation e){
        	System.out.println("The picture is bigger than the longblob");
        }catch(com.mysql.jdbc.PacketTooBigException e){
        	System.out.println("The picture is bigger than the my.ini setting's threshold!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null)
                        preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } 
     }
}
 