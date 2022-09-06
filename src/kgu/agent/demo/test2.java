package kgu.agent.demo;


import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;

public class test2 extends ArbiAgent {
	
	public static void main( String argv[] ){
		new test2();

	}

	public test2() {
		executeAgent();
		System.out.println("start");

	}

	public void executeAgent() {
		ArbiAgentExecutor.execute("tcp://127.0.0.1:61616", "test2", this, Broker.ZEROMQ); //arbi 기반으로 CM시작 코드
		System.out.println("Agent Executed !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
	}

	public void onNotify(String sender, String notification) {
		System.out.println(notification);

	}

	@Override
	public String onSubscribe(String sender, String subscribeGL) {

		// dc.subscribe(rule);
		System.out.println("onSubscribe Start!!");
		System.out.println("subscribeGL: "+subscribeGL);
		System.out.println("Sender: "+ sender);
		
		return "hello";

	}

	public void onUnsubscribe(String sender, String data) {
		String id = "";
		String rule = "";
		String t;


	}

	@Override
	public void onData(String sender, String data) {
	}

	@Override
	public String onQuery(String sender, String queryGL) {
		// TODO Auto-generated method stub

		System.out.println("onQuery Start!!");
		System.out.println("Query: "+queryGL);
		System.out.println("Sender: "+ sender);
		String[] arr = queryGL.split("\\(|\\)|\"| ");
		/*for(int i = 0; i < arr.length; i++) {
			System.out.println(i + " : " + arr[i]);
		}*/
		
		
		return "hello!";		
	}

	@Override
	public String onRequest(String sender, String requestGL) {
		
		System.out.println("onRequest Start!!");
		System.out.println("requestGL: "+requestGL);
		System.out.println("Sender: "+ sender);
		
		GeneralizedList gl = null;
		try {
			gl = GLFactory.newGLFromGLString(requestGL);
			String glName = gl.getName();

		} catch (Exception ex) {
			ex.getStackTrace();
			System.out.println("Request format error. GL Fomat wrong");
		}
		return "hello";
	}

	public void onMonitor(String address, String S, String P, String O) {

		String fact = S + " " + P + " " + O;
		System.out.println();
		System.out.println("onMonitor Start!! ");
		System.out.println("subscriber = " + address);
		System.out.println("fact = " + S + " " + P + " " + O);
		System.out.println();

	}


	@Override
	public void onStream(String data) {

		
	}

	@Override
	public void onStart() {		
		
		String glString = "(add #(add 3 4))";
		String result = query("test", glString);
		System.out.println("res : " + result);
		
		
		

		DataSource dc = new DataSource() {
			boolean Subscripting_start = false;
			@Override
			public void onNotify(String data) {
				if (!Subscripting_start) {
					System.out.println("Subscripting_start");
					Subscripting_start = true;
				}
				System.out.println("Notification : " + data);
				
				GeneralizedList gl=null;
				try {
					gl = GLFactory.newGLFromGLString(data);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		};

		dc.connect("tcp://127.0.0.1:61616", "testDB", Broker.ZEROMQ);

		// dc.subscribe(rule);

		dc.subscribe(
				"(rule (fact (Robot_position $x $y $z $a $b $c $width $depth $height $speed $battery $id)) --> (notify (Robot_position $x $y $z $a $b $c $width $depth $height $speed $battery $id)))");
		
		
		dc.subscribe(
				" (rule (fact (Object_position $x $y $z $a $b $c $d $width $depth $height $id)) --> (notify (Object_position $x $y $z $a $b $c $d $width $depth $height $id)))");
		
		
		
		System.out.println("======Subscripting robot info======");
		System.out.println();
	}
	
	public void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (Exception e) {
		}
	}

}