package kgu.agent.demo.action;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import kgu.agent.demo.actionArgument.BatteryArgument;
import kr.ac.uos.ai.arbi.agent.logger.ActionBody;


public class BatteryAction implements ActionBody {

	@Override
	public Object execute(Object o) {
		
		BatteryArgument Log = (BatteryArgument) o;
		String availableBatteryQuery = "batteryRemain('http://www.arbi.com/ontologies/arbi.owl#turtlebot01',Remain).";
		int batteryAvailable = 0;
		int status = 0;
	
		
		
		Query q = new Query(availableBatteryQuery);

		while (q.hasMoreSolutions()) {
			Map<String, Term> s3 = q.nextSolution();
		//	System.out.println(s3.get("Remain"));
			batteryAvailable = s3.get("Remain").intValue();
			// s3.get("O") + "}");
			//System.out.println(tripleCount);
		}
		
		Log.setAvailable(batteryAvailable);
		Log.setStatus(status);
		
		
		return batteryAvailable;
	}

}
