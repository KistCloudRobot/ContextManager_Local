package kgu.agent.demo.action;
import kgu.agent.demo.actionArgument.WorkingMemory_MonitorsArgument;
import kr.ac.uos.ai.arbi.agent.logger.ActionBody;


public class WorkingMemory_MonitorsAction implements ActionBody {

	@Override
	public Object execute(Object o) {
		WorkingMemory_MonitorsArgument Log = (WorkingMemory_MonitorsArgument) o;
		return Log.getMonitor();
	
	}

}
