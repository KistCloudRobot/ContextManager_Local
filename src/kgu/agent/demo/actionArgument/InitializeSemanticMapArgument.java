package kgu.agent.demo.actionArgument;

public class InitializeSemanticMapArgument {
	
	private String sender;
	private String sendGL;
	
	
	public InitializeSemanticMapArgument(String sender, String sendGL) {
		this.sender = sender;
		this.sendGL = sendGL;
		// TODO Auto-generated constructor stub
	}
	
	public String getSender() {
		return sender;
	}

	public String getGL() {
		return sendGL;
	}
}
