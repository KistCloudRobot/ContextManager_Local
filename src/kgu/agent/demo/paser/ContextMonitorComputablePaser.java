package kgu.agent.demo.paser;

public class ContextMonitorComputablePaser {

	// ���� prefix�� �پ��־�� ��.
	private static String locatedInRoom = "locatedInRoom";
	private static String inFrontAreaOfRoom = "inFrontAreaOfRoom";
	private static String insideAreaOfRoom = "insideAreaOfRoom";
	private static String emptyBattery = "emptyBattery";
	private static String batteryRemain = "batteryRemain";
	private static String batteryStatus = "batteryStatus";
	private static String voicePerceived = "voicePerceived";
	private static String facePerceived = "facePerceived";
	private static String speechContents = "speechContents";
	private static String notFollowRobot = "notFollowRobot";
	private static String near = "near";

	public static String getPredicate(String P) {
		if (P.contains(locatedInRoom))
			return locatedInRoom;
		else if (P.contains(inFrontAreaOfRoom))
			return inFrontAreaOfRoom;
		else if (P.contains(insideAreaOfRoom))
			return insideAreaOfRoom;
		else if (P.contains(emptyBattery))
			return emptyBattery;
		else if (P.contains(batteryStatus))
			return batteryStatus;
		else if (P.contains(batteryRemain))
			return batteryRemain;
		else if (P.contains(voicePerceived))
			return voicePerceived;
		else if (P.contains(facePerceived))
			return facePerceived;
		else if (P.contains(speechContents))
			return speechContents;
		else if (P.contains(notFollowRobot))
			return notFollowRobot;
		else if (P.contains(near))
			return near;

		else
			return "";
	}

	public static boolean isComputable(String P) {
		if (P.contains(locatedInRoom)) {
			return true;
		} else if (P.contains(inFrontAreaOfRoom))
			return true;
		else if (P.contains(insideAreaOfRoom))
			return true;
		else if (P.contains(emptyBattery))
			return true;
		else if (P.contains(batteryStatus))
			return true;
		else if (P.contains(batteryRemain))
			return true;
		else if (P.contains(voicePerceived))
			return true;
		else if (P.contains(facePerceived))
			return true;
		else if (P.contains(speechContents))
			return true;
		else if (P.contains(notFollowRobot))
			return true;
		else if (P.contains(near))
			return true;

		return false;
	}

	public static String getComputableMonitor(String S, String P, String O) {

		if (P.contains(locatedInRoom)) {

			return "monitor(assert(X, 'http://knowrob.org/kb/knowrob.owl#objectActedOn', " + S
					+ ", DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, println, ['dynamic monitor occured !!'], Sys), "
					+ "\n\trdf(X, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://www.arbi.com/ontologies/arbi.owl#LocationPerception'),"
					// + "\n\tcomp_locatedInRoom(" + S + ", " + O + "),";
					+ "\n\trdf_triple('http://www.arbi.com/ontologies/arbi.owl#locatedInRoom'," + S + ", " + O + "),";

		}

		else if (P.contains(inFrontAreaOfRoom)) {
			System.out.println(S);
			System.out.println(O);
			return "monitor(assert(X, 'http://knowrob.org/kb/knowrob.owl#objectActedOn', " + S
					+ ", DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), "
					+ "\n\trdf(X, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://www.arbi.com/ontologies/arbi.owl#LocationPerception'),"
					// + "\n\tcomp_locatedInRoom(" + S + ", " + O + "),";
					+ "\n\trdf_triple('http://www.arbi.com/ontologies/arbi.owl#inFrontAreaOfRoom'," + S + ", " + O
					+ "),";

		}

		else if (P.contains(insideAreaOfRoom)) {

			return "monitor(assert(X1, 'http://knowrob.org/kb/knowrob.owl#objectActedon', S1, DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), "
					+ "\n\trdf(X, 'http://knowrob.org/kb/knowrob.owl#objectActedOn', " + S + "),"
					+ "\n\trdf(X, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://www.arbi.com/ontologies/arbi.owl#LocationPerception'),"
					// + "\n\tcomp_locatedInRoom(" + S + ", " + O + "),";
					+ "\n\trdf_triple('http://www.arbi.com/ontologies/arbi.owl#insideAreaOfRoom'," + S + ", " + O
					+ "),";

		}

		else if (P.contains(emptyBattery)) {

			return "monitor(assert(X, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type','http://www.arbi.com/ontologies/arbi.owl#BatteryPerception'"
					+ ", DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), "
					+ "\n\trdf(X, 'http://knowrob.org/kb/knowrob.owl#objectActedOn'," + S + "),"
					// + "\n\tcomp_locatedInRoom(" + S + ", " + O + "),";
					// + "\n\temptyBattery("+S + ", " + O + "),";
					+ "\n\trdf_triple('http://www.arbi.com/ontologies/arbi.owl#emptyBattery'," + S + ", " + O + "),";

		}

		else if (P.contains(batteryStatus)) {

			return "monitor(assert(X, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type','http://www.arbi.com/ontologies/arbi.owl#BatteryPerception'"
					+ ", DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), "
					+ "\n\trdf(X, 'http://knowrob.org/kb/knowrob.owl#objectActedOn'," + S + "),"
					// + "\n\tcomp_locatedInRoom(" + S + ", " + O + "),";
					// + "\n\temptyBattery("+S + ", " + O + "),";
					+ "\n\trdf_triple('http://www.arbi.com/ontologies/arbi.owl#batteryStatus'," + S + ", " + O + "),";

		}

		else if (P.contains(batteryRemain)) {

			return "monitor(assert(X, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type','http://www.arbi.com/ontologies/arbi.owl#BatteryPerception'"
					+ ", DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), "
					+ "\n\trdf(X, 'http://knowrob.org/kb/knowrob.owl#objectActedOn'," + S + "),"
					// + "\n\tcomp_locatedInRoom(" + S + ", " + O + "),";
					// + "\n\temptyBattery("+S + ", " + O + "),";
					+ "\n\trdf_triple('http://www.arbi.com/ontologies/arbi.owl#batteryRemain'," + S + ", " + O + "),";

		}

		else if (P.contains(voicePerceived)) {
			System.out.println("s" + S);
			System.out.println("p" + P);
			System.out.println("o" + O);

			return "monitor(assert(" + "VoicePerception"
					+ ", 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type','http://www.arbi.com/ontologies/arbi.owl#SpeechPerception'"
					+ ", DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), " + "\n\trdf("
					+ "VoicePerception" + ", 'http://knowrob.org/kb/knowrob.owl#objectActedOn'," + S + ")," + "\n\trdf("
					+ "VoicePerception" + ", 'http://www.arbi.com/ontologies/arbi.owl#speechContents'," + O + "),";

		}

		else if (P.contains(speechContents)) {
			System.out.println("s" + S);
			System.out.println("p" + P);
			System.out.println("o" + O);

			return "monitor(assert(" + S
					+ ", 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type','http://www.arbi.com/ontologies/arbi.owl#SpeechPerception'"
					+ ", DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), " + "\n\trdf("
					+ S + ", 'http://www.arbi.com/ontologies/arbi.owl#speechContents'," + O + "),";

		}

		else if (P.contains(facePerceived)) {
			System.out.println("s" + S);
			System.out.println("p" + P);
			System.out.println("o" + O);

			return "monitor(assert(" + S
					+ ", 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type','http://knowrob.org/kb/knowrob.owl#VisualPerception'"
					+ ", DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), " + "\n\trdf("
					+ S + ", 'http://knowrob.org/kb/knowrob.owl#objectActedOn'," + O + "),";

		}

		else if (P.contains(near)) {
			System.out.println("s" + S);
			System.out.println("p" + P);
			System.out.println("o" + O);

			return "monitor(assert(VP, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://knowrob.org/kb/knowrob.owl#VisualPerception', DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), "
					+ "\n\trdf(VP, 'http://knowrob.org/kb/knowrob.owl#objectActedOn', " + O + "),"
					+ "\n\trdf_triple('http://www.arbi.com/ontologies/arbi.owl#near'," + S + ", " + O+ "),";

		}

		else if (P.contains(notFollowRobot)) {
			System.out.println("s" + S);
			System.out.println("p" + P);
			System.out.println("o" + O);
			// 차후 수정이 필요한 부분.
			
			return "monitor(assert(LP, 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://www.arbi.com/ontologies/arbi.owl#LocationPerception', DB)) :- (jpl_get('java.lang.System', out, Out), jpl_call(Out, print, [''], Sys), "
			 + "\n\trdf(LP, 'http://knowrob.org/kb/knowrob.owl#objectActedOn', " + O + "),"
			 + "\n\trdf_triple('http://www.arbi.com/ontologies/arbi.owl#notFollowRobot'," + S + ", " + O + "),";
			

		}

		return "ERROR : in getComputableMonitor()";
	}

}
