package model;

import java.util.ArrayList;
import java.util.Random;

import utils.AgentAction;

public class IABomberManAggro implements IAStrategi {

	@Override
	public void Action(Agent a, BomberManGame game) {
		AgentAction res;
		ListActionPoids listAction=new ListActionPoids();
		listAction.cheminPossible(a,game);		
		listAction.eviterBomb(a,game);
		if(a.getAgentG().isInvincible()) {
			Agent agentMove=listAction.searchEmmeni(a, game);
			listAction.moveto(a, game, agentMove.getX(), agentMove.getY());
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
