import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
 * Developed by Tony (Cao Lei)
 * 
 * It is another way to read and write Excel file.
 * 
 * This program is saved for future use.
 * 
 */

public class ReadExcel {

	public static String filePath = "c:\\11.xls";

	public static void main(String args[]) {
		try {

			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath));

			HSSFSheet sheet = workbook.getSheetAt(1);

			HSSFRow row = sheet.getRow(2);
			HSSFCell cell = row.getCell(1);
			if(cell!=null){
			System.out.println(cell.getRichStringCellValue());
			cell.setCellValue("my");
			}
			
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);
			out.close();

		} catch (Exception e) {
			System.out.println("Error : " + e);
		}
	}
}