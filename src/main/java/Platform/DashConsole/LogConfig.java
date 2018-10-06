package Platform.DashConsole;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.extern.java.Log;

@Log
public class LogConfig {

	public LogConfig() {
		System.out.println("logConfig");
		Logger log0 = Logger.getLogger("Platfrom.DashConsole.*");
		Logger p = log0.getParent();
		for (Handler h : p.getHandlers()) {
			p.removeHandler(h);
		}
		p.addHandler(new com.barolab.log.ConcoleHandler());
		log0.setUseParentHandlers(false);
		Logger.getLogger(VersionCache.class.getName()).setLevel(Level.ALL);
	}

}
