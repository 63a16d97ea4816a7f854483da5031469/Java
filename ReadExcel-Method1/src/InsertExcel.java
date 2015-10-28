import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import pojo.PojoDB;
import dao.PublicInsertDao;

/*
 * Developed by Tony (Cao Lei)
 * 
 * This program is for importing all the excel file's data into DB
 * 
 * It is for another Online Judge System's data conversion and consolidation
 * 
 * For safety, I remove the properties files in this project.
 * 
 */

public class InsertExcel {
	public static void main(String args[]) {
		insertLocalComplierDB();
	}

	public static void insertLocalComplierDB() {
		try {
			System.out.println("begin----->");
			Workbook book = Workbook.getWorkbook(new File("c:\\inputProblem.xls"));
			Sheet sheet = book.getSheet(0);

			for (int j = 2; j < sheet.getRows(); j++) {
				PojoDB db = new PojoDB();
				for (int i = 0; i < sheet.getColumns(); i++) {
					// System.out.println(sheet.getColumns());
					Cell cell2 = sheet.getCell(i, j);
					String resultRow = cell2.getContents();

					switch (i) {
					case 0:
						db.setTitle(resultRow);
						break;
					case 1:
						db.setTopic_id(resultRow);
						break;
					case 14:
						db.setDifficulity(resultRow);
						break;
					case 4:
						db.setInput(resultRow);
						break;

					case 5:
						db.setOutput(resultRow);
						break;

					case 6:
						db.setSampleinput(resultRow);
						break;
					case 7:
						db.setSampleoutput(resultRow);
						break;
					case 8:
						db.setHint(resultRow);
						break;
					case 9:
						db.setTimelimite(resultRow);
						break;
					case 10:
						db.setMemorylimit(resultRow);
						break;
					case 11:
						db.setFrompaper_source(resultRow);
						break;
					case 3:
						db.setQcontent(resultRow);
						break;
					case 12:
						db.setHalf_finished_sourcecode(resultRow);
						break;
					}
					db.setNoaccepted("0");
					db.setNoattempted("0");
				}
				// insert the OnlineDB object into DB
				PublicInsertDao insertdao = new PublicInsertDao();
				String str = "INSERT INTO `judge_problem`(title,description,input,output,sample_input,sample_output,time_limit,memory_limit,accepted,submit,difficulity,source,topic_id,code) VALUES ('"
						+ replaceBlank(db.getTitle())
						+ "','"
						+ replaceBlank(db.getQcontent())
						+ "','"
						+ replaceBlank(db.getInput())
						+ "','"
						+ replaceBlank(db.getOutput())
						+ "','"
						+ replaceBlank(db.getSampleinput())
						+ "','"
						+ replaceBlank(db.getSampleoutput())
						+ "',"
						+ replaceBlank(db.getTimelimite())
						+ ","
						+ replaceBlank(db.getMemorylimit())
						+ ","
						+ db.getNoaccepted()
						+ ","
						+ db.getNoattempted()
						+ ","
						+ db.getDifficulity()
						+ ",'"
						+ db.getFrompaper_source()
						+ "',"
						+ db.getTopic_id()
						+ ",'"
						+ replaceBlank(db.getHalf_finished_sourcecode()) + "')";
				System.out.println(str);
				insertdao.insert(str);
			}
			book.close();
			System.out.println("end-----<");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String MostreplaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\t*|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {

			Pattern p1 = Pattern.compile("\t");
			Matcher m1 = p1.matcher(str);
			dest = m1.replaceAll("");

			Pattern p2 = Pattern.compile("\r");
			Matcher m2 = p2.matcher(dest);
			dest = m2.replaceAll("\r");

			Pattern p3 = Pattern.compile("\\n");
			Matcher m3 = p3.matcher(dest);
			dest = m3.replaceAll("\\\\n ");

			Pattern p4 = Pattern.compile("\'");
			Matcher m4 = p4.matcher(dest);
			dest = m4.replaceAll("");
		}
		return dest;
	}
}
