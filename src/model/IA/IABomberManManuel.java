package model.IA;

import java.util.HashMap;
import java.util.List;

import model.BomberManGame;
import model.Agent.Agent;
import model.utils.AgentAction;

public class IABomberManManuel implements IAStrategi {
	private boolean joue = false;
	private HashMap<String, AgentAction> action = new HashMap<String, AgentAction>();

	@Override
	public void Action(Agent a, BomberManGame game) {
		if (action.size() == 0) {
			List<Agent> taille = game.getListBomberMan();
			for (int i = 0; i < taille.size(); i++) {
				action.put(taille.get(i).getId(), null);
			}
		}
		if (action.get(a.getId()) == null) {
			action.put(a.getId(), AgentAction.STOP);
		}
		if (action.get(a.getId()) != AgentAction.PUT_BOMB)
			game.moveAgent(a, action.get(a.getId()));
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

	public void setAction(AgentAction a, String place) {
		action.put(place, a);
	}

}
