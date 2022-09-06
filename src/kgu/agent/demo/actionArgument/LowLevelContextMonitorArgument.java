package kgu.agent.demo.actionArgument;

import org.json.simple.JSONObject;

public class LowLevelContextMonitorArgument {

	private String triples;
	private String objects;
	private String robots;
	private String humans;


	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();

		obj.put("triples", triples);
		obj.put("objects", objects);
		obj.put("robots", robots);
		obj.put("humans", humans);

		return obj.toJSONString();
	}


	public String getTriples() {
		return triples;
	}



	public void setTriples(String triples) {
		this.triples = triples;
	}
	
	
	public String getObjects() {
		return objects;
	}



	public void setObjects(String objects) {
		this.objects = objects;
	}



	public String getRobots() {
		return robots;
	}



	public void setRobots(String robots) {
		this.robots = robots;
	}



	public String getHumans() {
		return humans;
	}



	public void setHumans(String humans) {
		this.humans = humans;
	}


}
