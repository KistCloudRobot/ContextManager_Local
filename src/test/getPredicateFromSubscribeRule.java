package test;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;

public class getPredicateFromSubscribeRule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//	String data = "(querySchedule (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#cloudSource\" \"http://www.arbi.com/ontologies/NotifyMeeting.owl#GoogleCalendar_001\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#description\" \"Monthly meething on November\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" \"http://www.w3.org/2002/07/owl#NamedIndividual\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#place\" \"ConferenceRoom01\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#endAt\" \"2016-11-04 18:00\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#beginAt\" \"2016-11-04 15:00\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#summary\" \"Meeting\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" \"http://www.arbi.com/ontologies/arbi.owl#Schedule\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#relatedMaterial\" \"http://www.arbi.com/ontologies/arbi-map.owl#Document_001\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#attendee\" \"Sung Kee Park\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#attendee\" \"Kyeon Mo Yang\"))";
		String data = "(rule (fact (arbi:speechContents $SpeechPerception $Contens)) --> (notify (arbi:speechContents $SpeechPerception $Contens)) (id 1))";
		GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		Expression e1 = gl.getExpression(0);
		 e1 = gl.getExpression(2);
		 GeneralizedList temp2 = e1.asGeneralizedList();
		 Expression e2 = temp2.getExpression(0);
		 String predicate = e2.asGeneralizedList().getName();
		 System.out.println(predicate);



		
		
		/*

			Expression e1 = gl.getExpression(0);
			GeneralizedList temp = e1.asGeneralizedList();
			String S = temp.getExpression(0).toString().replace('"', '\'');
			
			Expression e2 = gl.getExpression(1);
			GeneralizedList temp2 = e2.asGeneralizedList();
			String O = temp2.getExpression(0).toString().replace('"', '\'');
	

		
			System.out.println("S : " + S);
			System.out.println("O : " + O);


*/

	}

}
