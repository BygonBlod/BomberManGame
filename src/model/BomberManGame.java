package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import model.IAutils.Position;
import model.utils.AgentAction;
import model.utils.InfoAgent;
import model.utils.InfoBomb;
import model.utils.InfoItem;
import model.utils.ItemType;
import model.utils.StateBomb;

public class BomberManGame extends Game {
	private String plateau;
	private String end = "";
	private List<Agent> listBomberMan;
	private List<Agent> listEnnemi;
	private List<InfoBomb> listBomb;
	private List<InfoItem> listItem;
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
		stratBomberman = new IABomberManManuel();
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
	/**
	 * initialize les listes de bombermans, Ennemis, bombs, item. Initialize les
	 * tableaux des murs et des murs cassables
	 */
	protected void initializeGame() {
		try {
			listBomberMan = Collections.synchronizedList(new ArrayList<Agent>());
			listEnnemi = Collections.synchronizedList(new ArrayList<Agent>());
			listBomb = Collections.synchronizedList(new ArrayList<InfoBomb>());
			listItem = Collections.synchronizedList(new ArrayList<InfoItem>());
			InputMap input = new InputMap(plateau);
			walls = input.get_walls();
			breakable_walls = input.getStart_breakable_walls();
			ArrayList<InfoAgent> agents = input.getStart_agents();
			synchronized (listBomberMan) {
				synchronized (listEnnemi) {
					int i = 0;
					for (InfoAgent a : agents) {// créer un nouveau type d'agent selon sont type
						switch (a.getType()) {
						case 'B':
							listBomberMan.add(new AgentBomberMan(a.getX(), a.getY(), a.getAgentAction(), a.getColor(),
									a.isInvincible(), a.isSick(), i + ""));
							i++;
							break;
						case 'R':
							listEnnemi.add(new AgentRajion(a.getX(), a.getY(), a.getAgentAction(), a.getColor(),
									a.isInvincible(), a.isSick()));
							break;
						case 'V':
							listEnnemi.add(new AgentBird(a.getX(), a.getY(), a.getAgentAction(), a.getColor(),
									a.isInvincible(), a.isSick()));
							break;
						default:
							listEnnemi.add(new AgentEnnemi(a.getX(), a.getY(), a.getAgentAction(), a.getType(),
									a.getColor(), a.isInvincible(), a.isSick()));
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	/**
	 * change le string de fin de parti selon la fin qui s'est passé
	 */
	public void gameOver() {
		System.out.println("------ Fin du Jeu ------bis");
		if (listBomberMan.size() == 0)
			end = "YOU DIED";
		if (listEnnemi.size() == 0)
			end = "YOU WIN";
		if (this.turn == this.getMaxturn()) {
			end = "GAME FINISH";
		}

	}

	/**
	 * vérifie si les listes d'agent sont vide
	 */
	@Override
	public boolean gameContinue() {
		synchronized (listBomberMan) {
			synchronized (listEnnemi) {
				if (listBomberMan.size() == 0)
					return false;
				if (listEnnemi.size() == 0)
					return false;
				return true;
			}
		}
	}

	/**
	 * fais toute les actions dans un ordre chronologique d'un tour
	 */
	@Override
	protected void takeTurn() {
		System.out.println("---------  Tour: " + turn + "  ---------");
		regleBomb();// augmente d'un tour chaque bomb et fait exploser celle qui sont au dernier
		// stade
		regleEnnemi();// supprime les Bomberman qui sont sous les ennemis
		try {
			thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// fait les actions des bomberman puis celle des ennemis
		synchronized (listBomberMan) {
			for (int i = 0; i < listBomberMan.size(); i++) {
				Agent a = listBomberMan.get(i);
				// if (i == 0) {
				stratBomberman.Action(a, this);
				stratBomberman.setAction(null, a.getId());
				// } else {
				// stratEnnemi.Action(a, this);
				// }
				if (a.getTpsInvincible() > 0)
					a.setTpsInvincible(a.getTpsInvincible() - 1);
				if (a.getTpsSick() > 0)
					a.setTpsSick(a.getTpsSick() - 1);
			}
		}
		synchronized (listEnnemi) {
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
		}
		regleItem();// si un agent est sur une case item il le reçoit

	}

	/**
	 * supprime de la liste des bombermans ceux qui sont en dessous d'enemis
	 */
	public void regleEnnemi() {
		ArrayList<Agent> removeBM = new ArrayList<Agent>();
		synchronized (listBomberMan) {
			synchronized (listEnnemi) {
				for (Agent b : listBomberMan) {
					for (Agent e : listEnnemi) {
						if (b.getX() == e.getX() && b.getY() == e.getY()) {
							removeBM.add(b);
						}
					}
				}
				listBomberMan.removeAll(removeBM);
			}
		}
	}

	/**
	 * pour chaque bomb sont état est augmenter de 1 si il arrive à l'état Boom elle
	 * explose et est supprimer de la liste
	 */
	private void regleBomb() {
		ArrayList<InfoBomb> remove = new ArrayList<InfoBomb>();
		synchronized (listBomb) {
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
	}

	/**
	 * fait exploser la bomb selon sont rayon et supprimer les agents et les murs
	 * cassables sur sont chemin
	 * 
	 * @param bomb
	 */
	public void explosion(InfoBomb bomb) {
		ArrayList<Agent> removeBM = new ArrayList<Agent>();
		ArrayList<Agent> removeE = new ArrayList<Agent>();
		int range = bomb.getRange();
		synchronized (listBomberMan) {
			synchronized (listEnnemi) {
				for (int i = 1; i <= range; i++) {
					for (Agent b : listBomberMan) {// ajoute à une liste les bomberman qui meurt à cause de la bomb
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
					for (Agent e : listEnnemi) {// ajoute à une liste les ennemis qui meurt à cause de la bomb
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
					// supression des murs cassables si ils sont dans l'explosion

					if (bomb.getX() + i < breakable_walls.length
							&& breakable_walls[bomb.getX() + i][bomb.getY()] == true) {
						NewItem(bomb.getX() + i, bomb.getY());
						breakable_walls[bomb.getX() + i][bomb.getY()] = false;
						noBreakable(bomb.getX() + i, bomb.getY());
					}
					if (bomb.getY() + i < breakable_walls[0].length
							&& breakable_walls[bomb.getX()][bomb.getY() + i] == true) {
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
				if (breakable_walls[bomb.getX()][bomb.getY()] == true) {
					NewItem(bomb.getX(), bomb.getY());
					breakable_walls[bomb.getX()][bomb.getY()] = false;
					noBreakable(bomb.getX(), bomb.getY());
				}
				// suppression des agents qui ont explosés
				listBomberMan.removeAll(removeBM);
				listEnnemi.removeAll(removeE);
			}
		}
	}

	/**
	 * ajoute à la liste d'items un nouvelle item aléatoire avec une chance sur 4
	 * 
	 * @param x
	 * @param y
	 */
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
			synchronized (listItem) {
				listItem.add(new InfoItem(x, y, type2));
			}
		}

	}

	/**
	 * si un agent est sur un item il obtient sont malus ou sont bonus et est
	 * supprimer de la liste des items
	 */
	public void regleItem() {
		ArrayList<InfoItem> remove = new ArrayList<InfoItem>();
		ArrayList<Agent> agent = new ArrayList<Agent>();
		agent.addAll(listBomberMan);
		agent.addAll(listEnnemi);
		synchronized (listItem) {
			for (InfoItem item : listItem) {
				for (Agent a : agent) {
					if (item.getX() == a.getX() && item.getY() == a.getY()) {
						if (item.getType() == ItemType.FIRE_DOWN && a.getLvlBomb() > 1) {
							a.setLvlBomb(a.getLvlBomb() - 1);// obtient un niveau de bomb inférieur
							remove.add(item);
						}
						if (item.getType() == ItemType.FIRE_UP && a.getLvlBomb() < 4) {
							a.setLvlBomb(a.getLvlBomb() + 1);// obtient un niveau de bomb supérieur
							remove.add(item);
						}
						if (item.getType() == ItemType.FIRE_SUIT && a.getTpsInvincible() == 0) {
							a.setTpsInvincible(10);// deviens invincible pendant 10 tours
							remove.add(item);
						}
						if (item.getType() == ItemType.SKULL && a.getTpsSick() == 0) {
							a.setTpsSick(10);// deviens malade pendant 10 tours
							remove.add(item);
						}

					}
				}
			}
			listItem.removeAll(remove);// supprime les items utiliser
		}
	}

	/**
	 * retourne true si l'action est possible false dans le cas contraire
	 * 
	 * @param a
	 * @param ag
	 * @return
	 */
	public boolean IsLegalMove(Agent a, AgentAction ag) {
		// pour chaque actions donné ont va vérifier qu'il n'y ai oas de mur ou ennemi
		// ou d'agent invincible
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
			if (a.getAgentG().isSick())// si un agent est malade il ne peut poser de bomb
				return false;
			for (InfoBomb b : listBomb) {
				if (b.getX() == a.getX() && b.getY() == a.getY())
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * regarde si l'action est possible et si c'est le cas l'agent fait cette action
	 * 
	 * @param a
	 * @param ag
	 */
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

	/***
	 * retourne la liste de tout les Infoagents
	 * 
	 * @return
	 */
	public ArrayList<InfoAgent> getAgents() {
		ArrayList<InfoAgent> res = new ArrayList<InfoAgent>();
		synchronized (listBomberMan) {
			for (Agent a : listBomberMan)
				res.add(a.getAgentG());
		}
		synchronized (listEnnemi) {
			for (Agent a : listEnnemi)
				res.add(a.getAgentG());
		}
		return res;
	}

	/**
	 * regarde si il est possible de poser une bomb
	 * 
	 * @param a
	 * @return
	 */
	public boolean IsLegalPutBomb(Agent a) {
		if (a.getAgentG().isSick())
			return false;
		synchronized (listBomb) {
			for (InfoBomb b : listBomb) {
				if (b.getX() == a.getX() && b.getY() == a.getY())
					return false;
			}
		}
		return true;
	}

	/**
	 * pose une bomb à l'endroit où est l'agent
	 * 
	 * @param a
	 */
	public void putBomb(Agent a) {
		if (IsLegalPutBomb(a))
			listBomb.add(new InfoBomb(a.getX(), a.getY(), a.getLvlBomb(), StateBomb.Step0));
	}

	/**
	 * retourne true si il y a un ennemi ou un bomberman invincible
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isEnnemiOrInvicible(int x, int y) {
		synchronized (listEnnemi) {
			for (Agent ennemi : listEnnemi) {
				if (ennemi.getX() == x && ennemi.getY() == y)
					return true;
			}
		}
		synchronized (listBomberMan) {
			for (Agent agent : listBomberMan) {
				if (agent.getTpsInvincible() > 0 && agent.getX() == x && agent.getY() == y)
					return true;
			}
		}
		return false;
	}

	/**
	 * notifie tous les observateurs
	 */
	public void gameChange() {
		// Deprecated parce que c'est mieux
		setChanged();
		notifyObservers();
	}

	/**
	 * change les mur qui viennent d'être supprimer et qui sont dans lea liste
	 * 
	 * @param list
	 */
	public void changeBreakables(List<Position> list) {
		for (Position w : list) {
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

	public List<InfoBomb> getListBomb() {
		synchronized (listBomb) {
			return listBomb;
		}
	}

	public List<InfoItem> getListItem() {
		synchronized (listItem) {
			return listItem;
		}
	}

	public void setListBomb(List<InfoBomb> listBomb) {
		synchronized (listBomb) {
			this.listBomb = listBomb;
		}
	}

	public void setListItem(List<InfoItem> listItem) {
		synchronized (listItem) {
			this.listItem = listItem;
		}
	}

	public List<Agent> getListEnnemi() {
		synchronized (listEnnemi) {
			return listEnnemi;
		}
	}

	public String getEnd() {
		return end;
	}

	public void setListEnnemi(List<Agent> listEnnemi) {
		synchronized (listEnnemi) {
			this.listEnnemi = listEnnemi;
		}
	}

	public List<Agent> getListBomberMan() {
		synchronized (listBomberMan) {
			return listBomberMan;
		}
	}

	public void setListBomberMan(List<Agent> listBomberMan) {
		synchronized (listBomberMan) {
			this.listBomberMan = listBomberMan;
		}
	}

}