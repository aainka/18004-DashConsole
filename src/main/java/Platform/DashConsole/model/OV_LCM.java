package Platform.DashConsole.model;

import com.barolab.util.model.BeanClass;

import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class OV_LCM {
	private int issueNo;
	private String status;
	private String severity;
	private String subject;
	private String designer;
	private String memo;
	private String LCM;
	static public BeanClass bClass = BeanClass.getInstance(OV_LCM.class);
	static public DAO_LCM dao = new DAO_LCM();
	
	public String toString() {
		return "issueNO="+issueNo+", status="+status+", subject="+subject;
	}
}
