package kgu.agent.demo.actionArgument;

import org.json.simple.JSONObject;

public class LatestPerceptionArgument {

	private String perceptionType;
	private String contents;
	private String perceptionGl;
	private int count = 0;
	private int batteryCount = 0;
	private int wheeldropCount = 0;
	private int cliffCount = 0;
	private int bumperCount = 0;
	private int speechCount = 0;
	private int HumanCount = 0;
	

	public LatestPerceptionArgument(String gl) {
		this.perceptionGl = gl;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		JSONObject obj = new JSONObject();

		obj.put("perceptionType", perceptionType);
		obj.put("contents", contents);

		return obj.toJSONString();
	}

	public String getPerceptionType() {
		return perceptionType;
	}

	public void setPerceptionType(String perceptionType) {
		this.perceptionType = perceptionType;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getPerceptionGl() {
		return perceptionGl;
	}

	public void setPerceptionGl(String perceptionGl) {
		this.perceptionGl = perceptionGl;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getBatteryCount() {
		return batteryCount;
	}

	public void setBatteryCount(int batteryCount) {
		this.batteryCount = batteryCount;
	}

	public int getWheeldropCount() {
		return wheeldropCount;
	}

	public void setWheeldropCount(int wheeldropCount) {
		this.wheeldropCount = wheeldropCount;
	}

	public int getCliffCount() {
		return cliffCount;
	}

	public void setCliffCount(int cliffCount) {
		this.cliffCount = cliffCount;
	}

	public int getBumperCount() {
		return bumperCount;
	}

	public void setBumperCount(int bumperCount) {
		this.bumperCount = bumperCount;
	}

	public int getSpeechCount() {
		return speechCount;
	}

	public void setSpeechCount(int speechCount) {
		this.speechCount = speechCount;
	}

	public int getHumanCount() {
		return HumanCount;
	}

	public void setHumanCount(int humanCount) {
		HumanCount = humanCount;
	}

}
