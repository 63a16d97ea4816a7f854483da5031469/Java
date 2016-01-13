import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeMap;

public class TestMain {
	
	private static byte nonLRC = 0x0D;
	private static byte prefix = 0x02;
	private static byte suffix = 0x03;
	
	public static void main(String args[]) {
		
		
		
		
		String middleMessage="fds";
		
		byte[] sending=new byte[middleMessage.length()+3];
		sending[0]=prefix;
		byte[] middleByte=middleMessage.getBytes();
		for(int i=0;i<middleByte.length;i++){
			sending[i+1]=middleByte[i];
		}
		
		sending[sending.length-2]=suffix;
		sending[sending.length-1]=nonLRC;

		
		for(byte tmp:sending){
			System.out.print(tmp+" ");
		}
		
		
		
		

		byte[] message = new byte[] { 0x02, (byte) 0xB3, 0x43, 0x4E, (byte) 0xB3, 0x31, (byte) 0xB3, 0x45, (byte) 0xB3,
				0x31, 0x30, 0x31, (byte) 0xB3, 0x10,  };
		String str2 = new String(message, StandardCharsets.UTF_8);
		System.out.println("send:" + str2);
		byte[] loop="\r".getBytes();
		for(byte tmp:loop){
			System.out.println(tmp);
		}
		System.out.println("the huan hang:"+ 		"0DH".getBytes()[2]);

		LinkedList list = new LinkedList();
		ArrayList arr = new ArrayList();

		System.out.println("MB: "
				+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);

		TreeMap table = new TreeMap();
		// 4000000
		for (int i = 0; i < 4000; i++) {
			TreeMap subTable = new TreeMap();
			for (int j = 0; j < 100; j++) {
				TestObj testObj = new TestObj();
				testObj.id = j;
				testObj.type = "fdsfaf";
				testObj.date = new Date();
				subTable.put(j, testObj);
			}
			table.put(i, subTable);
		}

		// System.out.println("loop the table:");
		// while(e.hasMoreElements()){
		// int key=(int) e.nextElement();
		// System.out.println(key+" "+((TestObj)table.get(key)).id);
		// }

		System.out.println("MB: "
				+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);

	}

	public void test() {

	}

}
