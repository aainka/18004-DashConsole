package Platform.DashConsole;

import java.awt.List;
import java.util.HashMap;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.SavedQuery;
import com.taskadapter.redmineapi.bean.Version;

public class MapVersion extends HashMap<String, Integer> {
	
	public MapVersion(RedmineManager redmine) {
		
		try {
			java.util.List<Project> list =  redmine.getProjectManager().getProjects();
			for ( Project p : list) {
				java.util.List<Version> listVersion = redmine.getProjectManager().getVersions(p.getId());
				for ( Version v : listVersion) {
					System.out.println("name = "+v.getName()+" id="+v.getId());
					put(v.getName(), v.getId());
				}
//				System.out.println("P.getIDt="+p.getIdentifier());
//				java.util.List<SavedQuery> listQuery = redmine.getIssueManager().getSavedQueries(p.getName());
//				for ( SavedQuery q : listQuery) {
//					System.out.println("name = "+q.getName()+" id="+q.getId());
//					//put(v.getName(), v.getId());
//				}
			}
			System.out.println("size = "+this.size());
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void init() {
		
	}

}
