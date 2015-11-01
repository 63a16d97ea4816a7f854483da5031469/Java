package Test;
/**
 * Code writer:Caolei
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadPicAndSaveIntoDB {
	public static void main(String args[]) {
	   DownloadPicAndSaveIntoDB download=new DownloadPicAndSaveIntoDB();
	   System.out.println(download.downloadPicFromUrl("http://www3.ntu.edu.sg/thinfilms/thinfilms/ThinFilms2014-photos/fullpic/DSC_6294.JPG","d:\\outpic.jpg"));
	}
	
	public boolean downloadPicFromUrl(String url,String picPathAndName){
		boolean flag=false;
		URL urlObject=null;
		try {
			urlObject = new URL(url);
			File outFile = new File(picPathAndName);
			OutputStream os = new FileOutputStream(outFile);
			InputStream is = urlObject.openStream();
			byte[] buff = new byte[1024];
			while (true) {
				int readed = is.read(buff);
				if (readed == -1) {
					break;
				}
				byte[] temp = new byte[readed];
				System.arraycopy(buff, 0, temp, 0, readed);
				os.write(temp);
			}
			is.close();
			os.close();
			flag=true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			flag=false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			flag=false;
		} catch (IOException e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
		
}
