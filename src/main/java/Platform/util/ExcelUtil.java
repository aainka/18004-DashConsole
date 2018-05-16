package Platform.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Platform.DashConsole.OV_Issue;

public class ExcelUtil {

	// https://www.callicoder.com/java-read-excel-file-apache-poi/
	//static String fname = "C:\\JaeyoungJeon\\RedmineManager\\SR120-NEW.xlsx";
	static String fname = "‪C:\\tmp\\18004-DashConsole\\SR120-NEW.xlsx";
	public static void save(List<OV_Issue> nlist, String string) {
		Class clazz = OV_Issue.class;
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");

		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		short dateFormat = createHelper.createDataFormat().getFormat("yyyy-MM-dd");
		cellStyle.setDataFormat(dateFormat);

		/*
		 * Head Row Creation
		 */
		int colIndex = 0;
		int rowIndex = 0;
		{
			Row row = sheet.createRow(rowIndex++);
			for (Field f : clazz.getDeclaredFields()) {
				Cell cell = row.createCell(colIndex++);
				cell.setCellValue(f.getName());
			}
		}
		for (OV_Issue issue : nlist) {
			Row row = sheet.createRow(rowIndex++);
			colIndex = 0;
			Object obj = issue;
			for (Field f : clazz.getDeclaredFields()) {
				Cell cell = row.createCell(colIndex++);
				try {
					if (f.getType() == int.class) {
						System.out.println(f.getName() + "=" + f.getInt(obj));
						cell.setCellValue(f.getInt(obj));
					}
					if (f.getType() == String.class) {
						System.out.println(f.getName() + "=" + (String) f.get(obj));
						cell.setCellValue((String) f.get(obj));
					}
					if (f.getType() == java.util.Date.class) {
						java.util.Date vDate = (Date) f.get(obj);
						if (vDate != null) {
							cell.setCellValue(vDate);
							cell.setCellStyle(cellStyle);
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(new File(fname));
			workbook.write(out);
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	////////////////////////

	public void read() {
		DataFormatter dataFormatter = new DataFormatter();
		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(new File(fname));
			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				for (Cell cell : row) {
					String cellValue = dataFormatter.formatCellValue(cell);
					System.out.print(cellValue + "\t");
				}
				System.out.println();
			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

//	public static final String SAMPLE_XLSX_FILE_PATH = "./sample-xlsx-file.xlsx";

	public void example() throws IOException, InvalidFormatException {

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File(fname));

		// Retrieving the number of sheets in the Workbook
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

		/*
		 * ============================================================= Iterating over
		 * all the sheets in the workbook (Multiple ways)
		 * =============================================================
		 */

		// 1. You can obtain a sheetIterator and iterate over it
		Iterator<Sheet> sheetIterator = workbook.sheetIterator();
		System.out.println("Retrieving Sheets using Iterator");
		while (sheetIterator.hasNext()) {
			Sheet sheet = sheetIterator.next();
			System.out.println("=> " + sheet.getSheetName());
		}

		// 2. Or you can use a for-each loop
		System.out.println("Retrieving Sheets using for-each loop");
		for (Sheet sheet : workbook) {
			System.out.println("=> " + sheet.getSheetName());
		}

		// 3. Or you can use a Java 8 forEach with lambda
		System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
		workbook.forEach(sheet -> {
			System.out.println("=> " + sheet.getSheetName());
		});

		/*
		 * ================================================================== Iterating
		 * over all the rows and columns in a Sheet (Multiple ways)
		 * ==================================================================
		 */

		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		// Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter = new DataFormatter();

		// 1. You can obtain a rowIterator and columnIterator and iterate over them
		System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			// Now let's iterate over the columns of the current row
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String cellValue = dataFormatter.formatCellValue(cell);
				System.out.print(cellValue + "\t");
			}
			System.out.println();
		}

		// 2. Or you can use a for-each loop to iterate over the rows and columns
		System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
		for (Row row : sheet) {
			for (Cell cell : row) {
				String cellValue = dataFormatter.formatCellValue(cell);
				System.out.print(cellValue + "\t");
			}
			System.out.println();
		}

		// 3. Or you can use Java 8 forEach loop with lambda
		System.out.println("\n\nIterating over Rows and Columns using Java 8 forEach with lambda\n");
		sheet.forEach(row -> {
			row.forEach(cell -> {
				String cellValue = dataFormatter.formatCellValue(cell);
				System.out.print(cellValue + "\t");
			});
			System.out.println();
		});

		// Closing the workbook
		workbook.close();
	}

	public static void main(String[] args) {
		List<OV_Issue> nList = new ArrayList<OV_Issue>();
		// 일단 Open Value Print
		// class화
		//new ExcelUtil().save(nList, "");
		new ExcelUtil().read();
	}

}