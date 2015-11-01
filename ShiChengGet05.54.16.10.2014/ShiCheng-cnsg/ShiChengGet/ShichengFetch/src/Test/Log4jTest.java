package Test;
/**
 * Code writer:Caolei
 */
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import util.UtilTool;

public class Log4jTest {

	public static void main(String[] args) {
//		// 1. create log
//		Logger log = Logger.getLogger("Log4jTest.class");
//		// 2. get log config file
//		PropertyConfigurator.configure("log4j.properties");
//		// 3. start log
		Logger log = UtilTool.getLogger("log");
		log.debug("Here is some DEBUG");
		log.info("Here is some INFO");
		Logger log2=UtilTool.getLogger("fdf");
		log2.warn("Here is some WARN");
		log2.error("Here is some ERROR");
		log2.fatal("Here is some FATAL");

	}

}
