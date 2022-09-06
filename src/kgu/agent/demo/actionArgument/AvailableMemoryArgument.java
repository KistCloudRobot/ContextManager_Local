package kgu.agent.demo.actionArgument;
import org.json.simple.JSONObject;
public class AvailableMemoryArgument {
	
	
	private int used;


	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		
		
		obj.put("used", used);



		
		
		return obj.toJSONString();
	}



	public int getUsed() {
		return used;
	}



	public void setUsed(int used) {
		this.used = used;
	}


	
	

}
