package live;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pojo.PageInfo;
import pojo.PreUnit;
import dao.PublicInsertDao;
import dao.PublicSearchDao;
/**
 * Code writer:Caolei
 */
public class GetListingUnitFromDBSavedPages {
	private static int pagenum = 2; // the Page Number to fetch after the First Page
	private static int wholeNum = 0; // the number of items which are inserted into the
								// DB
//	private static MyDBConnection myDB=new MyDBConnection();		
	
	private static PublicInsertDao dao = new PublicInsertDao();
	// root link: http://www.shichengzufang.com/
	// for every page after the first one: http://www.shichengzufang.com/?page=2

	public static void main(String args[]) throws SecurityException,
			IOException {

//		GetListingUnitFromDBSavedPages line = new GetListingUnitFromDBSavedPages();
//		line.start();
		convert();
	}
	
	public static void convert(){
		Document doc = null;

		PublicSearchDao dao = new PublicSearchDao();
		ArrayList<PageInfo> pagelist = null;
		try {
//			pagelist = dao.getAllPageSavedFromDB("334");
			pagelist = dao.getAllPageSavedFromDB(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (PageInfo page : pagelist) {
			doc = Jsoup.parse(page.getSourcecode());
			int flagInt = getOnePageList(doc, page);
 
			System.out.println(page.getId() + " is finished!");
		}
		System.out.println("The whole number of pages insert into the DB is: "
				+ wholeNum);
	} 
	
	public static int getOnePageList(Document doc, PageInfo pageEntity) {

		// ArrayList<PreUnit> list = new ArrayList<PreUnit>();
	
		Elements cells = doc.getElementsByClass("listCell");
		int result = -1;
		for (Element cell : cells) {
			wholeNum++;
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
			}
			try {
				Elements phoneClass = cell.getElementsByClass("span4");
				Element phoneElement = phoneClass.get(0);
				if (phoneElement != null) {
					Elements aElement = phoneElement.getElementsByTag("a");
					Element phoneSonElement = aElement.get(0);
					String phone = phoneSonElement != null ? phoneSonElement
							.text() : null;
					UnitObject.setPhone(phone);
				}
			} catch (java.lang.IndexOutOfBoundsException e) {
			}

			String price = "";
			Elements priceClass = cell.getElementsByTag("span");
			if (priceClass.size() == 4) {
				Element priceElement = priceClass.get(2);

				price = priceElement != null ? priceElement.text() : null;
				UnitObject.setPrice(price);
			}
			
			if(pageEntity.getLink()==null) {System.out.println("!!!!!!!!!!!!!!!!!!"+pageEntity.getId());}
			UnitObject.setPageReferenceId(pageEntity.getId() + "");
			UnitObject.setSourceWeb(pageEntity.getSourceWeb());
			UnitObject.setSourcePageLink(pageEntity.getLink());
//			System.out.println(UnitObject.toString());
 
			// Begin--insert the Unit Object into the DB
			result = dao.insertPreUnit(UnitObject);
			// End--insert the Unit Object into the DB
 
//			result=myDB.insertIntoUnit(UnitObject);
 
		}
		// Begin--insert the Page Object into the DB
		// End--insert the Page Object into the DB
		return result;

	}

}
