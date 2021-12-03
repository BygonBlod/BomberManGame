package model.IA;

import java.util.ArrayList;
import java.util.Random;

import model.Agent.Agent;
import model.BomberManGame;
import model.utilsIA.ActionPoids;
import model.utilsIA.ListActionPoids;
import model.utilsIA.Position;
import model.utils.AgentAction;

import static model.utilsIA.Research.getBomb;
import static model.utilsIA.Research.searchEnnemi;

public class IABomberManAggro implements IAStrategi {

	@Override
	public void Action(Agent a, BomberManGame game) {
		AgentAction res;
		ListActionPoids listAction=new ListActionPoids();
		ArrayList<Position> positionObject=new ArrayList<>();
		positionObject=getBomb(a,game);
		listAction.cheminPossible(a,game);		
		listAction.eviterObjects(a,game,positionObject);
		if(a.getAgentG().isInvincible()) {
			Position agentMove= searchEnnemi(a, game);
			listAction.moveto(a, game,new Position(agentMove.getX(), agentMove.getY()));
		}
		if(listAction.size()==0)res=AgentAction.STOP;
		else {
			ArrayList<ActionPoids> bestActions=listAction.getBestActions();
			if(bestActions.size()==1)res=bestActions.get(0).getAction();
			else {
				int nbAlea=new Random().nextInt(bestActions.size());
				System.out.println("size "+bestActions.size()+" : choix "+nbAlea+" list : "+bestActions);
				res=bestActions.get(nbAlea).getAction();
			}
		}
		
		listAction.clear();
		System.out.println(a.getType()+" x"+a.getX()+" y"+a.getY()+"  :"+res);
		if(res!=AgentAction.PUT_BOMB)game.moveAgent(a, res);
		else game.putBomb(a);
		
	}
	
	

}
