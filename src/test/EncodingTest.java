package test;

import org.apache.tika.parser.txt.CharsetDetector;

import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.ltm.DataSource;

public class EncodingTest extends ArbiAgent{
	private String MY_ADDRESS = "agent://www.arbi.com/ContextManager";
	private DataSource dataSource;
	
	private String brokerAddress = "172.16.165.158";
	private int port = 61316;
	private CharsetDetector detector;
	
	public EncodingTest() {
		detector = new CharsetDetector();
		dataSource = new DataSource() {
			@Override
			public void onNotify(String content) {
				System.out.println(content);
				detector.setText(content.getBytes());
				System.out.println("detect result :" + detector.detect() );
			}
		};
		ArbiAgentExecutor.execute(brokerAddress, port, MY_ADDRESS, this, BrokerType.ACTIVEMQ);
		dataSource.connect(brokerAddress, port, "ds://www.arbi.com/ContextManager", BrokerType.ACTIVEMQ);
		
		String subscribe = "(rule (fact (context $context)) --> (notify (context $context)))";
		dataSource.subscribe(subscribe);
	}
	
	public static void main(String[] args) {
		EncodingTest agent = new EncodingTest();
	}
}
