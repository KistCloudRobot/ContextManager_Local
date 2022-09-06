package kgu.agent.demo.paser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ContextMonitorParser {
	
	public static String PushRuleToMonitorRule(String pushRule){
		
		String monitorRule = "";
		
		HashMap<String, String> variable = new HashMap<String, String>();
		
		// ���� ����
		String[] forVariable = pushRule.split("\\)|\\(| |\n|\t");
		
		for(int i=0; i<forVariable.length; i++){
			System.out.println(forVariable[i]);
			if(forVariable[i].startsWith("$")){
				String v = forVariable[i].split("\\$")[1];
				variable.put(v, v.toUpperCase());
			}
		}
		

		String[] conditionAndAction = pushRule.split("-->");
		
		
		ArrayList<String> conditions = new ArrayList<String>();
		String conditionLine = "";
		int count = 1;
		
		if(conditionAndAction[0].startsWith("(rule ")){
			String[] splitedConditions = conditionAndAction[0].split("\\)|\\(| |\n|rule|\t");
			for(int i=0; i<splitedConditions.length; i++){
				//System.out.println(splitedConditions[i]);
				
				if(!splitedConditions[i].equals("")){
					
					conditionLine = conditionLine + splitedConditions[i] + " ";
					
					if(!splitedConditions[i].contains("#")){
						count++;
					}
					
					if(count == 5){
						count = 1;
						conditions.add(conditionLine);
						conditionLine = "";
					}
					
				}
			}
		}
		else{
			System.out.println("ERROR : Missing string : \"(rule \"");
			System.out.println("ERROR : " + conditionAndAction[0]);
		}
		
		// ���Ǻθ� �����$ ��Ģ���� ��ȯ
		for(int i=0; i<conditions.size(); i++){
		
			String condition = conditions.get(i);
			
			String[] c = condition.split(" ");
			String S = "";
			String P = "";
			String O = "";
			
			/*System.out.println(c[0]);
			System.out.println(c[1]);
			System.out.println(c[2]);
			System.out.println(c[3]);*/
			if(condition.contains("#")){
				if(c[2].startsWith("$")){
					c[2] = variable.get(c[1].split("\\$")[1]);
				}
				if(c[3].startsWith("$")){
					c[3] = variable.get(c[2].split("\\$")[1]);
				}
				if(c[4].startsWith("$")){
					c[4] = variable.get(c[3].split("\\$")[1]);
				}
				
				S = c[3];
				P = c[2];
				O = c[4];
			}else{
				if(c[1].startsWith("$")){
					c[1] = variable.get(c[1].split("\\$")[1]);
				}
				if(c[2].startsWith("$")){
					c[2] = variable.get(c[2].split("\\$")[1]);
				}
				if(c[3].startsWith("$")){
					c[3] = variable.get(c[3].split("\\$")[1]);
				}
				
				S = c[2];
				P = c[1];
				O = c[3];
			}
			
			// ���ͷ� �� ó��
			S = literalConversion(S);
			P = literalConversion(P);
			O = literalConversion(O);
			
			// prefix ó��
			if(S.contains(":")){
				S = prefixToURI(S);
			}
			if(P.contains(":")){
				P = prefixToURI(P);
			}
			if(O.contains(":")){
				O = prefixToURI(O);
			}

			// ù �� ��ȯ
			if(i == 0 && c[0].equals("fact")){
			
				/* �����$ �����$ ��Ģ ��ȯ �ʿ� */
				if(ContextMonitorComputablePaser.isComputable(P)){
					
					monitorRule = ContextMonitorComputablePaser.getComputableMonitor(S, P, O);
				}
				else{
					monitorRule = "monitor(assert("+ S +", " + P + ", " + O + "), DB) :- (";
				}
			
			// ù �� ���� ��ȯ
			}else if(i > 0){
				if(c[0].equals("expression")){
					P = ContextMonitorComputablePaser.getPredicate(P);
					
					monitorRule = monitorRule + "\n\t" + P + "("+ S +", " + O + "),";
				}
				else if(c[0].equals("fact")){
					
					monitorRule = monitorRule + "\n\trdf("+ S +", " + P + ", " + O + "),";
				}
				else{
					System.out.println("ERROR : In case of the line that is not first, condition must be 'fact' or 'expression #'");
					System.out.println("ERROR : " + condition);
				}
				
			}else{
				if(i == 0){
					System.out.println("ERROR : first line of condition must be 'fact'");
					System.out.println("ERROR : " + condition);
					System.out.println("ERROR : " + c[0]);
				}else{
					System.out.println("ERROR : In case of the line that is not first, condition must be 'fact' or 'expression #'");
					System.out.println("ERROR : " + condition);
				}

			}

		}
		
	
		String[] splitedActions = conditionAndAction[1].split("\\)|\\(| |\n|\t");
		ArrayList<String> actions = new ArrayList<String>();
		String actionLine = "";
		count = 1;
		for(int i=0; i<splitedActions.length; i++){
			//System.out.println(splitedConditions[i]);
			if(!splitedActions[i].equals("")){
				
				actionLine = actionLine + splitedActions[i] + " ";
				
				count++;
				if(count == 5){
					count = 1;
					actions.add(actionLine);
					actionLine = "";
					
				}
				
			}
		}
		
		// ����θ�$ �����$ ��Ģ���� ��ȯ
		for(int i=0; i < actions.size(); i++){
				
			String action = actions.get(i);
			
			
			String[] c = action.split(" ");
					
			/*System.out.println(c[0]);
			System.out.println(c[1]);
			System.out.println(c[2]);
			System.out.println(c[3]);*/
					
			/* ���ͷ� ó�� �ʿ� */
			if(c[1].startsWith("$")){
				c[1] = variable.get(c[1].split("\\$")[1]);
			}
			if(c[2].startsWith("$")){
				c[2] = variable.get(c[2].split("\\$")[1]);
			}
			if(c[3].startsWith("$")){
				c[3] = variable.get(c[3].split("\\$")[1]);
			}
			String S = c[2];
			String P = c[1];
			String O = c[3];
			
			// ���ͷ� �� ó��
			S = literalConversion(S);
			P = literalConversion(P);
			O = literalConversion(O);
			
			// String ó��, ������ ���� ������ �ƴ� ���$
			if(S.contains(":")){
				S = "'" + S + "'";
				// term_to_atom($Term, $Atom)
			}
			if(P.contains(":")){
				P = "'" + P + "'";
				// term_to_atom($Term, $Atom)
			}
			if(O.contains(":")){
				O = "'" + O + "'";
				// term_to_atom($Term, $Atom)
			}
			

			// ���� �� ��ȯ
			if(c[0].equals("notify")){
				
				monitorRule = monitorRule + "\n\tterm_to_atom('agent://www.arbi.com/taskManager', AA),";
				monitorRule = monitorRule + "\n\tterm_to_atom(" + S + ", SA),";
				monitorRule = monitorRule + "\n\tterm_to_atom(" + P + ", PA),";
				monitorRule = monitorRule + "\n\tterm_to_atom(" + O + ", OA),";

				monitorRule = monitorRule + "\n\tjpl_new('kgu.agent.demo.agent.ContextMonitor', [], CMM),";
				monitorRule = monitorRule + "\n\tjpl_call(CMM, onMonitor, [AA, SA, PA, OA], _))";
				System.out.println("##############");
				System.out.println(monitorRule);

				//monitorRule = monitorRule + "\n\tjpl_call(CM, onMonitor, ['!ADDRESS!', " + S + ", " + P + ", " + O + "], _)";

			}else{
				System.out.println("ERROR : only line of action must be 'notify(P S O)'");
				System.out.println("ERROR : " + action);
			}

		}
		
		
		
		
		Iterator<String> iter = variable.keySet().iterator();
		
		System.out.println();
		System.out.println("------- Variable List -------");
		while(iter.hasNext()){
			String key = iter.next();
			String value = variable.get(key);
			System.out.println(key + " " + value);
		}
		System.out.println();
		
		return monitorRule;
		
	}
	
	public static boolean isDouble(String num)
	{
		try{
			Double.parseDouble(num);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
	public static String literalConversion(String s){
		if (s.contains("'")) {
			s = s.split("'")[1];
			if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
				s = "literal(type('http://www.w3.org/2001/XMLSchema#boolean', "
						+ s + "))";
			} else {
				s = "literal(type('http://www.w3.org/2001/XMLSchema#double', '"
						+ s + "'))";
			}
		} else if (s.contains("\"")) {
			/*
			s = s.split("\"")[1];
			s = "literal(type('http://www.w3.org/2001/XMLSchema#string', '"
					+ s + "'))";
					*/
			s = s.replace("\"", "");
		}
		
		return s;
	}
	
	public static String prefixToURI(String s){
		System.out.println("#################################");
		
		s = s.replaceAll("rdf:", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		s = s.replaceAll("knowrob:", "http://knowrob.org/kb/knowrob.owl#");
		s = s.replaceAll("arbi:", "http://www.arbi.com/ontologies/arbi.owl#");
		s = s.replaceAll("test_sp:", "http://knowrob.org/kb/test_comp_spatial.owl#");
		
		s = "'" + s + "'";
		System.out.println(s);
		
		return s;
	}
	
	// low-level & variable
	public static void example_1(){
		
		String pushRule = "(rule "
				              + "\n\t(fact (rdf:type $x arbi:LocationPerception)) "
				              + "\n\t(fact (knowrob:objectActedOn $x arbi:turtlebot01)) "
				        + "\n--> "
				              + "\n\t(notify (rdf:type $x arbi:LocationPerception))"
				        + "\n)";
		
		System.out.println(pushRule);
		System.out.println();
		System.out.println();
		System.out.println(PushRuleToMonitorRule(pushRule));

	}
	
	// function & literal
	public static void example_2(){
		
		String pushRule = "(rule "
	              			+ "\n\t(fact (rdf:type $x arbi:locationPerception)) "
	              			+ "\n\t(fact (knowrob:objectActedOn $x arbi:turtlebot01)) "
	              			+ "\n\t(expression (arbi:locatedInRoom arbi:turtlebot01 knowrob:robby-room)) "
	              	    + "\n--> "
	              	    	+ "\n\t(notify (arbi:isArrivedDestination arbi:turtlebot01 'true'))"
	              	    + "\n)";

		System.out.println(pushRule);
		System.out.println();
		System.out.println();
		System.out.println(PushRuleToMonitorRule(pushRule));
		
	}
	
	// high-level
	public static void example_3(){
		
		String pushRule = "(rule "
							+ "\n\t(fact (arbi:locatedInRoom arbi:turtlebot01 $Room)) "
						+ "\n--> "
							+ "\n\t(notify (arbi:locatedInRoom arbi:turtlebot01 $Room)) "
						+ "\n)";

		System.out.println(pushRule);
		System.out.println();
		System.out.println();
		System.out.println(PushRuleToMonitorRule(pushRule));
		
	}

}
