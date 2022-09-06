package kgu.agent.demo.actionArgument;
import org.json.simple.JSONObject;
public class BatteryArgument {
	
	
	private int status;
	private int available;

	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		
		
		obj.put("status", status);
		obj.put("available", available);


		
		
		return obj.toJSONString();
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getAvailable() {
		return available;
	}


	public void setAvailable(int available) {
		this.available = available;
	}
	

}
