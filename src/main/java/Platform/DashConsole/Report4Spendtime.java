package Platform.DashConsole;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.barolab.html.HmTR;
import com.barolab.html.HmTable;
import com.barolab.html.HttpPrintStream;
import com.barolab.util.TableMap;

import lombok.extern.java.Log;

@Log
public class Report4Spendtime {

	HttpPrintStream h;
	private LogConfig x = new LogConfig();

	OV_Issue_DAO issues = new OV_Issue_DAO(OV_Issue.class);
	OV_TimeEntry_DataSource times = new OV_TimeEntry_DataSource(OV_TimeEntry.class);

	public void test() {

		issues.load(false);
		issues.sort("id");
		issues.writeExcel("c:/tmp/sorted_issues.xlsx");

		times.load(false);
		times.removeElements("projectName", "WCDMA", "Redmine");
		TableMap tableMap = times.toTableMap("weeknum", "userName");

		/**
		 * update issue.subject to logtime
		 */

		for (OV_TimeEntry te : times.getList()) {
			int id = te.getIssueId();
			OV_Issue issue = (OV_Issue) issues.find("id", id);
			if (issue == null) {
				log.info("############ No data issue.id = " + id);
			} else {
				// log.info(" data id = "+issue.getId()+", su="+issue.getSubject());
				te.setSubject(issue.getSubject());
			}
		}
		
		// ********************************
		// * REPORTING
		// *******************************

		int sum = 0;

		for (String rowKey : tableMap.getRowKeys()) {
			for (String colKey : tableMap.getColumnKeys()) {
				List<OV_TimeEntry> list = (List<OV_TimeEntry>) tableMap.get(rowKey, colKey);
				int size = 0;
				if (list != null) {
					size = list.size();
				}
				sum += size;
				// System.out.println(rowKey + ", " + colKey + " " + sum);
			}
		}

		File fp = new File("C:/tmp/ReportSpendtime.html");
		try {
			HttpPrintStream h = new HttpPrintStream(fp);
			HmTable htable = new HmTable();
			for (String colKey : tableMap.getColumnKeys()) {
				List<OV_TimeEntry> list = (List<OV_TimeEntry>) tableMap.get("1", colKey);
				if (list == null) {
					continue;
				}
				times.subsort(list,"issueId");
				// ListUtils.sort(list, "issueId");
				int sum2 = 0;
				int count = 0;
				for (OV_TimeEntry te : list) {
					if (list != null) {
						sum2 += te.getHours();
						count++;
					}
				}
				// OV_Issue issue = (OV_Issue) ListUtils.find(issues, "id",
				// Integer.parseInt(colKey));
				// HmTR tr = htable.addTR();

				for (OV_TimeEntry te : list) {
					HmTR tr = htable.addTR();
					if (count > 0) {
						tr.addTD().setAttribute("rowspan", count).add(colKey);
						tr.addTD().setWidth(50).setAttribute("rowspan", count).add(sum2);
						count = 0;
					}
					tr.addTD().add(te.getHours());
					tr.addTD().add(te.getSubject());
					tr.addTD().add(te.getComment());
					tr.addTD().add(te.getSpentOn());
				}
			}
			htable.toHTML(h);

//			int count = 1;
//			h.println("<table class='mytable' >");
//
//			for (OV_TimeEntry te : logtime) {
//
//				HmTR tr = new HmTR();
//
//				// String url = "http://redmine.ericssonlg.com/redmine/issues/" + issue.getId();
//
//				// String ref = mkHref("<a href=%s %s >%d</a>", qt2(url), " target=" +
//				// qt1("_sub"),issue.getId());
//				tr.addTD().setWidth(50).setAligh("center").add(count);
//				tr.addTD().add(te.getUserName());
//				tr.addTD().add(te.getWeeknum());
//				tr.addTD().add(te.getHours());
//				tr.addTD().add(te.getSubject());
//				tr.addTD().add(te.getSpentOn());
//
//				tr.toHTML(h);
//				count++;
//			}
//			h.println("</table>");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Report4Spendtime().test();
	}

}
