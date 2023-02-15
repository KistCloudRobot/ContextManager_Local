package kgu.agent.demo.action;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jpl7.Query;

import kgu.agent.demo.actionArgument.LatestPerceptionArgument;

import kr.ac.uos.ai.arbi.agent.logger.ActionBody;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.GLParser;


public class LatestPerceptionAction implements ActionBody {


	int visualObjectPerceptionCount = 0;
	int visualRobotBodyPerceptionCount = 0;
	int visualRobotHandPerceptionCount = 0;
	int visualRobotLeftHandPerceptionCount = 0;
	int[] visualRobotLeftFingerPerceptionCount = new int[3];
	int visualRobotRightHandPerceptionCount = 0;
	int[] visualRobotRightFingerPerceptionCount = new int[3];
	int jointPerceptionCount = 0;

	int visualObjectPerceptionInterval = 0;
	int visualRobotBodyPerceptionInterval = 0;
	int visualRobotHandPerceptionInterval = 0;
	int visualRobotLeftHandPerceptionInterval = 0;
	int[] visualRobotLeftFingerPerceptionInterval = new int[3];
	int visualRobotRightHandPerceptionInterval = 0;
	int[] visualRobotLefRightFingerPerceptionInterval = new int[3];
	int jointPerceptionInterval = 0;

	int removeTime = 2;
	int removeInterval = 2;

	static List<String> oSubject = new ArrayList<String>();
	static List<String> oProperty = new ArrayList<String>();
	static List<String> oObject = new ArrayList<String>();
	static List<String> oGraph = new ArrayList<String>();
	static int[] oStatus = new int[1000];
	static int oInd;
	static List<String> oManager = new ArrayList<String>();

	static List<String> bSubject = new ArrayList<String>();
	static List<String> bProperty = new ArrayList<String>();
	static List<String> bObject = new ArrayList<String>();
	static List<String> bGraph = new ArrayList<String>();
	static int[] bStatus = new int[1000];
	static int bInd;
	static List<String> bManager = new ArrayList<String>();

	static List<String> hSubject = new ArrayList<String>();
	static List<String> hProperty = new ArrayList<String>();
	static List<String> hObject = new ArrayList<String>();
	static List<String> hGraph = new ArrayList<String>();
	static int[] hStatus = new int[1000];
	static int hInd;
	static List<String> hManager = new ArrayList<String>();

	static int[][] oIdCount = new int[50][2];
	static int oIdInd;

	static int[][] bIdCount = new int[50][2];
	static int bIdInd;

	static int[][] hIdCount = new int[50][2];
	static int hIdInd;

	String assertString = "";
	String retractString = "";
	String t;
	String data;
	String perceptionType;
	String contents;

	public LatestPerceptionAction() {
		
	}

	@Override
	public Object execute(Object o) {
		//System.out.println("LatestPeception Execute");
		
		LatestPerceptionArgument Log = (LatestPerceptionArgument) o;
	

		data = Log.getPerceptionGl();

		perceptionType = "";
		contents = "";

		GLParser parser = new GLParser();

		GeneralizedList gl = null; // 입력 값 GL
		try {

			gl = parser.parseGL(data); // GL
			gl = gl.getExpression(0).asGeneralizedList();


		} catch (Exception ex) {
			ex.getStackTrace();
//			System.out.println("Request format error. GL Fomat wrong");
		}
		
		//2022.07.11 CM - KGU Demo Code

		if (gl.getName().equals("RobotJointStates")) {
			if (gl.getExpressionsSize() == 0)
				return null;

//			gl.getExpression(1).asGeneralizedList().getExpression(2)
//			Sample Code
			int time = 0;
			time = (int) (System.currentTimeMillis() / 1000);
			GeneralizedList name_list = gl.getExpression(1).asGeneralizedList();
			GeneralizedList position_list = gl.getExpression(2).asGeneralizedList();
			GeneralizedList velocity_list = gl.getExpression(3).asGeneralizedList();
			GeneralizedList effort_list = gl.getExpression(4).asGeneralizedList();
			
			//"left_wheel_joint" "right_wheel_joint" "left_swivel_joint" "left_caster_joint"
			//"right_swivel_joint" "right_caster_joint" "lift_joint"
			String name1 = name_list.getExpression(0).asValue().stringValue();
			String name2 = name_list.getExpression(1).asValue().stringValue();
			String name3 = name_list.getExpression(2).asValue().stringValue();
			String name4 = name_list.getExpression(3).asValue().stringValue();
			String name5 = name_list.getExpression(4).asValue().stringValue();
			String name6 = name_list.getExpression(5).asValue().stringValue();
			String name7 = name_list.getExpression(6).asValue().stringValue();
			
			String joint_1 = position_list.getExpression(0).asValue().stringValue();
			String joint_2 = position_list.getExpression(1).asValue().stringValue();
			String joint_3 = position_list.getExpression(2).asValue().stringValue();
			String joint_4 = position_list.getExpression(3).asValue().stringValue();
			String joint_5 = position_list.getExpression(4).asValue().stringValue();
			String joint_6 = position_list.getExpression(5).asValue().stringValue();
			String joint_7 = position_list.getExpression(6).asValue().stringValue();

			String velocity_1 = velocity_list.getExpression(0).asValue().stringValue();
			String velocity_2 = velocity_list.getExpression(1).asValue().stringValue();
			String velocity_3 = velocity_list.getExpression(2).asValue().stringValue();
			String velocity_4 = velocity_list.getExpression(3).asValue().stringValue();
			String velocity_5 = velocity_list.getExpression(4).asValue().stringValue();
			String velocity_6 = velocity_list.getExpression(5).asValue().stringValue();
			String velocity_7 = velocity_list.getExpression(6).asValue().stringValue();
			

			jointPerceptionCount++;
			
			
			/////Lift joint Ontology : for "isLifting / isLowering"
			assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + (++jointPerceptionCount)
                    + "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#JointPerception'"
                    + " graspPerception";

              assertTriple(assertString);
              // startTime
              assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + jointPerceptionCount
                    + "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
                    + time + "'"
                    + " graspPerception";

              assertTriple(assertString);
           
              // objectActedOn
              assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + jointPerceptionCount
                    + "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
                    + name7 + "'"
                    + " graspPerception";

              assertTriple(assertString);
              // radius
              assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + jointPerceptionCount
                    + "' 'http://knowrob.org/kb/knowrob.owl#radius' literal(type('http://www.w3.org/2001/XMLSchema#double','"
                    + joint_7 + "'))"
                    + " graspPerception";

              assertTriple(assertString);
              
              // velocity
              assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + jointPerceptionCount
                    + "' 'http://knowrob.org/kb/knowrob.owl#velocity' literal(type('http://www.w3.org/2001/XMLSchema#double','"
                    + velocity_7 + "'))"
                    + " graspPerception";

              assertTriple(assertString);
              
			/////right wheel joint Ontology : for "isMoving"
			 assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + (++jointPerceptionCount)
                     + "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#JointPerception'"
                     + " graspPerception";

               assertTriple(assertString);
               // startTime
               assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + jointPerceptionCount
                     + "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
                     + time + "'"
                     + " graspPerception";

               assertTriple(assertString);
            
               // objectActedOn
               assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + jointPerceptionCount
                     + "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
                     + name2 + "'"
                     + " graspPerception";

               assertTriple(assertString);
               // radius
               assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + jointPerceptionCount
                     + "' 'http://knowrob.org/kb/knowrob.owl#radius' literal(type('http://www.w3.org/2001/XMLSchema#double','"
                     + joint_2 + "'))"
                     + " graspPerception";

               assertTriple(assertString);
               
               // velocity
               assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + jointPerceptionCount
                     + "' 'http://knowrob.org/kb/knowrob.owl#velocity' literal(type('http://www.w3.org/2001/XMLSchema#double','"
                     + velocity_2 + "'))"
                     + " graspPerception";

               assertTriple(assertString);
               int tmps = 5;
               if (jointPerceptionCount % (6 * tmps) == 0) { // message.getName().size()
   				for (int k = jointPerceptionInterval; k < jointPerceptionCount - tmps * 6; k++)
   					retractTriple("'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + k + "' A" + " B C");
   				jointPerceptionInterval = jointPerceptionCount - tmps * 6;
   				// sleep(5);
   			}
               // effort
//               assertString = "'http://www.arbi.com/ontologies/arbi.owl#jointPerception" + jointPerceptionCount
//                     + "' 'http://knowrob.org/kb/knowrob.owl#effort' literal(type('http://www.w3.org/2001/XMLSchema#double','"
//                     + effort2 + "'))"
//                     + " graspPerception j";
//
//               assertTriple(assertString);
               
			
			
///end of JointStates
		}
		
		   if (gl.getName().equals("robotPosition222")) {
		         if (gl.getExpressionsSize() == 0)
		            return null;
		    
		            String robot_name = gl.getExpression(0).asValue().stringValue();
		            String ID = "";
		    		int subVisualRobotBodyPerceptionCount = 0;
					int subVisualRobotHandPerceptionCount = 0;
		            int robot_id = -1 ;
		            int time = 0;
		            
		            if(robot_name.equals("AMR_LIFT1")){
		                ID = "AMR_Lift01";
		            }
		            else if(robot_name.equals("AMR_LIFT2")){
		            	ID = "AMR_Lift02";
		            }

		      
		         
		            //position x,y,z
		            String x = gl.getExpression(1).asValue().stringValue();
		            String y = gl.getExpression(2).asValue().stringValue();
		            

		      
		         
		            //rotation a,b,c,d
		          

					visualRobotBodyPerceptionCount++;
					subVisualRobotBodyPerceptionCount = bIdCount[bIdInd][1]++;

					time = (int) (System.currentTimeMillis() / 1000);

					assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
							+ visualRobotBodyPerceptionCount
							+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#VisualRobotBodyPerception'"
							+ " robotPerception";

					assertTriple(assertString);
					// startTime
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
							+ visualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
							+ time + "'" + " robotPerception";

					assertTriple(assertString);

					// objectActedOn
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
							+ visualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
							+ ID + "'" + " robotPerception";

					assertTriple(assertString);
					// eventOccursAt
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
							+ visualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#eventOccursAt' 'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
							+ ID + subVisualRobotBodyPerceptionCount + "'" + " robotPerception";

					assertTriple(assertString);

					// rotationMatrix3D
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#RotationMatrix3D'"
							+ " robotPerception";

					assertTriple(assertString);
					// x,y,z
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m03' literal(type('http://www.w3.org/2001/XMLSchema#double','"
							+ x + "'))" + " robotPerception";

					assertTriple(assertString);

					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m13' literal(type('http://www.w3.org/2001/XMLSchema#double','"
							+ y + "'))" + " robotPerception";

					assertTriple(assertString);

			

					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m23' literal(type('http://www.w3.org/2001/XMLSchema#double','"
							+ '0' + "'))" + " robotPerception";

					assertTriple(assertString);


					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m32' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
							+ " robotPerception";

					assertTriple(assertString);

	

					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m32' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
							+ " robotPerception";

					assertTriple(assertString);

					// sleep(20);

					/*
					 * 
					 * if(visualRobotBodyPerceptionCount%removeTime==0){ for(int
					 * i=visualRobotBodyPerceptionInterval;i<visualRobotBodyPerceptionCount-10;i++){
					 * retractTriple(
					 * "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"+i+"' A"
					 * +" B b");
					 * //retractTriple("'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
					 * + ID+i+"' A"+" B"); }
					 * if(visualRobotBodyPerceptionInterval<visualRobotBodyPerceptionCount-10)
					 * visualRobotBodyPerceptionInterval=visualRobotBodyPerceptionCount-10;
					 * 
					 * // sleep(5); }
					 * 
					 */

					if (bIdCount[bIdInd][1] % removeTime == 0) {
						for (int i = bIdCount[bIdInd][0]; i < bIdCount[bIdInd][1] - removeInterval; i++) {
							retractTriple("'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception" + i + "' A"
									+ " B C");

							retractTriple(
									"'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID + i + "' A" + " B C");
						}
						bIdCount[bIdInd][0] = bIdCount[bIdInd][1] - removeInterval;
						// sleep(5);
					}
		            

		      }
		   
	         else if (gl.getName().equals("robotDegree")) {      
	    
	             if (gl.getExpressionsSize() == 0)
	             return null;
	             
	              String robot_name = gl.getExpression(0).asValue().stringValue();
	              String ID = "";
	              int subVisualRobotBodyPerceptionCount = 0;
	              int robot_id = -1 ;
	              int time = 0;
	                
	             if(robot_name.equals("AMR_LIFT1")){
	                 ID = "AMR_Lift01";
	             }
	             else if(robot_name.equals("AMR_LIFT2")){
	                ID = "AMR_Lift02";
	             }                  

	            String direction = gl.getExpression(1).asValue().stringValue();
	            String x = "0";
	            String y = "0";
	        
	      
	          visualRobotBodyPerceptionCount++;
	          subVisualRobotBodyPerceptionCount = bIdCount[bIdInd][1]++;
	          time = (int) (System.currentTimeMillis() / 1000);
	//
	          assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
	                + visualRobotBodyPerceptionCount
	                + "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#VisualRobotBodyPerception'"
	                + " robotPerception";
	//
	          assertTriple(assertString);
	          // startTime
	          assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
	                + visualRobotBodyPerceptionCount
	                + "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
	                + time + "'" + " robotPerception";

	          assertTriple(assertString);

	          // objectActedOn
	          assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
	                + visualRobotBodyPerceptionCount
	                + "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
	                + ID + "'" + " robotPerception";

	          assertTriple(assertString);
	          // eventOccursAt
	          assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
	                + visualRobotBodyPerceptionCount
	                + "' 'http://knowrob.org/kb/knowrob.owl#eventOccursAt' 'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
	                + ID + subVisualRobotBodyPerceptionCount + "'" + " robotPerception";

	          assertTriple(assertString);

	          // rotationMatrix3D
	          assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
	                + subVisualRobotBodyPerceptionCount
	                + "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#RotationMatrix3D'"
	                + " robotPerception";
	          assertTriple(assertString);
	          // x,y,z
	          assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
	                + subVisualRobotBodyPerceptionCount
	                + "' 'http://knowrob.org/kb/knowrob.owl#m12' literal(type('http://www.w3.org/2001/XMLSchema#double','"
	                + direction + "'))" + " robotPerception";

	          assertTriple(assertString);
	      	assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualRobotBodyPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#m03' literal(type('http://www.w3.org/2001/XMLSchema#double','"
					+ x + "'))" + " robotPerception";

			assertTriple(assertString);

			assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualRobotBodyPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#m13' literal(type('http://www.w3.org/2001/XMLSchema#double','"
					+ y + "'))" + " robotPerception";

			assertTriple(assertString);

	  

	          assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
	                + subVisualRobotBodyPerceptionCount
	                + "' 'http://knowrob.org/kb/knowrob.owl#m32' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
	                + " robotPerception";

	          assertTriple(assertString);

	         
	        //  System.out.println("COunt" + bIdCount[bIdInd][1] + " Remove Time : " + removeTime + "   " + bIdCount[bIdInd][1] % removeTime );
	          if (bIdCount[bIdInd][1] % removeTime == 0) {
	        	  //System.out.println("COunt" + bIdCount[bIdInd][1]);
	             for (int i = bIdCount[bIdInd][0]; i < bIdCount[bIdInd][1] - removeInterval; i++) {
	                retractTriple("'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception" + i + "' A"
	                      + " B C");
	                
	                retractTriple(
	                      "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID + i + "' A" + " B C");
	             }
	             bIdCount[bIdInd][0] = bIdCount[bIdInd][1] - removeInterval;
	             // sleep(5);
	          }
	             
	          
	          
	       }
	   
		   if (gl.getName().equals("AMR_LIFT01_pose")) {
			     
		         if (gl.getExpressionsSize() == 0)
	            return null;
		         
	            GeneralizedList AMR_LIFT01_pose = gl.getExpression(0).asGeneralizedList();
	            
	            String ID = "AMR_Lift01";
	    		int subVisualRobotBodyPerceptionCount = 0;
				int subVisualRobotHandPerceptionCount = 0;
	            int time = 0;
	            		         
	            GeneralizedList position_list = AMR_LIFT01_pose.getExpression(1).asGeneralizedList();
	            GeneralizedList rotation_list = AMR_LIFT01_pose.getExpression(2).asGeneralizedList();
       
	         String x = position_list.getExpression(0).asValue().stringValue();
	         String y = position_list.getExpression(1).asValue().stringValue();
	         String z = position_list.getExpression(2).asValue().stringValue();

	         String a = rotation_list.getExpression(0).asValue().stringValue();
	         String b = rotation_list.getExpression(1).asValue().stringValue();
	         String c = rotation_list.getExpression(2).asValue().stringValue();
	         String d = rotation_list.getExpression(3).asValue().stringValue();
				visualRobotBodyPerceptionCount++;
				subVisualRobotBodyPerceptionCount = bIdCount[bIdInd][1]++;

				time = (int) (System.currentTimeMillis() / 1000);
//
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
						+ visualRobotBodyPerceptionCount
						+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#VisualRobotBodyPerception'"
						+ " robotPerception";
//
				assertTriple(assertString);
				// startTime
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
						+ visualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
						+ time + "'" + " robotPerception";

				assertTriple(assertString);

				// objectActedOn
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
						+ visualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
						+ ID + "'" + " robotPerception";

				assertTriple(assertString);
				// eventOccursAt
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
						+ visualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#eventOccursAt' 'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
						+ ID + subVisualRobotBodyPerceptionCount + "'" + " robotPerception";

				assertTriple(assertString);

				// rotationMatrix3D
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualRobotBodyPerceptionCount
						+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#RotationMatrix3D'"
						+ " robotPerception";

				assertTriple(assertString);
				// x,y,z
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m03' literal(type('http://www.w3.org/2001/XMLSchema#double','"
						+ x + "'))" + " robotPerception";

				assertTriple(assertString);

				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m13' literal(type('http://www.w3.org/2001/XMLSchema#double','"
						+ y + "'))" + " robotPerception";

				assertTriple(assertString);

				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m23' literal(type('http://www.w3.org/2001/XMLSchema#double','"
						+ z + "'))" + " robotPerception";

				assertTriple(assertString);

//				// a,b,g
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m02' literal(type('http://www.w3.org/2001/XMLSchema#double','"
						+ a + "'))" + " robotPerception";

				assertTriple(assertString);

				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m12' literal(type('http://www.w3.org/2001/XMLSchema#double','"
						+ b + "'))" + " robotPerception";

				assertTriple(assertString);

				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m22' literal(type('http://www.w3.org/2001/XMLSchema#double','"
						+ c + "'))" + " robotPerception";

				assertTriple(assertString);

				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualRobotBodyPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m32' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
						+ " robotPerception";

				assertTriple(assertString);

			

				/*
				 * 
				 * if(visualRobotBodyPerceptionCount%removeTime==0){ for(int
				 * i=visualRobotBodyPerceptionInterval;i<visualRobotBodyPerceptionCount-10;i++){
				 * retractTriple(
				 * "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"+i+"' A"
				 * +" B b");
				 * //retractTriple("'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
				 * + ID+i+"' A"+" B"); }
				 * if(visualRobotBodyPerceptionInterval<visualRobotBodyPerceptionCount-10)
				 * visualRobotBodyPerceptionInterval=visualRobotBodyPerceptionCount-10;
				 * 
				 * // sleep(5); }
				 * 
				 */

				if (bIdCount[bIdInd][1] % removeTime == 0) {
					for (int i = bIdCount[bIdInd][0]; i < bIdCount[bIdInd][1] - removeInterval; i++) {
						retractTriple("'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception" + i + "' A"
								+ " B C");

						retractTriple(
								"'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID + i + "' A" + " B C");
					}
					bIdCount[bIdInd][0] = bIdCount[bIdInd][1] - removeInterval;
					// sleep(5);
				}
	            
	      }
	
		   if (gl.getName().equals("AMR_LIFT02_pose")) {
			     
			         if (gl.getExpressionsSize() == 0)
		            return null;
			         
		            GeneralizedList AMR_LIFT02_pose = gl.getExpression(0).asGeneralizedList();
		            
		            String ID = "AMR_Lift02";
		    		int subVisualRobotBodyPerceptionCount = 0;
					int subVisualRobotHandPerceptionCount = 0;
		            int time = 0;
		            		         
		            GeneralizedList position_list = AMR_LIFT02_pose.getExpression(1).asGeneralizedList();
		            GeneralizedList rotation_list = AMR_LIFT02_pose.getExpression(2).asGeneralizedList();
	         
		         String x = position_list.getExpression(0).asValue().stringValue();
		         String y = position_list.getExpression(1).asValue().stringValue();
		         String z = position_list.getExpression(2).asValue().stringValue();

		         String a = rotation_list.getExpression(0).asValue().stringValue();
		         String b = rotation_list.getExpression(1).asValue().stringValue();
		         String c = rotation_list.getExpression(2).asValue().stringValue();
		         String d = rotation_list.getExpression(3).asValue().stringValue();
					visualRobotBodyPerceptionCount++;
					subVisualRobotBodyPerceptionCount = bIdCount[bIdInd][1]++;

					time = (int) (System.currentTimeMillis() / 1000);
//
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
							+ visualRobotBodyPerceptionCount
							+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#VisualRobotBodyPerception'"
							+ " robotPerception";
//
					assertTriple(assertString);
					// startTime
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
							+ visualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
							+ time + "'" + " robotPerception";

					assertTriple(assertString);

					// objectActedOn
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
							+ visualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
							+ ID + "'" + " robotPerception";

					assertTriple(assertString);
					// eventOccursAt
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
							+ visualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#eventOccursAt' 'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
							+ ID + subVisualRobotBodyPerceptionCount + "'" + " robotPerception";

					assertTriple(assertString);

					// rotationMatrix3D
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#RotationMatrix3D'"
							+ " robotPerception";

					assertTriple(assertString);
					// x,y,z
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m03' literal(type('http://www.w3.org/2001/XMLSchema#double','"
							+ x + "'))" + " robotPerception";

					assertTriple(assertString);

					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m13' literal(type('http://www.w3.org/2001/XMLSchema#double','"
							+ y + "'))" + " robotPerception";

					assertTriple(assertString);

					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m23' literal(type('http://www.w3.org/2001/XMLSchema#double','"
							+ z + "'))" + " robotPerception";

					assertTriple(assertString);

//					// a,b,g
					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m02' literal(type('http://www.w3.org/2001/XMLSchema#double','"
							+ a + "'))" + " robotPerception";

					assertTriple(assertString);

					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m12' literal(type('http://www.w3.org/2001/XMLSchema#double','"
							+ b + "'))" + " robotPerception";

					assertTriple(assertString);

					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m22' literal(type('http://www.w3.org/2001/XMLSchema#double','"
							+ c + "'))" + " robotPerception";

					assertTriple(assertString);

					assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
							+ subVisualRobotBodyPerceptionCount
							+ "' 'http://knowrob.org/kb/knowrob.owl#m32' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
							+ " robotPerception";

					assertTriple(assertString);

				

					/*
					 * 
					 * if(visualRobotBodyPerceptionCount%removeTime==0){ for(int
					 * i=visualRobotBodyPerceptionInterval;i<visualRobotBodyPerceptionCount-10;i++){
					 * retractTriple(
					 * "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"+i+"' A"
					 * +" B b");
					 * //retractTriple("'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
					 * + ID+i+"' A"+" B"); }
					 * if(visualRobotBodyPerceptionInterval<visualRobotBodyPerceptionCount-10)
					 * visualRobotBodyPerceptionInterval=visualRobotBodyPerceptionCount-10;
					 * 
					 * // sleep(5); }
					 * 
					 */

					if (bIdCount[bIdInd][1] % removeTime == 0) {
						for (int i = bIdCount[bIdInd][0]; i < bIdCount[bIdInd][1] - removeInterval; i++) {
							retractTriple("'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception" + i + "' A"
									+ " B C");

							retractTriple(
									"'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID + i + "' A" + " B C");
						}
						bIdCount[bIdInd][0] = bIdCount[bIdInd][1] - removeInterval;
						// sleep(5);
					}
		            
		      }
		
		  		   
		   
		   if (gl.getName().equals("rackAt") ) {
				//System.out.println("RackAt send")		;	

				String IDN = gl.getExpression(0).asValue().stringValue();
				String x = gl.getExpression(1).asValue().stringValue();
				String y = gl.getExpression(2).asValue().stringValue();
								
			    int time = 0;
				int subVisualObjectPerceptionCount = 0;					
				String ID = "";
				
				if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_01")) {
					ID = "pallet_01";
					oIdInd = 0;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_02")) {
					ID = "pallet_02";
					oIdInd = 1;													
				}  else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_03")) {
					ID = "pallet_03";
					oIdInd = 2;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_04")) {
					ID = "pallet_04";
					oIdInd = 3;
				}  else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_05")) {
					ID = "pallet_05";
					oIdInd = 4;
		
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_06")) {
					ID = "pallet_06";
					oIdInd = 5;
		
				}else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_07")) {
					ID = "pallet_07";
					oIdInd = 6;
		
				}else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_08")) {
					ID = "pallet_08";
					oIdInd = 7;
		
				}else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_09")) {
					ID = "pallet_09";
					oIdInd = 8;
		
				}else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_10")) {
					ID = "pallet_10";
					oIdInd = 9;
		
				}else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#pallet_11")) {
					ID = "pallet_11";
					oIdInd = 10;
		
				}
				
				subVisualObjectPerceptionCount = oIdCount[oIdInd][1]++;
				visualObjectPerceptionCount = subVisualObjectPerceptionCount;

				time = (int) (System.currentTimeMillis() / 1000);// currentTimeMillis() : 1/1000 s return --> 0m 1s = 100
																	// --> 1s

//				retractString = "arbi:" + ID + " knowrob:widthOfObject A objectPerception";
//				retractTriple(retractString);
				

				assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_"
						+ visualObjectPerceptionCount
						+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#VisualObjectPerception'"
						+ " objectPerception";

				assertTriple(assertString);
				// startTime
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_"
						+ visualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
						+ time + "'" + " objectPerception";

				assertTriple(assertString);

				// objectActedOn
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_"
						+ visualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
						+ ID + "'" + " objectPerception";

				assertTriple(assertString);
				// eventOccursAt
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_"
						+ visualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#eventOccursAt' 'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
						+ ID + subVisualObjectPerceptionCount + "'" + " objectPerception";

				assertTriple(assertString);

				// rotationMatrix3D
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualObjectPerceptionCount
						+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#RotationMatrix3D'"
						+ " objectPerception";
				assertTriple(assertString);
				// x,y,z
				
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m03' literal(type('http://www.w3.org/2001/XMLSchema#double','"
						+ x + "'))" + " objectPerception";
				assertTriple(assertString);

				
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m13' literal(type('http://www.w3.org/2001/XMLSchema#double','"
						+ y + "'))" + " objectPerception";
				assertTriple(assertString);


				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m23' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
						+ " objectPerception";

				assertTriple(assertString);
				
				// a,b,g
				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m02' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
						+ " objectPerception";

				assertTriple(assertString);

				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m12' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
						+ " objectPerception";

				assertTriple(assertString);

				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m22' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
						+ " objectPerception";

				assertTriple(assertString);

				assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
						+ subVisualObjectPerceptionCount
						+ "' 'http://knowrob.org/kb/knowrob.owl#m32' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
						+ " objectPerception";

				assertTriple(assertString);
				if (oIdCount[oIdInd][1] % removeTime == 0) {
					for (int j = oIdCount[oIdInd][0]; j < oIdCount[oIdInd][1] - removeInterval; j++) {
						retractTriple("'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_" + j
								+ "' A" + " B C");

						retractTriple(
								"'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID + j + "' A" + " B C");
					}
					oIdCount[oIdInd][0] = oIdCount[oIdInd][1] - removeInterval;
				}
						
		
		}else if (gl.getName().equals("cargoAt") ) {
			//System.out.println("Cargoat send");

			String IDN = gl.getExpression(0).asValue().stringValue();
			String x = gl.getExpression(1).asValue().stringValue();
			String y = gl.getExpression(2).asValue().stringValue();
		
							
		    int time = 0;
			int subVisualObjectPerceptionCount = 0;					
			String ID = "";
			
			http://www.arbi.com/ontologies/arbi.owl#Bin_01
<<<<<<< HEAD
			if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_01")) {
				ID = "bin_01";
				oIdInd = 0;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_02")) {
				ID = "bin_02";
				oIdInd = 1;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_03")) {
				ID = "bin_03";
				oIdInd = 2;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_04")) {
				ID = "bin_04";
				oIdInd = 3;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_05")) {
				ID = "bin_05";
				oIdInd = 4;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_06")) {
				ID = "bin_06";
				oIdInd = 5;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_07")) {
				ID = "bin_07";
				oIdInd = 6;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_08")) {
				ID = "bin_08";
				oIdInd = 7;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_09")) {
				ID = "bin_09";
				oIdInd = 8;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_10")) {
				ID = "bin_10";
				oIdInd = 9;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_11")) {
				ID = "bin_11";
				oIdInd = 10;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_12")) {
				ID = "bin_12";
				oIdInd = 11;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_13")) {
				ID = "bin_13";
				oIdInd = 12;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_14")) {
				ID = "bin_14";
				oIdInd = 13;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_15")) {
				ID = "bin_15";
				oIdInd = 14;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_16")) {
				ID = "bin_16";
				oIdInd = 15;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_17")) {
				ID = "bin_17";
				oIdInd = 16;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_18")) {
				ID = "bin_18";
				oIdInd = 17;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_19")) {
				ID = "bin_19";
				oIdInd = 18;
			} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_20")) {
				ID = "bin_20";
				oIdInd = 19;
			}
=======
				if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_01")) {
					ID = "bin_01";
					oIdInd = 0;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_02")) {
					ID = "bin_02";
					oIdInd = 1;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_03")) {
					ID = "bin_03";
					oIdInd = 2;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_04")) {
					ID = "bin_04";
					oIdInd = 3;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_05")) {
					ID = "bin_05";
					oIdInd = 4;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_06")) {
					ID = "bin_06";
					oIdInd = 5;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_07")) {
					ID = "bin_07";
					oIdInd = 6;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_08")) {
					ID = "bin_08";
					oIdInd = 7;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_09")) {
					ID = "bin_09";
					oIdInd = 8;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_10")) {
					ID = "bin_10";
					oIdInd = 9;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_11")) {
					ID = "bin_11";
					oIdInd = 10;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_12")) {
					ID = "bin_12";
					oIdInd = 11;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_13")) {
					ID = "bin_13";
					oIdInd = 12;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_14")) {
					ID = "bin_14";
					oIdInd = 13;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_15")) {
					ID = "bin_15";
					oIdInd = 14;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_16")) {
					ID = "bin_16";
					oIdInd = 15;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_17")) {
					ID = "bin_17";
					oIdInd = 16;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_18")) {
					ID = "bin_18";
					oIdInd = 17;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_19")) {
					ID = "bin_19";
					oIdInd = 18;
				} else if (IDN.equals("http://www.arbi.com/ontologies/arbi.owl#bin_20")) {
					ID = "bin_20";
					oIdInd = 19;
				}
>>>>>>> refs/remotes/origin/ansung_demo
	
			subVisualObjectPerceptionCount = oIdCount[oIdInd][1]++;
			visualObjectPerceptionCount = subVisualObjectPerceptionCount;

			time = (int) (System.currentTimeMillis() / 1000);// currentTimeMillis() : 1/1000 s return --> 0m 1s = 100
																// --> 1s

//			retractString = "arbi:" + ID + " knowrob:widthOfObject A objectPerception";
//			retractTriple(retractString);
			

			assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_"
					+ visualObjectPerceptionCount
					+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#VisualObjectPerception'"
					+ " objectPerception";

			assertTriple(assertString);
			// startTime
			assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_"
					+ visualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
					+ time + "'" + " objectPerception";

			assertTriple(assertString);

			// objectActedOn
			assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_"
					+ visualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
					+ ID + "'" + " objectPerception";

			assertTriple(assertString);
			// eventOccursAt
			assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_"
					+ visualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#eventOccursAt' 'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
					+ ID + subVisualObjectPerceptionCount + "'" + " objectPerception";

			assertTriple(assertString);

			// rotationMatrix3D
			assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualObjectPerceptionCount
					+ "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#RotationMatrix3D'"
					+ " objectPerception";
			assertTriple(assertString);
			// x,y,z
			
			assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#m03' literal(type('http://www.w3.org/2001/XMLSchema#double','"
					+ x + "'))" + " objectPerception";
			assertTriple(assertString);

			
			assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#m13' literal(type('http://www.w3.org/2001/XMLSchema#double','"
					+ y + "'))" + " objectPerception";
			assertTriple(assertString);


			assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#m23' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
					+ " objectPerception";

			assertTriple(assertString);
			
			// a,b,g
			assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#m02' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
					+ " objectPerception";

			assertTriple(assertString);

			assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#m12' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
					+ " objectPerception";

			assertTriple(assertString);

			assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#m22' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
					+ " objectPerception";

			assertTriple(assertString);

			assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
					+ subVisualObjectPerceptionCount
					+ "' 'http://knowrob.org/kb/knowrob.owl#m32' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
					+ " objectPerception";

			assertTriple(assertString);
			if (oIdCount[oIdInd][1] % removeTime == 0) {
				for (int j = oIdCount[oIdInd][0]; j < oIdCount[oIdInd][1] - removeInterval; j++) {
					retractTriple("'http://www.arbi.com/ontologies/arbi.owl#visualObjectPerception" + "_" + ID + "_" + j
							+ "' A" + " B C");

					retractTriple(
							"'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID + j + "' A" + " B C");
				}
				oIdCount[oIdInd][0] = oIdCount[oIdInd][1] - removeInterval;
			}
		}
		return "Contents :" + contents + " PerceptionType :" + perceptionType;
	}
	
	

	public void assertTriple(String triple) {

		triple = triple.replace(" ", ",");
		Query.hasSolution("rdf_assert(" + triple + ")");
	}

	public void retractTriple(String triple) {
		triple = triple.replace(" ", ",");
	    Query.hasSolution("rdf_retractall(" + triple + ")");
	}

	public void updateTriple(String triple) {
		triple = triple.replace(" ", ",");
		Query.hasSolution("rdf_update(" + triple + ")");
	}

}
