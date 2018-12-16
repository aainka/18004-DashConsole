package Platform.DashConsole;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.barolab.html.HmTR;
import com.barolab.html.HttpPrintStream;
import com.barolab.util.TableMap;
import com.barolab.util.model.BeanClass;

import lombok.extern.java.Log;

@Log
public class TestLogtime {

	private LogConfig x = new LogConfig();
	List<OV_TimeEntry> list;
	HttpPrintStream h;
	TableMap<OV_TimeEntry> tableMap;
	BeanClass<OV_TimeEntry> LogtimeHelper = new BeanClass<OV_TimeEntry>(OV_TimeEntry.class);

	public void test() {
		
		list = LogtimeHelper.readExcel("C://tmp/RedmineManager/test_logtime.xlsx", "logtime");

		LogtimeHelper.removeElement(list, "projectName", "WCDMA", "Gerrit");
		LogtimeHelper.removeElement(list, "userName", "Suyoun Joe", "Woonsong Baik");

		LogtimeHelper.sort(list, "userName", "hours");
		// LogtimeHelper.writeExcel("C://tmp/RedmineManager/test_logtime_r2.xlsx",
		// "logtime", list);
		tableMap = LogtimeHelper.toTableMap(list, "userName", "weeknum");
		// TableMap<OV_TimeEntry> tableMap = LogtimeHelper.toTableMap(list, "userName",
		// "projectName");

		try {
			File fp = new File("C:/tmp/ReportSpendtime.html");
			h = new HttpPrintStream(fp);
			int count = 1;
			h.println("<table class='mytable' >");
			{
				HmTR tr = new HmTR();
				tr.addTD().add("X");
				tr.addTD().add("X");
				for (String col : tableMap.getColumnKeys()) {
					tr.addTD().add(col);
				}
				tr.toHTML(h);
			}
			count = 1;
			for (String row : tableMap.getRowKeys()) {
				HmTR tr = new HmTR();
				tr.addTD().add("" + count);
				tr.addTD().add(row);
				for (String col : tableMap.getColumnKeys()) {
					List<OV_TimeEntry> list = tableMap.get(row, col);
					if (list == null) {
						tr.addTD().add(" ");
					} else {
						float sum = 0;
						for (OV_TimeEntry element : list) {
							sum += element.getHours();
						}
						tr.addTD().setWidth(40).setAligh("right").add("" + (int) sum);
					}
				}
				tr.toHTML(h);
				count++;
			}
			h.println("</table>");
			for (String row : tableMap.getRowKeys()) {
			printWeek(row, "50");
			}
			h.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("***** End *****");
	}

	public void printWeek(String userName, String weeknum) {
		h.println("<br>");
		List<OV_TimeEntry> list = tableMap.get(userName, "48");
		
		LogtimeHelper.sort(list,"projectName", "issueId");
		h.println("<table class='mytable' >");
		
		for (OV_TimeEntry element : list) {
			HmTR tr = new HmTR();
			tr.addTD().add(element.getUserName());
			tr.addTD().add(element.getProjectName());
			tr.addTD().add(element.getIssueId());
			tr.addTD().add(element.getHours());
		
			tr.toHTML(h);
		}
		h.println("</table>");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestLogtime().test();
	}

}
