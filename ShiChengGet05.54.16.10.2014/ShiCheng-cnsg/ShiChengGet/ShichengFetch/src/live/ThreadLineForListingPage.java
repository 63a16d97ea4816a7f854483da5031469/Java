package live;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pojo.PageInfo;
import pojo.PreUnit;
import util.UtilTool;
import dao.PublicInsertDao;
/**
 * Code writer:Caolei
 */
public class ThreadLineForListingPage extends Thread {
	private Logger log=UtilTool.getLogger("ThreadLineForListingPage.class");
	private String rootlink="";
	private boolean flag=true;
	private boolean firstPageFlag=true;
	private int min=8;
	private int max=60;
	private int pagenum=2;	 // the Page Number to fetch after the First Page
	private int wholeNum=0;  //the number of items which are inserted into the DB
	PageInfo pageObject=new PageInfo();
	
	public ThreadLineForListingPage(String link){
		this.rootlink=link;
	}
	
	public ThreadLineForListingPage(String link,int fromPageNumBegin){
		this.rootlink=link;
		this.firstPageFlag=false;
		this.pagenum=fromPageNumBegin;
	}
	//root link: http://www.shichengzufang.com/
	//for every page after the first one: http://www.shichengzufang.com/?page=2
	
	
    public static void main(String args[]) throws SecurityException, IOException {	
    	
//    	ThreadLineForListingPage line=new ThreadLineForListingPage("http://www.shichengzufang.com/");
    	ThreadLineForListingPage line=new ThreadLineForListingPage("http://www.shichengzufang.com/",276);
    	line.start();    	
    }
    
    public void run(){
    	while(flag){
    	Document doc=null;
		try {

			String linkaddress="";
			if(firstPageFlag){
			linkaddress=rootlink;
			doc = Jsoup.connect(linkaddress).get();
			firstPageFlag=false;
		
			}else{
			linkaddress=rootlink+"?page="+pagenum;
			doc = Jsoup.connect(linkaddress).get();
			pagenum++;
			}
			
			//begin-set values to the pageInfo object
			pageObject.setSourcecode(doc.toString());
			pageObject.setLink(linkaddress);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fetchDateTime = df.format(new Date());
			pageObject.setDateTime(fetchDateTime);
			pageObject.setLengthofcode(doc.toString().length());
			
			Elements elem=doc.getElementsByTag("title");
			Element pagetitle=elem.get(0);
			String title=pagetitle!=null?pagetitle.text():null;
			pageObject.setSourceWeb(title);
			//end-set values to the pageInfo object
			
			int flagInt=getOnePageList(doc);
			if(flagInt==-1) {flag=false;}else{wholeNum++;}  //if the program cannot get any thing from the page, stop it.
			int randomNum=(int)Math.round(Math.random()*(max-min)+min);
//			log.info(pageObject);
			log.info((pagenum-1)+" is finished!");
			this.sleep(randomNum*1000);
		} catch (java.net.UnknownHostException e) {
			log.error("The program cannot connect to the server for this page!");
			e.printStackTrace();
			flag=false;
		}catch(IOException e){
			log.error("IOException!");
		} catch (InterruptedException e) {
			log.error("The thread is interrupted!");
			e.printStackTrace();
		}
    	}
    log.info("The whole number of pages insert into the DB is: "+wholeNum);	
    }
    
    
    public int getOnePageList(Document doc){
 
//		ArrayList<PreUnit> list = new ArrayList<PreUnit>();

		Elements cells = doc.getElementsByClass("listCell");
		int result=-1;
		int PageInsertResult=-1;
		for (Element cell : cells) {
			PreUnit UnitObject = new PreUnit();
			Elements titleClass = cell.getElementsByClass("title");
			Element titleElement = titleClass.get(0);
			if (titleElement != null) {
				String link = titleClass.get(0).attr("href");
				String title = titleClass.get(0).text();
				UnitObject.setLink(link);
				UnitObject.setTitle(title);
			}

			Elements dateClass = cell.getElementsByClass("span4");
			Element dateElement = dateClass.get(0);
			if (dateElement != null) {
				Object[] objects = dateElement.textNodes().toArray();
				String date = objects[1].toString();
				UnitObject.setDate(date);
				pageObject.setDateofcontent(date);
			}
			try{
			Elements phoneClass = cell.getElementsByClass("span4");
			Element phoneElement = phoneClass.get(0);
			if (phoneElement != null) {
				Elements aElement = phoneElement.getElementsByTag("a");
				Element phoneSonElement = aElement.get(0);
				String phone = phoneSonElement != null ? phoneSonElement.text() : null;
				UnitObject.setPhone(phone);
			}
			}catch(java.lang.IndexOutOfBoundsException e){
			}
			
			String price = "";
			Elements priceClass = cell.getElementsByTag("span");
			if (priceClass.size() == 4) {
				Element priceElement = priceClass.get(2);

				price = priceElement != null ? priceElement.text() : null;
				UnitObject.setPrice(price);
			}
//			list.add(UnitObject);
			
			UnitObject.setSourceWeb(pageObject.getSourceWeb());
			UnitObject.setSourcePageLink(pageObject.getLink());
			//Begin--insert the Unit Object into the DB
			PublicInsertDao dao=new PublicInsertDao();
			result=dao.insertPreUnit(UnitObject);			
			//End--insert the Unit Object into the DB
 
//			log.info(UnitObject.toString());
 
		}
		//Begin--insert the Page Object into the DB
		PublicInsertDao dao=new PublicInsertDao();
		PageInsertResult=dao.insertListingPageInfo(pageObject);
		//End--insert the Page Object into the DB
		return result;
    	
    }
    
}
