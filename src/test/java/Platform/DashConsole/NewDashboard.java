package Platform.DashConsole;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.SavedQuery;

public class NewDashboard {
	final ClientConnectionManager connectionManager = RedmineManagerFactory.createDefaultConnectionManager();
	final HttpClient client = RedmineManagerFactory.getNewHttpClient("aa", connectionManager);
	public RedmineManager redmine;

	private String url = "http://redmine.ericssonlg.com/redmine";

	public void test() {
		redmine = RedmineManagerFactory.createWithUserAuth(url, "ejaejeo", "ejaejeo", client);
		List<SavedQuery> savedQueries;
		try {
			List<Issue> list = redmine.getIssueManager().getIssues("vepg_si-2018", 147);
			System.out.println("size=" + list.size());
			List<OV_Issue> nlist = OV_Issue.toList(list);
			// for (OV_Issue issue : nlist) {
			// System.out.print("$" + issue.id);
			// System.out.print("$" + issue.parent_id);
			// System.out.println("$" + issue.subject);
			// }
			ExcelUtil.save(nlist, "filename");
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new NewDashboard().test();

	}

}
