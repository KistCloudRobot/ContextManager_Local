package kgu.agent.demo.actionArgument;
import org.json.simple.JSONObject;
public class TripleCountArgument {
	
	
	private String triple;

	public String getTriple() {
		return triple;
	}

	public void setTriple(String triple) {
		this.triple = triple;
	}
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		
		
		obj.put("triple", triple);


		
		
		return obj.toJSONString();
	}
	

}
