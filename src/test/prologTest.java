package test;
import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;





public class prologTest {
	
	
	
	public static void prolog() {		
		
		
		String t = "rdf(S,P,O).";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));
		Query q = new Query(t);
		
		while (q.hasMoreSolutions()) {
			Map<String, Term> s3 = q.nextSolution();
			// System.out.println("{" + s3.get("S") + s3.get("P") +
			// s3.get("O"));
			// + "}");
			// System.out.println("{" + s3.get("S") + " " +
			// "'http://ailab.kyonggi.ac.kr#isArrivedDestination'" + " " +
			// s3.get("O") + "}");
			System.out.println(s3.toString());
		}
		
	
		
	}

}
