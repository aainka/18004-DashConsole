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

	private void update(Issue src) {
		this.id = src.getId();
		this.status = src.getStatusName();
		this.tracker = src.getTracker().getName();
		this.subject = src.getSubject();
		this.assignee = src.getAssigneeName();
		this.author = src.getAuthorName();
		this.doneRatio = src.getDoneRatio();
		if (src.getSpentHours() != null) {
			this.spentHours = src.getSpentHours().intValue();
		}
		if (src.getTargetVersion() != null) {
			this.targetVersion = src.getTargetVersion().getName();
		}

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
