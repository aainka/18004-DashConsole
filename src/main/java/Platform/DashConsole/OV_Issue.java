package Platform.DashConsole;

import java.util.ArrayList;
import java.util.List;

import com.taskadapter.redmineapi.bean.Issue;

import lombok.Data;

@Data

public class OV_Issue {
	private int id;
	private String status;
	private String tracker;
	private String subject;
	private String assignee;
	private String author;
	private int estimatedHours;
	private int parent_id;
	private java.util.Date created;
	private java.util.Date dueDate;
	private java.util.Date closed;
	private int doneRatio;
	private String targetVersion;
	private int spentHours;

	public String toString() {
		String s = new String("" + id);
		s += ": " + subject;
		s += ": " + assignee;
		s += ": " + doneRatio;
		return s;
	}

	public static List<OV_Issue> toList(List<Issue> list) {
		List<OV_Issue> nList = new ArrayList<OV_Issue>();
		for (Issue src : list) {
			OV_Issue nIssue = new OV_Issue();
			nIssue.update(src);
			nList.add(nIssue);
		}
		return nList;
	}

	private void update(Issue orgIssue) {
		this.id = orgIssue.getId();
		this.status = orgIssue.getStatusName();
		this.tracker = orgIssue.getTracker().getName();
		this.subject = orgIssue.getSubject();
		this.assignee = orgIssue.getAssigneeName();
		this.author = orgIssue.getAuthorName();
		this.doneRatio = orgIssue.getDoneRatio();

		if (orgIssue.getTargetVersion() != null) {
			this.targetVersion = orgIssue.getTargetVersion().getName();
		}

		if (orgIssue.getParentId() != null) {
			this.parent_id = orgIssue.getParentId().intValue();
		}
		if (orgIssue.getEstimatedHours() != null) {
			this.estimatedHours = Math.round(orgIssue.getEstimatedHours());
		}
	 
		if (orgIssue.getSpentHours() != null) {
			this.spentHours = Math.round(orgIssue.getSpentHours());
		}
		this.dueDate = orgIssue.getDueDate();
		this.created = orgIssue.getCreatedOn();
		this.closed = orgIssue.getClosedOn();
		System.out.println("======="+orgIssue.getNotes());
		orgIssue.geN
	}
}
