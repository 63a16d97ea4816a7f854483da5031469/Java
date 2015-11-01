package Test;
/**
 * Code writer:Caolei
 */
import java.util.ArrayList;

public class TestReplaceSpecialWords {
	public static void main(String args[]){
		String str="fdsfdsf''232321''32'3";
		String newstr=str.replace("'", "~");
		ArrayList<String> list=new ArrayList<String>();
		list.add("32");
		list.add("11");
		for(String tmp:list){
			System.out.println(tmp);
		}
		
		System.out.println(newstr);
	}
}
