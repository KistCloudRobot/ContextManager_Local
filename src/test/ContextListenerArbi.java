package test;

import java.util.Scanner;

import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.ltm.DataSource;




public class ContextListenerArbi extends ArbiAgent {


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
	
	Scanner sc = new Scanner(System.in);
	String messageType = "";
	String message = "";
	

	public ContextListenerArbi() {

		executeAgent();

	}

	public void executeAgent() {
		ArbiAgentExecutor.execute(JMS_BROKER_URL, CL_ADDRESS, this, BrokerType.ZEROMQ);
	}

	@Override
	public void onStart() {
		DataSource ds = new DataSource();
		ds.connect("tcp://localhost:61616", "dc://CL", BrokerType.ZEROMQ);

		System.out.println("======Start Dummy_Listener_Manager======");
		
		String sen;
		String result;
		int x=0, y=0, z=0, a=0, b=0, c=0, d=0, width = 0, depth=0, height=0, id=10, IDN=0;
		int i=1;
		
		sen = "(Object_position 0 0 0 0 0 0 0 0 0 0 0)";
		ds.assertFact(sen);
		
		
		sen = "(Robot_position 0 0 0 0 0 0 0 0 0 0)";
		ds.assertFact(sen);
		
		
		while (true) {

			
			result = "(Object_position " + x + " " + y + " " + z + " " + a + " " + b + " " + c + " " + d + " "
					+ width + " " + depth + " " + height + " " + IDN+")";

			//ds.retractFact("(Object_position $a $b $c $d $e $f $g $h $i $j $k");
			ds.updateFact("(updateTest (Object_position $a $b $c $d $e $f $g $h $i $j $k) " + result+")");
			x+=i;y+=i;z+=i;a+=i;b+=i;c+=i;d+=i;width+=i;depth+=i;height+=i;
			
			
			result = "(Robot_position " + x + " " + y + " " + z + " " + a + " " + b + " " + c + " "
					+ width + " " + depth + " " + height + " " + id+")";
			ds.updateFact("(updateTest (Robot_position $a $b $c $d $e $f $g $h $i $j) " + result+")");
			//sleep(2000);
			i+=1;
		}

	}
	
	public void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (Exception e) {
		}
	}
	

	static public void main(String args[]) {

		new ContextListenerArbi();

	}

}