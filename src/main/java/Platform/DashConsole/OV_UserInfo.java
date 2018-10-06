package Platform.DashConsole;

import java.util.List;

import com.barolab.util.ExcelObjectReader;

import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class OV_UserInfo {
	private int no;
	private String hname;
	private String ename;
	private int id;
	private String role;
	static public List<OV_UserInfo> list = null;

	static public List<OV_UserInfo> load(String filename) {
		if (list == null) {
			list = (List<OV_UserInfo>) new ExcelObjectReader().read(OV_UserInfo.class, "User", filename);
		}
		log.info("load.count="+list.size());
		return list;
	}
}
