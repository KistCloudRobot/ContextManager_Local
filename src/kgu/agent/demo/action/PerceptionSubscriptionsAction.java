package kgu.agent.demo.action;

import kgu.agent.demo.actionArgument.PerceptionSubscriptionsArgument;
import kr.ac.uos.ai.arbi.agent.logger.ActionBody;



public class PerceptionSubscriptionsAction implements ActionBody {

	@Override
	public Object execute(Object o) {
		PerceptionSubscriptionsArgument Log = (PerceptionSubscriptionsArgument) o;
		return Log.getPerception();
	}

}
