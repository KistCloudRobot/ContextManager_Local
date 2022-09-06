package kgu.agent.demo.paser;

public class ContextQueryPaser {
	
	
	public static String literalConversion(String s) {
		if (s.contains("'")) {
			s = s.split("'")[1];
			if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
				s = "literal(type('http://www.w3.org/2001/XMLSchema#boolean', " + s + "))";
			} else {
				s = "literal(type('http://www.w3.org/2001/XMLSchema#double', '" + s + "'))";
			}
		} else if (s.contains("\"")) {
			s = s.split("\"")[1];
			s = "literal(type('http://www.w3.org/2001/XMLSchema#string', '" + s + "'))";
		}

		return s;
	}

	public static String prefixToURI(String s) {

		s = s.replaceAll("rdf:", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		s = s.replaceAll("knowrob:", "http://knowrob.org/kb/knowrob.owl#");
		s = s.replaceAll("arbi:", "http://www.arbi.com/ontologies/arbi.owl#");

		s = "'" + s + "'";

		return s;
	}

}
