package model;

import utils.AgentAction;
import utils.ColorAgent;

public class AgentBird extends AgentEnnemi{
	private boolean contact=false;

	public AgentBird(int x, int y, AgentAction agentAction, ColorAgent color, boolean isInvincible,
			boolean isSick) {
		super(x, y, agentAction, 'V', color, isInvincible, isSick);
	}

	public boolean isContact() {
		return contact;
	}

	public void setContact(boolean contact) {
		this.contact = contact;
	}
	

}
