import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class TestMain {
	
	public static void main(String args[]){
		
		
		LinkedList list=new LinkedList();
		ArrayList arr=new ArrayList();
		

		
		  System.out.println("MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024/1024);
		  

		Hashtable table=new Hashtable();
		
		for(int i=0;i<5000;i++){
			Hashtable subTable=new Hashtable();
		for(int j=0;j<1000;j++){
		TestObj testObj=new TestObj();
		testObj.id=j;
		testObj.type="fdsfaf";
		testObj.date=new Date();
		subTable.put(j, testObj);
			}
		table.put(i,subTable);
		}
		
		Enumeration e=table.keys();
		
//		System.out.println("loop the table:");
//		while(e.hasMoreElements()){
//			int key=(int) e.nextElement();
//			System.out.println(key+" "+((TestObj)table.get(key)).id);
//		}

	        System.out.println("MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024/1024);
	  
		
		
	}
	
	
	
	
	
}
