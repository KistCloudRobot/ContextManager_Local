package kgu.agent.demo.action;



import org.jpl7.Query;
import org.jpl7.Term;
import kgu.agent.demo.actionArgument.SubscribeArgument;
import kgu.agent.demo.paser.ContextMonitorParser;
import kr.ac.uos.ai.arbi.agent.logger.action.ActionBody;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;

public class SubscribeAction implements ActionBody {
	
	
	@Override
	public Object execute(Object o) {
		
		SubscribeArgument Log =  (SubscribeArgument)o;
		String subscribeGL = Log.getSubscribeGL();
		String sender = Log.getSubscriber();
		
		
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("---> onSubscribe() : ");
		System.out.println("message = " + subscribeGL + " from " + sender);


		String t = "";
		String rule = "";
		String id = "";

		String[] s = subscribeGL.split("\\(id ");

		rule = s[0];
		id = s[1].split("\\)| ")[0];
		System.out.println("id print" + id);
		System.out.println("Rule: "+rule);
		
		/*
		if (taskManagerSubsList.containsValue(convertedData)) {
			return "(subscribed \"fail\")";
		}*/


		String convertedData = ContextMonitorParser.PushRuleToMonitorRule(rule);
		convertedData = convertedData.replaceAll("!ADDRESS!", sender);
		String[] glToProlog = convertedData.split(":-");
		Log.setSubscribeGLToProlog(glToProlog[0]);
		
	
		System.out.println("converted subscribe rule = " + convertedData);

		
		t = "assert(" + convertedData + ")";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));
		return "(subscribed \"success\" \"" + id + "\")";
		
		
	}


}
