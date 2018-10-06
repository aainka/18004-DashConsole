package Platform.DashConsole;

import java.util.List;

import com.barolab.util.ExcelObjectReader;

import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class OV_ProjectInfo {
	private int no;
	private String name;

	static public List<OV_ProjectInfo> list = null;

	static public List<OV_ProjectInfo> load(String filename) {
		if (list == null) {
			list = (List<OV_ProjectInfo>) new ExcelObjectReader().read(OV_ProjectInfo.class, "Project", filename);
		}
		for (OV_ProjectInfo projectInfo : list) {
			log.finer(projectInfo.toString());
		}
		return list;
	}
}
