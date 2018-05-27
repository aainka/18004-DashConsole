package Platform.DashConsole;

import java.util.ArrayList;
import java.util.List;

import com.taskadapter.redmineapi.bean.Issue;

public class OV_Issue {
	public int id;
	public String status;
	public String tracker;
	public String subject;
	public String assignee;
	public String author;
	public int estimatedHours;
	public int parent_id;
	public java.util.Date created;
	public java.util.Date dueDate;
	public java.util.Date closed;
	public Integer doneRatio;
	
	public String toString() {
		String s = new String(""+id);
		s += ": "+subject;
		s += ": "+assignee;
		s += ": "+doneRatio;
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

	private void update(Issue src) {
		this.id = src.getId();
		this.status = src.getStatusName();
		this.tracker = src.getTracker().getName();
		this.subject = src.getSubject();
		this.assignee = src.getAssigneeName();
		this.author = src.getAuthorName();
		this.doneRatio = src.getDoneRatio();
		if (src.getParentId() != null) {
			this.parent_id = src.getParentId().intValue();
		}
		if (src.getEstimatedHours() != null) {
			this.estimatedHours = Math.round(src.getEstimatedHours());
		}
		this.dueDate = src.getDueDate();
		this.created = src.getCreatedOn();
		this.closed = src.getClosedOn();
		// System.out.println("subject = " + src.getTracker().getId());
		// src.getParentId()
	}
}
