package kgu.agent.demo.actionArgument;

import org.json.simple.JSONObject;

public class ReasoningQueryArgument {
	
	private String sender;
	private String queryGL;
	private String queryToProlog;
	private String queryResult;
	private double reasoningTime;

	
	
	public ReasoningQueryArgument(String sender, String queryGL) {
		this.sender = sender;
		this.queryGL = queryGL;
		// TODO Auto-generated constructor stub
	}



	public String getSender() {
		return sender;
	}



	public void setSender(String sender) {
		this.sender = sender;
	}



	public String getQueryGL() {
		return queryGL;
	}



	public void setQueryGL(String queryGL) {
		this.queryGL = queryGL;
	}



	public String getQueryToProlog() {
		return queryToProlog;
	}



	public void setQueryToProlog(String quertyToProlog) {
		this.queryToProlog = quertyToProlog;
	}



	public String getQueryResult() {
		return queryResult;
	}



	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}



	public double getReasoningTime() {
		return reasoningTime;
	}



	public void setReasoningTime(double reasoningTime) {
		this.reasoningTime = reasoningTime;
	}
	
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		
	
		obj.put("sender", sender);
		obj.put("queryGL", queryGL);
		obj.put("queryToProlog", queryToProlog);
		obj.put("queryResult", queryResult);
		obj.put("reasoningTime", reasoningTime);

		
		
		return obj.toJSONString();
	}
	
	


	
	

}
