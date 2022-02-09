package Server;

import java.util.ArrayList;
import java.util.List;

import model.BomberManGame;
import model.Agent.Agent;
import model.utils.InfoAgent;
import model.utils.InfoBomb;
import model.utils.InfoItem;
import model.utils.Wall;

public class GameChange {

	List<Agent> listBomberMan;
	List<Agent> listEnnemi;
	List<InfoBomb> listBomb;
	List<InfoItem> listItem;
	List<Wall> listBreakable;
	boolean breakable_walls[][];
	boolean walls[][];
	private String end;
	private int x;
	private int y;

	public GameChange(int x, int y) {
		this.x = x;
		this.y = y;
		this.end = "";
		this.breakable_walls = new boolean[x][y];
		this.walls = new boolean[x][y];
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

	public void set(GameChange game) {
		listBomberMan = game.getListBomberMan();
		listEnnemi = game.getListEnnemi();
		listBomb = game.getListBomb();
		listItem = game.getListItem();
		listBreakable = game.getListBreakable();
		for (Wall w : listBreakable) {
			breakable_walls[w.getX()][w.getY()] = false;
		}
		changeEnd();

	}

	private void changeEnd() {

		if (listBomberMan.size() == 0)
			end = "YOU DIED";
		if (listEnnemi.size() == 0)
			end = "YOU WIN";
		if (!end.contentEquals("")) {
			System.out.println("------ Fin du Jeu ------");
			System.out.println(end);
		}
	}

	public ArrayList<InfoAgent> getAgents() {
		ArrayList<InfoAgent> res = new ArrayList<InfoAgent>();
		for (Agent a : listBomberMan)
			res.add(a.getAgentG());

		for (Agent a : listEnnemi)
			res.add(a.getAgentG());

		return res;
	}

	public List<Agent> getListBomberMan() {
		return listBomberMan;
	}

	public void setListBomberMan(List<Agent> listBomberMan) {
		this.listBomberMan = listBomberMan;
	}

	public List<Agent> getListEnnemi() {
		return listEnnemi;
	}

	public void setListEnnemi(List<Agent> listEnnemi) {
		this.listEnnemi = listEnnemi;
	}

	public List<InfoBomb> getListBomb() {
		return listBomb;
	}

	public void setListBomb(List<InfoBomb> listBomb) {
		this.listBomb = listBomb;
	}

	public List<InfoItem> getListItem() {
		return listItem;
	}

	public void setListItem(List<InfoItem> listItem) {
		this.listItem = listItem;
	}

	public List<Wall> getListBreakable() {
		return listBreakable;
	}

	public void setListBreakable(List<Wall> res) {
		this.listBreakable = res;
	}

	public boolean[][] getBreakable_walls() {
		return breakable_walls;
	}

	public void setBreakable_walls(boolean[][] breakable_walls) {
		this.breakable_walls = breakable_walls;
	}

	public boolean[][] getWalls() {
		return walls;
	}

	public void setWalls(boolean[][] walls) {
		this.walls = walls;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
