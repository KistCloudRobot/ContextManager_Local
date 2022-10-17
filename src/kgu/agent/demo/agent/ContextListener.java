package kgu.agent.demo.agent;

import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.ltm.DataSource;




public class ContextListener extends ArbiAgent {


	public static final String JMS_BROKER_URL = "tcp://127.0.0.1:61616";
	public static final String TM_ADDRESS = "agent://www.arbi.com/taskManager";
	public static final String CL_ADDRESS = "agent://www.arbi.com/listenerManager";
	public static final String CM_ADDRESS = "agent://www.arbi.com/contextManager";
	public static final String KM_ADDRESS = "agent://www.arbi.com/knowledgeManager";
	public static final String BEHAVIOUR_INTERFACE_ADDRESS = "agent://www.arbi.com/behaviourInterface";
	public static final String PERCEPTION_ADRESS = "agent://www.arbi.com/perception";
	public static final String ACTION_ADDRESS = "agent://www.arbi.com/action";
	public static final String TASKLOG_ADDRESS = "agent://www.arbi.com/TaskLog";
	public static final String DC_URL = "dc://testdcCM";
	
	String object_message = "";
	String robot_message = "";
	
	DataSource ds;
	

	public ContextListener() {

		executeAgent();

	}
	
	public void updateFact(String message) {
		ds.updateFact(message);
		
	}	

	public void executeAgent() {
		ArbiAgentExecutor.execute(JMS_BROKER_URL, CL_ADDRESS, this, BrokerType.ZEROMQ);
	}
	

	@Override
	public void onStart() {
		ds = new DataSource();
		ds.connect("tcp://localhost:61616", "dc://CL", BrokerType.ZEROMQ);

		System.out.println("======Start Dummy_Listener_Manager======");
		
		String sen;

		sen = "(Object_position 0 0 0 0 0 0 0 0 0 0 0)";
		ds.assertFact(sen);
		
		
		sen = "(Robot_position 0 0 0 0 0 0 0 0 0 0)";
		ds.assertFact(sen);
	}
	
	public void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (Exception e) {
		}
	}
	

	static public void main(String args[]) {

		new ContextListener();

	}

}