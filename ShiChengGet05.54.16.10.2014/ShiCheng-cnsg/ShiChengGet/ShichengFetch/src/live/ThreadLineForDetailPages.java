package live;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pojo.DetailInfo;
import pojo.ImageInfo;
import pojo.PageInfo;
import pojo.PreUnit;
import util.UtilTool;
import dao.PublicInsertDao;
import dao.PublicSearchDao;
import dao.PublicUpdateDao;
/**
 * Code writer:Caolei
 */
public class ThreadLineForDetailPages extends Thread {
	private Logger log = UtilTool.getLogger("ThreadLineForDetailPages.class");
	private String rootlink = "";
	private boolean flag = true;
	// private boolean firstPageFlag=true;
	private int min = 8;
	private int max = 60;
	private int pagenum = 1; // the Page Number to fetch after the First Page
	private int wholeNum = 0; // the number of items which are inserted into the DB
	PageInfo pageObject = new PageInfo();
	private String localPathRoot = "c:\\fetchImages\\shichengBBS";
	PublicInsertDao insertDao = new PublicInsertDao();
	PublicUpdateDao updateDao = new PublicUpdateDao();
	PublicSearchDao selectDao = new PublicSearchDao();

	private int restartHoldTime = 60; // before Restart hold 1 minute

	private boolean holdflag = false; // If it restarts, it will hold for some time to continue;

	public ThreadLineForDetailPages(String link) {
		this.rootlink = link;
	}

	public ThreadLineForDetailPages(String link, int fromPageNumBegin) {
		this.rootlink = link;
		// this.firstPageFlag=false;
		this.pagenum = fromPageNumBegin;
	}

	// root link: http://www.shichengzufang.com/
	// for every page after the first one: http://www.shichengzufang.com/?page=2

	public static void main(String args[]) throws SecurityException, IOException {

		// ThreadLineForListingPage line=new ThreadLineForListingPage("http://www.shichengzufang.com/");
		// the -1 means begin from the id 1
		// the number means begin from >= that id num
		// ThreadLineForDetailPages line = new ThreadLineForDetailPages("http://www.shichengzufang.com", 653);
		ThreadLineForDetailPages line = new ThreadLineForDetailPages("http://www.shichengzufang.com");
		line.RestartFromException();
		// line.start();
	}

	public void RestartFromException() {
		try {
			int lastnum = selectDao.getLastNum();
			holdflag = true; // set the flag to true;
			log.info("Warning!!!: Restart from the last number->" + lastnum);
			ThreadLineForDetailPages line = new ThreadLineForDetailPages("http://www.shichengzufang.com", lastnum);
			line.start();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Restart Wrong!!!");
			log.error(e.getMessage());
			log.error(e.toString());
		}
	}

	public ArrayList<PreUnit> getPageInfoListFromDB(int fromBeginPageNum) {
		Document doc = null;

		PublicSearchDao dao = new PublicSearchDao();
		ArrayList<PreUnit> pagelist = null;
		try {
			pagelist = dao.getAllListingUnitsSavedFromDB(fromBeginPageNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagelist;

		// System.out.println("The whole number of pages insert into the DB is: "
		// + wholeNum);
	}

	public void run() {

		if (holdflag) {
			try {
				System.out.println("restart sleep - 60");
				this.sleep(restartHoldTime * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("RestartHoldTime is broken");
				log.error(e.toString());
			}
		}
		Document doc = null;
		try {
			String linkaddress = "";
			ArrayList<PreUnit> pagelist = getPageInfoListFromDB(pagenum);

			for (PreUnit page : pagelist) {
				linkaddress = rootlink + page.getLink();
				doc = Jsoup.connect(rootlink + page.getLink()).get();

				// begin-set values to the pageInfo object
				pageObject.setListingTableReferenceID(Integer.parseInt(page.getId()));
				pageObject.setSourcecode(doc.toString());
				pageObject.setLink(linkaddress);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String fetchDateTime = df.format(new Date());
				pageObject.setDateTime(fetchDateTime);
				pageObject.setLengthofcode(doc.toString().length());

				Elements elem = doc.getElementsByTag("title");
				Element pagetitle = elem.get(0);
				String title = pagetitle != null ? pagetitle.text() : null;
				pageObject.setSourceWeb(title);
				// end-set values to the pageInfo object

				int flagInt = getOnePageDetailInfo(doc);
				if (flagInt == -1) {
					flag = false;
				} else {
					wholeNum++;
				} // if the program cannot get any thing from the page, stop it.
				int randomNum = (int) Math.round(Math.random() * (max - min) + min);
				// log.info(pageObject);
				System.out.println("sleep: " + randomNum);
				this.sleep(randomNum * 1000);
				updateDao.updateLastNum(Integer.parseInt(page.getId()));
				log.info(("ID: " + page.getId() + " is finished!"));
			}

		} catch (java.net.UnknownHostException e) {
			log.error("The program cannot connect to the server for this page!");
			e.printStackTrace();
			log.error(e.toString());
			flag = false;
			RestartFromException();
		} catch (IOException e) {
			log.error("IOException!");
			log.error(e.toString());
			e.printStackTrace();
			RestartFromException();
		} catch (InterruptedException e) {
			log.error("The thread is interrupted!");
			log.error(e.toString());
			e.printStackTrace();
			RestartFromException();
		}

		log.info("The whole number of items are inserted into the DB is: " + wholeNum);
	}

	public int getOnePageDetailInfo(Document doc) {

		int result = -1;
		int PageInsertResult = -1;
		try {
			DetailInfo infoObject = new DetailInfo();

			infoObject.setLink(pageObject.getLink());

			Elements moduleClassElements = doc.getElementsByClass("module");

			Elements moduleClassDivs = moduleClassElements.get(0).getElementsByTag("div");
			// Get the title
			Element titleAnddetailDiv = moduleClassDivs.get(1);
			Elements titleDiv = titleAnddetailDiv.getElementsByAttributeValue("style", "color: #1155cc;");
			String title = titleDiv != null ? titleDiv.text() : null;
			infoObject.setTitle(title);

			// Get the date,location,source
			Elements tableTags = titleAnddetailDiv.getElementsByTag("table");
			Element tableTag = tableTags.get(0);
			Elements tableTdTags = tableTag.getElementsByTag("td");
			Element dateTd = tableTdTags.get(0);
			Element locationTd = tableTdTags.get(1);
			Element sourceTd = tableTdTags.get(2);
			String date = dateTd != null ? dateTd.text() : null;
			String location = locationTd != null ? locationTd.text() : null;
			String sourceWeb = sourceTd != null ? sourceTd.text() : null;
			infoObject.setDate(date);
			infoObject.setSourceWeb(sourceWeb);
			infoObject.setLocation(location);

			pageObject.setDateofcontent(date);

			// Get the price
			Elements detailPriceDivs = titleAnddetailDiv.getElementsByAttributeValue("style",
					"color:#c00;font-size:x-large;font-weight:bold;");
			String price = detailPriceDivs != null ? detailPriceDivs.text() : null;
			infoObject.setPrice(price);

			// Get the detail information
			Elements detailInfoDivs = titleAnddetailDiv.getElementsByAttributeValue("style", "display: block;");
			Elements detailInfoPtags = detailInfoDivs.get(0).getElementsByTag("p");
			StringBuffer sb = new StringBuffer();
			for (Element elem : detailInfoPtags) {
				sb.append(elem.text() + "\n");
			}
			String detail = sb.toString();
			infoObject.setDetail(detail);

			// Because of the FK, we must insert the page first.
			// Begin--insert the Page Object into the DB
			PageInsertResult = insertDao.insertDetailPageInfo(pageObject);
			// End--insert the Page Object into the DB

			// Get the phone number
			try {
				Elements phonenumDiv = titleAnddetailDiv.getElementsByAttributeValue("style", "line-height: normal;");
				Elements phonenumtags = phonenumDiv.get(0).getElementsByTag("a");
				Element phonenumtag = phonenumtags.get(0);
				String phone = phonenumtag != null ? phonenumtag.text() : null;
				infoObject.setPhone(phone);
			} catch (java.lang.IndexOutOfBoundsException e) {
			}

			result = insertDao.insertUnit(infoObject);

			// get the images from the page
			Elements imgTagElements = doc.getElementsByAttributeValue("class", "thumbnail");
			// Elements imgTagElements=doc.getElementsByTag("img");
			int count = 0;
			for (Element tmpelem : imgTagElements) {
				String piclink = tmpelem.attr("src");
				ImageInfo images = new ImageInfo();
				images.setDetailPageLink(pageObject.getLink());
				images.setPiclink(piclink);

				int TheLastDotPosition = (piclink.lastIndexOf(".") != -1) ? piclink.lastIndexOf(".") : -1;
				if (TheLastDotPosition != -1) {
					String type = piclink.substring(TheLastDotPosition + 1);
					images.setType(type);
					// The image id is related to the listingTableReferenceID column
					images.setLocalpath(localPathRoot + "\\" + pageObject.getListingTableReferenceID() + "\\" + count
							+ "." + images.getType());
					insertDao.insertImage(images);
					count++;
					// System.out.println(images.toString());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(e.toString());
			// RestartFromException();
		}
		return result;

	}

}
