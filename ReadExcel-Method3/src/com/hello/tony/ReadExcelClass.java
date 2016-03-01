

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/*
 * Developed by Tony (Cao Lei)
 * 
 * This program is for comparing excel file and .txt file to fill in some data in related fields.
 * This is a internal tool for myself to finish my own job faster.
 * I put this file in this project because it need to use the same libraries to read Excel file. 
 *
 * 
 * For safety, I remove the properties files in this project.
 * 
 */

public class ReadExcelClass {
 
	public static void main(String args[]) throws URISyntaxException {
 
		getValuesFromExcel("");

	}

	public static void getValuesFromExcel(String excelFileName) {

		DataEngine dataEngine = DataEngine.getInstance();

		Workbook book;
		try {
			//how to get the File outside the Jar file:
			URL applicationRootPathURL = ReadExcelClass.class.getProtectionDomain().getCodeSource().getLocation();
			File applicationRootPath = new File(applicationRootPathURL.getPath());
			File myFile;
			if (applicationRootPath.isDirectory()) {
				myFile = new File(applicationRootPath, "VxxxListing.xls");
			} else {
				myFile = new File(applicationRootPath.getParentFile(), "VxxxListing.xls");
			}

			System.out.println(">>  " + myFile);
			
			//print out: >>  /Users/Tony/Documents/REST_Tasks/ReadExcel-Method1/target/......xls

			book = Workbook.getWorkbook(myFile);
			Sheet sheet = book.getSheet(0);

			int count = 0;

			System.out.println(sheet.getRows());

			// for (int i = 0; i < sheet.getColumns(); i++) {
			for (int j = 1; j < sheet.getRows(); j++) {
				Cell emailCell = sheet.getCell(1, j);// 前一个参数代表列，后一个参数代表行
				String emailStr = emailCell.getContents();
				
				Cell firstNameCell = sheet.getCell(2, j);// 前一个参数代表列，后一个参数代表行
				String firstNameStr = firstNameCell.getContents();

				Cell lastNameCell = sheet.getCell(3, j);// 前一个参数代表列，后一个参数代表行
				String lastNameStr = lastNameCell.getContents();
	
				Cell companyCell = sheet.getCell(4, j);// 前一个参数代表列，后一个参数代表行
				String companyStr = companyCell.getContents();

				if ((companyStr != null && !companyStr.trim().equals(""))
						|| (emailStr != null && !emailStr.trim().equals(""))) {

					emailStr = emailStr.trim();
					companyStr = companyStr.trim();

					count++;
					System.out.println(count + "> firstName: "+firstNameStr+"lastName: "+lastNameStr+"Email: " + emailStr + "Company: " + companyStr);
		 
				}

				// System.out.println(firstresultRow+"->"+secondresultRow);
			}
			// }
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static HashMap getMapFromBasecopy(boolean keyValueOrder) {
		System.out.println("begin----->");
		HashMap map = new HashMap();
		Workbook book;
		try {
			book = Workbook.getWorkbook(new File("c:\\rem4dhk.xls"));
			Sheet sheet = book.getSheet(0);
			for (int i = 0; i < sheet.getColumns(); i++) {
				for (int j = 0; j < sheet.getRows(); j++) {
					Cell firstColumnCell = sheet.getCell(0, j);// 前一个参数代表列，后一个参数代表行
					String firstresultRow = firstColumnCell.getContents();

					Cell secondColumnCell = sheet.getCell(1, j);// 前一个参数代表列，后一个参数代表行
					String secondresultRow = secondColumnCell.getContents();

					if (!keyValueOrder)
						map.put(firstresultRow, secondresultRow);
					else
						map.put(secondresultRow, firstresultRow);
					// System.out.println(firstresultRow+"->"+secondresultRow);
				}
			}

		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static HashMap getStringsFromBasecopy() {
		System.out.println("begin----->");
		HashMap map = new HashMap();
		Workbook book;
		try {
			book = Workbook.getWorkbook(new File("c:\\m4dhk.xls"));
			Sheet sheet = book.getSheet(0);

			for (int i = 0; i < sheet.getColumns(); i++) {
				for (int j = 2; j < sheet.getRows(); j++) {
					Cell firstColumnCell = sheet.getCell(0, j);// 前一个参数代表列，后一个参数代表行
					String firstresultRow = firstColumnCell.getContents();
					Cell secondColumnCell = sheet.getCell(1, j);// 前一个参数代表列，后一个参数代表行
					String secondresultRow = secondColumnCell.getContents();
					map.put(firstresultRow.trim(), secondresultRow.trim());
					// System.out.println(firstresultRow+"->"+secondresultRow);
				}
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static void doMainJob() {
		String line = null;
		BufferedReader br;
		BufferedReader br2;
		try {
			br = new BufferedReader(new FileReader("c:\\basedLine.txt"));
			br2 = new BufferedReader(new FileReader("c:\\messageSet.txt"));

			while ((line = br.readLine()) != null) {
				// if(line.lastIndexOf("\"")==-1) continue;
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HashMap getMapFromFile(String filename, boolean keyValueOrder) {
		HashMap map = new HashMap();
		String line = null;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));

			while ((line = br.readLine()) != null) {

				if (line.lastIndexOf("\"") == -1)
					continue;
				// if (line.lastIndexOf("=") == -1)
				// continue;

				String firstPart = line.substring(0, line.indexOf("=")).replace("\"", "").replace(";", "").trim();
				String secondPart = line.substring(line.indexOf("=") + 1, line.length()).replace("\"", "")
						.replace(";", "").trim();

				// line = line.substring(line.indexOf("\""),
				// line.lastIndexOf("\"") - 1);
				// line = line.substring(0, line.lastIndexOf("\""));
				// System.out.println(line + "\"\"");

				if (keyValueOrder) {
					map.put(secondPart, firstPart);
				} else {
					map.put(firstPart, secondPart);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
