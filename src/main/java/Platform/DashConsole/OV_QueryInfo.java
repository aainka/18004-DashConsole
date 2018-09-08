package Platform.DashConsole;

import java.util.List;

import lombok.Data;

@Data

public class OV_QueryInfo {
	public int id;
	public String projectId;
	public int queryNumber;
	public String memo;
	public String outfile_sheetname;
	public transient List<OV_Issue> issues;
}
