package live;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pojo.PreUnit;
import util.UtilTool;
/**
 * Code writer:Caolei
 */
public class GetDataFromListing {
	private Logger log=UtilTool.getLogger("GetDataFromListing.class");
	public static void main(String args[]) {
 
		
		File input = new File("d:\\wrong.html");
		Document doc = null;
		try {
			doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		ArrayList<PreUnit> list = new ArrayList<PreUnit>();

		Elements cells = doc.getElementsByClass("listCell");

		for (Element cell : cells) {
			PreUnit UnitObject = new PreUnit();
			Elements titleClass = cell.getElementsByClass("title");
			Element titleElement = titleClass.get(0);
			if (titleElement != null) {
				String link = titleClass.get(0).attr("href");
				String title = titleClass.get(0).text();
				UnitObject.setLink("http://www.shichengzufang.com" + link);
				UnitObject.setTitle(title);
			}

			Elements dateClass = cell.getElementsByClass("span4");
			Element dateElement = dateClass.get(0);
			if (dateElement != null) {
				Object[] objects = dateElement.textNodes().toArray();
				String date = objects[1].toString();
				UnitObject.setDate(date);
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
			list.add(UnitObject);
			// PublicInsertDao dao=new PublicInsertDao();
 
			System.out.println(UnitObject.toString());

		}
	}
}
