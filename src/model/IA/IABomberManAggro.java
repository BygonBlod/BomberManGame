package model.IA;

import static model.IAutils.Research.getBomb;
import static model.IAutils.Research.searchEnnemi;

import java.util.ArrayList;
import java.util.Random;

import model.BomberManGame;
import model.Agent.Agent;
import model.IAutils.ActionPoids;
import model.IAutils.ListActionPoids;
import model.IAutils.Position;
import model.utils.AgentAction;

public class IABomberManAggro implements IAStrategi {

	@Override
	public void Action(Agent a, BomberManGame game) {
		AgentAction res;
		ListActionPoids listAction = new ListActionPoids();
		ArrayList<Position> positionObject = new ArrayList<>();
		positionObject = getBomb(a, game);
		listAction.cheminPossible(a, game);
		listAction.eviterObjects(a, game, positionObject, 10);
		if (a.getAgentG().isInvincible()) {
			Position agentMove = searchEnnemi(a, game);
			listAction.moveto(a, game, new Position(agentMove.getX(), agentMove.getY()), 5);
		}
		if (listAction.size() == 0)
			res = AgentAction.STOP;
		else {
			ArrayList<ActionPoids> bestActions = listAction.getBestActions();
			if (bestActions.size() == 1)
				res = bestActions.get(0).getAction();
			else {
				int nbAlea = new Random().nextInt(bestActions.size());
				res = bestActions.get(nbAlea).getAction();
			}
		}

		listAction.clear();
		if (res != AgentAction.PUT_BOMB)
			game.moveAgent(a, res);
		else
			game.putBomb(a);

	}

}
