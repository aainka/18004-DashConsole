package Platform.DashConsole.model;

import java.util.List;

import com.barolab.util.model.BeanClass;
import com.barolab.util.model.ListUtils;

import Platform.DashConsole.ConfigHost;
import lombok.Data;
import lombok.extern.java.Log;

@Log
@Data
public class DAO_LCM extends ListUtils<OV_LCM> {
	
	private String filename = ConfigHost.lcmfile;
	private List<OV_LCM> list;

	public DAO_LCM( ) {
		bClass = BeanClass.getInstance(OV_LCM.class);
	}

	public List<OV_LCM> load(boolean testMode) {
		list = (List<OV_LCM>) readExcel(filename,null);
		log.info("load OV_LCM.cont =" + list.size());
		return list;
	}

	public void save() {
		sort("issueNo");
		this.writeExcel("C:/tmp/RedmineManager/OtherNote/OtherNote2.xlsx");
	}

	@Override
	public String pivot(String opname, List<OV_LCM> list) {
		// TODO Auto-generated method stub
		return null;
	}
}
