package Platform.DashConsole;

import java.util.List;

import com.barolab.util.model.BeanClass;
import com.barolab.util.model.ListUtils;

import lombok.extern.java.Log;

@Log
public class OV_LCM_DAO extends ListUtils {
	List<OV_LCM> list;
	
	public OV_LCM_DAO(Class clazz) {
		bClass = BeanClass.getInstance(clazz);
	}

	public List<OV_LCM> load(boolean testMode) {
		list = (List<OV_LCM>) readExcel("C:/tmp/RedmineManager/OtherNote/OtherNote.xlsx");
		log.info("load OV_LCM.cont =" + list.size());
		return list;
	}

	@Override
	public List<?> getList() {
		return list;
	}
	
	public static void main9( String[] args) {
		new OV_LCM_DAO(OV_LCM.class).load(true);
	}
}
