package kgu.agent.demo;

import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;

public class Configuration {
	

	
	public static GeneralizedList NOTIFY_ROBOT_POSITION;
	public static GeneralizedList NOTIFY_ROBOT_BATTERY;
	public static GeneralizedList NOTIFY_ROBOT_WHEELDROP;
	public static GeneralizedList NOTIFY_ROBOT_CLIFF;
	public static GeneralizedList NOTIFY_ROBOT_BUMPER;
	public static GeneralizedList NOTIFY_ROBOT_SPEECH;
	public static GeneralizedList NOTIFY_ROBOT_HUMAN_TRACKING;
	public static GeneralizedList REQUEST_HUMAN_RECOGNITION;

	
	
	
	static {
		try {

	/*		SUBSCRIBE_HUMAN_PERCEPTION = GLFactory.newGLFromGLString("(rule (fact (humanPro $name $type $emotion $m00 $m01 $m02 $m03 $m10 $m11 $m12 $m13 $m20 $m21 $m22 $m23 $m30 $m31 $m32 $m33)) --> (notify (humanPro $name $type $emotion $m00 $m01 $m02 $m03 $m10 $m11 $m12 $m13 $m20 $m21 $m22 $m23 $m30 $m31 $m32 $m33)))");

			NOTIFY_HUMAN_PERCEPTION = GLFactory.newGLFromGLString("(humanPro $name $type $emotion $m00 $m01 $m02 $m03 $m10 $m11 $m12 $m13 $m20 $m21 $m22 $m23 $m30 $m31 $m32 $m33)");
		*/

			
			NOTIFY_ROBOT_POSITION = GLFactory.newGLFromGLString("(Robot_position (time $time) (position $pos1 $pos2) (angle $angle))");
			NOTIFY_ROBOT_BATTERY = GLFactory.newGLFromGLString("(Robot_battery (time $time) (gauge $gauge))");
			NOTIFY_ROBOT_WHEELDROP = GLFactory.newGLFromGLString("(Robot_wheeldrop (time $time) (state $state))");
			NOTIFY_ROBOT_CLIFF = GLFactory.newGLFromGLString("(Robot_cliff (time $time) (state $state))");
			NOTIFY_ROBOT_BUMPER = GLFactory.newGLFromGLString("(Robot_bumper (time $time) (state $state))");
			NOTIFY_ROBOT_SPEECH = GLFactory.newGLFromGLString("(Speech (userID $userID) (time $time) (sentence $sentence))");
			NOTIFY_ROBOT_HUMAN_TRACKING = GLFactory.newGLFromGLString("(Human_tracking (time $time) (userID $userID) (physical_state $state) (position $x $y $z))");
			REQUEST_HUMAN_RECOGNITION = GLFactory.newGLFromGLString("(Human_recognition (time $time) (actionID $actionID) (name $name))");
		} catch (ParseException e) {
	
			e.printStackTrace();
		}
	}
	
	

}