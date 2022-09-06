package test;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.model.Binding;
import kr.ac.uos.ai.arbi.model.BindingFactory;
import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;

public class glTest extends ArbiAgent {

	public static void main(String[] args) {
	// TODO Auto-generated method stub
	 String request = "(arbi:checkCliff \"arbi:turtlebot01\" $Cliff)";
	//String request = "(requestWrap (arbi:batteryRemain \"arbi:turtlebot01\" $Remain) (request2 $value $result))";
	String prologQuery ="";
	//String request = "(requestWrap (arbi:batteryRemain \"arbi:turtlebot01\" \"arbi:turtlebot02\") (arbi:batteryRemain \"arbi:turtlebot03\" \"arbi:turtlebot04\"))";

	GeneralizedList gl = null;
	try {
		gl = GLFactory.newGLFromGLString(request);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(gl.toString());
	System.out.println(gl.getExpressionsSize());
		

		
		

		System.out.println(gl.toString());
		Binding binding = BindingFactory.newBinding();
		boolean Ov = false;
		boolean Sv = false;



		String P = gl.getName();
		P = prefixToURI(P);
		String S = gl.getExpression(0).toString();
		String O = gl.getExpression(1).toString();

		if (S.contains("$")) { // ���� ���� Ȯ��
			Sv = true;
			S = S.substring(1);
		} else { // ������ �ƴ� ������ prefix�� ���ͷ��� ó��
			S = S.substring(1, S.length() - 1);
			S = literalConversion(S);
			if (S.contains(":"))
				S = prefixToURI(S);
			else
				S = "'" + S + "'";

		}

		if (O.contains("$")) { // ���� ���� Ȯ��
			Ov = true;
			O = O.substring(1);
		} else { // ������ prefix�� ���ͷ��� ó��
			O = O.substring(1, O.length() - 1);
			O = literalConversion(O);
			if (O.contains(":"))
				O = prefixToURI(O);
			else
				O = "'" + O + "'";

		}

		System.out.println("P : " + P);
		System.out.println("S : " + S + " ���� ���� :" + Sv);
		System.out.println("O : " + O + " ���� ���� :" + Ov);
		
	
			prologQuery = "\nrdf_triple(" + P + ", " + S + ", " + O + ").";

		
		
		
		
		

	
	System.out.println(prologQuery);

}
	
	
	
	
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
		s = s.replaceAll("arbi:", "http://www.arbi.com/arbi#");
		s = s.replaceAll("test_sp:", "http://knowrob.org/kb/test_comp_spatial.owl#");

		s = "'" + s + "'";

		return s;
	}
	
	
	
	
	
	
	
	
	
	
	
}