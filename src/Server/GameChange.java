package Server;

import java.util.ArrayList;

import model.BomberManGame;
import model.Agent.Agent;
import model.utils.InfoAgent;
import model.utils.InfoBomb;
import model.utils.InfoItem;

public class GameChange {

	ArrayList<Agent> listBomberMan;
	ArrayList<Agent> listEnnemi;
	ArrayList<InfoAgent> listAgents;
	ArrayList<InfoBomb> listBomb;
	ArrayList<InfoItem> listItem;
	boolean breakable_walls[][];
	private int x;
	private int y;

	public GameChange(int x, int y) {
		this.x = x;
		this.y = y;
		reset();
	}

	public void reset() {
		listBomberMan = new ArrayList<Agent>();
		listEnnemi = new ArrayList<Agent>();
		listAgents = new ArrayList<InfoAgent>();
		listBomb = new ArrayList<InfoBomb>();
		listItem = new ArrayList<InfoItem>();
		breakable_walls = new boolean[x][y];
	}

	public void set(BomberManGame game) {
		listAgents = game.getAgents();
		listBomb = game.getListBomb();
		listItem = game.getListItem();
		breakable_walls = game.getBreakable_walls();
	}

	public ArrayList<InfoAgent> getListAgents() {
		return listAgents;
	}

	public void setListAgents(ArrayList<InfoAgent> listAgents) {
		this.listAgents = listAgents;
	}

	public ArrayList<Agent> getListBomberMan() {
		return listBomberMan;
	}

	public void setListBomberMan(ArrayList<Agent> listBomberMan) {
		this.listBomberMan = listBomberMan;
	}

	public ArrayList<Agent> getListEnnemi() {
		return listEnnemi;
	}

	public void setListEnnemi(ArrayList<Agent> listEnnemi) {
		this.listEnnemi = listEnnemi;
	}

	public ArrayList<InfoBomb> getListBomb() {
		return listBomb;
	}

	public void setListBomb(ArrayList<InfoBomb> listBomb) {
		this.listBomb = listBomb;
	}

	public ArrayList<InfoItem> getListItem() {
		return listItem;
	}

	public void setListItem(ArrayList<InfoItem> listItem) {
		this.listItem = listItem;
	}

	public boolean[][] getBreakable_walls() {
		return breakable_walls;
	}

	public void setBreakable_walls(boolean[][] breakable_walls) {
		this.breakable_walls = breakable_walls;
	}

}
