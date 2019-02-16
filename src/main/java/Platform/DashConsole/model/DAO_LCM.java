package Platform.DashConsole.model;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.barolab.util.ExcelUtils;
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
	// private ExcelUtils<OV_LCM> excelUtils = new ExcelUtils<OV_LCM>();

	public DAO_LCM() {
		bClass = BeanClass.getInstance(OV_LCM.class);
	}

	public void test() {
		try {
			list = OV_LCM.excelUtils.read(this.bClass, "Sheet1", ConfigHost.lcmfile);
			OV_LCM.excelUtils.addRedmineNumberLinke(1);
//			for (OV_LCM p : list) {
//				p.setDesigner("second designer");
//			}
//			excelUtils.append(list);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<OV_LCM> load(boolean testMode) {
		try {
			list = OV_LCM.excelUtils.read(this.bClass, "Sheet1", ConfigHost.lcmfile);
			OV_LCM.excelUtils.addRedmineNumberLinke(1);
			log.info("load OV_LCM.cont =" + list.size());
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	 
	public void save() {
		sort("issueNo");
	//	this.writeExcel("C:/tmp/RedmineManager/config/OtherNote2.xlsx");
	}

	@Override
	public String pivot(String opname, List<OV_LCM> list) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main99(String[] args) {
		new DAO_LCM().test();
	}


}
