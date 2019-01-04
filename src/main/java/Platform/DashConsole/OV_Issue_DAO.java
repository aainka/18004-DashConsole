package Platform.DashConsole;

import java.util.List;

import com.barolab.util.model.BeanClass;
import com.barolab.util.model.ListUtils;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;

import lombok.extern.java.Log;

@Log
public class OV_Issue_DAO extends ListUtils {

	private List<OV_Issue> list = null;
	SimpleRedmineConnector con = new SimpleRedmineConnector();

	public OV_Issue_DAO(Class clazz) {
		bClass = BeanClass.getInstance(clazz);
	}

	public void load(boolean testMode) {

		if (testMode) {
			list = (List<OV_Issue>) readExcel("C:/tmp/issues_2018ALL.xlsx");
		} else {
			try {
				List<Issue> list2 = con.redmine.getIssueManager().getIssues("vepg-si", 125);// 2018ALL 125, 128
				list = OV_Issue.toList(list2);
				sort("assignee");
				writeExcel("C:/tmp/issues_2018ALL.xlsx");
			} catch (RedmineException e) {
				e.printStackTrace();
			}
		}
		log.info("load OV_Issue.cont =" + list.size());
	}

	@Override
	public List<?> getList() {
		// TODO Auto-generated method stub
		return list;
	}



}
