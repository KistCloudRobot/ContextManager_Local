package kgu.agent.demo.actionArgument;

import org.json.simple.JSONObject;

public class GUIArgument {

	private LowLevelContextMonitorArgument llcm;

	private ContextOntologyMonitorArgument com;
	

	@Override
	public String toString() {
		JSONObject obj = new JSONObject();

		obj.put("llcm", llcm.toString());
		obj.put("com", com.toString());

		return obj.toJSONString();
	}
	
	
	

	public LowLevelContextMonitorArgument getLlcm() {
		return llcm;
	}


	public void setLlcm(LowLevelContextMonitorArgument llcm) {
		this.llcm = llcm;
	}


	public ContextOntologyMonitorArgument getCom() {
		return com;
	}


	public void setCom(ContextOntologyMonitorArgument com) {
		this.com = com;
	}



}
