package Platform.DashConsole;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;

import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;

public class SimpleRedmineConnector {

	final ClientConnectionManager connectionManager = RedmineManagerFactory.createDefaultConnectionManager();
	final HttpClient client = RedmineManagerFactory.getNewHttpClient("aa", connectionManager);
	private String url = "http://redmine.ericssonlg.com/redmine";
	public RedmineManager redmine;

	public SimpleRedmineConnector() {
		redmine = RedmineManagerFactory.createWithUserAuth(url, "ejaejeo", "ejaejeo", client);
	}
}
