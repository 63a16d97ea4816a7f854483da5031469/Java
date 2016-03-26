#Java Date SimpleDateFormat issue

##issue:

		Date now=Calendar.getInstance().getTime();
		SimpleDateFormat dt1 = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		String dateTime=dt1.format(now);
		

The Date's month over 12 like below:26/33/2016 12:33:53





##solution:

Modified the <font color=red>dd/mm/yyyy</font> to 
<font color=green>dd/MM/yyyy</font>

http://tutorials.jenkov.com/java-date-time/parsing-formatting-dates.html


yyyy-MM-dd           (2009-12-31)

dd-MM-YYYY           (31-12-2009)
    
yyyy-MM-dd HH:mm:ss  (2009-12-31 23:59:59)

HH:mm:ss.SSS         (23:59.59.999)

yyyy-MM-dd HH:mm:ss.SSS   (2009-12-31 23:59:59.999)

yyyy-MM-dd HH:mm:ss.SSS Z   (2009-12-31 23:59:59.999 +0100)        