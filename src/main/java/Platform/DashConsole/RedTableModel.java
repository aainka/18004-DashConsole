package Platform.DashConsole;

import java.lang.reflect.Field;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;

import Platform.util.ExcelUtil;

public class RedTableModel implements TableModel {

	final ClientConnectionManager connectionManager = RedmineManagerFactory.createDefaultConnectionManager();
	final HttpClient client = RedmineManagerFactory.getNewHttpClient("aa", connectionManager);
	private String url = "http://redmine.ericssonlg.com/redmine";
	public RedmineManager redmine = RedmineManagerFactory.createWithUserAuth(url, "ejaejeo", "ejaejeo", client);

	private List<OV_Issue> issues = null;
	private String[] coldef = new String[] { "id", "subject", "assignee", "tracker" };

	public void test() {
		try {
			loadRest();
		} catch (RedmineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// issues = ExcelUtil.read();
		// List<Issue> list = redmine.getIssueManager().getIssues("vepg_si-2018", 147);
	}

	public void loadRest() throws RedmineException {
		List<Issue> list = redmine.getIssueManager().getIssues("vepg_si-2018", 167);
		System.out.println("size=" + list.size());
		issues = OV_Issue.toList(list);
		ExcelUtil.save(issues, "2018ALL");
	}

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return coldef.length;
	}

	@Override
	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return "subject";
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return issues.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		Object anObject = issues.get(arg0);
		Object value = null;
		String colname = coldef[arg1];
		try {
			Field field = anObject.getClass().getField(colname);
			value = field.get(anObject);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new RedTableModel().test();
	}

}
