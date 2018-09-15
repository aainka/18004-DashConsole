package Platform.DashConsole;

import java.util.List;

import com.barolab.util.ExcelObjectReader;

import lombok.Data;

@Data

public class OV_User {
	private int no;
	private String hname;
	private String ename;
	private int id;
	private String role;
	static public List<OV_User> list = null;

	static public List<OV_User> load(String filename) {
		if (list == null) {
			 list = (List<OV_User>) new ExcelObjectReader().read(OV_User.class, "User",
					filename);
		}
		for ( OV_User user: list) {
			System.out.println(user);
		}
		return list;
	}
}
