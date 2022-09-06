package kgu.agent.demo.actionArgument;
import org.json.simple.JSONObject;
public class WorkingMemory_MonitorsArgument  {
	
	
	private String monitor;
	
	public WorkingMemory_MonitorsArgument(String monitorCount) {
		// TODO Auto-generated constructor stub
		this.monitor = monitorCount;
	}
	

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		
		
		obj.put("monitor", monitor);
	

		
		
		return obj.toJSONString();
	
	}

}
