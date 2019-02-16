package Platform.DashConsole.model;

import com.barolab.util.ExcelUtils;
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
	private java.util.Date created;

	static public BeanClass bClass = BeanClass.getInstance(OV_LCM.class);
	static public DAO_LCM dao = new DAO_LCM();
	static public ExcelUtils excelUtils = new ExcelUtils();

	public String toString() {
		return "issueNO=" + issueNo + ", status=" + status + ", subject=" + subject;
	}
	
	public boolean equals(Object obj) {
		System.out.println("eeequal called");
		if ( this == obj ) return true;
		
		return true;
	}
}
