package Test;
/**
 * Code writer:Caolei
 */

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class LogTest {

	public static void main(String[] args) throws IOException{
		Logger log = Logger.getLogger("LogTest");
		log.setLevel(Level.ALL);
		FileHandler fileHandler = new FileHandler("runninglog"+1+".log");
		fileHandler.setLevel(Level.ALL);
		fileHandler.setFormatter(new LogFormatter());
		log.addHandler(fileHandler);
		log.info("This is test java util log");   
	}

}

class LogFormatter extends Formatter {
	@Override
	public String format(LogRecord record) {
		Date date = new Date();
		String sDate = date.toString();
		return "[" + sDate + "]" + "[" + record.getLevel() + "]"
				+ "["+record.getLoggerName() +"] --> ["+ record.getMessage()+"]" + "\n";
	}

}