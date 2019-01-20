package Platform.DashConsole.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.taskadapter.redmineapi.bean.TimeEntry;

import lombok.Data;

@Data
public class OV_TimeEntry {

	private float hours;
	private int weeknum;
	private String userName;
	private int issueId;
	private String subject;
	private String lcm;
	private Date createdOn;
	private Date spentOn;
	private String comment;
	private String projectName;
	

	public static List<OV_TimeEntry> transform(List<TimeEntry> list) {
		List<OV_TimeEntry> nList = new ArrayList<OV_TimeEntry>();
		for (TimeEntry src : list) {
			OV_TimeEntry nIssue = new OV_TimeEntry();
			nIssue.update(src);
			nList.add(nIssue);
		}
		return nList;
	}

	private void update(TimeEntry s) {
		hours = s.getHours();
		userName = s.getUserName();
		try {
			issueId = s.getIssueId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		comment = s.getComment();
		createdOn = s.getCreatedOn();
		projectName = s.getProjectName();
		{
			spentOn = s.getSpentOn();
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(spentOn.getTime());
			weeknum = cal.get(Calendar.WEEK_OF_YEAR);
		}
	}

}
