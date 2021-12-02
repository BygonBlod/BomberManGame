package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import utils.AgentAction;
import utils.InfoBomb;

public class IARandom implements IAStrategi {

	@Override
	public void Action(Agent a,BomberManGame g) {
		int i=0;
		AgentAction res=null;
		ListActionPoids listAction=new ListActionPoids();
		listAction.cheminPossible(a,g);
		while(res==null) {			
			listAction.eviterBomb(a,g);
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
		}
		listAction.clear();
		System.out.println(a.getType()+" x"+a.getX()+" y"+a.getY()+"  :"+res);
		if(res!=AgentAction.PUT_BOMB)g.moveAgent(a, res);
		else g.putBomb(a);
	}
	

}
