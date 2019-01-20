package Platform.DashConsole.model;

import java.util.List;

import com.barolab.util.model.BeanClass;
import com.barolab.util.model.ListUtils;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;

import Platform.DashConsole.SimpleRedmineConnector;
import lombok.extern.java.Log;

@Log
public class DAO_Issue extends ListUtils <OV_Issue> {

	private List<OV_Issue> list = null;
	SimpleRedmineConnector con = new SimpleRedmineConnector();

	public DAO_Issue( ) {
		bClass = BeanClass.getInstance(OV_Issue.class);
	}

	public List<OV_Issue> load(boolean testMode) {
		if (testMode) {
			list = (List<OV_Issue>) readExcel("C:/tmp/issues_2018ALL.xlsx",null);
		} else {
			try {
				List<Issue> list2 = con.redmine.getIssueManager().getIssues("vepg-si", 125);// 2018ALL 125, 128
				list = OV_Issue.toList(list2);
			//	sort("assignee");
				writeExcel("C:/tmp/issues_2018ALL.xlsx");
			} catch (RedmineException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<OV_Issue> getList() {
		// TODO Auto-generated method stub
		return list;
	}
	 
	@Override
	public void setList(List<OV_Issue> list) {
		this.list = (List<OV_Issue>) list;
		
	}

	@Override
	public String pivot(String opname, List<OV_Issue> list) {
		// TODO Auto-generated method stub
		return null;
	}

}
