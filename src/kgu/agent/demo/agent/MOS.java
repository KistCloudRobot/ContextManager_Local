package kgu.agent.demo.agent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import kgu.agent.demo.action.AvailableMemoryAction;
import kgu.agent.demo.action.BatteryAction;
import kgu.agent.demo.action.InitializeSemanticMapAction;
import kgu.agent.demo.action.LatestPerceptionAction;
import kgu.agent.demo.action.NotifyAction;
import kgu.agent.demo.action.OntologyUpdateAction;
import kgu.agent.demo.action.PerceptionSubscriptionsAction;
import kgu.agent.demo.action.ReasoningQueryAction;
import kgu.agent.demo.action.SubscribeAction;
import kgu.agent.demo.action.TripleCountAction;
import kgu.agent.demo.action.WorkingMemory_MonitorsAction;
import kgu.agent.demo.actionArgument.AvailableMemoryArgument;
import kgu.agent.demo.actionArgument.BatteryArgument;
import kgu.agent.demo.actionArgument.GLArgument;
import kgu.agent.demo.actionArgument.InitializeSemanticMapArgument;
import kgu.agent.demo.actionArgument.LatestPerceptionArgument;
import kgu.agent.demo.actionArgument.PerceptionSubscriptionsArgument;
import kgu.agent.demo.actionArgument.ReasoningQueryArgument;
import kgu.agent.demo.actionArgument.SubscribeArgument;
import kgu.agent.demo.actionArgument.TripleCountArgument;
import kgu.agent.demo.actionArgument.WorkingMemory_MonitorsArgument;
import kgu.agent.demo.paser.ContextMonitorParser;
import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.agent.logger.AgentAction;
import kr.ac.uos.ai.arbi.agent.logger.LogTiming;
import kr.ac.uos.ai.arbi.agent.logger.LoggerManager;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;

public class MOS extends ArbiAgent {

	// private GeneralizedList eventGL;
	private Map<String, String> taskManagerSubsList;
	public AgentAction reasoningAction;
	public AgentAction subscribeAction;
	public AgentAction notifyAction;
	public AgentAction perceptionSubscriptionsAction;
	public AgentAction tripleCountAction;
	public AgentAction workingMemroy_MonitorsAction;
	public AgentAction batteryAction;
	public AgentAction latestPerceptionAction;
	public AgentAction availableMemoryAction;
	public AgentAction initializeSemanticMapAction;
	public AgentAction ontologyUpdateAction;
	private DataSource dc;

	public static final String JMS_BROKER_URL = "tcp://127.0.0.1:61616";
	public static final String TM_ADDRESS = "agent://www.arbi.com/taskManager";
	public static final String CM_ADDRESS = "agent://www.arbi.com/contextManager";	
	public static final String L1_CM_ADDRESS = "agent://www.arbi.com/L1_CM";
	public static final String LT_CM_ADDRESS = "agent://www.arbi.com/LT_CM";
	public static final String L1_SMM_ADDRESS = "agent://www.arbi.com/L1_SMM";
	public static final String LT_SMM_ADDRESS = "agent://www.arbi.com/LT_SMM";
	public static final String MOS_ADDRESS = "agent://www.arbi.com/MOS";

	public static final String KM_ADDRESS = "agent://www.arbi.com/knowledgeManager";
	public static final String BEHAVIOUR_INTERFACE_ADDRESS = "agent://www.arbi.com/behaviourInterface";
	public static final String PERCEPTION_ADRESS = "agent://www.arbi.com/perception";
	public static final String ACTION_ADDRESS = "agent://www.arbi.com/action";
	public static final String TASKLOG_ADDRESS = "agent://www.arbi.com/TaskLog";
	public static final String DC_URL = "dc://MOS";
	public static final String ScheduleQuery = "(querySchedule (triple $S $P $O))";
	public static final String FacePerceivedQuery = "(Human_recognition (actionID 1) (type \"Execute\") (param \"param \"))";
	String Schedule = "(querySchedule (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_52ioa30gprjnh41f2to8du5efv\"  \"http://www.arbi.com/ontologies/arbi.owl#summary\"  \"로봇지능회의\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_52ioa30gprjnh41f2to8du5efv\"  \"http://www.arbi.com/ontologies/arbi.owl#beginAt\"  \"2017-11-14T14:30:00\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_52ioa30gprjnh41f2to8du5efv\"  \"http://knowrob.org/kb/knowrob.owl#startTime\"  \"http://www.arbi.com/ontologies/GuideService.owl#timepoint_1510637400\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_52ioa30gprjnh41f2to8du5efv\"  \"http://www.arbi.com/ontologies/arbi.owl#endAt\"  \"2017-11-14T15:30:00\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_52ioa30gprjnh41f2to8du5efv\"  \"http://knowrob.org/kb/knowrob.owl#endTime\"  \"http://www.arbi.com/ontologies/GuideService.owl#timepoint_1510641000\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_52ioa30gprjnh41f2to8du5efv\"  \"http://www.arbi.com/ontologies/arbi.owl#place\"  \"회의실 101호\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_52ioa30gprjnh41f2to8du5efv\"  \"http://www.arbi.com/ontologies/arbi.owl#scheduleOccursAt\"  \"http://www.arbi.com/ontologies/arbi_map.owl#ConferenceRoom001\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_52ioa30gprjnh41f2to8du5efv\"  \"http://www.arbi.com/ontologies/arbi.owl#hasAttendee\"  \"http://www.arbi.com/ontologies/GuideService.owl#Person001\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_52ioa30gprjnh41f2to8du5efv\"  \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\"  \"http://www.arbi.com/ontologies/arbi.owl#Schedule\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_1ct8p1j86sohbkoisq6ttb5v1l\"  \"http://www.arbi.com/ontologies/arbi.owl#hasAttendee\"  \"http://www.arbi.com/ontologies/GuideService.owl#Person001\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_1ct8p1j86sohbkoisq6ttb5v1l\"  \"http://www.arbi.com/ontologies/arbi.owl#place\"  \"kist\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_1ct8p1j86sohbkoisq6ttb5v1l\"  \"http://knowrob.org/kb/knowrob.owl#endTime\"  \"http://www.arbi.com/ontologies/GuideService.owl#timepoint_1510632000\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_1ct8p1j86sohbkoisq6ttb5v1l\"  \"http://www.arbi.com/ontologies/arbi.owl#endAt\"  \"2017-11-14T13:00:00\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_1ct8p1j86sohbkoisq6ttb5v1l\"  \"http://knowrob.org/kb/knowrob.owl#startTime\"  \"http://www.arbi.com/ontologies/GuideService.owl#timepoint_1510628400\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_1ct8p1j86sohbkoisq6ttb5v1l\"  \"http://www.arbi.com/ontologies/arbi.owl#beginAt\"  \"2017-11-14T12:00:00\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_1ct8p1j86sohbkoisq6ttb5v1l\"  \"http://www.arbi.com/ontologies/arbi.owl#summary\"  \"점심시간\" ) (triple  \"http://www.arbi.com/ontologies/GuideService.owl#Schedule_1ct8p1j86sohbkoisq6ttb5v1l\"  \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\"  \"http://www.arbi.com/ontologies/arbi.owl#Schedule\" ))";

	public static int count = 0;
	public static int batteryCount = 0;
	public static int wheeldropCount = 0;
	public static int cliffCount = 0;
	public static int bumperCount = 0;
	public static int speechCount = 0;
	public static int HumanCount = 0;
	public static int perceptionCount = 0;
	
	public static int DC_BROKER_TYPE = Broker.ZEROMQ;
	public static int AA_BROKER_TYPE = Broker.ZEROMQ;

	public MOS() {

	}

	public void executeAgent() {

		ArbiAgentExecutor.execute(JMS_BROKER_URL, MOS_ADDRESS, this, AA_BROKER_TYPE); //arbi based code

		System.out.println("Agent Executed !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		ReasoningQueryAction action1 = new ReasoningQueryAction(dc);
		reasoningAction = new AgentAction("ContextService", action1);
		LoggerManager.getInstance().registerAction(reasoningAction, LogTiming.Later);
		
		LatestPerceptionAction action8 = new LatestPerceptionAction();
		latestPerceptionAction = new AgentAction("RobotContext", action8);
		LoggerManager.getInstance().registerAction(latestPerceptionAction, LogTiming.Later);

		System.out.println("Action register done !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	public void onNotify(String sender, String notification) {
		System.out.println(notification);

	}

	@Override
	public String onQuery(String sender, String queryGL) {
		// TODO Auto-generated method stub

		System.out.println("onQuery Start!!");
		System.out.println("Query: "+queryGL);
		System.out.println("Sender: "+ sender);
		String[] arr = queryGL.split("\\(|\\)| ");
		/*for(int i = 0; i < arr.length; i++) {
			System.out.println(i + " : " + arr[i]);
		}*/
		
		switch (arr[3]) {
		case "batteryRemained":
			System.out.println("received query : " + arr[3]);
			ReasoningQueryArgument argument = new ReasoningQueryArgument(sender, queryGL);
			String queryResult = (String) reasoningAction.execute(argument);
			System.out.println("reasoning result : " + queryResult);
			return queryResult;
		}

		return "not exist query";
	}

	@Override
	public void onStart() {
		dc = new DataSource() {
			@Override
			public void onNotify(String data) {
				System.out.println("Notification : " + data);
				String t = "";
				String rule = "";
				String id = "";
				

				LatestPerceptionArgument argument = new LatestPerceptionArgument(data);

				String latestPerception = (String) latestPerceptionAction.execute(argument);
				System.out.println(latestPerception);
			}
		};

		dc.connect(JMS_BROKER_URL, DC_URL, DC_BROKER_TYPE);

		System.out.println("======Start MOS======");

		init_prolog();

		taskManagerSubsList = new HashMap<String, String>();

		System.out.println();

	}

	public void init_prolog() {

		String t = "";

		t = "[cmProlog/prolog/init_cloud]";//"[prolog/init]";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));

		Query q = new Query(t);

		while (q.hasMoreSolutions()) {
			Map<String, Term> s3 = q.nextSolution();

			System.out.println(s3.toString());
		}

		System.out.println("SWI-Prolog working from now on");

	}

}