package Platform.DashConsole;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barolab.html.HmTR;
import com.barolab.html.HttpPrintStream;
import com.barolab.util.TableMap;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.TimeEntryManager;
import com.taskadapter.redmineapi.bean.TimeEntry;
import com.taskadapter.redmineapi.internal.ResultsWrapper;

public class Report4Spendtime extends MainDashBoard {

	HttpPrintStream h;

	public void test() {

		List<OV_TimeEntry> listLogtime = null;

		login();
		TimeEntryManager timeEntryManager = redmine.getTimeEntryManager();
		List<TimeEntry> totalTimeEntry = new ArrayList<TimeEntry>();

		try {
			// List<TimeEntry> elements = timeEntryManager.getTimeEntries();
			final Map<String, String> params = new HashMap<>();
			int totalFound = 1;
			for (int offset = 0; offset < totalFound; offset += 90) {
				params.put("limit", "90");
				params.put("offset", "" + offset);
				params.put("spent_on", "<2018-12-01|2018-12-30>");
				ResultsWrapper<TimeEntry> resultList = timeEntryManager.getTimeEntries(params);
				System.out.println("* time.offset=" + offset);
				// System.out.println(" time.result=" + resultList.getResultsNumber());
				// System.out.println(" time.max=" + resultList.getTotalFoundOnServer());
				System.out.println("  time.list=" + totalTimeEntry.size() + "/" + totalFound);
				totalFound = resultList.getTotalFoundOnServer();
				// OV_TimeEntry.transform(resultList.getResults()); // for test.
				totalTimeEntry.addAll(resultList.getResults());
				// totalFound = 200;
			}
			listLogtime = OV_TimeEntry.transform(totalTimeEntry);
			System.out.println("LogTime process ending.");
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ********************************
		// * REPORTING
		// *******************************

		Collections.sort(listLogtime, new Ascending2());
		// Map<String, List<OV_TimeEntry>> map = new HashMap<String,
		// List<OV_TimeEntry>>();

		TableMap<OV_TimeEntry> tbl = new TableMap<OV_TimeEntry>();
		tbl.build("UserName", "WeekNum");
		for (OV_TimeEntry te : listLogtime) {
			tbl.put(te.getUserName(), "" + te.getWeeknum(), te);
		}
		System.out.println(tbl.getRowKeys());
		System.out.println(tbl.getColumnKeys());

		int sum = 0;

		for (String rowKey : tbl.getRowKeys()) {
			for (String colKey : tbl.getColumnKeys()) {
				List<OV_TimeEntry> list = tbl.get(rowKey, colKey);
				int size = 0;
				if (list != null) {
					size = list.size();
				}
				sum += size;
				System.out.println(rowKey + ", " + colKey + " " + sum);
			}
		}

		File fp = new File("C:/tmp/ReportSpendtime.html");
		try {
			HttpPrintStream h = new HttpPrintStream(fp);
			int count = 1;
			h.println("<table class='mytable' >");

			for (OV_TimeEntry te : listLogtime) {

				HmTR tr = new HmTR();

				// String url = "http://redmine.ericssonlg.com/redmine/issues/" + issue.getId();

				// String ref = mkHref("<a href=%s %s >%d</a>", qt2(url), " target=" +
				// qt1("_sub"),issue.getId());
				tr.addTD().setWidth(50).setAligh("center").add(count);
				tr.addTD().add(te.getUserName());
				tr.addTD().add(te.getWeeknum());
				tr.addTD().add(te.getHours());
				tr.addTD().add(te.getSpentOn());

				tr.toHTML(h);
				count++;
			}
			h.println("</table>");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private class Ascending2 implements Comparator<OV_TimeEntry> {

		@Override
		public int compare(OV_TimeEntry arg0, OV_TimeEntry arg1) {
			int v = arg0.getWeeknum() - arg1.getWeeknum();
			if (v == 0) {
				return arg0.getUserName().compareTo(arg1.getUserName());
			}
			return v;
		}

	}

	public static void main(String[] args) {
		new Report4Spendtime().test();
	}

}
