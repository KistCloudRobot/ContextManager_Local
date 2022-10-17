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
import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.agent.logger.AgentAction;
import kr.ac.uos.ai.arbi.agent.logger.LogTiming;
import kr.ac.uos.ai.arbi.agent.logger.LoggerManager;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;

public class ContextManager extends ArbiAgent {

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
	public static final String KM_ADDRESS = "agent://www.arbi.com/knowledgeManager";
	public static final String BEHAVIOUR_INTERFACE_ADDRESS = "agent://www.arbi.com/behaviourInterface";
	public static final String PERCEPTION_ADRESS = "agent://www.arbi.com/perception";
	public static final String ACTION_ADDRESS = "agent://www.arbi.com/action";
	public static final String TASKLOG_ADDRESS = "agent://www.arbi.com/TaskLog";
	public static final String DC_URL = "dc://CM";
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

	public ContextManager() {

	}

	public void executeAgent() {

		ArbiAgentExecutor.execute(JMS_BROKER_URL, CM_ADDRESS, this, BrokerType.ZEROMQ); //arbi 기반으로 CM시작 코드

		System.out.println("Agent Executed !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		ReasoningQueryAction action1 = new ReasoningQueryAction(dc);
		reasoningAction = new AgentAction("ContextService", action1);
		LoggerManager.getInstance().registerAction(reasoningAction, LogTiming.Later);

		SubscribeAction action2 = new SubscribeAction();
		subscribeAction = new AgentAction("ContextService", action2);
		LoggerManager.getInstance().registerAction(subscribeAction, LogTiming.Later);

		NotifyAction action3 = new NotifyAction();
		notifyAction = new AgentAction("ContextService", action3); // type
		LoggerManager.getInstance().registerAction(notifyAction, LogTiming.Later); // action

		PerceptionSubscriptionsAction action4 = new PerceptionSubscriptionsAction();
		perceptionSubscriptionsAction = new AgentAction("sendPerception", action4);
		LoggerManager.getInstance().registerAction(perceptionSubscriptionsAction, LogTiming.Later);

		TripleCountAction action5 = new TripleCountAction();
		tripleCountAction = new AgentAction("sendTriple", action5);
		LoggerManager.getInstance().registerAction(tripleCountAction, LogTiming.Later);

		WorkingMemory_MonitorsAction action6 = new WorkingMemory_MonitorsAction();
		workingMemroy_MonitorsAction = new AgentAction("sendMonitor", action6);
		LoggerManager.getInstance().registerAction(workingMemroy_MonitorsAction, LogTiming.Later);
		
		BatteryAction action7 = new BatteryAction();
		batteryAction = new AgentAction("RobotContext", action7);
		LoggerManager.getInstance().registerAction(batteryAction, LogTiming.Later);
		
		LatestPerceptionAction action8 = new LatestPerceptionAction();
		latestPerceptionAction = new AgentAction("RobotContext", action8);
		LoggerManager.getInstance().registerAction(latestPerceptionAction, LogTiming.Later);
		
		AvailableMemoryAction action9 = new AvailableMemoryAction();
		availableMemoryAction = new AgentAction("WorkingMemory", action9);
		LoggerManager.getInstance().registerAction(latestPerceptionAction, LogTiming.Later);
		
		InitializeSemanticMapAction action10 = new InitializeSemanticMapAction();
		initializeSemanticMapAction = new AgentAction("InitializeSemanticMapAction", action10);
		LoggerManager.getInstance().registerAction(initializeSemanticMapAction, LogTiming.Later);
		
		OntologyUpdateAction action11 = new OntologyUpdateAction(this);
		ontologyUpdateAction = new AgentAction("OntologyUpdateAction", action11);
		LoggerManager.getInstance().registerAction(ontologyUpdateAction, LogTiming.Later);

		System.out.println("Action register done !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		
		/*final long timeInterval = 15000;
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					
					// ------- code for task to run
					// System.out.println("Thread Executed!!!!!!!!!!!!!!!!!!!!!!");
					// ------- ends here
					TripleCountArgument argument1 = new TripleCountArgument();
					String tripleCount = (String) tripleCountAction.execute(argument1);
					// System.out.println("recent triple count :" + tripleCount);

					WorkingMemory_MonitorsArgument argument2 = new WorkingMemory_MonitorsArgument(
							String.valueOf(taskManagerSubsList.size()));
					String monitorCount = (String) workingMemroy_MonitorsAction.execute(argument2);
					// System.out.println("recent monitor count :" + monitorCount);

					PerceptionSubscriptionsArgument argument3 = new PerceptionSubscriptionsArgument(
							String.valueOf(perceptionCount));
					String perceptionCount = (String) perceptionSubscriptionsAction.execute(argument3);
					// System.out.println("recent perception count :" + perceptionCount);
					
					BatteryArgument argument4 = new BatteryArgument();
					int availableBattery =  (int) batteryAction.execute(argument4);
					// System.out.println("recent available battery :" + availableBattery);
					
					AvailableMemoryArgument argument5 = new AvailableMemoryArgument();
					int availableMemory =  (int) availableMemoryAction.execute(argument5);
					// System.out.println("recent available memory :" + availableMemory);

					try {
						Thread.sleep(timeInterval);
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}
				}
				
			}
		};
		Thread thread = new Thread(runnable);
		thread.start(); // maybe cm start code*/

	}

	public void onNotify(String sender, String notification) {

		System.out.println(notification);

	}

	@Override
	public String onSubscribe(String sender, String subscribeGL) {

		// dc.subscribe(rule);
		System.out.println("onSubscribe Start!!");
		System.out.println("subscribeGL: "+subscribeGL);
		System.out.println("Sender: "+ sender);

		String[] s = subscribeGL.split("\\(id ");

		String rule = s[0];
		String id = s[1].split("\\)| ")[0];

		if (taskManagerSubsList.containsKey(id)) {
			return "(subscribed \"fail\")";
		}

		//subscribe 관리 리스트에 추가
		taskManagerSubsList.put(id, rule);

		String subsList = "";
		String subsKey = "";
		String subsValue = "";
		Iterator<String> subsIter = taskManagerSubsList.keySet().iterator();

		while (subsIter.hasNext()) {
			subsKey = subsIter.next();
			subsValue = taskManagerSubsList.get(subsKey);

			subsList = subsList + "\n" + subsKey + " " + subsValue;
			System.out.println("========================현재까지 등록된 subscribe====================");
			System.out.println(subsList);
		}

		SubscribeArgument argument = new SubscribeArgument(sender, subscribeGL);
		String queryResult = (String) subscribeAction.execute(argument);

		return queryResult;

	}

	public void onUnsubscribe(String sender, String data) {
		String id = "";
		String rule = "";
		String t;

		if (data.contains("id")) {
			System.out.println("들어갔다");
			data = data.split("id ")[1];
			id = data.replace(")", "");

			rule = taskManagerSubsList.get(id);
			System.out.println(rule);

		} else
			System.out.println("syntax error");
		
		taskManagerSubsList.remove(id);

		System.out.println("@@@" + rule);
		String convertedData = ContextMonitorParser.PushRuleToMonitorRule(rule);
		convertedData = convertedData.replaceAll("!ADDRESS!", sender);

		t = "retract(" + convertedData + ")";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));

	}

	@Override
	public void onData(String sender, String data) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("---> onData() : ");
		System.out.println("message = " + data + " from " + sender);

		GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(data);

		} catch (Exception ex) {
			ex.getStackTrace();
			System.out.println("Request format error. GL Fomat wrong");
		}
		
		System.out.println(gl.getName());
		
		if (gl.getName().equals("SemanticMap")) {
			System.out.println("SemtanticMap Message");
			InitializeSemanticMapArgument argument = new InitializeSemanticMapArgument(sender, data);
			initializeSemanticMapAction.execute(argument);
		}

	}

	@Override
	public String onQuery(String sender, String queryGL) {
		// TODO Auto-generated method stub

		System.out.println("onQuery Start!!");
		System.out.println("Query: "+queryGL);
		System.out.println("Sender: "+ sender);
		
		/*GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(queryGL);
			System.out.println(gl);


		} catch (Exception ex) {
			ex.getStackTrace();
			System.out.println("Request format error. GL Fomat wrong");
		}
		
		GLArgument GL_argument = new GLArgument(sender, queryGL);
		System.out.println(GL_argument);
		ontologyUpdateAction.execute(GL_argument);
		System.out.println("ont update action done");*/

		ReasoningQueryArgument argument = new ReasoningQueryArgument(sender, queryGL);
		String queryResult = (String) reasoningAction.execute(argument);
		System.out.println("reasoning action done " + queryResult);

		return queryResult;
	}

	@Override
	public String onRequest(String sender, String requestGL) {
		
		System.out.println("onRequest Start!!");
		System.out.println("requestGL: "+requestGL);
		System.out.println("Sender: "+ sender);
		
		GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(requestGL);
			String glName = gl.getName();

		} catch (Exception ex) {
			ex.getStackTrace();
			System.out.println("Request format error. GL Fomat wrong");
		}

		
		if (gl.getName().equals("Initiate")) {

			String PrologRule = dc.retrieveFact("(ServicePackage \"guideScenario\" \"arbi_comp_robotState.owl\" $result)");
			System.out.println("prologue rule :" + PrologRule);

		}

		if (gl.getName().equals("facePerceived")) {
			// String Schedule = query(KM_ADDRESS, ScheduleQuery);
			String result = request(PERCEPTION_ADRESS, FacePerceivedQuery);
			// (Human_recognition (actionID \"1\") (type \"Execute\") (param
			// \"param \"))

			return result;
		}

		return "(ok)";
	}

	public void onMonitor(String address, String S, String P, String O) {

		String fact = S + " " + P + " " + O;
		System.out.println();
		System.out.println("onMonitor Start!! ");
		System.out.println("subscriber = " + address);
		System.out.println("fact = " + S + " " + P + " " + O);
		System.out.println();

	}


	@Override
	public void onStream(String data) {

		
	}

	@Override
	public void onStart() {

		dc = new DataSource() {
			@Override
			public void onNotify(String data) {
				//System.out.println("Notification : " + data);
				String t = "";
				String rule = "";
				String id = "";
				

				LatestPerceptionArgument argument = new LatestPerceptionArgument(data);

				String latestPerception = (String) latestPerceptionAction.execute(argument);
				//System.out.println(latestPerception);
			}
		};

		dc.connect(JMS_BROKER_URL, DC_URL, BrokerType.ZEROMQ);

		// dc.subscribe(rule);
		System.out.println("======Start Context_Manager======");

		System.out.println("======Start Subscripting ~ ======");

		dc.subscribe(
				"(rule (fact (Robot_position $x $y $z $a $b $c $width $depth $height $id)) --> (notify (Robot_position $x $y $z $a $b $c $width $depth $height $id)))");
		perceptionCount++;
		
		dc.subscribe(
				" (rule (fact (Object_position $x $y $z $a $b $c $d $width $depth $height $id)) --> (notify (Object_position $x $y $z $a $b $c $d $width $depth $height $id)))");
		perceptionCount++;
		
		
		System.out.println("======Subscripting ~ done======");

		
		init_prolog();

		taskManagerSubsList = new HashMap<String, String>();

		System.out.println();

	}

	public void init_prolog() {

		String t = "";

		t = "[cmProlog/prolog/init_cloud]";//"[prolog/init]";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));

		/*
		t = "comp_locatedInArtifactContainer(Object, Container)";

		(arbi:latestObjectXpoint $X $Object)
		t = "rdfs_individual_of(Object, 'http://knowrob.org/kb/knowrob.owl#SpatialThing')";
		t = "rdf_triple('http://www.arbi.com/ontologies/arbi.owl#latestObjectYpoint', X, Object)";
		t = "comp_locatedInRoom(Object, Robot)";
		t = "rdf_triple('http://www.arbi.com/ontologies/arbi.owl#lookAt',A,B)";
		t = "comp_near(A,B)";
		t = "rdf_triple('http://www.arbi.com/ontologies/arbi.owl#near',A,B)";
		t = "comp_notFollowRobot(Object, Robot)";
		t = "rdf_triple('http://www.arbi.com/ontologies/arbi.owl#notFollowRobot',A,B)";
		t = " angleForLookAt(A,B,Direction,Angle)";
		t = "batteryRemain(Robot,Remain)";
		t = "atan2(1,2,A), print(A).";
		t= "comp_RCCD_C2(Object, Robot, 100, RCCD)";
		t = "locatedInRoom('http://www.arbi.com/ontologies/arbi.owl#turtlebot01', Room)";
		t = " angleForLookAt(Robot,Object,Direction,Angle)";
		t = "  on_Physical(A,B,'DURING','http://knowrob.org/kb/knowrob.owl#timepoint_0','http://knowrob.org/kb/knowrob.owl#timepoint_10').";
		t = " on_Physical(A,B)";
		t = "availableTheRoom('http://www.arbi.com/ontologies/arbi_map.owl#ConferenceRoom001',Result,'DURING','http://www.arbi.com/ontologies/GuideService.owl#timepoint_1501192900','http://www.arbi.com/ontologies/GuideService.owl#timepoint_1501193800')";
		t = "scheduleOccursAt(Room, '로봇지능', 'DURING', 'http://www.arbi.com/ontologies/GuideService.owl#timepoint_1501136000', 'http://www.arbi.com/ontologies/GuideService.owl#timepoint_1501192900').";
		t = "locatedInRoom(Object, Room)";
		t = "on_Physical( 'http://www.arbi.com/ontologies/arbi_map.owl#Document001', Botoom, 'DURING', 'http://www.arbi.com/ontologies/GuideService.owl#timepoint_1510531200', 'http://www.arbi.com/ontologies/GuideService.owl#timepoint_1510614000').";
		System.out.println("------------------" + t + "---------------------");
		*/
		Query q = new Query(t);

		while (q.hasMoreSolutions()) {
			Map<String, Term> s3 = q.nextSolution();

			System.out.println(s3.toString());
		}

		System.out.println("SWI-Prolog working from now on");

	}

}