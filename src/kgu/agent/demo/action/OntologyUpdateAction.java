package kgu.agent.demo.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpl7.Query;

import kgu.agent.demo.actionArgument.GLArgument;
import kgu.agent.demo.agent.ContextManager;
import kr.ac.uos.ai.arbi.agent.logger.action.ActionBody;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;


public class OntologyUpdateAction implements ActionBody{

	
	public static final String JMS_BROKER_URL = "tcp://127.0.0.1:61616";
	public static final String TM_ADDRESS = "agent://www.arbi.com/taskManager";
	public static final String KM_ADDRESS = "agent://www.arbi.com/knowledgeManager";
	public static final String DC_URL = "dc://testdcCM";
	private ContextManager cm;
	
	public OntologyUpdateAction(ContextManager cm) {
		this.cm = cm;
		//System.out.println("Ont action on");

	}
	

	@Override
	public Object execute(Object o) {
		System.out.println("Ont action do!");

		GLArgument Log = (GLArgument) o;
		String sendGL = Log.getGL();
		String sender = Log.getSender();
		GeneralizedList resultGL = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		String t = null;
		
		GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(sendGL);

		} catch (Exception ex) {
			ex.getStackTrace();
			System.out.println("Request format error. GL Fomat wrong");
		}
		
		int updateSize = 1;
		int[] Updateflag = new int[updateSize];
		
		if(gl.getName().equals("currentClass") && Updateflag[0] == 0 ) {
			
			String message = "(queryMultiRelation (tripleSet (triple $lecture \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" \"http://www.w3.org/2002/07/owl#NamedIndividual\") " 
				+	"(triple $lecture \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" \"http://www.robot-arbi.kr/ontologies/isro.owl#Lecture\") "
				+	"(triple $lecture \"http://www.robot-arbi.kr/ontologies/isro.owl#beginAt\" $beginTime) "
				+	"(triple $lecture \"http://www.robot-arbi.kr/ontologies/isro.owl#endAt\" $endTime) "
				+	"(triple $lecture \"http://www.robot-arbi.kr/ontologies/isro.owl#hasStudent\" $person) "
				+	"(triple $lecture \"http://www.robot-arbi.kr/ontologies/isro.owl#instructInSubject\" $subject) "
				+	"(triple $subject \"http://knowrob.org/kb/knowrob.owl#fullName\" $name)) $result)";

			
			//수정 필요부분 (Agent 호출, 메세지 형식)
			String result = cm.query(KM_ADDRESS, message);
			
			try {
				 resultGL = GLFactory.newGLFromGLString(result);
			} catch (ParseException e) {e.printStackTrace();}
			
			GeneralizedList subresultGL1 = resultGL.getExpression(1).asGeneralizedList();
			GeneralizedList subresultGL2 = subresultGL1.getExpression(0).asGeneralizedList();
			
			for(int i=0; i<subresultGL2.getExpressionsSize(); i++) {
				GeneralizedList subresultGL3 = subresultGL2.getExpression(i).asGeneralizedList();
				String query = "rdf_assert("
								+ subresultGL3.getExpression(0).asValue().stringValue() + ", "
								+ subresultGL3.getExpression(1).asValue().stringValue() + ", "
								+ subresultGL3.getExpression(2).asValue().stringValue() + ")";
				if(i== 2 || i==3) {
					String parseTime = subresultGL3.getExpression(2).asValue().stringValue();
					String date = parseTime.substring(-21, parseTime.length());
					String time = null;
					Date TimeValue;
					long TimeValueEpoch;
					
					System.out.println(date);
					
					try {
						time = date.substring(1, date.length()-1);
						TimeValue = sdf.parse(time);
						TimeValueEpoch = TimeValue.getTime() / 1000;
						time = "http://knowrob.org/kb/knowrob.owl#timepoint_" + TimeValueEpoch;
					} catch (java.text.ParseException e2) {}
					
					if(i ==2) {
						query = "rdf_assert("
								+ subresultGL3.getExpression(0).asValue().stringValue() + ", "
								+ "http://knowrob.org/kb/knowrob.owl#startTime ,"
								+ time + ")";
					}else if(i ==3) {
						query = "rdf_assert("
								+ subresultGL3.getExpression(0).asValue().stringValue() + ", "
								+ "http://knowrob.org/kb/knowrob.owl#endTime ,"
								+ time + ")";
					}
				}

				Query.hasSolution(query);
			}
			
			Updateflag[0] = 1;
		}
		
		
		
		
		
		return null;
	}

	
}
