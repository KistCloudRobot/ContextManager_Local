package test;

import java.util.Scanner;

import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.ltm.DataSource;




public class TestTaskManager extends ArbiAgent {

	// private GeneralizedList eventGL;
	private String analyzedData;

	public static final String JMS_BROKER_URL = "tcp://127.0.0.1:61616";
	public static final String TM_ADDRESS = "agent://www.arbi.com/taskManager";
	public static final String CM_ADDRESS = "agent://www.arbi.com/contextManager";
	public static final String KM_ADDRESS = "agent://www.arbi.com/knowledgeManager";
	public static final String BEHAVIOUR_INTERFACE_ADDRESS = "agent://www.arbi.com/behaviourInterface";
	public static final String PERCEPTION_ADRESS = "agent://www.arbi.com/perception";
	public static final String ACTION_ADDRESS = "agent://www.arbi.com/action";
	public static final String TASKLOG_ADDRESS = "agent://www.arbi.com/TaskLog";
	public static final String DC_URL = "dc://testdcCM";

	public TestTaskManager() {
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

			if (messageType.equals("q")) {
				System.out.println("please enter the query message.");
				message = sc.nextLine();
				String result = query(CM_ADDRESS, message);
				// System.out.println("Query : " + message + " to " +
				// CM_ADDRESS);
				System.out.println("res : " + result);

			} else if (messageType.equals("p")) {
				message = sc.nextLine();
				System.out.println("insert robot position x, y");
				request(CM_ADDRESS, message);
				// System.out.println("Subscribe : " + message + " to " +
				// CM_ADDRESS);

			}

			else if (messageType.equals("s")) {
				System.out.println("please enter the subscription message.");
				message = sc.nextLine();
				// send(CM_ADDRESS, message);
				String result = subscribe(CM_ADDRESS, message);
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
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	static public void main(String args[]) {

		new TestTaskManager();

	}

}