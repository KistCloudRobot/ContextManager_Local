package test;

import kr.ac.uos.ai.arbi.model.Binding;
import kr.ac.uos.ai.arbi.model.BindingFactory;
import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;

public class kmTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String data = "(querySchedule (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#cloudSource\" \"http://www.arbi.com/ontologies/NotifyMeeting.owl#GoogleCalendar_001\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#description\" \"Monthly meething on November\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" \"http://www.w3.org/2002/07/owl#NamedIndividual\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#place\" \"ConferenceRoom01\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#endAt\" \"2016-11-04 18:00\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#beginAt\" \"2016-11-04 15:00\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#summary\" \"Meeting\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" \"http://www.arbi.com/ontologies/arbi.owl#Schedule\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#relatedMaterial\" \"http://www.arbi.com/ontologies/arbi-map.owl#Document_001\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#attendee\" \"Sung Kee Park\") (triple \"http://www.arbi.com/ontologies/NotifyMeeting.owl#Schedule_20161104_60o30d9nc4o30c1g60o30dr4co\" \"http://www.arbi.com/ontologies/arbi.owl#attendee\" \"Kyeon Mo Yang\"))";

		GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(gl.toString());
		System.out.println(gl.getExpressionsSize());

		for (int i = 0; i < gl.getExpressionsSize(); i++) {

			Expression e = gl.getExpression(i);

			GeneralizedList temp = e.asGeneralizedList();
			System.out.println(temp.toString());
			Binding binding = BindingFactory.newBinding();
			boolean Ov = false;
			boolean Sv = false;

			// String P = "'" + temp.getName() + "'";
			String S = temp.getExpression(0).toString().replace('"', '\'');
			String P = temp.getExpression(1).toString().replace('"', '\'');
			String O = temp.getExpression(2).toString().replace('"', '\'');

			System.out.println("P : " + P);
			System.out.println("S : " + S);
			System.out.println("O : " + O);

		}

	}


}
