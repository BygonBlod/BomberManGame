package model;

import utils.AgentAction;

public class ActionPoids {
	private AgentAction action;
	private int poids;
	
	public ActionPoids(AgentAction a,int p) {
		action=a;
		poids=p;
	}

	public AgentAction getAction() {
		return action;
	}

	public void setAction(AgentAction action) {
		this.action = action;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

}
