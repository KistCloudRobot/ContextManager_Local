package kgu.agent.demo.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kgu.agent.demo.actionArgument.ContextOntologyMonitorArgument;
import kgu.agent.demo.actionArgument.GUIArgument;
import kgu.agent.demo.actionArgument.LowLevelContextMonitorArgument;
import kr.ac.uos.ai.arbi.agent.logger.ActionBody;
import kr.ac.uos.ai.arbi.ltm.DataSource;

public class GUIAction implements ActionBody {
	private DataSource ds;

	HashMap<String, String> prefixes = new HashMap<String, String>();

	String basePath;
	String knowrobFilePath;
	String graphFilePath;
	
	GUIArgument guiArg;

	public GUIAction() {

		basePath = new File(GUIAction.class.getProtectionDomain().getCodeSource().getLocation().getPath())
				.getAbsolutePath();
		basePath = basePath.substring(0, basePath.indexOf("bin"));
		//basePath+="ContextManager";
		/*graphFilePath = new File(basePath
				+ "graph_file/perceptionGraph.owl")
						.getAbsolutePath();*/
		System.out.println(basePath);

		knowrobFilePath = new File(basePath + "cmProlog/knowrob_library/social_.owl").getAbsolutePath();

		prefixes.put("http://www.arbi.com/ontologies/arbi.owl", "arbi");
		prefixes.put("http://knowrob.org/kb/comp_spatial.owl", "comp_spatial");
		prefixes.put("http://knowrob.org/kb/comp_temporal.owl", "comp_temporal");
		prefixes.put("http://knowrob.org/kb/computable.owl", "computable");
		prefixes.put("http://purl.org/dc/elements/1.1/", "dc");
		prefixes.put("http://knowrob.org/kb/knowrob.owl", "knowrob");
		prefixes.put("http://www.w3.org/2002/07/owl", "owl");
		prefixes.put("http://www.w3.org/1999/02/22-rdf-syntax-ns", "rdf");
		prefixes.put("http://www.w3.org/2000/01/rdf-schema", "rdfs");
		prefixes.put("http://knowrob.org/kb/srdl2-comp.owl", "srdl2comp");
		prefixes.put("http://www.w3.org/2001/XMLSchema", "xsd");

	}

	@Override
	public Object execute(Object o) {
		guiArg = (GUIArgument)o;
		lowLevelContextMonitor(guiArg.getLlcm());
		contextOntologyMonitor(guiArg.getCom());
		
		saveGraph();
		saveQueryGraph();
		return null;
	}

	public void saveGraph() {
		String getGraphData = null;
		String S = null;
		String P = null;
		String O = null;
		JSONObject jsonFile = new JSONObject();
		// JSONObject node = new JSONObject();
		// JSONObject edge = new JSONObject();
		JSONArray node_list = new JSONArray();
		JSONArray edge_list = new JSONArray();

		JSONObject nodeElement = new JSONObject();
		JSONObject edgeElement = new JSONObject();
		// String jsontext = "testdata = ";
		// String nodes="{\"nodes\":[";
		// String edges="], \"edges\":[";

		String getObjectGraphData = "rdf(S,P,O,objectPerception).";
		String getGraspGraphData = "rdf(S,P,O,graspPerception).";
		String getRobotGraphData = "rdf(S,P,O,robotPerception).";
		Query getObjectq = new Query(getObjectGraphData);
		Query getGraspq = new Query(getGraspGraphData);
		Query getRobotq = new Query(getRobotGraphData);

		/*
		 * node shape : "ellipse" color : object --> "#FB7E81" grasp --> "#97C2FC" robot
		 * --> "#C2FABC"
		 */
		String nodeShape = "ellipse";
		String objectColor = "#FB7E81";
		String graspColor = "#97C2FC";
		String robotColor = "#C2FABC";

		// answer
		Map<String, Term>[] Obj = getObjectq.allSolutions();
		Map<String, Term>[] Grs = getGraspq.allSolutions();
		Map<String, Term>[] Rbs = getRobotq.allSolutions();

		Map<String, String> tmpNodes = new HashMap<>();

		// This is for Object
		for (int i = 0; i < Obj.length; i++) {
			S = Obj[i].get("S").toString().replace("#", ":");
			P = Obj[i].get("P").toString().replace("#", ":");
			O = Obj[i].get("O").toString().replace("#", ":");

			for (Map.Entry<String, String> prefix : prefixes.entrySet()) {
				S = S.replace(prefix.getKey(), prefix.getValue());
				P = P.replace(prefix.getKey(), prefix.getValue());
				O = O.replace(prefix.getKey(), prefix.getValue());
			}

			if (tmpNodes.get(S) == null) {

				nodeElement.put("id", "o" + S);
				nodeElement.put("label", S);
				nodeElement.put("shape", nodeShape);
				nodeElement.put("color", objectColor);
				node_list.add(nodeElement);
				nodeElement = new JSONObject();
			}
			if (tmpNodes.get(O) == null) {

				if (O.contains("xsd:")) {
					objectColor = "#808080";
					nodeShape = "square";
				}

				nodeElement.put("id", "o" + O);
				nodeElement.put("label", O);
				nodeElement.put("shape", nodeShape);
				nodeElement.put("color", objectColor);
				node_list.add(nodeElement);
				nodeElement = new JSONObject();
				objectColor = "#FB7E81";
				nodeShape = "ellipse";

			}
			edgeElement.put("from", "o" + S);
			edgeElement.put("to", "o" + O);
			edgeElement.put("label", P);
			edgeElement.put("color", objectColor);
			edge_list.add(edgeElement);
			edgeElement = new JSONObject();
			tmpNodes.put(S, S);
			tmpNodes.put(O, O);
		}

		// This is for Grasp
		for (int i = 0; i < Grs.length; i++) {
			S = Grs[i].get("S").toString().replace("#", ":");
			P = Grs[i].get("P").toString().replace("#", ":");
			O = Grs[i].get("O").toString().replace("#", ":");

			for (Map.Entry<String, String> prefix : prefixes.entrySet()) {
				S = S.replace(prefix.getKey(), prefix.getValue());
				P = P.replace(prefix.getKey(), prefix.getValue());
				O = O.replace(prefix.getKey(), prefix.getValue());
			}

			if (tmpNodes.get(S) == null) {
				nodeElement.put("id", "g" + S);
				nodeElement.put("label", S);
				nodeElement.put("shape", nodeShape);
				nodeElement.put("color", graspColor);
				node_list.add(nodeElement);
				nodeElement = new JSONObject();
			}
			if (tmpNodes.get(O) == null) {
				if (O.contains("xsd:")) {
					graspColor = "#808080";
					nodeShape = "square";
				}

				nodeElement.put("id", "g" + O);
				nodeElement.put("label", O);
				nodeElement.put("shape", nodeShape);
				nodeElement.put("color", graspColor);
				node_list.add(nodeElement);
				nodeElement = new JSONObject();
				graspColor = "#97C2FC";
				nodeShape = "ellipse";

			}
			edgeElement.put("from", "g" + S);
			edgeElement.put("to", "g" + O);
			edgeElement.put("label", P);
			edgeElement.put("color", graspColor);
			edge_list.add(edgeElement);
			edgeElement = new JSONObject();
			tmpNodes.put(S, S);
			tmpNodes.put(O, O);
		}

		// This is for Robot
		for (int i = 0; i < Rbs.length; i++) {
			S = Rbs[i].get("S").toString().replace("#", ":");
			P = Rbs[i].get("P").toString().replace("#", ":");
			O = Rbs[i].get("O").toString().replace("#", ":");

			for (Map.Entry<String, String> prefix : prefixes.entrySet()) {
				S = S.replace(prefix.getKey(), prefix.getValue());
				P = P.replace(prefix.getKey(), prefix.getValue());
				O = O.replace(prefix.getKey(), prefix.getValue());
			}

			if (tmpNodes.get(S) == null) {
				nodeElement.put("id", "r" + S);
				nodeElement.put("label", S);
				nodeElement.put("shape", nodeShape);
				nodeElement.put("color", robotColor);
				node_list.add(nodeElement);
				nodeElement = new JSONObject();
			}
			if (tmpNodes.get(O) == null) {
				if (O.contains("xsd:")) {
					robotColor = "#808080";
					nodeShape = "square";
				}

				nodeElement.put("id", "r" + O);
				nodeElement.put("label", O);
				nodeElement.put("shape", nodeShape);
				nodeElement.put("color", robotColor);
				node_list.add(nodeElement);
				nodeElement = new JSONObject();
				robotColor = "#C2FABC";
				nodeShape = "ellipse";
			}
			edgeElement.put("from", "r" + S);
			edgeElement.put("to", "r" + O);
			edgeElement.put("label", P);
			edgeElement.put("color", robotColor);
			edge_list.add(edgeElement);
			edgeElement = new JSONObject();
			tmpNodes.put(S, S);
			tmpNodes.put(O, O);
		}

		// nodes=nodes.substring(0,nodes.length());

		// edges=edges.substring(0,edges.length());

		// jsontext += nodes + edges + "]};";
		// JSONObject jsonName = new JSONObject();
		jsonFile.put("nodes", node_list);
		jsonFile.put("edges", edge_list);

		try {
			String outDir = basePath + "pyweb/src/webtest/gui/";
			String outFileName = "perceptionGraph.json";

			File outputFile = new File(outDir, outFileName);
			FileWriter output = new FileWriter(outputFile);
			BufferedWriter bw = new BufferedWriter(output);

			bw.write(jsonFile.toJSONString());
			bw.flush();
			bw.close();
			output.close();

		} catch (Exception e) {

			e.getStackTrace();
		}

	}

	public void saveQueryGraph() {
		String getGraphData = null;
		String S = null;
		String P = null;
		String O = null;
		JSONObject jsonFile = new JSONObject();
		JSONArray node_list = new JSONArray();
		JSONArray edge_list = new JSONArray();
		JSONObject nodeElement = new JSONObject();
		JSONObject edgeElement = new JSONObject();

		String getQueryGraphData = "rdf(S,P,O,highLevel).";
		Query getQueryq = new Query(getQueryGraphData);

		String nodeShape = "dot";
		String queryColor = "#FB7E81";

		// answer
		Map<String, Term>[] query = getQueryq.allSolutions();

		Map<String, String> tmpNodes = new HashMap<>();

		// This is for Object
		for (int i = 0; i < query.length; i++) {
			S = query[i].get("S").toString().replace("#", ":");
			P = query[i].get("P").toString().replace("#", ":");
			O = query[i].get("O").toString().replace("#", ":");

			for (Map.Entry<String, String> prefix : prefixes.entrySet()) {
				S = S.replace(prefix.getKey(), prefix.getValue());
				P = P.replace(prefix.getKey(), prefix.getValue());
				O = O.replace(prefix.getKey(), prefix.getValue());
			}

			if (tmpNodes.get(S) == null) {
				nodeElement.put("id", "h" + S);
				nodeElement.put("label", S);
				nodeElement.put("shape", nodeShape);
				nodeElement.put("color", queryColor);
				node_list.add(nodeElement);
				nodeElement = new JSONObject();
			}
			if (tmpNodes.get(O) == null) {

				/*
				 * if(O.contains("xsd:")){ graspColor="#808080"; nodeShape = "square"; }
				 */

				nodeElement.put("id", "h" + O);
				nodeElement.put("label", O);
				nodeElement.put("shape", nodeShape);
				nodeElement.put("color", queryColor);
				node_list.add(nodeElement);
				nodeElement = new JSONObject();
			}
			edgeElement.put("from", "h" + S);
			edgeElement.put("to", "h" + O);
			edgeElement.put("label", P);
			edgeElement.put("color", queryColor);
			edge_list.add(edgeElement);
			edgeElement = new JSONObject();
			tmpNodes.put(S, S);
			tmpNodes.put(O, O);
		}

		jsonFile.put("nodes", node_list);
		jsonFile.put("edges", edge_list);

		try {
			// String outDir =
			// "/home/ubuntu/Desktop/test/social_ws/src/socialrobot/src/socialrobot_knowledge/context_manager/context_manager/src/main/java/org/ros/web_data_send_action/";
			String outDir = basePath + "pyweb/src/webtest/gui/";
			String outFileName = "FSDGraph.json";
			File outputFile = new File(outDir, outFileName);
			FileWriter output = new FileWriter(outputFile);
			BufferedWriter bw = new BufferedWriter(output);

			bw.write(jsonFile.toJSONString());
			bw.flush();
			bw.close();
			output.close();
			output = null;

		} catch (Exception e) {

			e.getStackTrace();
		}

	}

	public void lowLevelContextMonitor(LowLevelContextMonitorArgument llcm) {

		int tCount = 0;
		int oCount = 0;
		int rCount = 0;
		int hCount = 0;

		String getGraphDataEdge = null;
		String tripleNum = "0";
		getGraphDataEdge = "rdf_graph_property(objectPerception, triples(Count)).";
		Query q = new Query(getGraphDataEdge);
		Map<String, Term>[] Obj = q.allSolutions();

		if (Obj.length != 0)
			tripleNum = Obj[0].get("Count").toString();
		tCount += Integer.parseInt(tripleNum);

		getGraphDataEdge = "rdf_graph_property(robotPerception, triples(Count)).";
		q = new Query(getGraphDataEdge);
		Obj = q.allSolutions();

		if (Obj.length != 0)
			tripleNum = Obj[0].get("Count").toString();
		tCount += Integer.parseInt(tripleNum);

		getGraphDataEdge = "rdf_graph_property(graspPerception, triples(Count)).";
		q = new Query(getGraphDataEdge);
		Obj = q.allSolutions();

		if (Obj.length != 0)
			tripleNum = Obj[0].get("Count").toString();
		tCount += Integer.parseInt(tripleNum);

		// System.out.println("getGraphDataTriple: "+tCount);

		// SD.LCMD.triples = Integer.toString(tripleNum);

		String getGraphDataObject = null;
		getGraphDataObject = "rdf(ObjectPerception, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://knowrob.org/kb/knowrob.owl#VisualObjectPerception').";
		q = new Query(getGraphDataObject);
		while (q.hasMoreSolutions()) {
			q.nextSolution();
			oCount++;
		}
		// System.out.println("getGraphDataObject:"+oCount);
		// count=0;

		// SD.LCMD.objectperception = Integer.toString((int) count);

		String getGraphDataRobot = null;
		getGraphDataRobot = "(rdfs_individual_of(RobotPerception, 'http://knowrob.org/kb/knowrob.owl#Proprioception');rdfs_individual_of(RobotPerception, 'http://knowrob.org/kb/knowrob.owl#VisualRobotPerception')),(rdf(RobotPerception,_,_);rdf(_,_,RobotPerception)).";
		q = new Query(getGraphDataRobot);

		while (q.hasMoreSolutions()) {
			q.nextSolution();
			rCount++;
		}
		// System.out.println("getGraphDataRobot:"+rCount);
		// count=0;
		// SD.LCMD.robotperception = Integer.toString((int) count);

		String getGraphDataHuman = null;
		getGraphDataHuman = "rdf(HumanPerception, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://knowrob.org/kb/knowrob.owl#VisualHumanPerception'),(rdf(HumanPerception,_,_);rdf(_,_,HumanPerception)).";
		q = new Query(getGraphDataHuman);

		while (q.hasMoreSolutions()) {
			q.nextSolution();
			hCount++;
		}
		// System.out.println("getGraphDataHuman:"+hCount);

		// SD.LCMD.humanperception = Integer.toString((int) count);

		llcm.setTriples(Integer.toString(tCount));
		llcm.setObjects(Integer.toString(oCount));
		llcm.setRobots(Integer.toString(rCount));
		llcm.setHumans(Integer.toString(hCount));
		// To web
		// lContextProvider.publish(llcm);

	}

	public void contextOntologyMonitor(ContextOntologyMonitorArgument com) {
		int tCount = 0;
		int cCount = 0;
		int iCount = 0;
		int oCount = 0;
		int dCount = 0;

		String getGraphDataEdge = null;
		String tripleNum = "0";
		getGraphDataEdge = "rdf_graph_property('file://" + knowrobFilePath + "', " + "triples(Count))."; // file:///home/ubuntu/cmProlog/knowrob_library/knowrob.owl',
																											// triples(Count)).";
		Query q = new Query(getGraphDataEdge);
		Map<String, Term>[] Obj = q.allSolutions();

		if (Obj.length != 0)
			tripleNum = Obj[0].get("Count").toString();
		tCount = Integer.parseInt(tripleNum);

		// System.out.println("getGraphDataTriple: "+tripleNum);

		// SD.COMD.triples = Integer.toString(tripleNum);

		HashSet classSet = new HashSet();
		String getGraphClasses = null;
		getGraphClasses = "(rdf(_,'http://www.w3.org/2000/01/rdf-schema#subClassOf',S,'file://" + knowrobFilePath
				+ "');rdf(S,'http://www.w3.org/2000/01/rdf-schema#subClassOf',_,'file://" + knowrobFilePath + "')).";

		q = new Query(getGraphClasses);
		Map<String, Term>[] t = q.allSolutions();
		for (int i = 0; i < t.length; i++) {
			classSet.add(t[i].get("S").toString());
		}
		// System.out.println("getGraphClasses:"+ClassSet.size());
		// System.out.println("Classes:"+ClassSet);

		cCount = classSet.size();
		// SD.COMD.classes = Integer.toString(ClassSet.size());

		HashSet individualSet = new HashSet();
		String getGraphDataIndividual = null;
		getGraphDataIndividual = "rdf_reachable(S,'http://www.w3.org/1999/02/22-rdf-syntax-ns#type','http://www.w3.org/2002/07/owl#NamedIndividual'),rdf(S,_,_,'file://"
				+ knowrobFilePath + "').";
		q = new Query(getGraphDataIndividual);

		Map<String, Term>[] s = q.allSolutions();
		for (int i = 0; i < s.length; i++) {
			individualSet.add(s[i].get("S").toString());
		}
		// System.out.println("getGraphDataIndividual:"+individualSet.size());

		// SD.COMD.individuals = Integer.toString(individualSet.size());
		iCount = individualSet.size();

		HashSet objectPropertySet = new HashSet();
		String getGraphDataObjectProperty = null;
		getGraphDataObjectProperty = "rdf_reachable(P, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type','http://www.w3.org/2002/07/owl#ObjectProperty'),rdf(P,_,_,'file://"
				+ knowrobFilePath + "').";
		q = new Query(getGraphDataObjectProperty);

		Map<String, Term>[] w = q.allSolutions();

		for (int i = 0; i < w.length; i++) {
			objectPropertySet.add(w[i].get("P").toString());
		}
		// System.out.println("getGraphDataObjectProperty:"+objectPropertySet.size());

		// SD.COMD.objectProperties= Integer.toString(objectPropertySet.size());
		oCount = objectPropertySet.size();

		HashSet dataPropertySet = new HashSet();
		String getGraphDataDataProperty = null;
		getGraphDataDataProperty = "rdf_reachable(P, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type','http://www.w3.org/2002/07/owl#DatatypeProperty'),rdf(P,_,_,'file://"
				+ knowrobFilePath + "').";
		q = new Query(getGraphDataDataProperty);

		Map<String, Term>[] l = q.allSolutions();

		for (int i = 0; i < l.length; i++) {
			dataPropertySet.add(l[i].get("P").toString());
		}
		// System.out.println("getGraphDataDataProperty:"+dataPropertySet.size());

		// SD.COMD.datatypeProperties = Integer.toString(dataPropertySet.size());
		dCount = dataPropertySet.size();

		com.setTriples(Integer.toString(tCount));
		com.setClasses(Integer.toString(cCount));
		com.setIndividuals(Integer.toString(iCount));
		com.setObjectProperties(Integer.toString(oCount));
		com.setDatatypeProperties(Integer.toString(dCount));

		// To web
		// cContextProvider.publish(com);

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

}