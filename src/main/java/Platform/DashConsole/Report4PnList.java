package Platform.DashConsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.barolab.MailApiClient;
import com.barolab.OV_MailContent;
import com.barolab.html.HmTR;
import com.barolab.html.HttpBuilder;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.SavedQuery;

import Platform.DashConsole.model.DAO_Issue;
import Platform.DashConsole.model.DAO_LCM;
import Platform.DashConsole.model.DAO_User;
import Platform.DashConsole.model.OV_Issue;
import Platform.DashConsole.model.OV_LCM;
import lombok.extern.java.Log;

@Log
public class Report4PnList extends MainDashBoard {

	HttpBuilder h;
	List<OV_Issue> nlist;
	DAO_User daoUser = new DAO_User();

	// 이름순 소트 : 고객편의사항
	// 상태별 소트
	public void test() {

		String filename = "C:/tmp/pnlist2.html";

		DAO_Issue daoIssue = new DAO_Issue();
		DAO_LCM daoLCM = new DAO_LCM();
		daoLCM.load(false);
		
		daoUser.load(true); // @Todo
		daoUser.convName(daoIssue.getList());

		login();
	//	List<SavedQuery> savedQueries;
		try {
			List<Issue> list = redmine.getIssueManager().getIssues("vepg-si-pr", 160);// proj, query
			nlist = OV_Issue.toList(list);
			log.info("load pr count=" + list.size());
			daoIssue.setList(nlist);
			daoIssue.sort("assignee");
		} catch (RedmineException e) {
			e.printStackTrace();
		}

		HttpBuilder h = null;
		try {
			h = new HttpBuilder();

		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int count = 1;
		h.println("<table class='mytable' >");
		for (OV_Issue issue : nlist) {
			h.setObject("issue", issue);
			OV_LCM lcm = (OV_LCM) daoLCM.find("issueNo", issue.getId());
			if (lcm != null) {
				// LCM exist
				h.setObject("lcm", lcm); // careful....
				h.exec("issue.severity = lcm.severity");

				h.exec("issue.assignee = lcm.designer");
				if (match(lcm.getStatus(), "Resolved", "Hold")) {
					// issue.setStatus(lcm.getStatus());
					h.exec("issue.status = lcm.status");
				}
				if (contains(lcm.getMemo(), "차기검토","종결검토")) {
					continue;
				}
			} else {
				lcm = new OV_LCM();
				daoLCM.getList().add(lcm);
			}

			h.setObject("lcm", lcm);
			h.exec("lcm.issueNo = issue.id");
			h.exec("lcm.subject = issue.subject");
			h.exec("print lcm");

			if (match(issue.getStatus(), "Resolved", "Closed", "Feedback")) {
				continue;

			}
			if (contains(issue.getSubject(), "(현상대기)", "R140예비", "R140검토")) {
				continue;
			}
		
			HmTR tr = new HmTR();
			String url = "http://redmine.ericssonlg.com/redmine/issues/" + issue.getId();
			String ref = mkHref("<a href=%s %s >%d</a>", qt2(url), " target=" + qt1("_sub"), issue.getId());
			tr.addTD().setWidth(50).setAligh("center").add(count);
			tr.addTD().setWidth(100).setAligh("center").add(ref);
			tr.addTD().setWidth(120).setAligh("center").add(issue.getStatus());
			tr.addTD().setWidth(120).setAligh("center").add(issue.getSeverity());
			tr.addTD().setWidth(200).add(issue.getSubject());
			tr.addTD().setWidth(150).setAligh("center").add(issue.getAssignee());
			tr.addTD().setWidth(150).setAligh("center").add(issue.getAuthor());
			tr.addTD().setWidth(150).add(lcm.getMemo());
			tr.toHTML(h);
			count++;
		}
		h.println("</table>");
		h.close();
		try {
			h.writeToFile(filename);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		daoLCM.save();

		// 화일읽기

		String msg;
		try {
			msg = h.readFile(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (true) {
			return;
		}

		MailApiClient mailApi = new MailApiClient();
		List<OV_MailContent> list = new LinkedList<OV_MailContent>();
		OV_MailContent item = new OV_MailContent();
		item.subject = "PN-LISt2";
		item.message = msg;
		list.add(item);
		try {
			mailApi.insert(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String mkHref(String msg, Object... args) {
		String s = String.format(msg, args);
		// System.out.println(s);
		return s;

	}

	public String qt1(String msg) {
		return "'" + msg + "'";
	}

	public String qt2(String msg) {
		return "\"" + msg + "\"";
	}

	public boolean match(String key, String... args) {
		if (key == null)
			return false;
		for (String arg : args) {
			if (key.equals(arg)) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(String key, String... args) {
		if ( key == null) {
			return false;
		}
		for (String arg : args) {
			if (key.indexOf(arg) >= 0) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		new Report4PnList().test();
	}

	private class Ascending implements Comparator<OV_Issue> {

		@Override
		public int compare(OV_Issue arg0, OV_Issue arg1) {
			return arg0.getAssignee().compareTo(arg1.getAssignee());
		}

	}
}
