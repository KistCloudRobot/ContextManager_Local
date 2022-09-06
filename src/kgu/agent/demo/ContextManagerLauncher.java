package kgu.agent.demo;

import org.jpl7.Query;

import kr.ac.uos.ai.arbi.Broker;

public class ContextManagerLauncher {

	public static final String JMS_BROKER_URL = "tcp://127.0.0.1:61616";
	public static final String TM_ADDRESS = "agent://www.arbi.com/taskManager";
	public static final String CM_ADDRESS = "agent://www.arbi.com/contextManager";
	public static final String KM_ADDRESS = "agent://www.arbi.com/knowledgeManager";
	public static final String BEHAVIOUR_INTERFACE_ADDRESS = "agent://www.arbi.com/behaviourInterface";
	public static final String PERCEPTION_ADRESS = "agent://www.arbi.com/perception";
	public static final String ACTION_ADDRESS = "agent://www.arbi.com/action";
	public static final String TASKLOG_ADDRESS = "agent://www.arbi.com/TaskLog";
	public static final String DC_URL = "dc://CM";

	
	public static int DC_BROKER_TYPE = Broker.ZEROMQ;
	public static int AA_BROKER_TYPE = Broker.ZEROMQ;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String t = "use_module(library('semweb/rdf_db'))";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));	
		
		t = "use_module(library('jpl'))";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));		
		
		t = "jpl_new('kgu.agent.demo.agent.ContextManager', [], CM), nb_setval(cm, CM)";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));		
	
		System.out.println("Execute CM");		
		t = "nb_getval(cm, CM), jpl_call(CM, executeAgent, [], _)";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));
		

		System.out.println("End CM");		

		
		/*try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

}
