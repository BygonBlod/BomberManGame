package model;

import utils.AgentAction;
import utils.ColorAgent;
import utils.InfoAgent;

public abstract class Agent {
	private int posX;
	private int posY;
	private InfoAgent agentG;
	private int lvlBomb;
	private int tpsInvincible;
	private int tpsSick;
	
	public Agent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick) {
		this.posX=x;
		this.posY=y;
		this.lvlBomb=1;
		this.tpsInvincible=0;
		this.tpsSick=0;
		agentG=new InfoAgent(x,y,agentAction,type,color,isInvincible,isSick);
	}

	
	public int getTpsInvincible() {
		return tpsInvincible;
	}


	public void setTpsInvincible(int tps) {
		if(tps>0 && tps<1000) {
			this.tpsInvincible = tps;
			agentG.setInvincible(true);
		}
		else {
			this.tpsInvincible=0;
			agentG.setInvincible(false);
		}
	}


	public int getTpsSick() {
		return tpsSick;
	}


	public void setTpsSick(int tps) {
		if(tps>0 && tps<1000) {
			this.tpsSick = tps;
			agentG.setSick(true);
		}
		else {
			this.tpsSick=0;
			agentG.setSick(false);
		}
	}


	public int getLvlBomb() {
		return lvlBomb;
	}


	public void setLvlBomb(int lvlBomb) {
		this.lvlBomb = lvlBomb;
	}


	public int getX() {
		return posX;
	}

	public void setX(int x) {
		this.posX = x;
		this.agentG.setX(x);
	}

	public int getY() {
		return posY;
	}

	public void setY(int y) {
		this.posY = y;
		this.agentG.setY(y);
	}
		
	public char getType() {
		return agentG.getType();
	}
	
	public AgentAction getAgentAction() {
		return agentG.getAgentAction();
	}


	public void setAgentAction(AgentAction agentAction) {
		agentG.setAgentAction(agentAction);
	}
	public InfoAgent getAgentG() {
		return this.agentG;
	}
	
	

}
