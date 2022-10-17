package test;


import java.util.Scanner;

import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.ltm.DataSource;




public class TestTaskManager2 extends ArbiAgent {

	// private GeneralizedList eventGL;
	private String analyzedData;

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
	
	public static final String IA_PL_LT_ADDRESS = "agent://www.arbi.com/IA_PL_LT";

	
	public static final String DC_URL = "dc://testdcCM";

	public TestTaskManager2() {

		executeAgent();

	}

	public void executeAgent() {
		ArbiAgentExecutor.execute(JMS_BROKER_URL, TM_ADDRESS, this, BrokerType.ZEROMQ);
	}

	@Override
	public String onRequest(String sender, String data) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("---> onRequest() : ");
		System.out.println("Message = " + data);

		return "";

	}

	@Override
	public void onData(String sender, String data) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("---> handle() : ");
		System.out.println("Message = " + data);
		System.out.println("-------------------------------");

	}

	@Override
	public void onNotify(String sender, String notification) {
		// TODO Auto-generated method stub
		System.out.println("�̰Կ�? :" + notification);
	}

	@Override
	public String onQuery(String sender, String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart() {
		DataSource dc = new DataSource();
		dc.connect("tcp://localhost:61616", "dc://TM", BrokerType.ZEROMQ);

		System.out.println("======Start Dummy_Task_Manager======");

		Scanner sc = new Scanner(System.in);
		String messageType = "";
		String message = "";
		
		

		
		
		while (true) {
			
			//dc.assertFact("(Robot_battery (time 2) (gauge 2))");

			
			System.out.println("\nplease enter the 'query(q)' or 'subscribe(s)' or 'generate perception(p)' or 'initail SemanticMap(i).");
			messageType = sc.nextLine();
			
			String result="false";

			if (messageType.equals("q")) {
				/*System.out.println("query : robotAt amr_lift01 X Y");
				message = "(context (robotAt \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : cargoAt c X Y");
				message = "(context (cargoAt $A $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : rackAt r X Y");
				message = "(context (rackAt $A $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : robotAt X Y");
				message = "(context (robotAt $X $Y $R))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				
				System.out.println("query : on_Physical X Y");
				message = "(context (on_Physical $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				*/
				/*System.out.println("query : rackAt A X Y");
				message = "(context (rackAt $A $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : loadedBy X Y");
				message = "(context (loadedBy $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : mountedBy X Y");
				message = "(context (mountedBy $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				/*
				System.out.println("query : on_Physical X Y");
				message = "(context (on_Physical $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : fuelEfficiency X Y");
				message = "(context (fuelEfficiency $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : batteryRemained X Y");
				message = "(context (batteryRemained $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : cargoLoadedAt A B X Y");
				message = "(context (cargoLoadedAt $X $Y $A $B))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : rackAttachedAt A B X Y");
				message = "(context (rackAttachedAt $X $Y $A $B))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				*/
				
				
				System.out.println("query : robotAt X Y");
				message = "(context (robotAt $X $Y $R))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				/*
				System.out.println("query : inFrontOf X Y");
				message = "(context (inFrontOf $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				result=result.replace("http://www.arbi.com/ontologies/arbi.owl#", "");
				System.out.println("res : " + result);
				
				System.out.println("query : behind X Y");
				message = "(context (behind $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				result=result.replace("http://www.arbi.com/ontologies/arbi.owl#", "");
				System.out.println("res : " + result);
				
				System.out.println("query : toTheLeftOf X Y");
				message = "(context (toTheLeftOf $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				result=result.replace("http://www.arbi.com/ontologies/arbi.owl#", "");
				System.out.println("res : " + result);
				
				System.out.println("query : toTheRightOf X Y");
				message = "(context (toTheRightOf $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				result=result.replace("http://www.arbi.com/ontologies/arbi.owl#", "");
				System.out.println("res : " + result);
				
				/*System.out.println("query : orient A B X Y");
				message = "(context (orient $A $B $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				/*System.out.println("query : robotRotation X Y");
				message = "(context (robotRotation $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				
				System.out.println("query : nearBy amr_lift01 amr_lift02");
				message = "(context (nearBy \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" \"http://www.arbi.com/ontologies/arbi.owl#amr_lift02\"))";
				result = query(LT_CM_ADDRESS, message);
				if (result.equals("")) 
					result="false";

				
				System.out.println("res : " + result);
				*/
				
				/*System.out.println("query : faceToFace amr_lift01 amr_lift02");
				message = "(context (faceToFace \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" \"http://www.arbi.com/ontologies/arbi.owl#amr_lift02\"))";
				result = query(LT_CM_ADDRESS, message);
				if (result.equals("")) 
					result="false";

				
				System.out.println("res : " + result);
				
				
				System.out.println("query : cargoAt box01 X Y");
				message = "(context (cargoAt \"http://www.arbi.com/ontologies/arbi.owl#box01\" $X $Y))";
				String result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : robotAt amr_lift01 X Y");
				message = "(context (robotAt \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" $X $Y))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("please enter the query message.");
				//message = sc.nextLine();
				System.out.println("query : collidable amr_lift01 moveRack CollisionObject");
				message = "(context (collidable \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" $Task $C))";
				String result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : predictTaskTime amr_lift01 moveRack Time");
				message = "(context (predictTaskTime \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" $Task $Time))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : batteryRemained amr_lift01 B");
				message = "(context (batteryRemained \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" $B))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : batteryNeed amr_lift01 B");
				message = "(context (batteryNeed \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" $Task $B))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);*/

			} else if (messageType.equals("p")) {
				System.out.println("query : safe amr_lift01 Task Safe");
				message = "(context (safe \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" \"move(\"rackArea01\")\" $Safe))";
				result = query(IA_PL_LT_ADDRESS, message);
				System.out.println("res : " + result);
				 

			} else if (messageType.equals("r")) {
				/*System.out.println("query : robotVelocity amr_lift01 Task Safe");
				message = "(context (robotVelocity $A $B))";
				result = query(L1_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : fuelEfficiency X Y");
				message = "(context (fuelEfficiency $X $Y))";
				result = query(L1_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : batteryRemained X Y");
				message = "(context (batteryRemained $X $Y))";
				result = query(L1_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : loadedBy X Y");
				message = "(context (loadedBy $X $Y))";
				result = query(L1_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : mountedBy X Y");
				message = "(context (mountedBy $X $Y))";
				result = query(L1_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : robotAt X Y");
				message = "(context (robotAt $R $X $Y))";
				result = query(L1_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : robotRotation X Y");
				message = "(context (robotRotation $R $X))";
				result = query(L1_CM_ADDRESS, message);
				System.out.println("res : " + result);*/
				

				

			}
			else if (messageType.equals("l")) {
				/*System.out.println("query : batteryRemained X Y");
				message = "(context (batteryRemained $R $X))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : doorOpened");
				message = "(context (doorOpened))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : doorClosed");
				message = "(context (doorClosed))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				/*System.out.println("query : type X Y");
				message = "(context (type $A $P))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : semanticMapPerception X Y");
				message = "(context (semanticMapPerception $A $P))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);*/

				/*System.out.println("query : areaPose X Y");
				message = "(context (areaPose $Area $Pose))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);

				System.out.println("query : onStation X Y");
				message = "(context (onStation $E $A))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : stationAt A X Y");
				message = "(context (stationAt $S $E $A))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : stationAt A X Y");
				message = "(context (stationAt $S $E $A))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : chargingBatteryAt A X Y");
				message = "(context (chargingBatteryAt $S $E $A))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : endChargingBattery X Y");
				message = "(context (endChargingBattery $E $A))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);

				/*System.out.println("query : onRobotBatteryStatus R B");
				message = "(context (onRobotBatteryStatus $R $B))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : onRackStatus R B S");
				message = "(context (onRackStatus $R $B $S))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : onBoxStatus R B S");
				message = "(context (onBoxStatus $R $B $S))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);*/
				
				System.out.println("query : taskDistance R B S");
				message = "(context (taskDistance $R $B $S))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : predictTaskTime R B S");
				message = "(context (predictTaskTime $R $B $S))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
				
				System.out.println("query : batteryNeed R B S");
				message = "(context (batteryNeed $R $B $S))";
				result = query(LT_CM_ADDRESS, message);
				System.out.println("res : " + result);
			}

			else if (messageType.equals("s")) {
				System.out.println("please enter the subscription message.");
				message = sc.nextLine();
				// send(CM_ADDRESS, message);
				result = subscribe(CM_ADDRESS, message);
				System.out.println("Subscribe : " + message + " to " + CM_ADDRESS);
				System.out.println("subscribe result :" + result);

				// (rule fact(rdf:type S arbi:LocationPerception) -->
				// notify(rdf:type S arbi:LocationPerception))

			} else if (messageType.equals("u")) {
				System.out.println("please enter the unsubscription message.");
				message = sc.nextLine();
				unsubscribe(CM_ADDRESS, message);
				//send(CM_ADDRESS, "unsubscribe");
				System.out.println("unsubscribe done");
			}

			else if (messageType.equals("i")) {
				System.out.println("initailize semanticmap message");
				message = sc.nextLine();
				send(CM_ADDRESS, message);
				System.out.println("send initailze massage done");
			}
			
			sleep(2000);
		}

	}
	public void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (Exception e) {
		}
	}
	
	static public void main(String args[]) {

		new TestTaskManager2();

	}

}