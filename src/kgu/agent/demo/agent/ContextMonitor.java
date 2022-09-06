package kgu.agent.demo.agent;

import kgu.agent.demo.actionArgument.NotifyArgument;
import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.agent.logger.AgentAction;
import kr.ac.uos.ai.arbi.agent.logger.LoggerManager;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kr.ac.uos.ai.arbi.model.GeneralizedList;

public class ContextMonitor extends ArbiAgent {

	private GeneralizedList eventGL;
	private String monitoredFact;

	public static final String CM_MONITOR_ADDRESS = "agent://www.arbi.com/contextMonitorManager";
	public static final String JMS_BROKER_URL = "tcp://127.0.0.1:61616";
	public static final String TM_ADDRESS = "agent://www.arbi.com/taskManager";
	public static final String CM_ADDRESS = "agent://www.arbi.com/contextManager";
	public static final String KM_ADDRESS = "agent://www.arbi.com/knowledgeManager";
	public static final String BEHAVIOUR_INTERFACE_ADDRESS = "agent://www.arbi.com/behaviourInterface";
	public static final String PERCEPTION_ADRESS = "agent://www.arbi.com/perception";
	public static final String ACTION_ADDRESS = "agent://www.arbi.com/action";
	public static final String TASKLOG_ADDRESS = "agent://www.arbi.com/TaskLog";
	public static final String DC_URL = "dc://testdcCM";
	public AgentAction notifyAction;

	public ContextMonitor() {
		System.out.println("Create ContextMonitor******************");
		executeAgent();
	}

	public void executeAgent() {

		ArbiAgentExecutor.execute(JMS_BROKER_URL, CM_MONITOR_ADDRESS, this, Broker.ZEROMQ);
//		NotifyAction action = new NotifyAction();
//		notifyAction = new AgentAction("sendLogNotify", action);
//		LoggerManager.getInstance().registerAction("notify", notifyAction, LogTiming.Later);

	}

	public void onMonitor(String address, String S, String P, String O) {
		
	

		String fact = S + " " + P + " " + O;
		if(P.contains("'")){
			P.replaceAll("'","");
		}
		//P = P.substring(1, P.length() - 1);
		NotifyArgument argument = new NotifyArgument(address,S, P, O);
		String notify = (String) LoggerManager.getInstance().getAction("notify").execute(argument);
	
		
		/*
		(rule (fact (arbi:insideAreaOfRoom "arbi:turtlebot01" "arbi:OfficeRoom_002")) --> (notify (arbi:insideAreaOfRoom "arbi:turtlebot01" "arbi:OfficeRoom_002")) (id 5))
(rule (fact (arbi:inFrontAreaOfRoom "arbi:turtlebot01" "arbi:OfficeRoom_002")) --> (notify (arbi:inFrontAreaOfRoom "arbi:turtlebot01" "arbi:OfficeRoom_002")) (id 4))
(rule (fact (arbi:insideAreaOfRoom "arbi:turtlebot01" "arbi:OfficeRoom_001")) --> (notify (arbi:insideAreaOfRoom "arbi:turtlebot01" "arbi:OfficeRoom_001")) (id 3))
(rule (fact (arbi:inFrontAreaOfRoom "arbi:turtlebot01" "arbi:OfficeRoom_001")) --> (notify (arbi:inFrontAreaOfRoom "arbi:turtlebot01" "arbi:OfficeRoom_001")) (id 2))
		 * 
		 * 
		 */
		notify(address.split("'")[1], notify);

	

	}

	@Override
	public void onStart() {
		DataSource dc = new DataSource();
		dc.connect("tcp://localhost:61616", "dc://CMM", Broker.ZEROMQ);
	}

}
