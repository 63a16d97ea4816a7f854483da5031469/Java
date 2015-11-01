package jdbc;

import java.awt.*;
import javax.swing.JPanel;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.beans.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */


/**
* 管理类DBConnectionManager支持对一个或多个由属性文件定义的数据库连接
* 池的访问.可以调用getInstance()方法访问本类的唯一实例.
*/
public class DBConnectionManager implements Serializable{
  static private DBConnectionManager instance__; // 唯一实例
  static private int clients__;

  private Vector drivers_ = new Vector();
  //change use log
  //  private PrintWriter log_;

  private Hashtable pools_ = new Hashtable();
  private Properties dbProps_= new Properties();

  /**
  * 返回唯一实例.如果是第一次调用此方法,则创建实例
  *
  * @return DBConnectionManager 唯一实例
  */
  static synchronized public DBConnectionManager getInstance() {
    if (instance__ == null) {
       instance__ = new DBConnectionManager();
    }
    clients__++;
    return instance__;
  }

  /**
  * 建构函数私有以防止其它对象创建本类实例
  */
  private DBConnectionManager() {
    init();
  }

  /**
  * 将连接对象返回给由名字指定的连接池
  *
  * @param name 在属性文件中定义的连接池名字
  * @param con 连接对象
  */
  public void freeConnection(String name, Connection con) {
    DBConnectionPool pool = (DBConnectionPool) pools_.get(name);
    if (pool != null) {
       pool.freeConnection(con);
    }
  }

  /**
  * 获得一个可用的(空闲的)连接.如果没有可用连接,且已有连接数小于最大连接数
  * 限制,则创建并返回新连接
  *
  * @param name 在属性文件中定义的连接池名字
  * @return Connection 可用连接或null
  */
  public Connection getConnection(String name) {
    DBConnectionPool pool = (DBConnectionPool) pools_.get(name);
    if (pool != null) {
       return pool.getConnection();
    }
    return null;
  }

  /**
  * 获得一个可用连接.若没有可用连接,且已有连接数小于最大连接数限制,
  * 则创建并返回新连接.否则,在指定的时间内等待其它线程释放连接.
  *
  * @param name 连接池名字
  * @param time 以毫秒计的等待时间
  * @return Connection 可用连接或null
  */
  public Connection getConnection(String name, long time) {
    DBConnectionPool pool = (DBConnectionPool) pools_.get(name);
    if (pool != null) {
       return pool.getConnection(time);
    }
    return null;
  }

  /**
  * 关闭所有连接,撤销驱动程序的注册
  */
  public synchronized void release() {
  // 等待直到最后一个客户程序调用
    if (--clients__!= 0) {
       return;
    }

    Enumeration all_pools = pools_.elements();

    while (all_pools.hasMoreElements()) {
      DBConnectionPool pool = (DBConnectionPool) all_pools.nextElement();
      pool.release();
    }

    Enumeration all_drivers = drivers_.elements();

    while (all_drivers.hasMoreElements()) {

      Driver driver = (Driver) all_drivers.nextElement();

      try {
        DriverManager.deregisterDriver(driver);
        log("撤销JDBC驱动程序 " + driver.getClass().getName()+"的注册");
      }
      catch (SQLException e) {
        log(e, "无法撤销下列JDBC驱动程序的注册: " + driver.getClass().getName());
      }
    }
  }

  /**
  * 根据指定属性创建连接池实例.
  *
  * @param props 连接池属性
  */
  private void createPools(Properties props) {
    Enumeration prop_names = props.propertyNames();
    while (prop_names.hasMoreElements()) {
      String name = (String) prop_names.nextElement();
      if (name.endsWith(".url")) {
        String pool_name = name.substring(0, name.lastIndexOf("."));
        String url = props.getProperty(pool_name + ".url");
        if (url == null) {
          log("没有为连接池" + pool_name + "指定URL");
          continue;
        }
        String user = props.getProperty(pool_name + ".user");
        String password = props.getProperty(pool_name + ".password");
        String max_conn = props.getProperty(pool_name + ".maxconn", "0");
        int max;
        try {
          max = Integer.valueOf(max_conn).intValue();
        }
        catch (NumberFormatException e) {
          log("错误的最大连接数限制: " + max_conn + " .连接池: " + pool_name);
          max = 10;
        }
        DBConnectionPool pool =
        new DBConnectionPool(pool_name, url, user, password, max);
        pools_.put(pool_name, pool);
        log("成功创建连接池" + pool_name);
      }
    }
  }

  /**
  * 读取属性完成初始化
  */
  private void init() {
    InputStream is =null;
//        getClass().getResourceAsStream("doc/db.properties");
    try {
//      File file=new File("./config/db.properties");
//      is =new FileInputStream(file);
      is =getClass().getResourceAsStream("db.properties");;
      dbProps_.load(is);
      is.close();
    }
    catch (Exception e) {
      System.err.println("不能读取属性文件. " +
      "请确保db.properties在CLASSPATH指定的路径中"+e.getMessage());
      return;
    }
//    String log_file = dbProps_.getProperty("logfile", "DBConnectionManager.log");
    try {
      //log_ = new PrintWriter(new FileWriter(log_file, true), true);
    }
    catch (Exception e) {
      System.err.println("无法打开日志文件 ");
//      log_ = new PrintWriter(System.err);
    }
    loadDrivers(dbProps_);
    createPools(dbProps_);
  }

  /**
  * 装载和注册所有JDBC驱动程序
  *
  * @param props 属性
  */
  private void loadDrivers(Properties props) {
    String driver_classes = props.getProperty("drivers");
    StringTokenizer st = new StringTokenizer(driver_classes);
    while (st.hasMoreElements()) {
      String driver_class_name = st.nextToken().trim();
      try {
        Driver driver = (Driver)
        Class.forName(driver_class_name).newInstance();
        DriverManager.registerDriver(driver);
        drivers_.addElement(driver);
        log("成功注册JDBC驱动程序" + driver_class_name);
      }
      catch (Exception e) {
        log("无法注册JDBC驱动程序: " +
        driver_class_name + ", 错误: " + e);
      }
    }
  }


  /**
  * 将文本信息写入日志文件
  */
  private void log(String msg) {
//    log_.println(new Date() + ": " + msg);
    System.out.println(new Date() + ": " + msg);
  }

  /**
  * 将文本信息与异常写入日志文件
  */
  private void log(Throwable e, String msg) {
    System.out.println(" error:"+e.getMessage() +" msg=" + msg);
//    log_.println(new Date() + ": " + msg);
//    e.printStackTrace(log_);
  }

  /**
  * 返回当前连接数
  */
  private int getCurConnNum(){
    return clients__;
  }

  /**
  * 此内部类定义了一个连接池.它能够根据要求创建新连接,直到预定的最
  * 大连接数为止.在返回连接给客户程序之前,它能够验证连接的有效性.
  */
  class DBConnectionPool {
    private int checkedOut_;
    private Vector freeConnections_ = new Vector();
    private int maxConn_;
    private String name_;
    private String password_;
    private String URL_;
    private String user_;

    /**
    * 创建新的连接池
    *
    * @param name 连接池名字
    * @param URL 数据库的JDBC URL
    * @param user 数据库帐号,或 null
    * @param password 密码,或 null
    * @param maxConn 此连接池允许建立的最大连接数
    */
    public DBConnectionPool(String name, String URL, String user, String password,
    int maxConn) {
      this.name_ = name;
      this.URL_ = URL;
      this.user_ = user;
      this.password_ =password ;
      this.maxConn_ = maxConn;
    }

    /**
    * 将不再使用的连接返回给连接池
    *
    * @param con 客户程序释放的连接
    */
    public synchronized void freeConnection(Connection con) {
    // 将指定连接加入到向量末尾
      freeConnections_.addElement(con);
      checkedOut_--;
      notifyAll();
    }

    /**
    * 从连接池获得一个可用连接.如没有空闲的连接且当前连接数小于最大连接
    * 数限制,则创建新连接.如原来登记为可用的连接不再有效,则从向量删除之,
    * 然后递归调用自己以尝试新的可用连接.
    */
    public synchronized Connection getConnection() {
      Connection con = null;
      if (freeConnections_.size() > 0) {
      // 获取向量中第一个可用连接
        con = (Connection) freeConnections_.firstElement();
        freeConnections_.removeElementAt(0);
        try {
          if (con.isClosed()) {
            log("从连接池" + name_+"删除一个无效连接");
            // 递归调用自己,尝试再次获取可用连接
            con = getConnection();
          }
        }
        catch (SQLException e) {
          log("从连接池" + name_+"删除一个无效连接");
          // 递归调用自己,尝试再次获取可用连接
          con = getConnection();
        }
        catch(Exception e){
          log("从连接池" + name_+"删除一个无效连接");
          // 递归调用自己,尝试再次获取可用连接
          con = getConnection();
        }
      }
      else if (maxConn_ == 0 || checkedOut_ < maxConn_) {
        con = newConnection();
      }
      if (con != null) {
        checkedOut_++;
      }
      return con;
    }

    /**
    * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间
    * 参见前一个getConnection()方法.
    *
    * @param timeout 以毫秒计的等待时间限制
    */
    public synchronized Connection getConnection(long timeout) {
      long start_time = new Date().getTime();
      Connection con;
      while ((con = getConnection()) == null) {
        try {
          wait(timeout);
        }
        catch (InterruptedException e) {}
          if ((new Date().getTime() - start_time) >= timeout) {
          // wait()返回的原因是超时
          return null;
        }
      }
      return con;
    }

    /**
    * 关闭所有连接
    */
    public synchronized void release() {
      Enumeration all_connections = freeConnections_.elements();
      while (all_connections.hasMoreElements()) {
        Connection con = (Connection) all_connections.nextElement();
        try {
          con.close();
          log("关闭连接池" + name_+"中的一个连接");
        }
          catch (SQLException e) {
          log(e, "无法关闭连接池" + name_+"中的连接");
        }
      }
      freeConnections_.removeAllElements();
    }

    /**
    * 创建新的连接
    */
    private Connection newConnection() {
      Connection con = null;
      try {
        if (user_ == null) {
        con = DriverManager.getConnection(URL_);
      }
      else {
        con = DriverManager.getConnection(URL_, user_, password_);
      }
        log("连接池" + name_+"创建一个新的连接");
      }
      catch (SQLException e) {
        log(e, "无法创建下列URL的连接: " + URL_);
        return null;
      }
      catch(Exception e){
        log(e, "无法创建下列URL的连接: " + URL_);
        return null;
      }
      return con;
    }
  }
}
