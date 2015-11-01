package Test;
/**
 * Code writer:Caolei
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
 
public class TestRandom {
 
 public static void main(String args[]){
 
	 int min=8;
	 int max=60;
	 HashMap map=new HashMap();
	 int i=0;
	 while(i<100){
	  int randomNum=(int)Math.round(Math.random()*(max-min)+min);
	  if(map.get(randomNum)==null) map.put(randomNum, 1);
	  else
	  {
		 int now=Integer.parseInt(map.get(randomNum)+"");
		 now++;
		 map.put(randomNum, now);
	 
	  }
	  i++;
	 }
	Set set= map.keySet();
	Iterator it=set.iterator();
	 while(it.hasNext()){
		 int tmp=Integer.parseInt(it.next()+"");
		 System.out.println(tmp+"->"+map.get(tmp));
	 }
	 
 }
}
