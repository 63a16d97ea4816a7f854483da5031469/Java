import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeMap;
 

public class TestMain {
	
	public static void main(String args[]){
		
		
		LinkedList list=new LinkedList();
		ArrayList arr=new ArrayList();
 
 
		  System.out.println("MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024/1024);
		  

		  TreeMap table=new TreeMap();
		//4000000
		for(int i=0;i<4000;i++){
			TreeMap subTable=new TreeMap();
		for(int j=0;j<100;j++){
		TestObj testObj=new TestObj();
		testObj.id=j;
		testObj.type="fdsfaf";
		testObj.date=new Date();
		subTable.put(j, testObj);
			}
		table.put(i,subTable);
		}
 
		
//		System.out.println("loop the table:");
//		while(e.hasMoreElements()){
//			int key=(int) e.nextElement();
//			System.out.println(key+" "+((TestObj)table.get(key)).id);
//		}

	        System.out.println("MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024/1024);
	  
		
		
	}
	
	public void test(){
 
		
	}
	
	
	
	
	
}
