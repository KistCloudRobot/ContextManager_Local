package kgu.agent.demo.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpl7.Query;

import kgu.agent.demo.actionArgument.InitializeSemanticMapArgument;
import kgu.agent.demo.actionArgument.LatestPerceptionArgument;
import kgu.agent.demo.agent.ContextManager;
import kr.ac.uos.ai.arbi.agent.logger.action.ActionBody;
import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.GLParser;

public class InitializeSemanticMapAction implements ActionBody{

	@Override
	public Object execute(Object o) {
		
		InitializeSemanticMapArgument Log = (InitializeSemanticMapArgument) o;
		String sendGL = Log.getGL();
		String sender = Log.getSender();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(sendGL);

		} catch (Exception ex) {
			ex.getStackTrace();
			System.out.println("Request format error. GL Fomat wrong");
		}
		
		Expression[] e = new Expression[3];
		GeneralizedList[] GL = new GeneralizedList[3];
		
		int std_cnt = 0;
		int room_cnt = 0;
		String t = null;
		
		for(int i=0; i<3; i++) {
			e[i] = gl.getExpression(i);
			GL[i] = e[i].asGeneralizedList();

			System.out.println(GL[i]);

			if(GL[i].getName().equals("student")) {
				System.out.println("student case");
				String studentID = GL[i].getExpression(0).toString();
				String student_x_position = GL[i].getExpression(1).toString();
				String student_y_position = GL[i].getExpression(2).toString();
				String student_z_position = GL[i].getExpression(3).toString();
				String student_x_orientation = GL[i].getExpression(4).toString();
				String student_y_orientation = GL[i].getExpression(5).toString();
				String student_z_orientation = GL[i].getExpression(6).toString();
				String Date = GL[i].getExpression(7).toString();
				
				studentID = studentID.substring(1, studentID.length()-1);
				
				String time = null;
				Date startTime;
				long startTimeEpoch;
				
				try {
					time = Date.substring(1, Date.length()-1);
					startTime = sdf.parse(time);
					startTimeEpoch = startTime.getTime() / 1000;
					time = "http://knowrob.org/kb/knowrob.owl#timepoint_" + startTimeEpoch;
				} catch (java.text.ParseException e2) {}
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#" + studentID +
						"', 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://knowrob.org/kb/knowrob.owl#Person')";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#visualPerception" + String.valueOf(std_cnt) +
						"', 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://knowrob.org/kb/knowrob.owl#VisualHumanPerception')";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#visualPerception" + String.valueOf(std_cnt) +
						"', 'http://knowrob.org/kb/knowrob.owl#startTime', '"+ time +"')";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#visualPerception" + String.valueOf(std_cnt) +
						"', 'http://knowrob.org/kb/knowrob.owl#objectActedOn', 'http://www.arbi.com/ontologies/arbi.owl#"+ studentID +"')";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#visualPerception" + String.valueOf(std_cnt) +
						"', 'http://knowrob.org/kb/knowrob.owl#eventOccursAt', 'http://www.arbi.com/ontologies/arbi.owl#rotMat3D_"+ studentID + "_" + String.valueOf(std_cnt)+"')";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#rotMat3D_" + studentID + "_" + String.valueOf(std_cnt) 
						+ "', 'http://knowrob.org/kb/knowrob.owl#m03', literal(type('http://www.w3.org/2001/XMLSchema#double',  '"
						+ student_x_position + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#rotMat3D_" + studentID + "_" + String.valueOf(std_cnt) 
						+ "', 'http://knowrob.org/kb/knowrob.owl#m13', literal(type('http://www.w3.org/2001/XMLSchema#double',  '"
						+ student_y_position + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#rotMat3D_" + studentID + "_" + String.valueOf(std_cnt) 
						+ "', 'http://knowrob.org/kb/knowrob.owl#m23', literal(type('http://www.w3.org/2001/XMLSchema#double',  '"
						+ student_z_position + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#rotMat3D_" + studentID + "_" + String.valueOf(std_cnt) 
						+ "', 'http://knowrob.org/kb/knowrob.owl#m02', literal(type('http://www.w3.org/2001/XMLSchema#double',  '"
						+ student_x_orientation + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#rotMat3D_" + studentID + "_" + String.valueOf(std_cnt) 
						+ "', 'http://knowrob.org/kb/knowrob.owl#m12', literal(type('http://www.w3.org/2001/XMLSchema#double',  '"
						+ student_y_orientation + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#rotMat3D_" + studentID + "_" + String.valueOf(std_cnt) 
						+ "', 'http://knowrob.org/kb/knowrob.owl#m22', literal(type('http://www.w3.org/2001/XMLSchema#double',  '"
						+ student_z_orientation + "')))";
				System.out.println(t);
				Query.hasSolution(t); 

				std_cnt = std_cnt + 1 ;
				
			}else if(GL[i].getName().equals("classroom")) {
				System.out.println("classroom case");
				
				String classroomID = GL[i].getExpression(0).toString();
				String room_center_x = GL[i].getExpression(1).toString();
				String room_center_y = GL[i].getExpression(2).toString();
				String room_width = GL[i].getExpression(3).toString();
				String room_depth = GL[i].getExpression(4).toString();
				String room_height = GL[i].getExpression(5).toString();
				
				classroomID = classroomID.substring(1, classroomID.length()-1);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#" + classroomID +
						"', 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type', 'http://knowrob.org/kb/knowrob.owl#RoomInAConstruction')";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#" + classroomID +
						"', 'http://knowrob.org/kb/knowrob.owl#heightOfObject', literal(type('http://www.w3.org/2001/XMLSchema#double', '" + room_height + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#" + classroomID +
						"', 'http://knowrob.org/kb/knowrob.owl#depthOfObject', literal(type('http://www.w3.org/2001/XMLSchema#double', '" + room_depth + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#" + classroomID +
						"', 'http://knowrob.org/kb/knowrob.owl#widthOfObject', literal(type('http://www.w3.org/2001/XMLSchema#double', '" + room_width + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#" + classroomID +
						"', 'http://knowrob.org/kb/knowrob.owl#center', 'http://www.arbi.com/ontologies/arbi.owl#roomCenterPoint"+ String.valueOf(room_cnt) +"')";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#roomCenterPoint"+ String.valueOf(room_cnt) +
						"', 'http://knowrob.org/kb/knowrob.owl#xCoord', literal(type('http://www.w3.org/2001/XMLSchema#double', '" + room_center_x + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#roomCenterPoint"+ String.valueOf(room_cnt) +
						"', 'http://knowrob.org/kb/knowrob.owl#yCoord', literal(type('http://www.w3.org/2001/XMLSchema#double', '" + room_center_y + "')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				t = "rdf_assert('http://www.arbi.com/ontologies/arbi.owl#roomCenterPoint"+ String.valueOf(room_cnt) +
						"', 'http://knowrob.org/kb/knowrob.owl#xCoord', literal(type('http://www.w3.org/2001/XMLSchema#double', '0.0')))";
				System.out.println(t);
				Query.hasSolution(t);
				
				room_cnt = room_cnt + 1;
				
			}
		}
		
		
		
		
		return null;
	}

}
