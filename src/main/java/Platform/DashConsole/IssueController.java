package Platform.DashConsole;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Issue;

public class IssueController {

	private RedmineManager redmine;

	public void dump(int issueId) {
		try {
			Issue issue = redmine.getIssueManager().getIssueById(issueId);
			System.out.println("issue.id = " + issue.getId());
			System.out.println("issue.a.id = " + issue.getAssigneeId());
			System.out.println("issue.a.name = " + issue.getAssigneeName());
			;
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setRedmine(RedmineManager redmine) {
		this.redmine = redmine;

	}

}
