package kgu.agent.demo;

import org.jpl7.Query;	

import kgu.agent.demo.agent.ContextManager;
import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;

public class Local_CM_Launcher {
	public static void main(String[] args) {
		String t = "jpl_new('kgu.agent.demo.agent.Local_CM', [], Local_CM), nb_setval(local_cm, Local_CM)";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));		
	
		t = "nb_getval(local_cm, Local_CM), jpl_call(Local_CM, executeAgent, [], _)";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));	
		
		System.out.println("Execute Local_CM");		
	}
}