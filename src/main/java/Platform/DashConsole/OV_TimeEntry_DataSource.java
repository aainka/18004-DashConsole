package Platform.DashConsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barolab.util.model.BeanClass;
import com.barolab.util.model.ListUtils;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.TimeEntryManager;
import com.taskadapter.redmineapi.bean.TimeEntry;
import com.taskadapter.redmineapi.internal.ResultsWrapper;

public class OV_TimeEntry_DataSource extends ListUtils {
	
	List<OV_TimeEntry> list = null;
	SimpleRedmineConnector con = new SimpleRedmineConnector();
	
	public OV_TimeEntry_DataSource(Class clazz) {
		bClass = BeanClass.getInstance(clazz);
	}

	public void load(boolean testMode) {
		if (testMode) {
			list = (List<OV_TimeEntry>)  readExcel("C:/tmp/logtime_2018ALL.xlsx" );
		} else {
			TimeEntryManager timeEntryManager = con.redmine.getTimeEntryManager();
			List<TimeEntry> totalTimeEntry = new ArrayList<TimeEntry>();
			try {
				final Map<String, String> params = new HashMap<>();
				int totalFound = 1;
				for (int offset = 0; offset < totalFound; offset += 90) {
					params.put("limit", "90");
					params.put("offset", "" + offset);
					params.put("spent_on", "<2019-1-1|2019-12-30>");
					ResultsWrapper<TimeEntry> resultList = timeEntryManager.getTimeEntries(params);
					System.out.println("* time.offset=" + offset);
					System.out.println("  time.list=" + totalTimeEntry.size() + "/" + totalFound);
					totalFound = resultList.getTotalFoundOnServer();
					totalTimeEntry.addAll(resultList.getResults());
				}
				list = OV_TimeEntry.transform(totalTimeEntry);
			 writeExcel(  "C:/tmp/logtime_2018ALL.xlsx");
				System.out.println("LogTime process ending.");
			} catch (RedmineException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<OV_TimeEntry> getList() {
		return list;
	}

}
