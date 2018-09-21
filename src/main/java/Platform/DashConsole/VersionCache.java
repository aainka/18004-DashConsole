package Platform.DashConsole;

import java.util.LinkedList;
import java.util.List;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.Version;

import lombok.Data;

@Data
public class VersionCache extends LinkedList<Version> {

	private RedmineManager redmine;

	public VersionCache(RedmineManager redmine) {
		this.redmine = redmine;
	}

	public void init() {
		try {
			List<Project> projects = redmine.getProjectManager().getProjects();
			for ( Project project : projects) {
				System.out.println("Project = "+project);
			}
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initByProjectId(int projectId) {
		List<Version> versions;
		try {
			versions = redmine.getProjectManager().getVersions(81);
			this.addAll(versions);
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Version get(String versionName) {
		for (Version v : this) {
			System.out.println(" list.in " + v.getName());
			if (v.getName().equals(versionName)) {
				return v;
			}
		}
		return null;
	}

}
