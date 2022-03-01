package model.IA;

import java.util.ArrayList;
import java.util.List;

import model.BomberManGame;
import model.Agent.Agent;
import model.utils.AgentAction;

public class IABomberManManuel implements IAStrategi {
	private boolean joue = false;
	private ArrayList<AgentAction> action = new ArrayList<AgentAction>();

	@Override
	public void Action(Agent a, BomberManGame game) {
		if (action.size() == 0) {
			int taille = game.getListBomberMan().size();
			for (int i = 0; i < taille; i++) {
				action.add(null);
			}
		}
		int pl = placeAgent(a, game);
		if (action.get(pl) == null) {
			action.remove(pl);
			action.add(pl, AgentAction.STOP);
		}
		if (action.get(pl) != AgentAction.PUT_BOMB)
			game.moveAgent(a, action.get(pl));
		else
			game.putBomb(a);
		joue = false;

	}

	private int placeAgent(Agent a, BomberManGame g) {
		List<Agent> bomber = g.getListBomberMan();
		int i = 0;
		for (Agent agent : bomber) {
			if (agent == a) {
				return i;
			}
			i++;
		}
		return 0;
	}

	public void setAction(AgentAction a, int place) {
		action.remove(place);
		action.add(place, a);
	}

}
