package test;

import java.util.Scanner;

import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.ltm.DataSource;




public class LT_TM extends ArbiAgent {

	// private GeneralizedList eventGL;
	private String analyzedData;

	public static final String JMS_BROKER_URL = "tcp://127.0.0.1:61616";
	public static final String TM_ADDRESS = "agent://www.arbi.com/L1_TM";
	public static final String CM_ADDRESS = "agent://www.arbi.com/L1_CM";
	public static final String KM_ADDRESS = "agent://www.arbi.com/knowledgeManager";
	public static final String BEHAVIOUR_INTERFACE_ADDRESS = "agent://www.arbi.com/behaviourInterface";
	public static final String PERCEPTION_ADRESS = "agent://www.arbi.com/perception";
	public static final String ACTION_ADDRESS = "agent://www.arbi.com/action";
	public static final String TASKLOG_ADDRESS = "agent://www.arbi.com/TaskLog";
	public static final String DC_URL = "dc://LT_TM";
	
	public static final String LT_TM_ADDRESS = "agent://www.arbi.com/LT_TM";

	
	public static final String L1_CM_ADDRESS = "agent://www.arbi.com/L1_CM";
	public static final String LT_CM_ADDRESS = "agent://www.arbi.com/LT_CM";
	public static final String L1_SMM_ADDRESS = "agent://www.arbi.com/L1_SMM";
	public static final String LT_SMM_ADDRESS = "agent://www.arbi.com/LT_SMM";
	public static final String MOS_ADDRESS = "agent://www.arbi.com/MOS";
	

	public LT_TM() {

		executeAgent();

	}

	public void executeAgent() {
		ArbiAgentExecutor.execute(JMS_BROKER_URL, LT_TM_ADDRESS, this, Broker.ZEROMQ);
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
		dc.connect("tcp://localhost:61616", "dc://LT_TM", Broker.ZEROMQ);

		System.out.println("======Start LT_Task_Manager======");

		Scanner sc = new Scanner(System.in);
		String messageType = "";
		String message = "";
		
		

		
		messageType = sc.nextLine();
		while (true) {
			sleep(500);
			String result="";
			
			//dc.assertFact("(Robot_battery (time 2) (gauge 2))");

			System.out.println("\nplease enter the 'query(q)' or 'subscribe(s)' or 'generate perception(p)' or 'initail SemanticMap(i).");
			//messageType = sc.nextLine();
			messageType = "q1";

			if (messageType.equals("q")) {
				message = "(context (deadlock \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" \"http://www.arbi.com/ontologies/arbi.owl#amr_lift02\"))";
				result = subscribe(L1_CM_ADDRESS, message);
				if (result.equals("")) 
					result="false";
				System.out.println("res : " + result);
				//System.out.println("please enter the query message.");
				//message = sc.nextLine();
				//String result = query(CM_ADDRESS, message);
				// System.out.println("Query : " + message + " to " +
				// CM_ADDRESS);
				
				/*System.out.println("query : deadlock amr_lift01 amr_lift02");
				message = "(context (deadlock \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\" \"http://www.arbi.com/ontologies/arbi.owl#amr_lift02\"))";
				result = query(LT_CM_ADDRESS, message);
				if (result.equals("")) 
					result="false";
				System.out.println("res : " + result);*/
				
				/*System.out.println("query : loadedBy box01 amr_lift01");
				message = "(context (loadedBy $A $B))";
				result = query(L1_CM_ADDRESS, message);
				if (result.equals("")) 
					result="false";
				System.out.println("res : " + result);
				
				
				System.out.println("query : mountedBy rack01 amr_lift01");
				message = "(context (mountedBy $A $B))";
				result = query(L1_CM_ADDRESS, message);
				if (result.equals("")) 
					result="false";
				System.out.println("res : " + result);*/

			}
			else if (messageType.equals("q1")) {
				//System.out.println("please enter the query message.");
				//message = sc.nextLine();
				//String result = query(CM_ADDRESS, message);
				// System.out.println("Query : " + message + " to " +
				// CM_ADDRESS); 
				System.out.println("query : loadedBy box01 amr_lift01");
				message = "(context (loadedBy \"http://www.arbi.com/ontologies/arbi.owl#box01\" \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\"))";
				result = query(L1_CM_ADDRESS, message);
				if (result.equals("")) 
					result="false";

				
				System.out.println("res : " + result);

			} else if (messageType.equals("q2")) {
				//System.out.println("please enter the query message.");
				//message = sc.nextLine();
				//String result = query(CM_ADDRESS, message);
				// System.out.println("Query : " + message + " to " +
				// CM_ADDRESS);
				
				System.out.println("query : mountedBy rack01 amr_lift01");
				message = "(context (mountedBy \"http://www.arbi.com/ontologies/arbi.owl#rack01\" \"http://www.arbi.com/ontologies/arbi.owl#amr_lift01\"))";
				result = query(L1_CM_ADDRESS, message);
				if (result.equals("")) 
					result="false";

				
				System.out.println("res : " + result);

			} 
			else if (messageType.equals("p")) {
				System.out.println("please enter the query message.");
				message = sc.nextLine();
				String result2 = query(L1_CM_ADDRESS, message);
				 System.out.println("Query : " + message + " to " +
				 CM_ADDRESS);
				 System.out.println(result2);

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

		new LT_TM();

	}

}