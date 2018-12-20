package Platform.DashConsole;

 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barolab.html.HmTR;
import com.barolab.html.HttpPrintStream;
import com.barolab.util.TableMap;
import com.barolab.util.model.ListUtils;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.TimeEntryManager;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.SavedQuery;
import com.taskadapter.redmineapi.bean.TimeEntry;
import com.taskadapter.redmineapi.internal.ResultsWrapper;

import lombok.extern.java.Log;

@Log
public class Report4Spendtime extends MainDashBoard {

	HttpPrintStream h;
	private List<OV_TimeEntry> logtime = null;
	private List<OV_Issue> issues = null;

	public void loadLogtime() {
		TimeEntryManager timeEntryManager = redmine.getTimeEntryManager();
		List<TimeEntry> totalTimeEntry = new ArrayList<TimeEntry>();

		try {
			final Map<String, String> params = new HashMap<>();
			int totalFound = 1;
			for (int offset = 0; offset < totalFound; offset += 90) {
				params.put("limit", "90");
				params.put("offset", "" + offset);
				params.put("spent_on", "<2018-12-20|2018-12-30>");
				ResultsWrapper<TimeEntry> resultList = timeEntryManager.getTimeEntries(params);
				System.out.println("* time.offset=" + offset);
				System.out.println("  time.list=" + totalTimeEntry.size() + "/" + totalFound);
				totalFound = resultList.getTotalFoundOnServer();
				totalTimeEntry.addAll(resultList.getResults());
			}
			logtime = OV_TimeEntry.transform(totalTimeEntry);
			System.out.println("LogTime process ending.");
			
		} catch (RedmineException e) {
			e.printStackTrace();
		}
	}

	public void loadIssues() {
		List<SavedQuery> savedQueries;
		try {

		//	List<Issue> list = redmine.getIssueManager().getIssues("vepg-si-pr", 160);// proj, query
			List<Issue> list = redmine.getIssueManager().getIssues("vepg-si", 125);// proj, query
			issues = OV_Issue.toList(list);
			log.info("load pr count=" + list.size());
			ListUtils.sort(issues, "assignee");
			ListUtils.writeExcel(issues, "C:/tmp/xxx.xlsx");

		} catch (RedmineException e) {
			e.printStackTrace();
		}
	}

	public void test() {

		login();

		loadLogtime();
		loadIssues();
		
		ListUtils.sort(logtime, "weeknum", "userName");
		ListUtils.removeElements(logtime,"projectName","WCDMA");
		TableMap tableMap = ListUtils.toTableMap(logtime, "userName", "projectName");
	
		
		
		/**
		 * update issue.subject to logtime
		 */
		
		for ( OV_TimeEntry te : logtime) {
			int id = te.getIssueId();
			log.info("find id = "+id);
			OV_Issue issue  = (OV_Issue) ListUtils.find(issues, "id", id); 
			log.info(" data id = "+issue.getId()+", su="+issue.getSubject());
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
				System.out.println(rowKey + ", " + colKey + " " + sum);
			}
		}

		File fp = new File("C:/tmp/ReportSpendtime.html");
		try {
			HttpPrintStream h = new HttpPrintStream(fp);
			int count = 1;
			h.println("<table class='mytable' >");

			for (OV_TimeEntry te : logtime) {

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

	public static void main(String[] args) {
		new Report4Spendtime().test();
	}

}
