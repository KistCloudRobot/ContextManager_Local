package test;

import kr.ac.uos.ai.arbi.model.Binding;
import kr.ac.uos.ai.arbi.model.Expression;

import static kgu.agent.demo.Configuration.*;

import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;

public class SpeackTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//	String data = "(querySchedule (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#cloudSource\" \"http://www.arbi.com/ontologies/NotifyMeeting.owl#GoogleCalendar_001\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#description\" \"Monthly meething on November\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" \"http://www.w3.org/2002/07/owl#NamedIndividual\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#place\" \"ConferenceRoom01\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#endAt\" \"2016-11-04 18:00\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#beginAt\" \"2016-11-04 15:00\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#summary\" \"Meeting\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" \"http://www.arbi.com/ontologies/arbi.owl#Schedule\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#relatedMaterial\" \"http://www.arbi.com/ontologies/arbi-map.owl#Document_001\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#attendee\" \"Sung Kee Park\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#attendee\" \"Kyeon Mo Yang\"))";
//		String data = "(Speech (time $time) (sentence $sentence))";
	//	String data = "(Speech (time \"1234\") (sentence \"hi\"))";
	//	String data = "(Robot_position (time $time) (position $pos1 $pos2) (angle $angle)))";
	//String data = "(Robot_position (time \"1234\") (position \"1\" \"2\") (angle \"5\")))";
	//	String data = "(Robot_battery (time \"1234\") (gauge \"1\"))";
	//	String data = "(Robot_wheeldrop (time \"1234\") (state \"1\"))";
//		String data = "(Robot_cliff (time \"1234\") (state \"1\"))";
	//	String data = "(Speech (time \"1234\") (sentence \"12\"))";
		//(Speech (UserID $UserID) (time $time) (sentence $sentence))
	//	String data = "(Speech (UserID \"1234\") (time \"1234\") (sentence \"1234\"))";
		String data = "(notify (Speech (userID \"Person001\") (time \"1234\") (sentence \"로봇지능 회의 떄문에 왔습니다\")))";
		GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Expression e1 = gl.getExpression(0);
		gl = e1.asGeneralizedList();
		
		
		Binding b = NOTIFY_ROBOT_SPEECH.unify(gl, null);
		String UserID = b.retrieve("$UserID").asValue().stringValue();
		String time = b.retrieve("$time").asValue().stringValue();
		String sentence = b.retrieve("$sentence").asValue().stringValue();
		//String y = b.retrieve("$y").asValue().stringValue();


		System.out.println("time :" + time );
		System.out.println("UserID :" + UserID );
		System.out.println("sentence :" + sentence );
	//	System.out.println("y :" + y );

		/*

			
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
