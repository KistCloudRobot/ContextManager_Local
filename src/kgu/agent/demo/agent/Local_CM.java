package kgu.agent.demo.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import kgu.agent.demo.action.GUIAction;
import kgu.agent.demo.action.LatestPerceptionAction;
import kgu.agent.demo.action.ReasoningQueryAction;
import kgu.agent.demo.actionArgument.ContextOntologyMonitorArgument;
import kgu.agent.demo.actionArgument.GUIArgument;
import kgu.agent.demo.actionArgument.LatestPerceptionArgument;
import kgu.agent.demo.actionArgument.LowLevelContextMonitorArgument;
import kgu.agent.demo.actionArgument.ReasoningQueryArgument;
import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.agent.logger.LogTiming;
import kr.ac.uos.ai.arbi.agent.logger.LoggerManager;
import kr.ac.uos.ai.arbi.agent.logger.action.AgentAction;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kgu.agent.demo.actionArgument.SubscribeArgument;

import kgu.agent.demo.paser.ContextMonitorParser;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.parser.ParseException;

public class Local_CM extends ArbiAgent {

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
	public AgentAction guiAction;

	public static String CONTEXTMANAGER_ADRESS = "agent://www.arbi.com/ContextManager";
	public static String TASKMANAGER_ADDRESS = "agent://www.arbi.com/TaskManager";
	public static String brokerAddress;
	private final int brokerPort;
	LatestPerceptionAction action8;
	DataSource ds;
	static int queryCount = 0;
	int fakeTMQueryTime = 20;
	
	private int emptyStorintStationCount = 0;

	public Local_CM(String brokerAddress, int brokerPort) {
		this.brokerAddress = brokerAddress;
		this.brokerPort = brokerPort;
		ArbiAgentExecutor.execute(brokerAddress, brokerPort, CONTEXTMANAGER_ADRESS, this, BrokerType.ACTIVEMQ);
		init_prolog();
		ds = new DataSource() {
			boolean Subscripting_start = false;

			@Override
			public void onNotify(String data) {
				// print log
//				if (data.contains("rackAt") || data.contains("cargoAt"))
//					System.out.println("OnNotify_start : " + data);

				String sender = "FakeTM";
				String queryGL;
				String queryresult;

				queryCount++;
				if (queryCount % fakeTMQueryTime == 0) {
					
//					queryGL = "(context (emptyStoringStation $A))";
//					onQuery(sender, queryGL);
////					
//					queryGL = "(context (rackOn $A $B))";
//					onQuery(sender, queryGL);
//					
//					queryGL = "(context (preCompareVertex $A $B))";
//					onQuery(sender, queryGL);

//					queryGL = "(context (rackOn \"http://www.arbi.com/ontologies/arbi.owl#pallet_03\" $B))";
//					onQuery(sender, queryGL);

//					queryGL = "(context (cargoOnStoringStation $A))";
//					onQuery(sender, queryGL);

//					queryGL = "(context (cargoOn $A \"http://www.arbi.com/ontologies/arbi.owl#pallet_03\"))";
//					onQuery(sender, queryGL);
				}

				if (!Subscripting_start) {
					System.out.println("Subscripting_start");
					Subscripting_start = true;
				}

				GeneralizedList gl = null;

				try {
					gl = GLFactory.newGLFromGLString(data);
					gl = gl.getExpression(0).asGeneralizedList();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// System.out.println("Notification : " + gl.toString());

				LatestPerceptionArgument argument = new LatestPerceptionArgument(data);
				// LatestPerceptionAction action8 = new LatestPerceptionAction();
				// System.out.println("iii" + data);

				if (argument == null) {
					return;
				}

				String latestPerception = (String) action8.execute(argument);


			}

		};

		ReasoningQueryAction action1 = new ReasoningQueryAction(ds);
		reasoningAction = new AgentAction("ContextService", action1);
		LoggerManager.getInstance().registerAction(reasoningAction, LogTiming.Later);

		action8 = new LatestPerceptionAction();
		latestPerceptionAction = new AgentAction("RobotContext", action8);
		LoggerManager.getInstance().registerAction(latestPerceptionAction, LogTiming.Later);
		ds.connect(brokerAddress, brokerPort, "ds://www.arbi.com/ContextManager", BrokerType.ACTIVEMQ);
		String subscribeStatement = "(rule (fact (context $context)) --> (notify (context $context)))";
		ds.subscribe(subscribeStatement);
//		String subscriptionID = ds.subscribe("(rule (fact (context (robotAt $robotID $x $y))) --> (notify (robotAt $robotID $x $y)))");
		// System.out.println(subscriptionID);
//		ds.subscribe("(rule (fact (context (cargoAt $cargoID $x $y))) --> (notify (cargoAt $cargoID $x $y)))");
//		ds.subscribe("(rule (fact (context (rackAt $cargoID $x $y))) --> (notify (rackAt $cargoID $x $y)))");
//		ds.subscribe("(rule (fact (context (robotInfo $robotID $x $y $theta $loading $time))) --> (notify (robotInfo $robotID $x $y $theta $loading $time)))");
	}

	public void onNotify(String sender, String notification) {
		System.out.println(notification);
	}

//	public String onQuery(String sender, String queryGL) {
//		System.out.println("ONQuery start");
//		String message, result, latestPerception, predicate;
//		System.out.println("sender : " + sender);
//		System.out.println("queryGL : " + queryGL);
////		String queryResult = "";
//		GeneralizedList gl = null;
//		try {
//			gl = GLFactory.newGLFromGLString(queryGL);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(gl.getExpression(0).asGeneralizedList().getName().equals("emptyStoringStation")) {
//			String queryResult = "(context (emptyStation \"http://www.arbi.com/ontologies/arbi.owl#station10\"))";
//		} else {
////			predicate = queryGL.split(" ")[1].replace("(", "");
//			ReasoningQueryArgument rqArgument = new ReasoningQueryArgument(sender, queryGL);
//			System.out.println("ds : " + ds);
//			ReasoningQueryAction action1 = new ReasoningQueryAction(ds);
//			reasoningAction = new AgentAction("ContextService", action1);
//			String queryResult = (String) reasoningAction.execute(rqArgument);
//			System.out.println("queryResult : " + queryResult);
//		}
//		
//		
//		return queryResult;
//	}
	public String onQuery(String sender, String queryGL) {
		System.out.println("ONQuery start");
		String message, result, latestPerception, predicate;
		System.out.println("sender : " + sender);
		System.out.println("queryGL : " + queryGL);
		GeneralizedList gl = null;
		
		// sender == taskPolicyLearner then
//		if (sender.contains("TaskPolicyLearner")) {
//			System.out.println("!!!!");
//			try {
//				GeneralizedList query = GLFactory.newGLFromGLString(queryGL);
//
//				System.out.println(query);
//
//				String glName = query.getExpression(0).toString();
//				String test = query.getExpression(1).toString();
//				System.out.println(glName);
//				System.out.println(test);
//				if (glName.contains("batteryRemained")) {
//					return "(context (batteryRemained " + query.getExpression(1).toString() + " \"100\"))";
//				} else if (glName.contains("batteryNeed")) {
//					return "(context (batteryNeed " + query.getExpression(1).toString() + " "
//							+ query.getExpression(2).toString() + " \"50\"))";
//				} else if (glName.contains("taskDistance")) {
//					return "(context (taskDistance " + query.getExpression(1).toString() + " "
//							+ query.getExpression(2).toString() + " \"23\"))";
//				} else if (glName.contains("predictTaskTime")) {
//					return "(context (predictTaskTime " + query.getExpression(1).toString() + " "
//							+ query.getExpression(2).toString() + " \"450\"))";
//				} else if (glName.contains("loadedBy")) {
//					return "(context (loadedBy " + query.getExpression(1).toString() + " \"True\"))";
//				} else if (glName.contains("collidable")) {
//					return "(context (collidable " + query.getExpression(1).toString() + " "
//							+ query.getExpression(2).toString() + " \"Flase\"))";
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

//		String queryResult = "";

		try {
			gl = GLFactory.newGLFromGLString(queryGL);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//			predicate = queryGL.split(" ")[1].replace("(", "");
		
		if(gl.getExpression(0).asGeneralizedList().getName().equals("emptyStoringStation" )) {
			System.out.println("emptyStoringStation dummy query");
			if(emptyStorintStationCount == 0) {
				String queryResult = "(context (emptyStoringStation \"http://www.arbi.com/ontologies/arbi.owl#station20\"))";
				emptyStorintStationCount += 1;
				return queryResult;
			} else if (emptyStorintStationCount == 1) {
				String queryResult = "(context (emptyStoringStation \"http://www.arbi.com/ontologies/arbi.owl#station12\"))";
				emptyStorintStationCount += 1;
				return queryResult;
			} else if (emptyStorintStationCount == 2) {
				String queryResult = "(context (emptyStoringStation \"http://www.arbi.com/ontologies/arbi.owl#station10\"))";
				emptyStorintStationCount += 1;
				return queryResult;
			} else if (emptyStorintStationCount == 3) {
				String queryResult = "(context (emptyStoringStation \"http://www.arbi.com/ontologies/arbi.owl#station48\"))";
				emptyStorintStationCount += 1;
				return queryResult;
			}
		}
		ReasoningQueryArgument rqArgument = new ReasoningQueryArgument(sender, queryGL);
		ReasoningQueryAction action1 = new ReasoningQueryAction(ds);
		reasoningAction = new AgentAction("ContextService", action1);
		String queryResult = (String) reasoningAction.execute(rqArgument);
		System.out.println("queryResult : " + queryResult);

		return queryResult;
	}

	public static void main(String[] args) {
		String brokerAddress;
		String robotID;
		int brokerPort = 0;
		if(args.length == 0) {
//			brokerAddress = "127.0.0.1";
//			brokerAddress = "172.16.165.164";
			brokerAddress = "192.168.100.11";

//			brokerAddress = "tcp://192.168.100.10:61316";
//			brokerAddress = "tcp://172.16.165.141:61316";
			brokerPort = 61316;
			robotID = "Local";
		} else {
			robotID = args[0];
			brokerAddress = args[1];
		}
		Local_CM agent = new Local_CM(brokerAddress, brokerPort);
//		agent.onQuery("testTM", "(context (rackOn \"http://www.arbi.com/ontologies/arbi.owl#station10\" $Rack))");
	}

	public static void log(String log) {
		try {
			File file = new File("E:\\contextLog.txt");
			FileWriter fw = new FileWriter(file, true);

			fw.write(log + "\n");

			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init_prolog() {

		String t = "";
		t = "[cmProlog/prolog/init_isaac]";
		// t = "[cmProlog/prolog/init]";// "[prolog/init]";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));

		Query q = new Query(t);

		while (q.hasMoreSolutions()) {
			Map<String, Term> s3 = q.nextSolution();

			System.out.println(s3.toString());
		}

		System.out.println("SWI-Prolog working from now on");

	}

	public void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (Exception e) {
		}
	}

}