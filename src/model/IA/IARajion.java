package model.IA;

import model.Agent.Agent;
import model.BomberManGame;
import model.utilsIA.ActionPoids;
import model.utilsIA.ListActionPoids;
import model.utilsIA.Position;
import model.utils.AgentAction;

import java.util.ArrayList;
import java.util.Random;

import static model.utilsIA.Research.getBomb;
import static model.utilsIA.Research.searchEnnemi2;

public class IARajion implements IAStrategi {

	@Override
	public void Action(Agent a, BomberManGame game) {
		AgentAction res;
		ListActionPoids listAction=new ListActionPoids();
		ArrayList<ActionPoids> bestActions=new ListActionPoids();
		ArrayList<Position> positionObject=new ArrayList<>();
		positionObject=getBomb(a,game);
		listAction.cheminPossible(a,game);
		listAction.eviterObjects(a,game,positionObject);
		if(a.getAgentG().isInvincible()) {
			Agent agentMove=searchEnnemi2(a, game);
			listAction.moveto(a, game, agentMove.getX(), agentMove.getY());
		}
		if(listAction.size()==0)res=AgentAction.STOP;
		else {
			bestActions=listAction.getBestActions();
			if(bestActions.size()==1)res=bestActions.get(0).getAction();
			else {
				int nbAlea=new Random().nextInt(bestActions.size());
				System.out.println("size "+bestActions.size()+" : choix "+nbAlea);
				res=bestActions.get(nbAlea).getAction();
			}
		}
		System.out.println("list "+bestActions);
		listAction.clear();
		System.out.println(a.getType()+" x"+a.getX()+" y"+a.getY()+"  :"+res);
		if(res!=AgentAction.PUT_BOMB)game.moveAgent(a, res);
		else game.putBomb(a);

	}

}
