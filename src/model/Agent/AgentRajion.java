package model.Agent;

import model.utils.AgentAction;
import model.utils.ColorAgent;

public class AgentRajion extends AgentEnnemi {

	public AgentRajion(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible,
			boolean isSick) {
		super(x, y, agentAction, 'R', color, isInvincible, isSick);
	}

}
