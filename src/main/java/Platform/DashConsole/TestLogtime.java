package Platform.DashConsole;

import java.util.List;

import com.barolab.util.model.BeanClass;

import lombok.extern.java.Log;

@Log
public class TestLogtime {

	private LogConfig x = new LogConfig();
	List<OV_TimeEntry> list;

	public void test() {
		BeanClass<OV_TimeEntry> LogtimeHelper = new BeanClass<OV_TimeEntry>(OV_TimeEntry.class);
		list = LogtimeHelper.readExcel("C://tmp/test_logtime.xlsx", "logtime");
		dump();
	//	LogtimeHelper.sort(list, "userName","hours");
		LogtimeHelper.writeExcel("C://tmp/test_logtime_r2.xlsx", "logtime", list);
		
		System.out.println("****End");
	}

	public void dump() {
		int count = 0;
		for (OV_TimeEntry te : list) {
			log.info("" + te.getCreatedOn());
			if (count++ > 20)
				break;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestLogtime().test();
	}

}
