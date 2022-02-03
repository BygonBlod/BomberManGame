package Server;

import java.util.ArrayList;

import model.BomberManGame;
import model.Agent.Agent;
import model.utils.InfoBomb;
import model.utils.InfoItem;
import model.utils.Wall;

public class GameChange {

	ArrayList<Agent> listBomberMan;
	ArrayList<Agent> listEnnemi;
	ArrayList<InfoBomb> listBomb;
	ArrayList<InfoItem> listItem;
	ArrayList<Wall> listBreakable;
	boolean breakable_walls[][];
	private int x;
	private int y;

	public GameChange(int x, int y) {
		this.x = x;
		this.y = y;
		reset();
	}

	public GameChange() {
		reset();
	}

	public void reset() {
		listBomberMan = new ArrayList<Agent>();
		listEnnemi = new ArrayList<Agent>();
		listBomb = new ArrayList<InfoBomb>();
		listItem = new ArrayList<InfoItem>();
		listBreakable = new ArrayList<Wall>();
	}

	public void set(BomberManGame game) {
		listBomberMan = game.getListBomberMan();
		listEnnemi = game.getListEnnemi();
		listBomb = game.getListBomb();
		listItem = game.getListItem();
		System.out.println("agents: " + listBomberMan.toString());
		System.out.println("bombs: " + listBomb.toString());
		System.out.println("items: " + listItem.toString());
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

	public ArrayList<Wall> getBreakable_walls() {
		return listBreakable;
	}

}
