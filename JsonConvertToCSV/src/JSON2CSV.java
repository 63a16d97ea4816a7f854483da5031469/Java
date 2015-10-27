import java.io.File;
import java.io.IOException;

import net.sf.json.CDL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

public class JSON2CSV {
	public static void main(String myHelpers[]) {
		String jsonString = "{\"VWwJson\":[{\"date\":\"04/01/2014 00:00\",\"Treated xxxxx to Fort Canning\":6.882586,\"Treated xxxxx to MKA\":11.10599},{\"date\":\"04/18/2014 00:00\"}]}";
		JSONObject output = JSONObject.fromString(jsonString);
		JSONArray docs = output.getJSONArray("VWwJson");
		String csv = CDL.toString(docs);
		System.out.println(csv);
		File file = new File("c:\\testConverJson.csv");
		try {
			FileUtils.writeStringToFile(file, csv);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}