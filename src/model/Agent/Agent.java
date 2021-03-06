package model.Agent;

import model.utils.AgentAction;
import model.utils.ColorAgent;
import model.utils.InfoAgent;

/**
 * 
 * @author Antonin class pour créer un agent basique avec toutes les
 *         informations dont ont a besoin pour l'utiliser dans le jeu
 *
 */
public abstract class Agent {
	private int posX;
	private int posY;
	private InfoAgent agentG;
	private int lvlBomb;
	private int tpsInvincible;
	private int tpsSick;
	private String id;

	public Agent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible,
			boolean isSick) {
		this.posX = x;
		this.posY = y;
		this.lvlBomb = 1;
		this.tpsInvincible = 0;
		this.tpsSick = 0;
		agentG = new InfoAgent(x, y, agentAction, type, color, isInvincible, isSick);
	}

	public Agent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible,
			boolean isSick, String s) {
		this.posX = x;
		this.posY = y;
		this.lvlBomb = 1;
		this.tpsInvincible = 0;
		this.tpsSick = 0;
		this.id = s;
		agentG = new InfoAgent(x, y, agentAction, type, color, isInvincible, isSick);
	}

	public void change(int x, int y, AgentAction action, int ti, int ts, int lvl) {
		this.posX = x;
		this.posY = y;
		this.setLvlBomb(lvl);
		this.setAgentAction(action);
		this.setTpsInvincible(ti);
		this.setTpsSick(ts);
	}

	public int getTpsInvincible() {
		return tpsInvincible;
	}

	public void setTpsInvincible(int tps) {
		if (tps > 0 && tps < 1000) {
			this.tpsInvincible = tps;
			agentG.setInvincible(true);
		} else {
			this.tpsInvincible = 0;
			agentG.setInvincible(false);
		}
	}

	public int getTpsSick() {
		return tpsSick;
	}

	public void setTpsSick(int tps) {
		if (tps > 0 && tps < 1000) {
			this.tpsSick = tps;
			agentG.setSick(true);
		} else {
			this.tpsSick = 0;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * méthode utiliser pour créer le json
	 */
	public String toString() {
		return "{\"type\":\"" + this.getType() + "\",\"x\":" + this.getX() + ",\"y\":" + this.getY() + ",\"lvlB\":"
				+ lvlBomb + ",\"ti\":" + tpsInvincible + ",\"ts\":" + tpsSick + ",\"action\":\"" + getAgentAction()
				+ "\"" + "\"id\":\"" + id + "\"}";
	}

}
