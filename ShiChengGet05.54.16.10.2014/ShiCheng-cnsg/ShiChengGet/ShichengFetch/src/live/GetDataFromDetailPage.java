package live;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dao.PublicInsertDao;

import pojo.DetailInfo;
import pojo.ImageInfo;
import pojo.PageInfo;
import util.UtilTool;
/**
 * Code writer:Caolei
 */
public class GetDataFromDetailPage {
	private Logger log=UtilTool.getLogger("GetDataFromDetailPage.class");
	private static String localPathRoot="g:\\fetchImages";
	
	public static void main(String args[]) {
		//Begin-for log
		Logger log = Logger.getLogger(GetDataFromDetailPage.class);
		PropertyConfigurator.configure("log4j.properties");
		//End-for log
		
		String link = "d:\\haspicture.html";
//		String link ="d:\\nopicture.html";
		File input = new File(link);
		Document doc = null;
		try {
			doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
 

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fetchDateTime = df.format(new Date());

		PageInfo pageObject = new PageInfo();
		pageObject.setSourcecode(doc.toString());
		pageObject.setLengthofcode(pageObject.getSourcecode().toString().length());
		pageObject.setLink(link);
		pageObject.setDateTime(fetchDateTime);

		DetailInfo infoObject = new DetailInfo();
		infoObject.setLink(link);
//		System.out.println(link);
		Elements moduleClassElements = doc.getElementsByClass("module");

		Elements moduleClassDivs = moduleClassElements.get(0).getElementsByTag("div");
		// Get the title
		Element titleAnddetailDiv = moduleClassDivs.get(1);
		Elements titleDiv = titleAnddetailDiv.getElementsByAttributeValue("style", "color: #1155cc;");
		String title = titleDiv != null ? titleDiv.text() : null;
		infoObject.setTitle(title);

		
		//Get the price
 
		Elements detailPriceDivs = titleAnddetailDiv.getElementsByAttributeValue("style", "color:#c00;font-size:x-large;font-weight:bold;");
 		String price=detailPriceDivs!=null?detailPriceDivs.text():null;
		infoObject.setPrice(price);
		

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
		pageObject.setSourceWeb(sourceWeb);


		// Get the detail information
		Elements detailInfoDivs = titleAnddetailDiv.getElementsByAttributeValue("style", "display: block;");
		Elements detailInfoPtags = detailInfoDivs.get(0).getElementsByTag("p");
		StringBuffer sb = new StringBuffer();
		for (Element elem : detailInfoPtags) {
			sb.append(elem.text() + "\n");
		}
		String detail = sb.toString();
		infoObject.setDetail(detail);

		// Get the phone number
		Elements phonenumDiv = titleAnddetailDiv.getElementsByAttributeValue("style", "line-height: normal;");
		Elements phonenumtags = phonenumDiv.get(0).getElementsByTag("a");
		Element phonenumtag = phonenumtags.get(0);
		String phone = phonenumtag != null ? phonenumtag.text() : null;
		infoObject.setPhone(phone);
		
		
		//get the images from the page		
		Elements imgTagElements=doc.getElementsByAttributeValue("class", "thumbnail");
//		Elements imgTagElements=doc.getElementsByTag("img");
		int count=0;
		for(Element tmpelem:imgTagElements){	
		String piclink=tmpelem.attr("src");		
		ImageInfo images=new ImageInfo();
		images.setDetailPageLink(pageObject.getLink());
		images.setPiclink(piclink);

 
		int TheLastDotPosition= (piclink.lastIndexOf(".")!=-1)? piclink.lastIndexOf("."):-1;
		if(TheLastDotPosition!=-1){
		String type=piclink.substring(TheLastDotPosition+1);
		images.setType(type);
		images.setLocalpath(localPathRoot+"\\"+pageObject.getId()+"\\"+count+"."+images.getType());
//		System.out.println(images);
//		System.out.println(images.toString());
		}
		}	
//		log.info(infoObject);

//		 System.out.println(pageObject);

		 System.out.println(infoObject);
 
		PublicInsertDao dao=new PublicInsertDao();
		
		dao.insertUnit(infoObject);
 
	}
}
