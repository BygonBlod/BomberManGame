package model.IA;

import java.util.ArrayList;
import java.util.Random;

import model.Agent.Agent;
import model.BomberManGame;
import model.utilsIA.ActionPoids;
import model.utilsIA.ListActionPoids;
import model.utilsIA.Position;
import model.utils.AgentAction;

public class IARandom implements IAStrategi {

	@Override
	public void Action(Agent a, BomberManGame g) {
		int i=0;
		AgentAction res=null;
		ListActionPoids listAction=new ListActionPoids();
		ArrayList<Position> positionObject=new ArrayList<>();
		listAction.cheminPossible(a,g);		
		listAction.eviterObjects(a,g,positionObject);
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
		if(res!=AgentAction.PUT_BOMB)g.moveAgent(a, res);
		else g.putBomb(a);
	}
	

}
