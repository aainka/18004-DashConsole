package Platform.DashConsole;

import lombok.Data;

@Data
public class ConfigHost {
 	public static String path = "C:/tmp/RedmineManager/config/";
//	public static String path = "/root/dashboard/";
	public static String lcmfile = path+"OtherNote.xlsx";
	public static String userfile = path+"Info.xlsx";
}
