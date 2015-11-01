package Test;
/**
 * Code writer:Caolei
 */
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestGet {
  public static void main(String args[]){
	  Document doc=null;
		try {
			doc = Jsoup.connect("http://www3.nationproperty.com.sg/npp/Search_Result_TransactedPrivate.aspx?SearchType=Property&Ref=&SearchSubType=CAVEAT&fieldStreet=&fieldBuilding=&fieldDistrictZone=&fieldDistrict1=&fieldDistrict2=&fieldDistrict3=&fieldNewPostal=&fieldOldPostal=&fieldPropType=&fieldUnitType=&fieldTenure=&fieldLandAreaFr=&fieldLandAreaTo=&fieldLandPSFFr=&fieldLandPSFTo=&fieldFlrAreaFr=&fieldFlrAreaTo=&fieldFlrPSFFr=&fieldFlrPSFTo=&fieldContDateFr=&fieldContDateTo=&fieldView=Table").get();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br=null;
		BufferedWriter bw=null;
		BufferedReader br1=null;
		try {
//			 br=new BufferedReader(new InputStreamReader(System.in));//å­—ç¬¦è¾“å…¥æµ� ä»Žé”®ç›˜è¯»å�–æ•°æ�®
			 bw=new BufferedWriter(new FileWriter("D:\\title.txt"));//å­—ç¬¦è¾“å‡ºæµ�  å†™æ•°æ�®åˆ°æ–‡ä»¶
//			 br1=new BufferedReader(new FileReader("D:/temp/title.txt"));//å­—ç¬¦è¾“å…¥æµ� ä»Žæ–‡ä»¶è¯»å�–æ•°æ�®
//			String str=br.readLine();
			bw.write(doc.toString());
			bw.flush();
//			System.out.println(br1.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
//				br.close();
				bw.close();
//				br1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	 
  }
}
