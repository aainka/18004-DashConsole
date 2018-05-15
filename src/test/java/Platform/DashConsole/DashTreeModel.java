package Platform.DashConsole;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.SavedQuery;

public class DashTreeModel extends DefaultTreeModel {

	final ClientConnectionManager connectionManager = RedmineManagerFactory.createDefaultConnectionManager();
	final HttpClient client = RedmineManagerFactory.getNewHttpClient("aa", connectionManager);
	public RedmineManager redmine;

	HashMap<Integer, MutableTreeNode> map = new HashMap<Integer, MutableTreeNode>();

	
	private String url = "http://redmine.ericssonlg.com/redmine";
	List<OV_Issue> issues = null;
	List<OV_Issue> rest = new LinkedList<OV_Issue>();
	List<OV_Issue> tmp = new LinkedList<OV_Issue>();

	public DashTreeModel(TreeNode arg0) {
		super(arg0);
		loading();
	}
	

	public void loading() {
		redmine = RedmineManagerFactory.createWithUserAuth(url, "ejaejeo", "ejaejeo", client);
		List<SavedQuery> savedQueries;
		try {
			List<Issue> list = redmine.getIssueManager().getIssues("vepg_si-2018", 147);
			System.out.println("size=" + list.size());
			issues = OV_Issue.toList(list);
			// for (OV_Issue issue : nlist) {
			// System.out.print("$" + issue.id);
			// System.out.print("$" + issue.parent_id);
			// System.out.println("$" + issue.subject);
			// }
			  ExcelUtil.save(issues, "filename");
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rest.addAll(issues);
		for (OV_Issue issue : issues) {
			if (issue.parent_id == 0) {
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) getRoot();
				MutableTreeNode newChild = new DefaultMutableTreeNode(issue);
				this.insertNodeInto(newChild, parent, parent.getChildCount());
				map.put(issue.id, newChild);
				rest.remove(issue);
			}
		}
		System.out.println("rest0.count = " + rest.size());
		for (int i = 0; i < 3; i++) {
			tmp.clear();
			tmp.addAll(rest);
			for (OV_Issue issue : tmp) {
				MutableTreeNode parent = map.get(issue.parent_id);
				if (parent != null) {
					MutableTreeNode newChild = new DefaultMutableTreeNode(issue);
					this.insertNodeInto(newChild, parent, parent.getChildCount());
					map.put(issue.id, newChild);
					rest.remove(issue);
				}
			}
			System.out.println("rest.count = " + rest.size());
		}

		for (OV_Issue issue : rest) {
			System.out.println("issue = " + issue);
		}

	}

}
