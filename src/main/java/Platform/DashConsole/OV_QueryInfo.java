package Platform.DashConsole;

import java.util.List;

import Platform.DashConsole.model.OV_Issue;
import lombok.Data;

@Data

public class OV_QueryInfo {
	private int id;
	private String projectId;
	private int queryNumber;
	private String memo;
	private String outfile_sheetname;
	private transient List<OV_Issue> issues;
}
