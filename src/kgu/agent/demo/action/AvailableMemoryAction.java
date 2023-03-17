package kgu.agent.demo.action;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import kgu.agent.demo.actionArgument.AvailableMemoryArgument;
import kr.ac.uos.ai.arbi.agent.logger.action.ActionBody;


public class AvailableMemoryAction implements ActionBody {

	@Override
	public Object execute(Object o) {
		
		AvailableMemoryArgument Log = (AvailableMemoryArgument) o;
		String memoryAvailableQuery = "statistics(atoms,Used).";
		int used =0;
	
		
		
		Query q = new Query(memoryAvailableQuery);

		while (q.hasMoreSolutions()) {
			Map<String, Term> s3 = q.nextSolution();
			System.out.println(s3.get("Used"));
			used = s3.get("Used").intValue();

		}
		
		Log.setUsed(used);
		
		
		return used;
	}

}
