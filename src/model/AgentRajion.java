package model;

import utils.AgentAction;
import utils.ColorAgent;

public class AgentRajion extends AgentEnnemi {

	public AgentRajion(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible,
			boolean isSick) {
		super(x, y, agentAction, 'R', color, isInvincible, isSick);
	}

}
