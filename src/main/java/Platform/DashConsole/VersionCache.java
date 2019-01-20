package Platform.DashConsole;

import java.util.LinkedList;
import java.util.List;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.Version;

import Platform.DashConsole.model.OV_User;
import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Builder
@Log
public class VersionCache extends LinkedList<Version> {

	private RedmineManager redmine;
	private String infopath;

	public VersionCache(RedmineManager redmine, String infopath) {
		this.redmine = redmine;
		this.infopath = infopath;
		log.info("User/ Project/ Loading");
		OV_User.load(infopath);
		OV_ProjectInfo.load(infopath);
	}

	public void init() {
		try {

			List<Project> projects = redmine.getProjectManager().getProjects();
			for (Project project : projects) {
				System.out.println("Project = " + project);
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
			// System.out.println(" list.in " + v.getName());
			if (v.getName().equals(versionName)) {
				return v;
			}
		}
		return null;
	}

}
