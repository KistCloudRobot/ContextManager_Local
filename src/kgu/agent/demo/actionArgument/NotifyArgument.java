package kgu.agent.demo.actionArgument;

import org.json.simple.JSONObject;

public class NotifyArgument {
	
	
	private String notify;
	private String subject;
	private String predicate;
	private String object;
	private String subscriber;
	private double reasoningTime;
	
	public NotifyArgument(String subscriber,String subject, String predicate, String object) {
		// TODO Auto-generated constructor stub
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
		this.subscriber = subscriber;
	}

	
	public String getNotify() {
		return notify;
	}




	public void setNotify(String notify) {
		this.notify = notify;
	}




	public String getSubject() {
		return subject;
	}




	public void setSubject(String subject) {
		this.subject = subject;
	}




	public String getPredicate() {
		return predicate;
	}




	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}




	public String getObject() {
		return object;
	}




	public void setObject(String object) {
		this.object = object;
	}




	public double getReasoningTime() {
		return reasoningTime;
	}




	public void setReasoningTime(double reasoningTime) {
		this.reasoningTime = reasoningTime;
	}







	public String getSubscriber() {
		return subscriber;
	}


	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		

		obj.put("notify", notify);
		obj.put("subscriber", subscriber);
	//	obj.put("reasoningTime", reasoningTime);
	
		
		return obj.toJSONString();
	
	}
}
