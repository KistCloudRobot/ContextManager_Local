package kgu.agent.demo.action;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;
import kgu.agent.demo.actionArgument.ReasoningQueryArgument;
import kgu.agent.demo.actionArgument.TripleCountArgument;
import kr.ac.uos.ai.arbi.agent.logger.action.ActionBody;


public class TripleCountAction implements ActionBody {

	@Override
	public Object execute(Object o) {
		
		TripleCountArgument Log = (TripleCountArgument) o;
		String tripleCountQuery = "rdf_statistics(triples(Count)).";
		String tripleCount ="";
	
		
		
		Query q = new Query(tripleCountQuery);

		while (q.hasMoreSolutions()) {
			Map<String, Term> s3 = q.nextSolution();
			System.out.println(s3.get("Count"));
			tripleCount = s3.get("Count").toString();
			// s3.get("O") + "}");
			//System.out.println(tripleCount);
		}
		
		Log.setTriple(tripleCount);
		
		
		return tripleCount;
	}

}
