package util;
/**
 * Code writer:Caolei
 */
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import dao.PublicInsertDao;

public class UtilTool {
  public static Logger getLogger(String classname){
		//Begin-for log
		PropertyConfigurator.configure("log4j.properties");
		Logger log = Logger.getLogger(classname);
		//End-for log	  
	  return log;
  }
}
