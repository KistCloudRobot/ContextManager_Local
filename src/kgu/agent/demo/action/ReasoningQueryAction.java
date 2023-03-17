package kgu.agent.demo.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.jpl7.Query;
import org.jpl7.Term;

import kgu.agent.demo.actionArgument.ReasoningQueryArgument;
import kgu.agent.demo.paser.ContextQueryPaser;
import kr.ac.uos.ai.arbi.agent.logger.action.ActionBody;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kr.ac.uos.ai.arbi.model.Binding;
import kr.ac.uos.ai.arbi.model.BindingFactory;
import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;

public class ReasoningQueryAction implements ActionBody {
	private DataSource ds;
	
	public ReasoningQueryAction(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public Object execute(Object o) {
		ReasoningQueryArgument Log = (ReasoningQueryArgument) o;
		String queryToProlog;
		String queryResult = "";
		double time2;
		double time;

		//System.out.println("what i got is  :" +  o);
		GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(Log.getQueryGL());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// String query = "(context (on_Physical $Top
		// \"http://www.arbi.com/ontologies/arbi.owl#table1\") (on_Physical
		// $Top2 \"http://www.arbi.com/ontologies/arbi.owl#table2\") )";
		String sumOfPrologQuery = "";
		String temp = prefixToURI(Log.getQueryGL()).replaceAll("-", "_");
		String[] forVariable = temp.split("\\)|\\(| |\n|\t");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		
		//System.out.println("gl: "+gl); //test
		//System.out.println(gl.getExpressionsSize()); //test
		
		//GL to PL 바꿔주는 부분 (나중에 모듈화 필요)
		for (int i = 0; i < gl.getExpressionsSize(); i++) {

			Expression e = gl.getExpression(i);
			GeneralizedList temp2 = e.asGeneralizedList();

			int tempSize = temp2.getExpressionsSize();

			String predicate = temp2.getName();
			String prologQuery = predicate + "(";
			String[] arg = new String[tempSize];

			for (int j = 0; j < tempSize; j++) {

				long startTimeEpoch = 0;
				Date startTime = null;

				arg[j] = temp2.getExpression(j).toString();
				//System.out.println(arg[j]);

				if (arg[j].contains("$")) {
					arg[j] = arg[j].substring(1, arg[j].length());
					prologQuery += "" + arg[j] + ", ";

				} else {

					arg[j] = arg[j].substring(1, arg[j].length() - 1);
					//arg[j] = arg[j].substring(1, arg[j].length());
					
					try {
						startTime = sdf.parse(arg[j]);
						startTimeEpoch = startTime.getTime() / 1000;
						arg[j] = "http://www.arbi.com/ontologies/arbi.owl#timepoint_" + startTimeEpoch;
					} catch (java.text.ParseException e2) {}
		
					if(arg[j].contains("#"))
						prologQuery += "'" + arg[j] + "', ";
					else
						prologQuery += "" + arg[j] + ", ";

				} if (j + 1 == temp2.getExpressionsSize()) {
					prologQuery = prologQuery.substring(0, prologQuery.length() - 2);
				}

			}
			prologQuery += ")";

			
		//	System.out.println("prolog Query: "+prologQuery);

			if (i + 1 == gl.getExpressionsSize()) {
				sumOfPrologQuery += prologQuery + ".";
			}else {
				sumOfPrologQuery += prologQuery + ", ";
			}
			

		}
		//System.out.println("Trans to Prolog Query : \n"+sumOfPrologQuery);

		Log.setQueryToProlog(sumOfPrologQuery);

		time = System.currentTimeMillis();
		
		
		
		////////////////////////////////////////////////////// 추론 시작
		Query q = new Query(sumOfPrologQuery);
		//System.out.println("q in ReasoningQueryAction.java" + q);
		while (q.hasMoreSolutions()) {
			//System.out.println("hasSolution q name: " + q);
			// 질의에 대한 결과가 있는 경우
			String temp2 = temp;
			Map<String, Term> s3 = q.nextSolution();
			
			//System.out.println("Map length :"+forVariable.length);

			for (int l = 0; l < forVariable.length; l++) {
				
				//System.out.println("forVariable[l] :" + forVariable[l]);
				
				if (forVariable[l].contains("$")) {
					String variable = forVariable[l].substring(1);						
					String answer = s3.get(variable).toString();
					
					//System.out.println("answer :" + answer);
					
					answer = answer.replace("'", "\"");
					if (!answer.contains("\""))
						answer = "\"" + answer + "\"";
					temp2 = temp2.replace("$" + variable, answer);
				//	System.out.println("temp2" + temp2);
				}

				// System.out.println(s3.toString());

			}
			
			/*if(temp2.split("")) {
				
			}*/

			// System.out.println(s3.toString());
			/*if(temp2.split(" ").length==4) {
				String exps[] = temp2.split(" ");
				String predicate = exps[1].replace("(", "");
				String param1 = exps[2].replace("\"", "'");
				String param2 = exps[3].replace(")", "");
				
				String result=param1+","+predicate+","+param2+",highLevel";
				System.out.println(result);
				
				assertTriple(param1+","+predicate+","+param2+",highLevel");
			}*/

			queryResult += temp2 + "\n";

		}

		time2 = System.currentTimeMillis();
		Log.setReasoningTime(time2 - time);
		Log.setQueryResult(queryResult);
		//System.out.println("query result : " + queryResult);

		if(!queryResult.equals("")) { 
			//hwan//
	        System.out.println("Result in ReasoningQueryAction" + queryResult.getClass().getName());	
		    String[] array=queryResult.split("\\n");
		   // System.out.println(array.length);	
		    Random rand = new Random();   
		    queryResult = array[rand.nextInt(array.length)];
			ds.assertFact(queryResult);
		}
		
		return queryResult;
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
		s = s.replaceAll("arbi:", "http://www.arbi.com/ontologies/arbi.owl#");
		s = s.replaceAll("robot:", "http://knowrob.org/kb/jaco.owl#");

		s = "" + s + "";

		return s;
	}

	public static String URIToPrefix(String s) {

		s = s.replaceAll("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf:");
		s = s.replaceAll("http://knowrob.org/kb/knowrob.owl#", "knowrob:");
		s = s.replaceAll("http://www.arbi.com/ontologies/arbi.owl#", "arbi:");
		s = s.replaceAll("http://knowrob.org/kb/jaco.owl#", "robot:");

		return s;
	}
	
	public void assertTriple(String triple) {
		triple = triple.replace(" ", ",");
		Query.hasSolution("rdf_assert("+triple+")");
	}
	
	public void retractTriple(String triple) {
		triple = triple.replace(" ", ",");
		Query.hasSolution("rdf_retractall("+triple+")");
	}
	
	public void updateTriple(String triple) {
		triple = triple.replace(" ", ",");
		Query.hasSolution("rdf_update("+triple+")");
	}

}