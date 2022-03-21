package model.Agent;

import model.utils.AgentAction;
import model.utils.ColorAgent;

public class AgentBomberMan extends Agent {

	public AgentBomberMan(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible, boolean isSick,
			String d) {
		super(x, y, agentAction, 'B', color, isInvincible, isSick, d);
	}

}
