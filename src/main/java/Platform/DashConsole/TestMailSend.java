package Platform.DashConsole;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.barolab.MailApiClient;
import com.barolab.OV_MailContent;
import com.barolab.util.LogUtil;

public class TestMailSend {

	public void test() {
		MailApiClient mailApi = new MailApiClient();
		List<OV_MailContent> list = new LinkedList<OV_MailContent>();
		OV_MailContent item = new OV_MailContent();
		item.subject = "From 13F."+LogUtil.getToday();
		item.message = "kkkk";
		list.add(item);
		try {
			mailApi.insert(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TestMailSend().test();

	}

}
