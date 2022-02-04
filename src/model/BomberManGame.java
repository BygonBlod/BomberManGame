package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import model.Agent.Agent;
import model.Agent.AgentBird;
import model.Agent.AgentBomberMan;
import model.Agent.AgentEnnemi;
import model.Agent.AgentRajion;
import model.IA.IABomberManManuel;
import model.IA.IARajion;
import model.IA.IARandom;
import model.IA.IAStrategi;
import model.IA.IAVol;
import model.utils.AgentAction;
import model.utils.InfoAgent;
import model.utils.InfoBomb;
import model.utils.InfoItem;
import model.utils.ItemType;
import model.utils.StateBomb;
import model.utils.Wall;

public class BomberManGame extends Game {
	private String plateau;
	private String end = "";
	private ArrayList<Agent> listBomberMan;
	private ArrayList<Agent> listEnnemi;
	private ArrayList<InfoBomb> listBomb;
	private ArrayList<InfoItem> listItem;
	private boolean walls[][];
	private boolean breakable_walls[][];
	private IAStrategi stratEnnemi;
	// public IAStrategi stratBomberman;
	public IABomberManManuel stratBomberman;
	private IAStrategi stratVol;
	private IAStrategi stratRajion;

	public BomberManGame(String p) {
		super(200);
		this.plateau = p;
		stratEnnemi = new IARandom();
		stratBomberman = new /* IABomberManAggro(); */IABomberManManuel();
		stratVol = new IAVol();
		stratRajion = new IARajion();
		this.initializeGame();
	}

	public BomberManGame() {
		super(200);
		listBomberMan = new ArrayList<Agent>();
		listEnnemi = new ArrayList<Agent>();
		listBomb = new ArrayList<InfoBomb>();
		listItem = new ArrayList<InfoItem>();
	}

	@Override
	protected void initializeGame() {
		try {
			listBomberMan = new ArrayList<Agent>();
			listEnnemi = new ArrayList<Agent>();
			listBomb = new ArrayList<InfoBomb>();
			listItem = new ArrayList<InfoItem>();
			InputMap input = new InputMap(plateau);
			walls = input.get_walls();
			breakable_walls = input.getStart_breakable_walls();
			ArrayList<InfoAgent> agents = input.getStart_agents();
			for (InfoAgent a : agents) {
				switch (a.getType()) {
				case 'B':
					listBomberMan.add(new AgentBomberMan(a.getX(), a.getY(), a.getAgentAction(), a.getColor(),
							a.isInvincible(), a.isSick()));
					break;
				case 'R':
					listEnnemi.add(new AgentRajion(a.getX(), a.getY(), a.getAgentAction(), a.getColor(),
							a.isInvincible(), a.isSick()));
					break;
				case 'V':
					listEnnemi.add(new AgentBird(a.getX(), a.getY(), a.getAgentAction(), a.getColor(), a.isInvincible(),
							a.isSick()));
					break;
				default:
					listEnnemi.add(new AgentEnnemi(a.getX(), a.getY(), a.getAgentAction(), a.getType(), a.getColor(),
							a.isInvincible(), a.isSick()));
					break;
				}
			}
			System.out.println(listBomberMan.size());
			System.out.println(listEnnemi.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void gameOver() {
		System.out.println("------ Fin du Jeu ------");
		if (listBomberMan.size() == 0)
			end = "YOU DIED";
		if (listEnnemi.size() == 0)
			end = "YOU WIN";
		this.thread.stop();

	}

	@Override
	protected boolean gameContinue() {
		Iterator<Agent> bomberIterator = listBomberMan.iterator();
		Iterator<Agent> ennemiIterator = listEnnemi.iterator();
		while (bomberIterator.hasNext()) {
			Agent bomber = (Agent) bomberIterator.next();
			while (ennemiIterator.hasNext()) {
				Agent ennemi = ennemiIterator.next();
				if ((bomber.getX() == ennemi.getX() && bomber.getY() == ennemi.getY())) {
					listBomberMan.remove(bomber);
				}
			}
		}
		if (listBomberMan.size() == 0)
			return false;
		if (listEnnemi.size() == 0)
			return false;
		return true;
	}

	@Override
	protected void takeTurn() {
		System.out.println("---------  Tour: " + turn + "  ---------");
		regleBomb();
		try {
			thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < listBomberMan.size(); i++) {
			Agent a = listBomberMan.get(i);
			if (i == 0) {
				stratBomberman.Action(a, this);
				stratBomberman.setAction(null);
			} else {
				stratEnnemi.Action(a, this);
			}
			if (a.getTpsInvincible() > 0)
				a.setTpsInvincible(a.getTpsInvincible() - 1);
			if (a.getTpsSick() > 0)
				a.setTpsSick(a.getTpsSick() - 1);
		}
		for (Agent a : listEnnemi) {
			switch (a.getType()) {
			case 'V':
				stratVol.Action(a, this);
				break;
			case 'E':
				stratRajion.Action(a, this);
				break;
			default:
				stratEnnemi.Action(a, this);
			}
			if (a.getTpsInvincible() > 0)
				a.setTpsInvincible(a.getTpsInvincible() - 1);
			if (a.getTpsSick() > 0)
				a.setTpsSick(a.getTpsSick() - 1);
		}
		regleItem();

	}

	private void regleBomb() {
		ArrayList<InfoBomb> remove = new ArrayList<InfoBomb>();
		for (InfoBomb bomb : listBomb) {
			switch (bomb.getStateBomb()) {
			case Step0:
				bomb.setStateBomb(StateBomb.Step1);
				break;
			case Step1:
				bomb.setStateBomb(StateBomb.Step2);
				break;
			case Step2:
				bomb.setStateBomb(StateBomb.Step3);
				break;
			case Step3:
				bomb.setStateBomb(StateBomb.Boom);
				break;
			case Boom:
				explosion(bomb);
				remove.add(bomb);
				break;
			}
		}
		listBomb.removeAll(remove);
	}

	public void explosion(InfoBomb bomb) {
		// System.out.println("explosion");
		ArrayList<Agent> removeBM = new ArrayList<Agent>();
		ArrayList<Agent> removeE = new ArrayList<Agent>();
		int range = bomb.getRange();
		for (int i = 1; i <= range; i++) {
			for (Agent b : listBomberMan) {
				if (b.getX() == bomb.getX() && b.getY() == bomb.getY() && !b.getAgentG().isInvincible())
					removeBM.add(b);
				if (b.getX() == bomb.getX() + i && b.getY() == bomb.getY() && !b.getAgentG().isInvincible())
					removeBM.add(b);
				if (b.getY() == bomb.getY() + i && b.getX() == bomb.getX() && !b.getAgentG().isInvincible())
					removeBM.add(b);
				if (b.getX() == bomb.getX() - i && b.getY() == bomb.getY() && !b.getAgentG().isInvincible())
					removeBM.add(b);
				if (b.getY() == bomb.getY() - i && b.getX() == bomb.getX() && !b.getAgentG().isInvincible())
					removeBM.add(b);
			}
			for (Agent e : listEnnemi) {
				if (e.getX() == bomb.getX() && e.getY() == bomb.getY() && !e.getAgentG().isInvincible())
					removeE.add(e);
				if (e.getX() == bomb.getX() + i && e.getY() == bomb.getY() && !e.getAgentG().isInvincible())
					removeE.add(e);
				if (e.getY() == bomb.getY() + i && e.getX() == bomb.getX() && !e.getAgentG().isInvincible())
					removeE.add(e);
				if (e.getX() == bomb.getX() - i && e.getY() == bomb.getY() && !e.getAgentG().isInvincible())
					removeE.add(e);
				if (e.getY() == bomb.getY() - i && e.getX() == bomb.getX() && !e.getAgentG().isInvincible())
					removeE.add(e);
			}
			if (breakable_walls[bomb.getX()][bomb.getY()] == true) {
				NewItem(bomb.getX(), bomb.getY());
				breakable_walls[bomb.getX()][bomb.getY()] = false;
				noBreakable(bomb.getX(), bomb.getY());
			}
			if (bomb.getX() + i < breakable_walls.length && breakable_walls[bomb.getX() + i][bomb.getY()] == true) {
				NewItem(bomb.getX() + i, bomb.getY());
				breakable_walls[bomb.getX() + i][bomb.getY()] = false;
				noBreakable(bomb.getX() + i, bomb.getY());
			}
			if (bomb.getY() + i < breakable_walls[0].length && breakable_walls[bomb.getX()][bomb.getY() + i] == true) {
				NewItem(bomb.getX(), bomb.getY() + i);
				breakable_walls[bomb.getX()][bomb.getY() + i] = false;
				noBreakable(bomb.getX(), bomb.getY() + i);
			}
			if (bomb.getX() - i > -1 && breakable_walls[bomb.getX() - i][bomb.getY()] == true) {
				NewItem(bomb.getX() - i, bomb.getY());
				breakable_walls[bomb.getX() - i][bomb.getY()] = false;
				noBreakable(bomb.getX() - i, bomb.getY());
			}
			if (bomb.getY() - i > -1 && breakable_walls[bomb.getX()][bomb.getY() - i] == true) {
				NewItem(bomb.getX(), bomb.getY() - i);
				breakable_walls[bomb.getX()][bomb.getY() - i] = false;
				noBreakable(bomb.getX(), bomb.getY() - i);
			}
		}
		listBomberMan.removeAll(removeBM);
		listEnnemi.removeAll(removeE);
	}

	public void NewItem(int x, int y) {
		int rand = new Random().nextInt(4);
		if (rand == 0) {
			int type = new Random().nextInt(4);
			ItemType type2 = null;
			switch (type) {
			case 0:
				type2 = ItemType.FIRE_DOWN;
				break;
			case 1:
				type2 = ItemType.FIRE_UP;
				break;
			case 2:
				type2 = ItemType.FIRE_SUIT;
				break;
			case 3:
				type2 = ItemType.SKULL;
				break;
			}
			listItem.add(new InfoItem(x, y, type2));
		}

	}

	public void regleItem() {
		ArrayList<InfoItem> remove = new ArrayList<InfoItem>();
		ArrayList<Agent> agent = new ArrayList<Agent>();
		agent.addAll(listBomberMan);
		agent.addAll(listEnnemi);
		for (InfoItem item : listItem) {
			for (Agent a : agent) {
				if (item.getX() == a.getX() && item.getY() == a.getY()) {
					if (item.getType() == ItemType.FIRE_DOWN && a.getLvlBomb() > 1) {
						// System.out.println("fire-down");
						a.setLvlBomb(a.getLvlBomb() - 1);
						remove.add(item);
					}
					if (item.getType() == ItemType.FIRE_UP && a.getLvlBomb() < 4) {
						// System.out.println("fire-up");
						a.setLvlBomb(a.getLvlBomb() + 1);
						remove.add(item);
					}
					if (item.getType() == ItemType.FIRE_SUIT && a.getTpsInvincible() == 0) {
						// System.out.println("fire-suit");
						a.setTpsInvincible(10);
						remove.add(item);
					}
					if (item.getType() == ItemType.SKULL && a.getTpsSick() == 0) {
						// System.out.println("skull");
						a.setTpsSick(10);
						remove.add(item);
					}

				}
			}
		}
		listItem.removeAll(remove);
	}

	public boolean IsLegalMove(Agent a, AgentAction ag) {
		if (ag.equals(AgentAction.MOVE_UP)) {
			if (walls[a.getX()][a.getY() - 1] == true)
				return false;
			else {
				if (isEnnemiOrInvicible(a.getX(), a.getY() - 1))
					return false;
				else {
					if (a.getType() == 'V')
						return true;
					else {
						if (breakable_walls[a.getX()][a.getY() - 1] == true)
							return false;

						else {
							return true;
						}
					}
				}
			}
		}
		if (ag.equals(AgentAction.MOVE_DOWN)) {
			if (walls[a.getX()][a.getY() + 1] == true)
				return false;
			else {
				if (isEnnemiOrInvicible(a.getX(), a.getY() + 1))
					return false;
				else {
					if (a.getType() == 'V')
						return true;
					else {
						if (breakable_walls[a.getX()][a.getY() + 1] == true)
							return false;
						else {
							return true;
						}
					}
				}
			}
		}
		if (ag.equals(AgentAction.MOVE_LEFT)) {
			if (walls[a.getX() - 1][a.getY()] == true)
				return false;
			else {
				if (isEnnemiOrInvicible(a.getX() - 1, a.getY()))
					return false;
				else {
					if (a.getType() == 'V')
						return true;
					else {
						if (breakable_walls[a.getX() - 1][a.getY()] == true)
							return false;

						else {
							return true;
						}
					}
				}
			}
		}
		if (ag.equals(AgentAction.MOVE_RIGHT)) {
			if (walls[a.getX() + 1][a.getY()] == true)
				return false;
			else {
				if (isEnnemiOrInvicible(a.getX() + 1, a.getY()))
					return false;
				else {
					if (a.getType() == 'V')
						return true;
					else {
						if (breakable_walls[a.getX() + 1][a.getY()] == true)
							return false;
						else {
							return true;
						}
					}
				}
			}
		}
		if (ag.equals(AgentAction.PUT_BOMB)) {
			if (a.getAgentG().isSick())
				return false;
			for (InfoBomb b : listBomb) {
				if (b.getX() == a.getX() && b.getY() == a.getY())
					return false;
			}
			return true;
		}
		return false;
	}

	public void moveAgent(Agent a, AgentAction ag) {
		if (IsLegalMove(a, ag)) {
			if (ag.equals(AgentAction.MOVE_UP)) {
				a.setY(a.getY() - 1);
				a.setAgentAction(ag);
			}
			if (ag.equals(AgentAction.MOVE_DOWN)) {
				a.setY(a.getY() + 1);
				a.setAgentAction(ag);
			}
			if (ag.equals(AgentAction.MOVE_RIGHT)) {
				a.setX(a.getX() + 1);
				a.setAgentAction(ag);
			}
			if (ag.equals(AgentAction.MOVE_LEFT)) {
				a.setX(a.getX() - 1);
				a.setAgentAction(ag);
			}
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

	public boolean IsLegalPutBomb(Agent a) {
		if (a.getAgentG().isSick())
			return false;
		for (InfoBomb b : listBomb) {
			if (b.getX() == a.getX() && b.getY() == a.getY())
				return false;
		}
		return true;
	}

	public void putBomb(Agent a) {
		if (IsLegalPutBomb(a))
			listBomb.add(new InfoBomb(a.getX(), a.getY(), a.getLvlBomb(), StateBomb.Step0));
	}

	public ArrayList<Agent> getListBomberMan() {
		return listBomberMan;
	}

	public void setListBomberMan(ArrayList<Agent> listBomberMan) {
		this.listBomberMan = listBomberMan;
	}

	public boolean isEnnemiOrInvicible(int x, int y) {
		for (Agent ennemi : listEnnemi) {
			if (ennemi.getX() == x && ennemi.getY() == y)
				return true;
		}
		for (Agent agent : listBomberMan) {
			if (agent.getTpsInvincible() > 0 && agent.getX() == x && agent.getY() == y)
				return true;
		}
		return false;
	}

	public void gameChange() {
		setChanged();
		notifyObservers();
	}

	public void changeBreakables(ArrayList<Wall> breakable) {
		for (Wall w : breakable) {
			breakable_walls[w.getX()][w.getY()] = false;
		}
	}

	public void noBreakable(int x, int y) {

	}

	public void addActionBomberMan(AgentAction action) {

	}

	public boolean[][] getWalls() {
		return walls;
	}

	public void setWalls(boolean[][] walls) {
		this.walls = walls;
	}

	public boolean[][] getBreakable_walls() {
		return breakable_walls;
	}

	public void setBreakable_walls(boolean[][] breakable_walls) {
		this.breakable_walls = breakable_walls;
	}

	public ArrayList<InfoBomb> getListBomb() {
		return listBomb;
	}

	public ArrayList<InfoItem> getListItem() {
		return listItem;
	}

	public void setListBomb(ArrayList<InfoBomb> listBomb) {
		this.listBomb = listBomb;
	}

	public void setListItem(ArrayList<InfoItem> listItem) {
		this.listItem = listItem;
	}

	public ArrayList<Agent> getListEnnemi() {
		return listEnnemi;
	}

	public String getEnd() {
		return end;
	}

	public void setListEnnemi(ArrayList<Agent> listEnnemi) {
		this.listEnnemi = listEnnemi;
	}

}
