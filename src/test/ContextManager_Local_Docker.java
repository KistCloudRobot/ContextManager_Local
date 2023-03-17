package test;

import kgu.agent.demo.agent.Local_CM;

public class ContextManager_Local_Docker {
	public static void main(String[] args) {
		String brokerAddress = System.getenv("BROKER_ADDRESS");
		String stringPort = System.getenv("BROKER_PORT");
		int port = Integer.parseInt(stringPort);
		
		Local_CM contextManager = new Local_CM(brokerAddress,port);
	}	
}
