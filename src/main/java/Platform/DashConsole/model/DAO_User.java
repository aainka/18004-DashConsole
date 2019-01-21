package Platform.DashConsole.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barolab.util.model.BeanClass;
import com.barolab.util.model.ListUtils;

import Platform.DashConsole.ConfigHost;
import lombok.Data;
import lombok.extern.java.Log;

@Log
@Data
public class DAO_User extends ListUtils<OV_User> {

	private String filename =  ConfigHost.userfile;
	private List<OV_User> list;
	private Map<String, OV_User> map = new HashMap<String, OV_User>();

	public DAO_User() {
		bClass = BeanClass.getInstance(OV_User.class);
	}
	
	@Override
	public List<OV_User> load(boolean cacheMode) {
		list = (List<OV_User>) readExcel(filename, "User");
		for (OV_User user : list) {
			map.put(user.getEname(), user);
			log.fine( "put = "+user.getEname());
		}
		return list;
	}

	public static void main(String[] args) {
		new DAO_User().load(true);
	}

	public void convName(List<OV_Issue> list) {
		if ( list == null) {
			return;
		}
		for (OV_Issue issue : list) {
			OV_User user = map.get(issue.getAssignee());
			if (user != null) {
				issue.setAssignee(user.getHname());
			} else {
				log.info("missing = "+issue.getAssignee());
			}
		}

	}

 
	public void convName2(List<OV_TimeEntry> list) {
		for (OV_TimeEntry te : list) {
			OV_User user = map.get(te.getUserName());
			if (user != null) {
				te.setUserName( user.getHname());
			} else {
				log.info("missing = "+te.getUserName());
			}
		}
		
	}

	@Override
	public String pivot(String opname, List<OV_User> list) {
		// TODO Auto-generated method stub
		return null;
	}

//	public void save() {
//		sort("issueNo");
//		this.writeExcel("C:/tmp/RedmineManager/OtherNote/OtherNote2.xlsx");
//	}

//	static public List<OV_UserInfo> load(String filename) {
//	if (list == null) {
//		list = (List<OV_UserInfo>) new ExcelObjectReader().read(OV_UserInfo.class, "User", filename);
//	}
//	log.info("load.count="+list.size());
//	return list;
//}
}
