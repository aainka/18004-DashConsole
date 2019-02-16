package Platform.DashConsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.barolab.MailApiClient;
import com.barolab.OV_MailContent;
import com.barolab.html.HttpBuilder;
import com.barolab.util.LogUtil;
import com.barolab.util.TableMap;

import Platform.DashConsole.model.DAO_Issue;
import Platform.DashConsole.model.DAO_LCM;
import Platform.DashConsole.model.DAO_TimeEntry;
import Platform.DashConsole.model.DAO_User;
import Platform.DashConsole.model.OV_Issue;
import Platform.DashConsole.model.OV_LCM;
import Platform.DashConsole.model.OV_TimeEntry;
import lombok.extern.java.Log;

@Log
public class Report4Spendtime {

	HttpBuilder h;
	private LogConfig logBox = new LogConfig();

	DAO_Issue daoIssue = new DAO_Issue();
	DAO_TimeEntry daoTime = new DAO_TimeEntry();
	DAO_User daoUser = new DAO_User();
	DAO_LCM daoLCM = new DAO_LCM();

	public void test() {

		boolean isFromXls = false;

		logBox.setLogLevel(Level.OFF, "com.barolab.util.ExcelObjectReader");
		LogUtil.getOsName();

		/*
		 * Issue DB
		 */
		daoIssue.load(true);
//		daoIssue.sort("id");
//		daoIssue.writeExcel("c:/tmp/sorted_issues.xlsx");

		/**
		 * Time DB
		 */
		daoTime.load(isFromXls); // test mode flag
		daoTime.removeElements("projectName", "WCDMA", "Redmine");

		daoUser.load(true); // @Todo
		daoUser.convName(daoIssue.getList());
		daoUser.convName2(daoTime.getList());

		/**
		 * LCM DB
		 */
		daoLCM.load(true); // @Todo

		// times.toTableMap("issueNo", "userName");

		/**
		 * verify issueNo
		 */
		List<OV_LCM> add_list = new LinkedList<OV_LCM>();
		TableMap tableMap = daoTime.toTableMap("issueId", "userName");
		{
			for (Object rowKey : tableMap.getRowKeys()) {
				int issueId = Integer.parseInt((String) rowKey);
				OV_LCM lcm = daoLCM.find("issueNo", issueId);
				if (lcm != null) { // 있으면....

				} else {
					OV_Issue issue = daoIssue.find("id", issueId);
					System.out.println("add to LCM : " + issueId + "," + issue.getSubject());
					if (issue != null) {
						lcm = new OV_LCM();
						daoLCM.getList().add(lcm);
						lcm.setIssueNo(issue.getId());
						lcm.setSubject(issue.getSubject());
						lcm.setStatus(issue.getStatus());
						lcm.setDesigner(issue.getAssignee());
						add_list.add(lcm);
					}
				}
			}
		}

		try {
			OV_LCM.excelUtils.append(add_list); // 신규추가 된 내용을 파일에 업데이트 한다.
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		TableMap table2 = daoTime.toTableMap("userName", "weeknum");

		String msg = "test";
		try {
			HttpBuilder h = new HttpBuilder();
			h.printTable(table2, daoTime, "hours", 50);
			h.close();
			msg = h.toString();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * mail sending
		 */
		MailApiClient mailApi = new MailApiClient();
		List<OV_MailContent> list = new LinkedList<OV_MailContent>();
		OV_MailContent item = new OV_MailContent();
		item.subject = "Logtime Summary " + LogUtil.getToday();
		item.message = msg;
		list.add(item);
		try {
			mailApi.insert(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * update time.subject, time.lcm
		 */
		for (OV_TimeEntry te : daoTime.getList()) {
			OV_Issue issue = daoIssue.find("id", te.getIssueId());
			OV_LCM lcm = daoLCM.find("issueNo", te.getIssueId());
			if (issue != null) {
				te.setSubject(issue.getSubject());
			}
			if (lcm != null) {
				te.setLcm(lcm.getLCM());
			}
		}

		/**
		 * update LCM_CODE
		 */

		// daoLCM.writeExcel("c:/tmp/new_lcm.xlsx", add_list);
		daoTime.writeExcel("c:/tmp/result_2019_time.xlsx");

		System.out.println("### Collection Completed ");

		// ********************************
		// * REPORTING
		// *******************************

		int sum = 0;

		for (String rowKey : tableMap.getRowKeys()) {
			for (String colKey : tableMap.getColumnKeys()) {
				List<OV_TimeEntry> list2 = (List<OV_TimeEntry>) tableMap.get(rowKey, colKey);
				int size = 0;
				if (list2 != null) {
					size = list2.size();
				}
				sum += size;
				// System.out.println(rowKey + ", " + colKey + " " + sum);
			}
		}

//		File fp2 = new File("C:/tmp/ReportSpendtime.html");
//		try {
//			HttpPrintStream h = new HttpPrintStream(fp2);
//			HmTable htable = new HmTable();
//			for (String colKey : tableMap.getColumnKeys()) {
//				List<OV_TimeEntry> list3  = (List<OV_TimeEntry>) tableMap.get("1", colKey);
//				if (list == null) {
//					continue;
//				}
//			//	daoTime.subsort(list3, "issueId");
//				// ListUtils.sort(list, "issueId");
//				int sum2 = 0;
//				int count = 0;
//				for (OV_TimeEntry te : list3) {
//					if (list != null) {
//						sum2 += te.getHours();
//						count++;
//					}
//				}
//				// OV_Issue issue = (OV_Issue) ListUtils.find(issues, "id",
//				// Integer.parseInt(colKey));
//				// HmTR tr = htable.addTR();
//
//				for (OV_TimeEntry te : list3) {
//					HmTR tr = htable.addTR();
//					if (count > 0) {
//						tr.addTD().setAttribute("rowspan", count).add(colKey);
//						tr.addTD().setWidth(50).setAttribute("rowspan", count).add(sum2);
//						count = 0;
//					}
//					tr.addTD().add(te.getHours());
//					tr.addTD().add(te.getSubject());
//					tr.addTD().add(te.getComment());
//					tr.addTD().add(te.getSpentOn());
//				}
//			}
//			htable.toHTML(h);

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
//	}catch(
//
//	FileNotFoundException e1)
//	{
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}

	}

	public static void main(String[] args) {
		new Report4Spendtime().test();
	}

}
