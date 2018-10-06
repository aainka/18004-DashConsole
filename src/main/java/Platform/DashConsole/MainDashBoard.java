package Platform.DashConsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.poi.ss.usermodel.Cell;

import com.barolab.util.BeanAttribute;
import com.barolab.util.ExcelObjectReader;
import com.barolab.util.ExcelObjectWriter;
import com.barolab.util.LogUtil;
import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.TimeEntryManager;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.SavedQuery;
import com.taskadapter.redmineapi.bean.TimeEntry;
import com.taskadapter.redmineapi.bean.Version;
import com.taskadapter.redmineapi.internal.ResultsWrapper;

import lombok.extern.java.Log;

@Log
public class MainDashBoard {
	private LogConfig x = new LogConfig();
	final ClientConnectionManager connectionManager = RedmineManagerFactory.createDefaultConnectionManager();
	final HttpClient client = RedmineManagerFactory.getNewHttpClient("aa", connectionManager);
	public RedmineManager redmine;

	private String url = "http://redmine.ericssonlg.com/redmine";
	private String outDir = "C://tmp//";
	private ExcelObjectWriter excelObjectWriter = null;

	private VersionCache versionCache = null;

	public void login() {

		redmine = RedmineManagerFactory.createWithUserAuth(url, "ejaejeo", "ejaejeo", client);
		excelObjectWriter = new ExcelObjectWriter() {
			@Override
			public int format(Cell cell, BeanAttribute attr, Object value) {
				if (attr.getName().equals("id")) {
					try {
						int id = (int) value;
						setHyperlink(cell, "http://redmine.ericssonlg.com/redmine/issues/" + id, id);
						return 1;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
				return 0;
			}
		};
	}

	public void initInfo() {
		String infopath = outDir + "RedmineManager\\Config\\Info.xlsx";
		versionCache = VersionCache.builder()
				.redmine(redmine)
				.infopath(infopath)
				.build();
		log.info("init Cache ###############");

	

	}

	public void test() {
		login();
		List<SavedQuery> savedQueries;
		try {
			List<Issue> list = redmine.getIssueManager().getIssues("vepg_si-2018", 147);
			List<OV_Issue> nlist = OV_Issue.toList(list);
			excelObjectWriter.write(nlist, OV_Issue.class, "sheet1", outDir + "SR120-NEW.xlsx");
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test21() {
		login();
		try {
			List<Project> list = redmine.getProjectManager().getProjects();
			for (Project project : list) {
				System.out.println("list.project = " + project.getIdentifier());
				if (project.getIdentifier().indexOf("190") == 0) {
					System.out.println("change-->" + project.getId());
					project.setName("NSA_R100");

					project.setIdentifier("NSA_R100");

					redmine.getProjectManager().update(project);
				}
			}
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test2() {
		List<OV_Issue> nlist = (List<OV_Issue>) new ExcelObjectReader().read(OV_Issue.class, null,
				outDir + "SR120-NEW.xlsx");
		login();
		excelObjectWriter.write(nlist, OV_Issue.class, "sheet1", outDir + "SR120-" + LogUtil.getToday() + ".xlsx");
	}

	public void test3() {

		login();

		/**
		 * loading redmine configuration
		 */
		String filename = outDir + "RedmineManager\\Config\\Info.xlsx";
		List<OV_QueryInfo> queryList = (List<OV_QueryInfo>) new ExcelObjectReader().read(OV_QueryInfo.class, "Query",
				filename);

		/**
		 * loading issues for each query
		 */
		for (OV_QueryInfo qinfo : queryList) {
			// System.out.println("\n" + LogUtil.dump(qinfo));
			System.out.println("\n" + qinfo);
			try {
				List<Issue> list = redmine.getIssueManager().getIssues(qinfo.getProjectId(), qinfo.getQueryNumber());
				System.out.println("Load.Count = " + list.size());
				qinfo.setIssues(OV_Issue.toList(list));
				excelObjectWriter.write(qinfo.getIssues(), OV_Issue.class, qinfo.getOutfile_sheetname(),
						outDir + "RedmineManager\\" + qinfo.getMemo() + "_" + LogUtil.getToday() + ".xlsx");
			} catch (RedmineException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void test4() {

		login();

		IssueController issueController = new IssueController();
		issueController.setRedmine(redmine);
		issueController.dump(19734);

		TimeEntryManager timeEntryManager = redmine.getTimeEntryManager();
		List<TimeEntry> totalTimeEntry = new ArrayList<TimeEntry>();

		try {
			// List<TimeEntry> elements = timeEntryManager.getTimeEntries();
			final Map<String, String> params = new HashMap<>();
			int totalFound = 1;
			for (int offset = 0; offset < totalFound; offset += 90) {
				params.put("limit", "90");
				params.put("offset", "" + offset);
				params.put("spent_on", "<2018-07-01|2018-12-30>");
				ResultsWrapper<TimeEntry> resultList = timeEntryManager.getTimeEntries(params);
				System.out.println("* time.offset=" + offset);
				// System.out.println(" time.result=" + resultList.getResultsNumber());
				// System.out.println(" time.max=" + resultList.getTotalFoundOnServer());
				System.out.println("  time.list=" + totalTimeEntry.size() + "/" + totalFound);
				totalFound = resultList.getTotalFoundOnServer();
				// OV_TimeEntry.transform(resultList.getResults()); // for test.
				totalTimeEntry.addAll(resultList.getResults());
				// totalFound = 200;
			}

			List<OV_TimeEntry> list = OV_TimeEntry.transform(totalTimeEntry);

			excelObjectWriter.write(list, OV_TimeEntry.class, "logTime",
					outDir + "RedmineManager\\logTime(" + list.size() + ")_" + LogUtil.getToday() + ".xlsx");

			System.out.println("LogTime process ending.");
			;
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateVersion() throws RedmineException {
		log.setLevel(java.util.logging.Level.ALL);
		log.setUseParentHandlers(false);

		log.info("update version ###############");
		initInfo();
		login();
		IssueManager mgr = redmine.getIssueManager();
//		/**
//		 * List of Status
//		 */
//		List<IssueStatus> statusList = mgr.getStatuses();
//		for (IssueStatus s : statusList) {
//			System.out.println("StatusList name=" + s.getName() + " id=" + s.getId());
//
//		}
//
//		MapVersion mapVersion = new MapVersion(redmine);
//
//		// redmine.getProjectManager().getProjects(){
//
//		System.out.println("update....");

		// int projectID =

		versionCache.setRedmine(redmine);
		versionCache.init();
		versionCache.initByProjectId(81);
		List<Version> versions = redmine.getProjectManager().getVersions(81);
		for (Version va : versions) {
			System.out.println(va);
		}

		Issue issue = mgr.getIssueById(34563);
		Version version = issue.getTargetVersion();
		System.out.println("Project = " + issue.getProjectId());
		System.out.println("Version = " + versionCache.get("SR999"));

		issue.setTargetVersion(versionCache.get("SR999"));
		// mgr.update(issue);

// Version [id=182, name=SR999]
		// Version [id=201, name=SR120]
//		issue.setStatusId(2);
//		System.out.println("--- id=" + issue.getStatusId());
//		issue.setStatusName("Closed");
//
//		System.out.println("--- id2=" + issue.getStatusId());
//		// mgr.update(issue);

	}

	public static void main(String[] args) {
//		try {
//			new MainDashBoard().updateVersion();
//		} catch (RedmineException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		new MainDashBoard().test3();
		new MainDashBoard().test4();
//		try {
//			new MainDashBoard().updateVersion();
//		} catch (RedmineException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
