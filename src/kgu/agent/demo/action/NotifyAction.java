package kgu.agent.demo.action;

import kgu.agent.demo.actionArgument.NotifyArgument;
import kr.ac.uos.ai.arbi.agent.logger.action.ActionBody;


public class NotifyAction implements ActionBody {

	@Override
	public Object execute(Object o) {
		double time2;
		double time;
		time = System.currentTimeMillis();
		NotifyArgument Log =  (NotifyArgument)o;
		String S = Log.getSubject();
		String P = Log.getPredicate();
		String O = Log.getObject();
		
		
		S = S.replace('\'', '\"');
		O = O.replace('\'', '\"');
		P = P.replaceAll("'","");

		if (!S.contains("\""))
			S = "\"" + S + "\"";
		if (!O.contains("\""))
			O = "\"" + O + "\"";

		System.out.println();
		System.out.println("---> onMonitor() : ");
		//System.out.println("subscriber = " + address);
		System.out.println("fact = " + S + " " + P + " " + O);
		String notify = "(" + P + " " + S + " " + O + ")";
		
		System.out.println();
		// send(address.split("'")[1],gl);
		time2 = System.currentTimeMillis();
		Log.setReasoningTime(time2-time);
		Log.setNotify(notify);
		
		// TODO Auto-generated method stub
		return notify;
	}

}
