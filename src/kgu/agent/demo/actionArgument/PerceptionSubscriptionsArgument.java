package kgu.agent.demo.actionArgument;
import org.json.simple.JSONObject;
public class PerceptionSubscriptionsArgument {
	
	private String perception="0";

	
	
	
	
	public PerceptionSubscriptionsArgument(String perception) {
		this.perception = perception;
		// TODO Auto-generated constructor stub
	}
	public String getPerception() {
		return perception;
	}

	public void setPerception(String perception) {
		this.perception = perception;
	}
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		
		
		obj.put("perception", perception);


		
		
		return obj.toJSONString();

	}

}
