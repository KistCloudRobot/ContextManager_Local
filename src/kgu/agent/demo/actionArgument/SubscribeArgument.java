package kgu.agent.demo.actionArgument;

import org.json.simple.JSONObject;

public class SubscribeArgument {
	private String subscriber;
	private String subscribeGL;
	private String subscribeGLToProlog;

	public SubscribeArgument(String subscriber, String subscribeGL) {
		this.subscriber = subscriber;
		this.subscribeGL = subscribeGL;
		// TODO Auto-generated constructor stub
	}


	public String getSubscriber() {
		return subscriber;
	}


	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}


	public String getSubscribeGL() {
		return subscribeGL;
	}


	public void setSubscribeGL(String subscribeGL) {
		this.subscribeGL = subscribeGL;
	}


	public String getSubscribeGLToProlog() {
		return subscribeGLToProlog;
	}


	public void setSubscribeGLToProlog(String subscribeGLToProlog) {
		this.subscribeGLToProlog = subscribeGLToProlog;
	}

	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		
		obj.put("subscriber", subscriber);
		obj.put("subscribeGL", subscribeGL);
		obj.put("subscribeGLToProlog", subscribeGLToProlog);

		return obj.toJSONString();
	}
	

}
