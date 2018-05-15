package Platform.DashConsole;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 

public class ExcelUtil {

	public static void save(List<OV_Issue> nlist, String string)   {
		Class clazz = OV_Issue.class;
		// OV_Issue x = new OV_Issue();
		// x.subject = "kim";
		// keep order
		String fname = "C:\\JaeyoungJeon\\RedmineManager\\SR120-NEW.xlsx";
	//	FileInputStream inputStream = new FileInputStream(fname);
	 	XSSFWorkbook workbook = new XSSFWorkbook();
	//	Workbook workbook = new SXSSFWorkbook();
		XSSFSheet sheet =   workbook.createSheet("Sheet1");

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
			XSSFRow row = sheet.createRow(rowIndex++);
			for (Field f : clazz.getDeclaredFields()) {
				Cell cell = row.createCell(colIndex++);
				cell.setCellValue(f.getName());
			}
		}
		for (OV_Issue issue : nlist) {
			XSSFRow row = sheet.createRow(rowIndex++);
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
						System.out.println("DDate Conversion ==> " + vDate);
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
			FileOutputStream out = new FileOutputStream(new File("C:\\JaeyoungJeon\\RedmineManager\\SR120-NEW.xlsx"));
			workbook.write(out);
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		List<OV_Issue> nList = new ArrayList<OV_Issue>();
		new ExcelUtil().save(nList, "");
	}

}
